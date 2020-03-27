var usuario = JSON.parse(localStorage.getItem('usuario'));
var persona;

if (usuario == null) {
    window.location = "ingresar_usuario.html";
} else {
    if (JSON.stringify(usuario).indexOf("idUsuario") < 0) {
        window.location = "administrador.html";
    }
}


persona = usuario.persona;

function establecerCampos() {
    Object.keys(usuario).forEach(key => {

        if (key == "persona") {
            Object.keys(persona).forEach(key => {
                switch (key) {
                    case "idPersona":
                        document.getElementById("formIdPersona").value = persona[key];
                        break;
                    case "foto":
                        if (persona[key] != "") {
                            document.getElementById("muestraFoto").src = persona[key];
                            document.getElementById("modificarFotoURL").value = persona[key];
                        }
                        break;
                    case "nombre":
                        document.getElementById("modificarNombre").value = persona[key];
                        break;
                    case "apellido":
                        document.getElementById("modificarApellido").value = persona[key];
                        break;
                    case "correo":
                        document.getElementById("modificarCorreo").value = persona[key];
                        break;
                    case "contrasenia":
                        document.getElementById("modificarContrasenia").value = persona[key];
                        break;
                    case "estatus":
                        document.getElementById("formEstatus").value = persona[key];
                        break;
                    case "token":
                        document.getElementById("formToken").value = persona[key];
                }
            });
        } else {
            document.getElementById("formIdUsuario").value = usuario[key];
        }

    });
}

function cerrarSesionUsuario() {

    $.ajax({
        type: 'POST',
        async: true,
        url: "api/usuario/cerrarSesion/",
        data: {
            usuario: JSON.stringify(usuario)
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert("Fallo cerrar sesión. Consulta consola");
                console.log(data);
                return;
            }
            var respuesta = JSON.stringify(data);
            if (respuesta.includes("Éxito")) {
                localStorage.removeItem("usuario");
                window.location = "ingresar_usuario.html";
                return;
            } else {

                alert("Fallo cerrar sesión. Consulta consola");
                console.log(data);
                return;
            }

        }
        console.log(data != null);
        alert("Data nula");
        return;
    });
}

function modificarInformacionUsuario() {

    persona.idPersona = document.getElementById("formIdPersona").value;
    persona.nombre = document.getElementById("modificarNombre").value;
    persona.apellido = document.getElementById("modificarApellido").value;
    persona.estatus = document.getElementById("formEstatus").value;
    persona.correo = document.getElementById("modificarCorreo").value;
    persona.contrasenia = document.getElementById("modificarContrasenia").value;
    persona.foto = document.getElementById("modificarFotoURL").value;
    persona.token = document.getElementById("formToken").value;

    usuario.persona = persona;

    usuario.idUsuario = document.getElementById("formIdUsuario").value;

    var json = JSON.stringify(usuario);

    $.ajax({
        type: "POST",
        async: true,
        url: "api/usuario/modificar/",
        data: {
            usuario: json
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert("Fallo modificación. Consultar consola");
                console.log(data);
                return;
            }
            localStorage.setItem("usuario", JSON.stringify(data));
            alert("Modificación exitosa");
            window.location.reload(false);
            return;
        }
        console.log(data);
        alert("Data nula");
        return;
    });
}

function eliminarUsuario() {
    $.ajax({
        type: "POST",
        async: true,
        url: "api/usuario/eliminar/",
        data: {
            usuario: JSON.stringify(usuario)
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert("Fallo eliminación. Consultar consola");
                console.log(data);
                return;
            }
            alert("Usuario dado de baja con exito");
            localStorage.removeItem("usuario");
            window.location.reload(false);
            return;
        } else {
            console.log(data);
            alert("Data nula. Consultar consola.");
            return;
        }
    });
}