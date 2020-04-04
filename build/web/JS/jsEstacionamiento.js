/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var usuario = new Object();

var estacionamientosJSON;
var administradoresJSON = [];

var JSONSeleccionado;

var imageBase64;

var isActive = true;
var isInsert = true;



// selecciona el <option> default
// $('#sucursalReservacionListado option:eq(0)').prop('slected', true);

/**
 * Cuando el DOM esté listo
 */
$(document).ready(function () {
    usuario = JSON.parse(localStorage.getItem("usuario"));

    iniciarComponentes();

    listarEstacionamientos();

    cargarJSONElements();

});
/**
 * 
 */
function iniciarComponentes() {
    $('#fullHeightModalRight').modal({
        backdrop: 'static',
        show: false
    });

    // $('.mdb-select').materialSelect();

    $('#isActive').prop("checked", false);
}

$('#isActive').change(function () {
    listarEstacionamientos();
});


// carga los elementos JSON de los que es dependiente la página
function cargarJSONElements() {
    $.ajax({
        type: 'POST',
        url: 'api/administrador/listado',
        data: {
            active: true,
            administrador: JSON.stringify(usuario)
        }
    }).done(function (data) {
        // por si el servidor devuelve error en json
        if (data.error) {
            alert("Problema \n" + data.error);
            return;
        }

//        console.log("datos");
//        console.log(data);

        // Asigna a varible local el JSON
        administradoresJSON = data;

        // elimina las opciones que estaban el el <select> excepto la primera con indice[0]
        $("#admin option").slice(1).remove();

        // Asigna cada elemento al <section>
        $.each(data, function (i, item) {

            // guardará el HTML de cada <option>
            var html = "";

//             console.log("each " + i);
//             console.log(item);

            // Construcción del HTML por <option>
            html += "<option value='" + item.idAdministrador + "'>" + item.persona.nombre + "</option>";

            // Adición del HTML creado a <Section>
            $("#admin").append(html);
        });

    }).fail(function (data) {
        console.log("falló el AJAX funcion");
    });
}

function encodeImageFileAsURL(element) {
    var file = element.files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        //console.log('RESULT', reader.result);
        imageBase64 = reader.result;
        $('#imgElement').attr('src', imageBase64);
    }
    reader.readAsDataURL(file);

    // console.log("valor" + $('#foto').val());
}

function verDetalle(indice) {

    JSONSeleccionado = estacionamientosJSON[indice];
    isInsert = false;

    $('#nombre').val(JSONSeleccionado.nombre);
    $('#latitud').val(JSONSeleccionado.latitud);
    $('#longitud').val(JSONSeleccionado.longitud);
    $('#domicilio').val(JSONSeleccionado.domicilio);
    $('#foto').replaceWith($('#foto').clone());
    // JSONSeleccionado.foto = imageBase64;
    imageBase64 = JSONSeleccionado.foto;
    $('#imgElement').attr('src', imageBase64);

    $('#capacidad').val(JSONSeleccionado.capacidad);
    $('#costo').val(JSONSeleccionado.costo);


    //$('#admin option:eq(0)').prop('selected', true);
    //
    //$("#admin").val("1");//.change();
    //$('#admin').selectmenu().selectmenu('refresh');

    $("#admin").attr("selected", false);
    $("#admin option[value="+JSONSeleccionado.idAdministrador+"]").attr("selected", true).change();
}

$('#fullHeightModalRight').on('hidden.bs.modal', function (e) {
    // por defecto es insertar
    isInsert = true;
    
    $('#nombre').val("");
    $('#latitud').val("");
    $('#longitud').val("");
    $('#domicilio').val("");
    $('#capacidad').val("");
    $('#costo').val("");
    $("#admin").attr("selected", false);
    $("#admin option[value=0]").attr("selected", true).change();

    // cambia la imagen a nula
    imageBase64 = null;
    $('#imgElement').attr('src', imageBase64);
});

/**********************************************
 * 
 * FUNCIONES CRUD
 * 
 **********************************************/

/**
 * Crea la las filas de la tabla después de hacer la
 * petición al servicio REST
 */
function listarEstacionamientos() {

    var active = !$('#isActive').is(':checked');
    var token
    $.ajax({
        type: 'GET',
        url: 'api/estacionamiento/getAll',
        data: {
            active: active,
            persona: JSON.stringify(usuario)
        }
    }).done(function (data) {
        // por si el servidor devuelve error en json
        if (data.error) {
            alert("Problema \n" + data.error);
            return;
        }

        console.log(data);

        // Asigna a varible local el JSON
        estacionamientosJSON = data;

        var html = "";
        $("#listadoBody").html(html);

        $.each(data, function (i, item) {

            // console.log("each " + i);
            // console.log(item);


            // Construcción del HTML de una fila
            html += "<tr>" +
                    "<th scope=\"row\">" + item.nombre + "</th>" +
                    "<td>" + item.domicilio + "</td>" +
                    "<td>" + item.capacidad + "</td>" +
                    "<td>" + item.administrador.persona.nombre + "</td>" +
                    "<td>" + item.longitud + "</td>" +
                    "<td>" + item.latitud + "</td>" +
                    "<td><a onclick='verDetalle(" + i + ")' data-toggle='modal' data-target='#fullHeightModalRight' style='color: blue;'>Detalle</a></td>";

            if (active)
            {
                html += "<td><a onclick='eliminar(" + i + ")' style='color: red;'>eliminar</a></td>" +
                        "</tr>";
            } else {
                html += "<td><a onclick='deseliminar(" + i + ")' style='color: green;'>activar</a></td>" +
                        "</tr>";
            }

        });

        // Adición del HTML creado a <Section>
        $("#listadoBody").html(html);

    }).fail(function (data) {
        console.log("falló el AJAX funcion llenarCamposReservacionAtender");
    });
}

function manejarInsertarModificar() {

    // Previene la página de recargarse
    event.preventDefault();

    if (isInsert) {
        console.log("insertar");
        insertar();
    } else {
        console.log("modificar");
        modificar();
    }
}

function insertar() {

    var objecto = new Object();

    var nombre = $('#nombre').val();
    var latitud = $('#latitud').val();
    var longitud = $('#longitud').val();
    var domicilio = $('#domicilio').val();
    var foto = imageBase64;
    var capacidad = $('#capacidad').val();
    var costo = $('#costo').val();
    var adminIndex = $('#admin').val();

    objecto.nombre = nombre;
    objecto.latitud = latitud;
    objecto.longitud = longitud;
    objecto.domicilio = domicilio;
    objecto.foto = foto;
    objecto.capacidad = capacidad;
    objecto.costo = costo;
    objecto.estatus = 1;
    // asignación del administrador
    administradoresJSON.forEach(function (admin) {
//        console.log("buscar: "+ adminIndex);
//        console.log(admin);
//        console.log(adminIndex);
        if(admin.idAdministrador == adminIndex)
            objecto.administrador = admin;
    });

    // console.log(JSON.stringify(objecto));
    console.log(objecto);

    $.ajax({
        type: 'POST',
        url: 'api/estacionamiento/insert',
        data: {
            estacionamiento: JSON.stringify(objecto),
            persona: JSON.stringify(usuario)
        }
    }).done(function (data) {
        // por si el servidor devuelve error en json
        if (data.error) {
            alert("Problema \n" + data.error);
            return;
        }

        alert("Operación exitosa");

        $('#fullHeightModalRight').modal('hide');
        isInsert = true;

        listarEstacionamientos();

    }).fail(function (data) {
        console.log("falló el AJAX funcion");
    });
}

function modificar() {

    var objecto = new Object();

//     var _id = JSONSeleccionado._id;
//    console.log(estacionamientosJSON);
    // console.log(JSONSeleccionado);
    var nombre = $('#nombre').val();
    var latitud = $('#latitud').val();
    var longitud = $('#longitud').val();
    var domicilio = $('#domicilio').val();
    var foto = imageBase64;
    var capacidad = $('#capacidad').val();
    var costo = $('#costo').val();
    var adminIndex = $('#admin').val();

    console.log("admin index");
    console.log(adminIndex);

    objecto._id = JSONSeleccionado._id;
    objecto.nombre = nombre;
    objecto.latitud = latitud;
    objecto.longitud = longitud;
    objecto.domicilio = domicilio;
    objecto.foto = foto;
    objecto.capacidad = capacidad;
    objecto.costo = costo;
    objecto.estatus = JSONSeleccionado.estatus;
    // asignación del administrador
    administradoresJSON.forEach(function (admin) {
//        console.log("buscar: "+ adminIndex);
//        console.log(admin);
//        console.log(adminIndex);
        if(admin.idAdministrador == adminIndex)
            objecto.administrador = admin;
    });

    console.log(objecto);
//    console.log(JSON.stringify(objecto));

    $.ajax({
        type: 'PUT',
        url: 'api/estacionamiento/update',
        data: {
            estacionamiento: JSON.stringify(objecto),
            persona: JSON.stringify(usuario)
        }
    }).done(function (data) {
        // por si el servidor devuelve error en json
        if (data.error) {
            alert("Problema \n" + data.error);
            return;
        }

        alert("Operación exitosa");

        $('#fullHeightModalRight').modal('hide');
        isInsert = true;

        listarEstacionamientos();

    }).fail(function (data) {
        console.log("falló el AJAX funcion");
    });

}

function eliminar(indice) {
    var seleccionado = estacionamientosJSON[indice];

    // console.log(seleccionado);

    $.ajax({
        type: 'PUT',
        url: 'api/estacionamiento/delete',
        data: {
            estacionamiento: JSON.stringify(seleccionado),
            persona: JSON.stringify(usuario)
        }
    }).done(function (data) {
        // por si el servidor devuelve error en json
        if (data.error) {
            alert("Problema \n" + data.error);
            return;
        }


        alert("Operación exitosa");

        listarEstacionamientos();


    }).fail(function (data) {
        console.log("falló el AJAX");
    });
}

function deseliminar(indice) {
    var seleccionado = estacionamientosJSON[indice];

    $.ajax({
        type: 'PUT',
        url: 'api/estacionamiento/undelete',
        data: {
            estacionamiento: JSON.stringify(seleccionado),
            persona: JSON.stringify(usuario)
        }
    }).done(function (data) {
        // por si el servidor devuelve error en json
        if (data.error) {
            alert("Problema \n" + data.error);
            return;
        }

        alert("Operación exitosa");

        listarEstacionamientos();


    }).fail(function (data) {
        console.log("falló el AJAX");
    });
}