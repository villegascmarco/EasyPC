/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var usuario = JSON.parse(localStorage.getItem('usuario'));
var persona;

if (usuario == null) {
    window.location = "index.html";
}

persona = usuario.persona;

Object.keys(usuario).forEach(key => {

    if (key == "persona") {

        Object.keys(persona).forEach(key => {

            if (key == "foto") {
                document.getElementById("miniatura").src = persona[key];
            } else if (key == "nombre") {
                document.getElementById("tituloBienvenida").innerHTML = "Hola, " + persona[key] + "!";
            }

            document.getElementById(key).value = persona[key];


        });

    } else {

        document.getElementById(key).value = usuario[key];

    }

});

function cerrarSesion() {



    $.ajax({
        type: 'POST',
        async: true,
        url: "api/administrador/cerrarSesion",
        data: {
            administrador: JSON.stringify(usuario)
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert('Problemas');
                return;
            }

            localStorage.removeItem('usuario');
            window.location = "index.html";



            return;

        }
        alert("No generado");

        return;
    });
}

function modificarInformacion() {

    var json;

    persona.idPersona = document.getElementById("idPersona").value;
    persona.nombre = document.getElementById("nombre").value;
    persona.apellido = document.getElementById("apellido").value;
    persona.estatus = document.getElementById("estatus").value;
    persona.correo = document.getElementById("correo").value;
    persona.contrasenia = document.getElementById("contrasenia").value;
    persona.foto = document.getElementById("foto").value;
    persona.token = document.getElementById("token").value;

    usuario.persona = persona;

    usuario.idAdministrador = document.getElementById("idAdministrador").value;
    usuario.telefono = document.getElementById("telefono").value;
    usuario.colonia = document.getElementById("colonia").value;
    usuario.calles = document.getElementById("calles").value;
    usuario.numero = document.getElementById("numero").value;
    usuario.cp = document.getElementById("cp").value;

    json = JSON.stringify(usuario);

    $.ajax({
        type: 'POST',
        async: true,
        url: "api/administrador/modificar",
        data: {
            administrador: json
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert('Problemas');
                return;
            }
            var respuesta = JSON.stringify(data);
            if (data.equals != "") {
                localStorage.setItem("usuario", respuesta);
                window.location.reload(false);
                return;
            }
            return;

        }
        alert("No generado");

        return;
    });
}

function agregarAdministrador() {
    let persona = new Object();
    let administrador = new Object();
    let json;

    persona.nombre = document.getElementById("nombreAgregar").value;
    persona.apellido = document.getElementById("apellidoAgregar").value;
    persona.correo = document.getElementById("correoAgregar").value;
    persona.contrasenia = document.getElementById("contraseniaAgregar").value;
    persona.foto = document.getElementById("fotoAgregar").value;


    administrador.persona = persona;

    administrador.telefono = document.getElementById("telefonoAgregar").value;
    administrador.colonia = document.getElementById("coloniaAgregar").value;
    administrador.calles = document.getElementById("callesAgregar").value;
    administrador.numero = document.getElementById("numeroAgregar").value;
    administrador.cp = document.getElementById("cpAgregar").value;

    json = JSON.stringify(administrador);

    $.ajax({
        type: 'POST',
        async: true,
        url: "api/administrador/crear",
        data: {
            administrador: json,
            viejo: JSON.stringify(usuario)
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert('Problemas');
                return;
            }
            var respuesta = JSON.stringify(data);
            if (respuesta.includes("idAdministrador")) {

                alert("Éxito");
            } else {
                alert(respuesta);
            }
            return;

        }
        alert("No generado");

        return;
    });
}

function modificarEstatus(estatus) {
    var correo;
    if (estatus == 0) {
        correo = document.getElementById("correoEliminar").value;
    } else {
        correo = document.getElementById("correoAlta").value;
    }
    $.ajax({
        type: 'POST',
        async: true,
        url: "api/administrador/modificarEstatus",
        data: {
            correo: correo,
            estatus: estatus,
            viejo: JSON.stringify(usuario)
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert('Problemas');
                return;
            }
            alert(data.toString());
            return;

        }
        alert("No generado");

        return;
    });
}

function listarAdministradores() {

    $(".listado p").remove();


    $.ajax({
        type: 'POST',
        async: true,
        url: "api/administrador/listado",
        data: {
            administrador: JSON.stringify(usuario)
        }
    }).done(function (data) {
        if (data != null) {
            if (data.error != null) {
                alert('Problemas');
                return;
            }
            var respuesta = JSON.stringify(data);
            var arreglo = JSON.parse(respuesta);

            let table = document.getElementById("listado");

            $("#listado tbody tr").remove();

            arreglo.forEach(element => {

                let row = table.insertRow();

                let telefono = row.insertCell();
                let nombre = row.insertCell();
                let apellido = row.insertCell();
                let correo = row.insertCell();
                let direccion = row.insertCell();


                telefono.appendChild(document.createTextNode(element.telefono));
                nombre.appendChild(document.createTextNode(element.persona.nombre));
                apellido.appendChild(document.createTextNode(element.persona.apellido));
                correo.appendChild(document.createTextNode(element.persona.correo));
                direccion.appendChild(document.createTextNode(element.colonia));

            });

            return;

        }
        alert("No generado");

        return;
    });
}

