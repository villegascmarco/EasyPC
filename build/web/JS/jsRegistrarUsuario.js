/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var user = localStorage.getItem('user');

if (user != null) {
    window.location = "usuario.html";
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

                localStorage.setItem("user", respuesta);
                alert("Nuevo usuario registrado!");
                window.location = "usuario.html";
            } else {
                alert(respuesta);
            }
            return;
        }
    });
}


