
var tratamientos =
[
    {
        id: 1, 
        nombre:'Masaje muscular',
        descripcion: 'Alivia los musculos devolviendo su elasticidad, recomendado para deportistas (ciclistas, luchadores, futbolistas, etc)',
        estatus : 'Activo'
    },
   
    {
        id: 2, 
        nombre:'Masaje de pies',
        descripcion: 'Se realizan para relajarse y tener bienestar, atiende las tensiones y consigue la comodidad y la calma',
        estatus : 'Activo'
    },
    
    {
        id: 3,
        nombre:'Circuito de aguas',
        descripcion: 'Pasar y mojarse con chorros de agua que estan a diferentes temperaturas, esto con tal de hidratar la piel y tener el cuerpo relajado',
        estatus : 'Inactivo'
    },
];

function inicializarTratamiento()
{
    setDetalleVisible();
    refrescar();
}

function guardar()
{
    var status="";
        if ($('#txtIdTrat').val() === "") 
        {
            Swal.fire('','El campo "Código de tratamiento" es obligatorio','warning');
        }
        else
        {
            if(document.getElementById("statusActivo").checked){
                status="Activo";
            }
            else{
                status="Inactivo";
            }
            var pos = -1;
   
    var tratamiento = new Object();
    tratamiento.id = parseInt($('#txtIdTrat').val());
    tratamiento.nombre =$('#txtNombre').val();
    tratamiento.descripcion = $('#txtDescripcion').val();
    tratamiento.estatus = status;

    pos = buscarPorId(tratamiento.id);
    if(pos < 0)
    {
        tratamientos.push(tratamiento);
    }
    else
    {
        tratamientoss[pos] = tratamientos;
    }
        Swal.fire('Movimiento realizado','Datos del tratamiento guardados correctamente','success');
        refrescar();
        }
     
}

function refrescar()
{
    var contenido = '';
    for (var i = 0; i < tratamientos.length; i++)
    {
        contenido = contenido + '<tr>' +
                                     '<td>' + tratamientos[i].id + '</td>' +
                                     '<td>' + tratamientos[i].nombre + '</td>' + 
                                     '<td>' + tratamientos[i].descripcion + '</td>' + 
                                     '<td>' + tratamientos[i].estatus + '</td>' + 
                                     '<td><a href="#" class="text-light" onclick="mostrarDetalle('+ tratamientos[i].id + ');">Mostrar detalle</a>' + '</td>' +
                                '</tr>';
    }
    
    $('#tbodyTratamientos').html(contenido);
}

function mostrarDetalle(idTratamiento){

    var pos = buscarPorId(idTratamiento);
    var t = tratamientos[pos];
    $('#txtIdTrat').val(t.id);
    $('#txtNombre').val(t.nombre);
    $('#txtDescripcion').val(t.descripcion);
    setDetalleVisible(true);
}
function buscarPorId(id)
{
    for (var i = 0; i < tratamientos.length; i++)
    {
        if(tratamientos[i].id === id)
        {
            return i;
        }
    }
    return -1;
}

function eliminar(){
    var id = parseInt($('#txtIdTrat').val());
    var pos = buscarPorId(id);
    if(pos >= 0)
    {
        tratamientos.splice(pos,1);
        Swal.fire('Movimieto Realizado','Trataiento eliminado','success');
        limpiar();
        refrescar();
    }
    else
    {
        Swal.fire('','Tratamiento no encontrado con el ID especificado','warning');
    }
}

function limpiar()
{
    $('#txtIdTrat').val('');
    $('#txtNombre').val('');
    $('#txtDescripcion').val('');
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
    $('#contenedorPrincipal').html('');
}

