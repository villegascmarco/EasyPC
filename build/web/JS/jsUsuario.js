/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var user = JSON.parse(localStorage.getItem('user'));
var persona;

if (user == null) {
    window.location = "ingresar_usuario.html";
}

persona = user.persona;

Object.keys(user).forEach(key => {

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
        document.getElementById("formIdUsuario").value = user[key];
    }

});

function cerrarSesionUsuario() {

    $.ajax({
        type: 'POST',
        async: true,
        url: "api/usuario/cerrarSesion/",
        data: {
            usuario: JSON.stringify(user)
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
                localStorage.removeItem("user");
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

    user.persona = persona;

    user.idUsuario = document.getElementById("formIdUsuario").value;

    var json = JSON.stringify(user);

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
            localStorage.setItem("user", JSON.stringify(data));
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
            usuario: JSON.stringify(user)
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert("Fallo eliminación. Consultar consola");
                console.log(data);
                return;
            }
            alert("Usuario dado de baja con exito");
            localStorage.removeItem("user");
            window.location.reload(false);
            return;
        } else {
            console.log(data);
            alert("Data nula. Consultar consola.");
            return;
        }
    });
}