package com.node.easypc.comandos;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.node.easypc.baseDatos.BaseDatos;
import com.node.easypc.commons.Commons;
import com.node.easypc.modelo.Persona;
import com.node.easypc.modelo.Usuario;
import java.util.LinkedList;
import java.util.regex.Pattern;
import org.bson.Document;

/**
 *
 * @author logic
 */
public class ComandosUsuario {

    private BaseDatos baseDatos = new BaseDatos();
    
       public String iniciarSesionGoogle(Usuario usuario) {
        String respuesta = null;

        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_USUARIO);

        //persona.parametro porque estamos buscando desde otra coleccion diferente
        //de persona
        DBObject query = BasicDBObjectBuilder.start()
                .add("persona.correo", usuario.getPersona().getCorreo())
                .add("persona.estatus", 1)
                .get();

        DBCursor dBCursor = dBCollection.find(query);

        if (dBCursor.hasNext()) {
            respuesta = dBCursor.next().toString();
        }

        baseDatos.cerrarConexion();
        return respuesta;
    }

    public boolean crearUsuario(Usuario usuario) {
        boolean respuesta = true;

        try {

            DBCollection colUsuario = baseDatos.obtenerColeccion(Commons.COLECCION_USUARIO);

            baseDatos.insertarDocumento(colUsuario, usuario.toString());

        } catch (Exception e) {
            respuesta = false;
            System.out.println("\nExcepción:" + e.getMessage() + "\n");
        }
        baseDatos.cerrarConexion();
        return respuesta;
    }

    public String modificarDatos(Usuario usuario) {
        String respuesta = null;
        try {
            DBCollection colUsuario = baseDatos.obtenerColeccion(Commons.COLECCION_USUARIO);

            DBObject query = BasicDBObjectBuilder.start()
                    .add("persona.idPersona", usuario.getPersona().getIdPersona())
                    .get();

            colUsuario.update(query, usuario.toBObject());

            respuesta = usuario.toString();
        } catch (Exception e) {
            respuesta = null;
            System.out.println("\nExcepción:" + e.getMessage() + "\n");
        }
        baseDatos.cerrarConexion();
        return respuesta;
    }

    public boolean modificarEstatus(Usuario usuario) {
        boolean respuesta = true;
        String correo = usuario.getPersona().getCorreo();

        try {

            DBCollection colUsuario = baseDatos.obtenerColeccion(Commons.COLECCION_USUARIO);

            DBObject query = BasicDBObjectBuilder.start()
                    .add("persona.correo", correo)
                    .get();

            BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("persona.estatus", 0));

            colUsuario.update(query, set);
        } catch (Exception e) {
            respuesta = false;
            System.out.println("\nExcepción:" + e.getMessage() + "\n");
        }
        baseDatos.cerrarConexion();
        return respuesta;
    }

    public String buscarUsuarios(Usuario usuarioBuscado) {
        
        LinkedList<Usuario> usuarios = new LinkedList<>();

        try {
            DBCollection colUsuario = baseDatos.obtenerColeccion(Commons.COLECCION_USUARIO);

            Document query = new Document();

            if (usuarioBuscado.getIdUsuario() != null) {
                query.append("idUsuario", usuarioBuscado.getIdUsuario());
            }
            if (!usuarioBuscado.getPersona().getNombre().equals("")) {
                Document regex = new Document().append("$regex", "^(?)" + Pattern.quote(usuarioBuscado.getPersona().getNombre()));
                query.append("nombre", regex);
            }
            if (!usuarioBuscado.getPersona().getApellido().equals("")) {
                Document regex = new Document().append("$regex", "^(?)" + Pattern.quote(usuarioBuscado.getPersona().getApellido()));
                query.append("apellidoPaterno", regex);
            }
            if (!usuarioBuscado.getPersona().getCorreo().equals("")) {
                Document regex = new Document().append("$regex", "^(?)" + Pattern.quote(usuarioBuscado.getPersona().getCorreo()));
                query.append("correo", regex);
            }
            if (!usuarioBuscado.getPersona().getContrasenia().equals("")) {
                Document regex = new Document().append("$regex", "^(?)" + Pattern.quote(usuarioBuscado.getPersona().getContrasenia()));
                query.append("contrasenia", regex);
            }
            
            DBObject queryDBO = (DBObject) query;

            DBCursor dBCursor = colUsuario.find(queryDBO);

            while (dBCursor.hasNext()) {
                usuarios.add(new Usuario(dBCursor.next().toString()));
            }
            baseDatos.cerrarConexion();
            return new Gson().toJson(usuarios);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String listado() {
        LinkedList<Usuario> usuarios = new LinkedList<>();

        DBCollection colUsuario = baseDatos.obtenerColeccion(Commons.COLECCION_USUARIO);

        DBObject query = BasicDBObjectBuilder.start()
                .add("persona.estatus", 1)
                .get();

        DBCursor dBCursor = colUsuario.find(query);

        while (dBCursor.hasNext()) {
            usuarios.add(new Usuario(dBCursor.next().toString()));
        }
        baseDatos.cerrarConexion();
        return new Gson().toJson(usuarios);
    }

    public boolean cerrarSesion(Usuario usuario) {
        boolean respuesta = true;
        String correo = usuario.getPersona().getCorreo();

        try {
            DBCollection colUsuario = baseDatos.obtenerColeccion(Commons.COLECCION_USUARIO);

            DBObject query = BasicDBObjectBuilder.start()
                    .add("persona.correo", correo)
                    .get();

            BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("persona.token", ""));

            colUsuario.update(query, set);

        } catch (Exception e) {
            respuesta = false;
            System.out.println("\nExcepción:" + e.getMessage() + "\n");
        }
        baseDatos.cerrarConexion();
        return respuesta;
    }

}
