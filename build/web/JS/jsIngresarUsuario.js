/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var user = localStorage.getItem('user');

if (user != null) {
    window.location = "usuario.html";
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
            localStorage.setItem("user", respuesta);
            window.location = "usuario.html";
            return;

        }
        document.getElementById("errorSesion").classList.add("show");

        return;
    });
    
    
}