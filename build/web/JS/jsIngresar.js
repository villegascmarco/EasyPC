/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var usuario = localStorage.getItem('usuario');

if (usuario != null) {
    window.location = "administrador.html";
}

function ingresar() {

    var datos = new Object();
    var persona = new Object();
    var json;

    persona.correo = document.getElementById("correoElectronico").value;
    persona.contrasenia = document.getElementById("contrasenia").value;

    datos.persona = persona;

    json = JSON.stringify(datos);

    $.ajax({
        type: 'POST',
        async: true,
        url: "api/administrador/ingresar",
        data: {
            administrador: json
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert(JSON.stringify(data));

                return;
            }

            var respuesta = JSON.stringify(data);
            localStorage.setItem("usuario", respuesta);
            window.location = "administrador.html";
            return;

        }
        document.getElementById("errorSesion").classList.add("show");

        return;
    });
}