var horarios = [];

function inicializarModulo()
{
    setDetalleVisible();
    refrescarTabla();
}

function guardar(){
    var horario = new Object();
    
    if ($('#txtCodigo').val().length <1) {
        horario.id=0;
    }
    else{
        horario.id=parseInt($('#txtCodigo').val());
    }
    
    
    horario.horaInicio = $('#txthoraInicio').val();
    horario.horaFin = $('#txthoraFin').val();
    
    $.ajax  ({
            type: "POST",
            url: "api/horario/save",
            data:{
                idHorario  : horario.id,
                horaInicio : horario.horaInicio,
                horaFin    : horario.horaFin,
                }
            })
    .done(function (data){
        if (data.error != null) {
            Swal.fire('Error',data.error,'warning');
        }
        else{
            refrescarTabla();
            horario = data;
            $('#txtCodigo').val(horario.id);
            Swal.fire('Movimiento realizado','Datos de horario guardados','success');
        }
    });
}

function eliminar(){
    var id = parseInt($('#txtCodigo').val());
            $.ajax  ({
                        type: "DELETE",
                        url: "api/horario/delete",
                        data:{
                        idHorario  : id
                        }
            })
    .done(function (data){
        if (data.error != null) {
            Swal.fire('Error',data.error,'warning');
        }
        else{
            refrescarTabla();
            limpiar();
            Swal.fire('Movimiento realizado','Datos de horario eliminados','success');
        }
    });
    
}

function refrescarTabla(){
    var contenido = '';
    
    $.ajax  ({
                type:"GET",
                url:"api/horario/getAll"
            })
    .done(function(data){
        if(data.error!=null){
            Swal.fire('Error', data.error,'warning');
        }
        else{
            horarios=data;
            for (var i = 0; i < horarios.length; i++)
    {
        contenido = contenido + '<tr>' + 
                                      '<td>' + horarios[i].id + '</td>' + 
                                      '<td>' + horarios[i].horaInicio + '</td>' +
                                      '<td>' + horarios[i].horaFin + '</td>' +
                                      '<td><a href="#" class="text-light" onclick="mostrarDetalle(' + horarios[i].id +');">Mostrar detalle</a>' + 
                                      '</td>' + 
                                '</tr>';
    }
    $('#tbodyHorario').html(contenido);
        }
    });
}



function mostrarDetalle(idHorario)
{

    var pos = buscarPosicionPorId(idHorario);
    var h = horarios[pos];

    $('#txtCodigo').val(h.id);
    $('#txthoraInicio').val(h.horaInicio);
    $('#txthoraFin').val(h.horaFin);
    setDetalleVisible(true);
}


function buscarPosicionPorId(id)
{
    for (var i = 0; i < horarios.length; i++)
    {
        if(horarios[i].id === id)
        {
            return i;
        }
    }
    
    return -1;
}

function limpiarFormulario()
{
    $('#txtCodigo').val('');
    $('#txthoraInicio').val('');
    $('#txthoraFin').val('');
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