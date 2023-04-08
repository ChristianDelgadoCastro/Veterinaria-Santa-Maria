var productos =[];
                
function inicializarModuloCita()
{
    setDetalleVisible(false);
    refrescarTabla();
}

function guardar()
{
    //Creamos un nuevo objeto
    var cita = new Object();
    
    //Agregamos sus propiedades y valores:
    cita.id = 0;
    cita.fechaCita = $('#txtFecha').val();
    cita.horaCita = $('#txtHora').val();
    cita.estatus = 1;
    
    //Revisamos si hay un ID previo
    if($('#txtCodigo').val().length > 0)
    {
        cita.id = parseInt($('#txtCodigo').val());
    }
    $.ajax({
            type    : "POST",
            url     : "api/cita/save",
            data    : {
                        idCita  : cita.id,
                        fechaCita      : cita.fecha,
                        horaCita       : cita.hora
                      }
            })
    .done(function(data){
        //Revisamos si hubo algun error:
        if(data.error != null)
        {
            swal.fire('Error', data.error, 'warning');
        }
        else
        {
            //Refrescamos la tabla 
            refrescarTabla();
            cita = data;
            $('#txtCodigo').val(cita.id);
            //Notificamos al usuario de la accion realizada;
            Swal.fire('Movimiento realizado','Datos de producto guardados correctamente', 'success');
        }
    });
}

function eliminar()
{
    var id = parseInt($('#txtCodigo').val());
    
    $.ajax({
                type    : "DELETE",
                url     : "api/cita/delete",
                data    : {
                                idcita : id
                          }
           })
    .done(function (data){
        //Revisamos si hubo algun error:
        if(data.error != null)
        {
            swal.fire('Error', data.error, 'warning');
        }
        else
        {
            //Refrescamos la tabla 
            refrescarTabla();
            limpiarFormulario();
            Swal.fire("Movimiento Realizado", "Registro eliminado", "success");
        }
    });
}

function refrescarTabla()
{
    //Inicilizamos una variable donde vamos a colocar todo el contenido HTML 
    // de la tabla productos:
    var contenido = '';
    
    //Hacemsos la peticion al servicio REST que consulta los productos
    $.ajax({
                type    : "GET",
                url     : "api/cita/getAll"
           })
    .done(function (data){
        //Revisamos si hubo algun error:
        if(data.error != null)
        {
            swal.fire('Error', data.error, 'warning');
        }
        else
        {
            citas = data;
            //Recorremos el arreglo de productos posición por posición:
            for (var i = 0; i < data.length; i++)
            {
                //Agregamos un nuevo renglón a la tabla contenido
                // sus respetivas columnas y valores:
                contenido = contenido + '<tr>' + 
                                            '<td>' + citas[i].id + '</td>' +
                                            '<td>' + citas[i].fecha + '</td>' +
                                            '<td>' + citas[i].hora + '</td>' +
                                            '<td>' + citas[i].cliente + '</td>' +
                                            '<td>' + citas[i].empleado + '</td>' +
                                            '<td><a href="#" onclick="mostrarDetalle('+ citas[i].id + ');"><i class="fas fa-archive text-dark"></i></a>' + '</td>' +
                                        '</tr>';
            }
            //Insertamos el contenido generado previamente dentro del cuerpo de la tabla:
            $('#tbodyCitas').html(contenido);
        }
    });   
}

function mostrarDetalle(idCita)
{
    //Buscamos la posición del producto
    var pos = buscarPosicionPorId(idCita);
    //Obtenemos el objeto producto en la posicion requerida
    var p = citas[pos];
    
    //llenamos los controles del formulario de detalle
    $('#txtCodigo').val(p.id);
    $('#txtFecha').val(p.fecha);
    $('#txtHora').val(p.hora);
    
    setDetalleVisible(true);
}
/*
 * Esta función busca la posición que tiene un producto dentro del arreglo de productos
 * con base en el id que se pasa como parámetro.
 * 
 * La función devuelve la posición que ocupa el elemento buscado.
 * Siel id buscado no coincide con algun id de los elementos del arreglo,
 * la funcion retornra -1,el cual es un valor no válido que indica que 
 * no se encontro un producto con ese id.
 */
function buscarPosicionPorId(id)
{
    //Recorremos el arreglo posición por posición
    for(var i = 0; i < productos.length; i++)
    {
        //Evaluamos si el ID del producto en la posición actual es igual
        //que el id buscado:
        if (citas[i].id === id)
        {
            return i;
        }
    }
    
    //Si llegamos a este punto significa que no encontramos algun producto 
    //con un ID igual al buscado, en cuyo caso, regresamos un -1.
    return -1;
}

function limpiarFormulario()
{
    $('#txtCodigo').val('');
    $('#txtFecha').val('');
    $('#txtHora').val('');
}

/*
 * Esta funcion muestra u oculta el panel de detalle de un producto
 * si el valor true, el panel de detelle se muestra si el valor es false 
 * se oculta
 */
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