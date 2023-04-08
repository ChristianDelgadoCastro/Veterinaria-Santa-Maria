var empleados = [];

function inicializarModulo()
{
    setDetalleVisible();
    refrescar();
}

function guardar()
{
    var empleado = new Object();
    empleado.persona = new Object();
    empleado.usuario = new Object();

    empleado.id = 0;
    empleado.persona.nombre = limpieza($('#txtNombre').val());
    empleado.persona.apellidos = limpieza($('#txtApePat').val());
    empleado.persona.genero = $('#cmbGenero').val();
    empleado.persona.rfc = limpieza($('#txtRfc').val());
    empleado.persona.telefono = limpieza($('#txtTel').val());
    empleado.persona.domicilio = limpieza($('#txtDomicilio').val());
    empleado.usuario.nombreUsuario = limpieza($('#txtUsuario').val());
    empleado.usuario.contrasenia = limpieza($('#txtContrasenia').val());
    empleado.usuario.rol = $('#txtRol').val();
    empleado.numeroEmpleado = $('#txtNumEmp').val();
    empleado.puesto = limpieza($('#txtPuesto').val());
    empleado.persona.idPersona = $('#txtPersonaId').val();
    empleado.usuario.idUsuario = $('#txtUsuarioId').val();
    empleado.estatus = 1;

    if ($('#txtCodEmpleado').val().length > 0) {
        empleado.id = parseInt($('#txtCodEmpleado').val());
        empleado.persona.id = parseInt($('#txtCodigoPersona').val());
        empleado.usuario.id = parseInt($('#txtCodigoUsuario').val());
    }
    $.ajax({
        type: "POST",
        async: true,
        url: "api/empleado/save",
        data: {empleado: JSON.stringify(empleado)}
    }).done(function (data) {

        if (data.error != null) {
            Swal.fire('Error', data.error, 'warning');
        } else {
            refrescar();
            empleado = data;
            $('#txtCodEmpleado').val(empleado.id);
            $('#txtCodigoPersona').val(empleado.persona.id);
            $('#txtCodigoUsuario').val(empleado.usuario.id);
            $('#txtNumEmp').val(empleado.numeroEmpleado);

            limpiar();
            Swal.fire('Movimieto Realizado', 'Datos del empleado guardados correctamente', 'success');
        }
    });
}

function eliminar() {
    var id = parseInt($('#txtCodEmpleado').val());
    $.ajax({
        type: "DELETE",
        url: "api/empleado/delete",
        data: {
            idEmpleado: id
        }
    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    refrescar();
                    limpiar();
                    Swal.fire('Movimiento realizado', 'Datos del empleado eliminados', 'success');
                }
            });

}

function refrescar()
{
    var contenido = '';

    $.ajax({

        type: "GET",
        url: "api/empleado/getAll"

    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning')
                } else {
                    empleados = data;
                    for (var i = 0; i < data.length; i++) {
                        contenido = contenido + '<tr>' +
                                '<td>' + empleados[i].id + '</td>' +
                                '<td>' + empleados[i].numeroEmpleado + '</td>' +
                                '<td>' + empleados[i].persona.nombre + ' ' +
                                empleados[i].persona.apellidos + '</td>' +
                                '<td>' + empleados[i].puesto + '</td>' +
                                '<td>' + empleados[i].estatus + '</td>' +
                                '<td><a href="#" class="text-light" onclick="mostrarDetalle(' + empleados[i].id + ');">Mostrar detalle</a>' + '</td>' +
                                '</tr>';
                    }

                }
                $('#tbodyEmpleados').html(contenido);
            });
}

function mostrarDetalle(idEmpleado)
{

    var pos = buscarPorId(idEmpleado);
    var e = null;

    if (pos < 0)
        return;

    e = empleados[pos];

    $('#txtCodEmpleado').val(e.id);
    $('#txtPersonaId').val(e.persona.id);
    $('#txtUsuarioId').val(e.usuario.id);
    $('#txtRol').val(e.usuario.rol);
    $('#txtNombre').val(e.persona.nombre);
    $('#txtApePat').val(e.persona.apellidos);
    $('#txtDomicilio').val(e.persona.domicilio);
    $('#cmbGenero').val(e.persona.genero);
    $('#txtRfc').val(e.persona.rfc);
    $('#txtTel').val(e.persona.telefono);
    $('#txtUsuario').val(e.usuario.nombreUsuario);
    $('#txtContrasenia').val(e.usuario.contrasenia);
    $('#txtNumEmp').val(e.numeroEmpleado);
    $('#txtPuesto').val(e.puesto);
    setDetalleVisible(true);
}
function buscarPorId(id){
    for (var i = 0; i < empleados.length; i++){
        if (empleados[i].id === id){
            return i;
        }
    }
    return -1;
}



function limpiar()
{
    $('#txtCodEmpleado').val('');
    $('#txtNumEmp').val('');
    $('#txtNombre').val('');
    $('#txtApePat').val('');
    $('#cmbGenero').val('');
    $('#txtDomicilio').val('');
    $('#txtPersonaId').val('');
    $('#txtUsuarioId').val('');
    $('#txtTel').val('');
    $('#txtRfc').val('');
    $('#txtPuesto').val('');
    $('#txtUsuario').val('');
    $('#txtContrasenia').val('');
}

function setDetalleVisible(valor)
{
    if (valor)
    {
        $('#divDetalle').show();
        $('#divTabla').hide();
    } else
    {
        $('#divDetalle').hide();
        $('#divTabla').show();
    }
}

function cerrarModulo()
{
    $('#contenedorPrincipal').html('');
}

function normalizar(texto){
    //Convertir el texto a mayusculas
    texto = texto.toUpperCase();
    
    //Eliminar acentos
    texto = texto.replaceAll("Á","A");
    texto = texto.replaceAll("É","E");
    texto = texto.replaceAll("Í","I");
    texto = texto.replaceAll("Ó","O");
    texto = texto.replaceAll("Ú","U");
    
    return texto;
}

function sanitizar(texto){
    texto = texto.replaceAll("(","");
    texto = texto.replaceAll(")","");
    texto = texto.replaceAll(";","");
    texto = texto.replaceAll("%","");
    texto = texto.replaceAll("'","");
    texto = texto.replaceAll("“","");
    texto = texto.replaceAll("”","");
    texto = texto.replaceAll("\"","");
    texto = texto.replaceAll("<","");
    texto = texto.replaceAll(">","");
    texto = texto.replaceAll("!","");
    texto = texto.replaceAll("$","");
    texto = texto.replaceAll("&","");
    texto = texto.replaceAll("/","");
    texto = texto.replaceAll("=","");
    texto = texto.replaceAll("?","");
    texto = texto.replaceAll("¿","");
    texto = texto.replaceAll("¡","");
    texto = texto.replaceAll("-","");
    texto = texto.replaceAll("^","");
    texto = texto.replaceAll("@","");
    texto = texto.replaceAll("|","");
    texto = texto.replaceAll("#","");
    
    return texto;
}

function limpieza(texto){
    texto = normalizar(texto);
    texto = sanitizar(texto);
    
    return texto;
}