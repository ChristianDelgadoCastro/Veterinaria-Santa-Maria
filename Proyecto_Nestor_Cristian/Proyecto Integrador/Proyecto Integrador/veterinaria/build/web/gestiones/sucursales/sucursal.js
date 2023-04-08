var sucursales = [];

function inicializarModulo()
{
    setDetalleVisible();
    refrescarTabla();
}


function guardar(){
    var sucursal = new Object();
    
    if ($('#txtCodigo').val().length <1) {
        sucursal.id=0;
    }
    else{
        sucursal.id=parseInt($('#txtCodigo').val());
    }
    
    
    sucursal.nombre = $('#txtNombre').val();
    sucursal.domicilio = $('#txtDomicilio').val();
    sucursal.latitud = $('#txtLatitud').val();
    sucursal.longitud = $('#txtLongitud').val();
    sucursal.estatus = 1;
    
    $.ajax  ({
            type: "POST",
            url: "api/sucursal/save",
            data:{
                idSucursal  : sucursal.id,
                nombre      : sucursal.nombre,
                domicilio   : sucursal.domicilio,
                latitud     : sucursal.latitud,
                longitud    : sucursal.longitud
            }
            })
    .done(function (data){
        if (data.error != null) {
            Swal.fire('Error',data.error,'warning');
        }
        else{
            refrescarTabla();
            sucursal = data;
            $('#txtCodigo').val(sucursal.id);
            Swal.fire('Movimiento realizado','Datos de sucursal guardados','success');
        }
    });
}

function eliminar(){
    var id = parseInt($('#txtCodigo').val());
            $.ajax  ({
                        type: "DELETE",
                        url: "api/sucursal/delete",
                        data:{
                        idSuc  : id
                        }
            })
    .done(function (data){
        if (data.error != null) {
            Swal.fire('Error',data.error,'warning');
        }
        else{
            refrescarTabla();
            limpiar();
            Swal.fire('Movimiento realizado','Datos de sucursal eliminados','success');
        }
    });
    
}

function refrescarTabla(){
    var contenido = '';
    
    $.ajax  ({
                type:"GET",
                url:"api/sucursal/getAll"
            })
    .done(function(data){
        if(data.error!=null){
            Swal.fire('Error', data.error,'warning');
        }
        else{
            sucursales=data;
            for (var i = 0; i < sucursales.length; i++)
    {
        contenido = contenido + '<tr>' + 
                                      '<td>' + sucursales[i].id + '</td>' + 
                                      '<td>' + sucursales[i].nombre + '</td>' +
                                      '<td>' + sucursales[i].domicilio + '</td>' +
                                      '<td>' + sucursales[i].latitud + '</td>' +
                                      '<td>' + sucursales[i].longitud + '</td>' + 
                                      '<td>' + sucursales[i].estatus + '</td>' +
                                      '<td><a href="#" class="text-light" onclick="mostrarDetalle(' + sucursales[i].id +');">Mostrar detalle</a>' + 
                                      '</td>' + 
                                '</tr>';
    }
    $('#tbodySucursales').html(contenido);
        }
    });
}

function mostrarDetalle(idSucursal)
{

    var pos = buscarPosicionPorId(idSucursal);
    var s = sucursales[pos];

    $('#txtCodigo').val(s.id);
    $('#txtNombre').val(s.nombre);
    $('#txtLatitud').val(s.latitud);
    $('#txtLongitud').val(s.longitud);
    $('#txtDomicilio').val(s.domicilio);
    setDetalleVisible(true);
}

function buscarPosicionPorId(id)
{

    for (var i = 0; i < sucursales.length; i++)
    {
        if(sucursales[i].id === id)
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
    $('#txtLatitud').val('');
    $('#txtLongitud').val('');
    $('#txtDomicilio').val('');
}

 function setDetalleVisible(valor)
 {
     if(valor)
     {
         $('#divTabla').removeClass('col-12');
         $('#divTabla').addClass('col-8');
         $('#divDetalle').show();
     }
     else
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