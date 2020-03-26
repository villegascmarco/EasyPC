var usuario = localStorage.getItem('usuario');

if (usuario != null) {
    if (JSON.stringify(usuario).indexOf("idAdministrador") >= 0) {
        window.location = "administrador.html";

    } else if (JSON.stringify(usuario).indexOf("idUsuario") >= 0) {
        window.location = "usuario.html";
    } else {
        alert("No es encontró coincidencias jsIngresar ln15");
        localStorage.removeItem('usuario');
    }
}

if (usuario != null) {
    if (JSON.stringify(usuario).indexOf("idAdministrador") >= 0) {
        window.location = "administrador.html";
    }
}

function registrarUsuario() {
    var usuario = new Object();
    var persona = new Object();

    persona.nombre = document.getElementById("registrarNombre").value;
    persona.apellido = document.getElementById("registrarApellido").value;
    persona.correo = document.getElementById("registrarCorreo").value;
    persona.contrasenia = document.getElementById("registrarContrasenia").value;
    persona.foto = document.getElementById("registrarFotoURL").value;

    usuario.persona = persona;

    var json = JSON.stringify(usuario);

    $.ajax({
        type: 'POST',
        async: 'true',
        url: "api/usuario/registrar/",
        data: {
            usuario: json
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert('Ocurrio un error');
                return;
            }
            var respuesta = JSON.stringify(data);
            if (respuesta.includes("idUsuario")) {

                localStorage.setItem("usuario", respuesta);
                alert("Nuevo usuario registrado!");
                window.location = "usuario.html";
            } else {
                alert(respuesta);
            }
            return;
        }
    });
}


