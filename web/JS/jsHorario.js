var usuario = JSON.parse(localStorage.getItem("usuario"));
var persona;
var estacionamiento = JSON.parse(localStorage.getItem("estacionamiento"));


if (usuario == null) {
    window.location = "index.html";
}

if (JSON.stringify(usuario).indexOf("idAdministrador") < 0) {//Validamos si es un administrador
    window.location = "usuario.html";
}

persona = usuario.persona;

var idEstacionamiento = estacionamiento.idEstacionamiento;
var token = persona.token;
var idPersona = persona.idPersona;

function listarHorarios() {
    $.ajax({
        type: "POST",
        async: true,
        url: 'api/horarios/listado',
        data: {
            idEstacionamiento: idEstacionamiento,
            idPersona: idPersona,
            token: token
        }

    }).done(function (data)
    {
        if (data.error != null) {
            window.alert('Problemas para listar');
            return;
        } else {
            horarios = data;
            var datos = "";
            datos += "<thead class='infoTabla'>";
            datos += "<tr>";
            datos += "<th scope='col' style='width:auto;'> Día </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Inicio </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Fin </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "</tr>";
            datos += "</thead>";
            datos += "<tbody>";

            for (var i = 0; i < horarios.length; i++) {
                var idH = horarios[i].idHorario;
                var dia = horarios[i].diaServicio;
                var horaI = horarios[i].horaInicio;
                var horaF = horarios[i].horaFin;
                
                datos += "<tr class='table-light'>";
                datos += "<td>" + horarios[i].diaServicio + "</td>";
                datos += "<td>" + horarios[i].horaInicio + "</td>";
                datos += "<td>" + horarios[i].horaFin + "</td>";
                datos += "<td><button type='button' class='btn btn-dark'\n\
     id='insertarProducto' onclick='modificarDatos(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Modificar</button></td>";
                datos += "<td><button type='button' class='btn btn-danger'\n\
     id='eliminarProducto' onclick='eliminarHorario(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Eliminar</button></td>";
                datos += "</tr>";
            }
            datos += "</tbody>";
            $('#tblHorarios').html(datos);

        }
    }
    );
}

function modificarDatos(idH, horarioS, horaI, horaF) {
    document.getElementById('txtidHorario').value = idH;
    document.getElementById('txtDia').value = horarioS;
    document.getElementById('txtHoraInicio').value = horaI;
    document.getElementById('txtHoraFin').value = horaF;
    document.getElementById('txtEstacionamiento').value = estacionamiento.nombre;
    document.getElementById('txtDireccion').value = estacionamiento.domicilio;

}

function actualizarDatos() {


    $.ajax({
        type: "POST",
        async: true,
        url: 'api/horarios/actualizarHorario',
        data: {
            idHorario: document.getElementById('txtidHorario').value,
            diaServicio: document.getElementById('txtDia').value,
            horaInicio: document.getElementById('txtHoraInicio').value,
            horaFin: document.getElementById('txtHoraFin').value,
            token: token,
            idPersona: idPersona
        }

    }).done(function (data)
    {
        if (data.error != null) {
            window.alert('Problemas para eliminar');
            return;
        } else {
            horarios = data;
            var datos = "";
            datos += "<thead class='infoTabla'>";
            datos += "<tr>";
            datos += "<th scope='col' style='width:auto;'> Día </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Inicio </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Fin </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "</tr>";
            datos += "</thead>";
            datos += "<tbody>";

            for (var i = 0; i < horarios.length; i++) {
                var idH = horarios[i].idHorario;
                var dia = horarios[i].diaServicio;
                var horaI = horarios[i].horaInicio;
                var horaF = horarios[i].horaFin;
                datos += "<tr class='table-light'>";
                datos += "<td>" + horarios[i].diaServicio + "</td>";
                datos += "<td>" + horarios[i].horaInicio + "</td>";
                datos += "<td>" + horarios[i].horaFin + "</td>";
                datos += "<td><button type='button' class='btn btn-dark'\n\
     id='insertarProducto' onclick='modificarDatos(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Modificar</button></td>";
                datos += "<td><button type='button' class='btn btn-danger'\n\
     id='eliminarProducto' onclick='eliminarHorario(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Eliminar</button></td>";
                datos += "</tr>";
            }
            datos += "</tbody>";
            $('#tblHorarios').html(datos);
        }
    }
    );
}

function eliminarHorario(idHorario, diaServicio, horaI, horaF) {
    $.ajax({
        type: "POST",
        async: true,
        url: 'api/horarios/eliminarHorario',
        data: {
            idHorario: idHorario,
            diaServicio: diaServicio,
            horaInicio: horaI,
            horaFin: horaF,
            token: token,
            idPersona: idPersona
        }

    }).done(function (data)
    {
        if (data.error != null) {
            window.alert('Problemas para eliminar');
            return;
        } else {
            horarios = data;
            var datos = "";
            datos += "<thead class='infoTabla'>";
            datos += "<tr>";
            datos += "<th scope='col' style='width:auto;'> Día </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Inicio </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Fin </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "</tr>";
            datos += "</thead>";
            datos += "<tbody>";

            for (var i = 0; i < horarios.length; i++) {
                var idH = horarios[i].idHorario;
                var dia = horarios[i].diaServicio;
                var horaI = horarios[i].horaInicio;
                var horaF = horarios[i].horaFin;
                datos += "<tr class='table-light'>";
                datos += "<td>" + horarios[i].diaServicio + "</td>";
                datos += "<td>" + horarios[i].horaInicio + "</td>";
                datos += "<td>" + horarios[i].horaFin + "</td>";
                datos += "<td><button type='button' class='btn btn-dark'\n\
     id='insertarProducto' onclick='modificarDatos(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Modificar</button></td>";
                datos += "<td><button type='button' class='btn btn-danger'\n\
     id='eliminarProducto' onclick='eliminarHorario(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Eliminar</button></td>";
                datos += "</tr>";
            }
            datos += "</tbody>";
            $('#tblHorarios').html(datos);
        }
    }
    );
}

function limpiarCampos() {
    document.getElementById('txtidHorario').value = "";
    document.getElementById('txtDia').value = "";
    document.getElementById('txtHoraInicio').value = "";
    document.getElementById('txtHoraFin').value = "";
}

function agregarHorario() {
    $.ajax({
        type: "POST",
        async: true,
        url: 'api/horarios/insertarHorario',
        data: {
            idEstacionamiento: idEstacionamiento,
            diaServicio: document.getElementById('txtDiaA').value,
            horaInicio: document.getElementById('txtHoraInicioA').value,
            horaFin: document.getElementById('txtHoraFinA').value,
            token: token,
            idPersona: idPersona
        }

    }).done(function (data)
    {
        if (data.error != null) {
            window.alert('Problemas para eliminar');
            return;
        } else {
            horarios = data;
            var datos = "";
            var datos = "";
            datos += "<thead class='infoTabla'>";
            datos += "<tr>";
            datos += "<th scope='col' style='width:auto;'> Día </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Inicio </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Fin </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "<th scope='col' style='width:auto;'> </th>";
            datos += "</tr>";
            datos += "</thead>";
            datos += "<tbody>";

            for (var i = 0; i < horarios.length; i++) {
                var idH = horarios[i].idHorario;
                var dia = horarios[i].diaServicio;
                var horaI = horarios[i].horaInicio;
                var horaF = horarios[i].horaFin;
                datos += "<tr class='table-light'>";
                datos += "<td>" + horarios[i].diaServicio + "</td>";
                datos += "<td>" + horarios[i].horaInicio + "</td>";
                datos += "<td>" + horarios[i].horaFin + "</td>";
                datos += "<td><button type='button' class='btn btn-dark'\n\
     id='insertarProducto' onclick='modificarDatos(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Modificar</button></td>";
                datos += "<td><button type='button' class='btn btn-danger'\n\
     id='eliminarProducto' onclick='eliminarHorario(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Eliminar</button></td>";
                datos += "</tr>";
            }
            datos += "</tbody>";
            $('#tblHorarios').html(datos);
        }
    }
    );
}
var horariosB = [];
function buscarHorario() {
    var diaS = ($('#diaH').val());
    $.ajax({
        type: "POST",
        async: true,
        url: 'api/horarios/buscarHorario',
        data: {
            idEstacionamiento: idEstacionamiento,
            diaServicio: diaS,
            idPersona: idPersona,
            token: token
        }

    }).done(function (data)
    {
        if (data.error != null) {
            window.alert('Problemas para listar');
            return;
        } else {
            console.log(data);
            horariosB = data;
            var datos = "";
            datos += "<thead class='infoTabla'>";
            datos += "<tr>";
            datos += "<th scope='col' style='width:auto;'> Día </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Inicio </th>";
            datos += "<th scope='col' style='width:auto;'> Hora Fin </th>";
            datos += "<th scope='col' style='width:auto;'>  </th>";
            datos += "<th scope='col' style='width:auto;'>  </th>";
            datos += "</tr>";
            datos += "</thead>";
            datos += "<tbody>";

            for (var i = 0; i < horariosB.length; i++) {
                var idH = horariosB[i].idHorario;
                var dia = horariosB[i].diaServicio;
                var horaI = horariosB[i].horaInicio;
                var horaF = horariosB[i].horaFin;
                datos += "<tr class='table-light'>";
                datos += "<td>" + horariosB[i].diaServicio + "</td>";
                datos += "<td>" + horariosB[i].horaInicio + "</td>";
                datos += "<td>" + horariosB[i].horaFin + "</td>";
                datos += "<td><button type='button' class='btn btn-dark'\n\
     id='insertarProducto' onclick='modificarDatos(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Modificar</button></td>";
                datos += "<td><button type='button' class='btn btn-danger'\n\
     id='eliminarProducto' onclick='eliminarHorario(" + idH + ",\"" + dia + "\", \"" +
                        horaI + "\", \"" + horaF + "\")'>Eliminar</button></td>";
                datos += "</tr>";
            }

            datos += "</tbody>";
            $('#tblBusqueda').html(datos);

        }
    }
    );
}

function limpiarRegistro() {
    document.getElementById('txtDiaA').value = "";
    document.getElementById('txtHoraInicioA').value = "";
    document.getElementById('txtHoraFinA').value = "";
}

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
        window.alert("No generado");
        return;
    });
}