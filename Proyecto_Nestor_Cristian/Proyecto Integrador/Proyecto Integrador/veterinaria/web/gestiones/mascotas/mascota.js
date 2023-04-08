var mascotas = [];
var clientes;

function inicializarModulo()
{
    setDetalleVisible();
    refrescarTabla();
}


function guardar() {
    var mascota = new Object();

    mascota.nombre = limpieza($('#txtNombre').val());
    mascota.raza = limpieza($('#txtRaza').val());
    mascota.edad = limpieza($('#txtEdad').val());
    mascota.peso = limpieza($('#txtPeso').val());
    mascota.descripcion = limpieza($('#txtDescripcion').val());
    mascota.idCliente = $('#txtIdCliente').val();
    mascota.estatus = 1;

    if ($('#txtCodigo').val().length > 0) {
        mascota.idMascota = parseInt($('#txtCodigo').val());
    }

    $.ajax({
        type: "POST",
        url: "api/mascota/save",
        data: {
            idMascota: mascota.idMascota,
            nombre: mascota.nombre,
            raza: mascota.raza,
            edad: mascota.edad,
            peso: mascota.peso,
            descripcion: mascota.descripcion,
            idCliente: mascota.idCliente
        }
    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    refrescarTabla();
//                    mascota = data;
//                    $('#txtCodigo').val(mascota.idMascota);
                    Swal.fire('Movimiento realizado', 'Datos de mascota guardados', 'success');
                }
            });
}

function eliminar() {
    var idMascota = parseInt($('#txtCodigo').val());
    $.ajax({
        type: "POST",
        url: "api/mascota/delete",
        data: {
            idMascota: idMascota
        }
    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    refrescarTabla();
                    limpiar();
                    Swal.fire('Movimiento realizado', 'Datos de mascota eliminados', 'success');
                }
            });

}

function refrescarTabla() {
    var contenido = '';

    $.ajax({
        type: "GET",
        url: "api/mascota/getAll"
    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    mascotas = data;
                    for (var i = 0; i < mascotas.length; i++)
                    {
                        contenido = contenido + '<tr>' +
                                '<td>' + mascotas[i].idMascota + '</td>' +
                                '<td>' + mascotas[i].nombre + '</td>' +
                                '<td>' + mascotas[i].descripcion + '</td>' +
                                '<td>' + mascotas[i].edad + '</td>' +
                                '<td>' + mascotas[i].peso + '</td>' +
                                '<td>' + mascotas[i].raza + '</td>' +
                                '<td><a href="#" class="text-light" onclick="mostrarDetalle(' + mascotas[i].idMascota + ');">Mostrar detalle</a>' +
                                '</td>' +
                                '</tr>';
                    }
                    $('#tbodyMascota').html(contenido);
                }
            });
    limpiar();
}

function mostrarDetalle(idMascota)
{
    var pos = buscarPosicionPorId(idMascota);
    var m = mascotas[pos];

    $('#txtCodigo').val(m.idMascota);
    $('#txtNombre').val(m.nombre);
    $('#txtRaza').val(m.raza);
    $('#txtEdad').val(m.edad);
    $('#txtPeso').val(m.peso);
    $('#txtDescripcion').val(m.descripcion);
    $('#txtIdCliente').val(m.idCliente);
    $('#txtEstatus').val(m.estatus);
    setDetalleVisible(true);
}

function buscarPosicionPorId(id)
{

    for (var i = 0; i < mascotas.length; i++)
    {
        if (mascotas[i].idMascota === id)
        {
            return i;
        }
    }
    return -1;
}

function limpiar()
{
    $('#txtCodigo').val('');
    $('#txtNombre').val('');
    $('#txtRaza').val('');
    $('#txtEdad').val('');
    $('#txtPeso').val('');
    $('#txtDescripcion').val('');
    $('#txtIdCliente').val('');
    $('#txtEstatus').val('');
}

function setDetalleVisible(valor)
{
    if (valor)
    {
        $('#divTabla').removeClass('col-12');
        $('#divTabla').addClass('col-8');
        $('#divDetalle').show();
    } else
    {
        $('#divTabla').removeClass('col-8');
        $('#divTabla').addClass('col-12');
        $('#divDetalle').hide();
    }
}

function cerrarModulo()
{
    $("#contenedorPrincipal").html('');
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