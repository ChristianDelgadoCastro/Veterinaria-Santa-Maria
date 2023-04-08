var productos = [];

function inicializarModuloProducto()
{
    setDetalleVisibleP();
    refrescarTablaP();
}

function guardar() {
    var productos = new Object();

    productos.nombre = limpieza($('#txtNombre').val());
    productos.marca = limpieza($('#txtMarca').val());
    productos.precioUso = limpieza($('#txtPrecio').val());
    productos.estatus = 1;

    if ($('#txtCodigo').val().length > 0) {
        productos.idProducto = parseInt($('#txtCodigo').val());
    }

    $.ajax({
        type: "POST",
        url: "api/producto/save",
        data: {
            idProducto: productos.idProducto,
            nombre: productos.nombre,
            marca: productos.marca,
            precioUso: productos.precioUso
        }
    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    refrescarTablaP();
                    //productos = data;
//                    $('#txtCodigo').val(productos.idProducto);
                    Swal.fire('Movimiento realizado', 'Datos de producto guardado', 'success');
                }
            });
}

function eliminar()
{
    //Obtenemos el ID de la sala que desea eliminar
    var id = parseInt($('#txtCodigo').val());
    //Buscamos la posicion del producto 
    var pos = buscarPosicionPorId(id);

    //Verificamos que sea una posicion valida;
    if (pos >= 0)
    {
        productos.splice(pos, 1);

        //Notificamos al usuario la accion realizada
        Swal.fire('Movimiento realizado', 'Producto eliminado', 'success');

        //Limpiamos el formulario
        limpiarFormulario();
        //Refrescamos la tabla
        refrescarTablaP();
    } else
    {
        Swal.fire('', 'Producto no encontrada con el ID especificado', 'warning');
    }
}


function eliminar() {
    var idProducto = parseInt($('#txtCodigo').val());
    $.ajax({
        type: "POST",
        url: "api/producto/delete",
        data: {
            idProducto: idProducto
        }
    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    refrescarTablaP();
                    Swal.fire('Movimiento realizado', 'Datos de producto eliminados', 'success');
                }
            });

}

function refrescarTablaP()
{
    var contenido = '';

    $.ajax({
        type: "GET",
        url: "api/producto/getAll"
    })
            .done(function (data) {
                if (data.error != null) {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    productos = data;
                    for (var i = 0; i < productos.length; i++)
                    {
                        contenido = contenido + '<tr>' +
                                '<td>' + productos[i].idProducto + '</td>' +
                                '<td>' + productos[i].nombre + '</td>' +
                                '<td>' + productos[i].marca + '</td>' +
                                '<td>' + productos[i].precioUso + '</td>' +
                                '<td>' + productos[i].estatus + '</td>' +
                                '<td><a href="#" class="text-light" onclick="mostrarDetalle(' + productos[i].idProducto + ');">Mostrar detalle</a>' +
                                '</td>' +
                                '</tr>';
                    }
                    $('#tbodyProducto').html(contenido);
                }
            });
            
    limpiarFormulario();
}

function mostrarDetalle(idProducto)
{
    //Buscamos la posicion del producto 
    var pos = buscarPosicionPorId(idProducto);
    //Obtenemos el objeto del folmulario detalle
    var s = productos[pos];

    //Llamamos los controles del formulario de detalle 
    $('#txtCodigo').val(s.idProducto);
    $('#txtNombre').val(s.nombre);
    $('#txtMarca').val(s.marca);
    $('#txtPrecio').val(s.precioUso);
    $('#txtEstatus').val(s.estatus);
    
    setDetalleVisibleP(true);
}

/*
 * Esta función busca la posición que tiene una sala dentro del arreglo de productos
 * con base en el id que se pasa como parámetro.
 * 
 * La función devuelve la posición que ocupa el elemento buscado.
 * Si el id buscado no coincide con algun id de los elementos del arreglo,
 * la funcion retornra -1,el cual es un valor no válido que indica que 
 * no se encontro un cliente con ese id.
 */
function buscarPosicionPorId(id)
{
    //Recorremos el arreglo posicion por posicion 
    for (var i = 0; i < productos.length; i++)
    {
        //Evaluamos si el ID del producto en la posicion es igual que el buscado:
        if (productos[i].idProducto === id)
        {
            return i;
        }
    }
    //Si llegamos a este punto significa que no encontramos alguna sala con un ID
    //igual al buscado, en cuyo casa, regresamos un -1:
    return -1;
}

function limpiarFormulario()
{
    $('#txtCodigo').val('');
    $('#txtNombre').val('');
    $('#txtMarca').val('');
    $('#txtPrecio').val('');
    $('#txtEstatus').val('');
}

/*
 * Esta funcion muestra u oculta el panel de detalle de un producto
 * si el valor true, el panel de detelle se muestra si el valor es false 
 * se oculta
 */


function setDetalleVisibleP(valor)
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