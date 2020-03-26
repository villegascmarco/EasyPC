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

function ingresar() {

    var usuario = new Object();
    var persona = new Object();
    var json;

    persona.correo = document.getElementById("correoElectronico").value;
    persona.contrasenia = document.getElementById("contrasenia").value;

    usuario.persona = persona;

    json = JSON.stringify(usuario);

    $.ajax({
        type: 'POST',
        async: true,
        url: "api/usuario/ingresar/",
        data: {
            usuario: json
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert(JSON.stringify(data));

                return;
            }

            var respuesta = JSON.stringify(data);
            localStorage.setItem("usuario", respuesta);
            window.location = "usuario.html";
            return;

        }
        document.getElementById("errorSesion").classList.add("show");

        return;
    });


}