package com.node.easypc.controlador;

import com.google.gson.Gson;
import com.node.easypc.comandos.ComandosUsuario;
import com.node.easypc.commons.Commons;
import com.node.easypc.modelo.Usuario;
import java.sql.Timestamp;

/**
 *
 * @author logic
 */
public class ControladorUsuario {

    private final ControladorPersona controladorPersona = new ControladorPersona();
    private final ComandosUsuario comandosUsuario = new ComandosUsuario();

    public String iniciarSesion(Usuario usuario) {
        String respuesta = controladorPersona.iniciarSesion(usuario.getPersona(), Commons.COLECCION_USUARIO);
        System.out.println(respuesta);

        if (respuesta != null) {
            Usuario aux = new Usuario(respuesta);

            if (aux.getIdUsuario() != null) {
                aux.getPersona().setToken();
                modificarUsuario(aux, true);
            }
            return aux.toString();

        } else {
            return null;
        }

    }

    /**
     * Registra una nuevo usuario siempre y cuando el correo no se haya usado
     * con anterioridad
     *
     * @param usuario El nuevo usuario a registrarse
     * @return Un String (JSON) con el usuario registrado, o null sí no pudo.
     */
    public String registrarUsuario(Usuario usuario) {

        try {
            if (!controladorPersona.validarAutenticidadCorreo(usuario.getPersona())) {
                return "Correo ya registrado";
            }

            String id = new Timestamp(System.currentTimeMillis()).getTime() + "";

            usuario.setIdUsuario(id);
            usuario.getPersona().setIdPersona(id);
            usuario.getPersona().setEstatus(1);

            usuario.getPersona().clearToken();

            if (controladorPersona.crearPersona(usuario.getPersona())) {
                if (comandosUsuario.crearUsuario(usuario)) {
                    return usuario.toString();
                } else {
                    System.out.println("crearUsuario devolvio falso");
                }
            } else {
                System.out.println("crearPersona devolvio falso");
            }
            return null;

        } catch (Exception e) {
            System.out.println("\nExcepción:" + e.getMessage() + "\n");
            return null;
        }

    }

    /**
     * Modifica los datos del usuario, más especificamente, su persona.
     *
     * @param usuario El usuario que se modificara
     * @param actualizarToken
     * @return null sí el correo nuevo ya esta en uso, sí el token no es valido,
     * o sí la modificación fallo, o un JSON con los nuevos datos sí la
     * modificación fue existosa
     */
    public String modificarUsuario(Usuario usuario, boolean actualizarToken) {
        if (controladorPersona.validarActualizacion(usuario.getPersona())) {
            return "Correo no disponible.";
        }

        if (!actualizarToken) {
            if (!controladorPersona.validarToken(usuario.getPersona())) {
                return "No tiene permisos para modifcar datos";
            }
        }

        if (controladorPersona.modificarDatos(usuario.getPersona())) {
            return comandosUsuario.modificarDatos(usuario);
        }
        return null;
    }

    /**
     * Modifica el estatus del usuario conforme este declarado en
     * <i>usuario</i>. Debe ser 0 o 1 para ser exitoso
     *
     * @param usuario El usuario con el nuevo estatus
     * @return Una cadena que indica sí la operación fue existosa, o fallo en
     * algun punto.
     */
    public String modificarEstatus(Usuario usuario) {

        if (!controladorPersona.validarToken(usuario.getPersona())) {
            return "No tiene permisos para modifcar datos";
        }

        if (!controladorPersona.validarEstatus(usuario.getPersona())) {
            return "Ingrese un estatus válido";
        }

        if (controladorPersona.modificarEstatus(usuario.getPersona())) {
            if (comandosUsuario.modificarEstatus(usuario)) {
                if (controladorPersona.cerrarSesion(usuario.getPersona())) {

                    if (comandosUsuario.cerrarSesion(usuario)) {
                        return "Éxito";
                    }
                }

            }
        }
        return "No se pudo dar de baja";
    }

    public String buscarUsuarios(Usuario usuario, Usuario usuarioBuscado) {

        if (!controladorPersona.validarToken(usuario.getPersona())) {
            return "Token inválido";
        }

        return comandosUsuario.buscarUsuarios(usuarioBuscado);
    }

    /**
     * Cierra la sesión del usuario
     *
     * @param usuario El usuario que quiere cerrar su sesión
     * @return Un JSON con "Éxito" sí logra cerrar sesión, o null sí no lo
     * logra.
     */
    public String cerrarSesion(Usuario usuario) {

        if (!controladorPersona.validarToken(usuario.getPersona())) {
            return null;
        }

        if (controladorPersona.cerrarSesion(usuario.getPersona())) {

            if (comandosUsuario.cerrarSesion(usuario)) {
                return new Gson().toJson("Éxito");
            }
        }

        return null;
    }

}
