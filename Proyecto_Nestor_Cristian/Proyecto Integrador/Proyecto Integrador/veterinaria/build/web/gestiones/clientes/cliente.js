var clientes =[];

function inicializarModulo()
{
    setDetalleVisible();
    refrescarTabla();
}

function guardar()
{
    //Creamos un objeto nuevo
    var cliente = new Object();
    cliente.persona = new Object();
    cliente.usuario = new Object();

    //Agregamos sus propiedades y sus valores:
    cliente.numeroUnico = limpieza($('#txtNumCliente').val());
    cliente.persona.nombre = limpieza($('#txtNombre').val());
    cliente.persona.apellidos = limpieza($('#txtApePat').val());
    cliente.persona.domicilio = limpieza($('#txtDomicilio').val());
    cliente.persona.idPersona = $('#txtPersona').val();
    cliente.usuario.id = $('#txtUsuarioId').val();
    cliente.persona.telefono = limpieza($('#txtTel').val());
    cliente.correo = limpieza($('#txtCorreo').val());
    cliente.persona.rfc = limpieza($('#txtRfc').val());
    cliente.usuario.nombreUsuario = limpieza($('#txtUsuarioC').val());
    cliente.usuario.contrasenia = limpieza($('#txtContrasenaC').val());
    cliente.usuario.rol = $('#txtRol').val();
    cliente.persona.genero = $('#cmbGenero').val();
    cliente.estatus = 1;
    
    //Verificamos que el cliente no exista:
    if($('#txtIdCliente').val().length > 0)
    {
        cliente.id = parseInt($('#txtIdCliente').val());
        cliente.persona.id = parseInt($('#txtPersona').val());
        cliente.usuario.id = parseInt($('#txtUsuarioId').val());
    }
    
    alert(cliente.id);
    
    $.ajax({
                type    : "POST",
                async   : true,
                url     : "api/cliente/save",
                data    : {cliente : JSON.stringify(cliente)}
            })
    .done(function(data){
        if(data.error != null)
        {
            Swal.fire('Error', data.error, 'warning')
        }
        else
        {
            refrescarTabla();
            cliente = data;
            $('#txtIdCliente').val(cliente.id);
            $('#txtPersona').val(cliente.persona.id);
            $('#txtUsuarioId').val(cliente.usuario.id);
            limpiarFormulario();
            //Notificamos al usuario de la accion realizada:
            Swal.fire('Movimiento realizado', 'Datos del cliente guardados correctamente', 'success');
        }
    });
}

function eliminar()
{
    var id = parseInt($('#txtIdCliente').val());
   
   $.ajax({
                type    :   "DELETE",
                url     :   "api/cliente/delete",
                data    :   {
                                idCliente : id
                            }   
          })
    .done(function(data){
        if(data.error != null)
        {
            Swal.fire('Error', data.error,'Warning');
        }
        else
        {
            refrescarTabla();
            limpiarFormulario();
            Swal.fire('Movimiento realizado', 'Registro eliminado','success');
        }
    });
}

function refrescarTabla()
{
    //inicializamos una variable donde vamos a colocar todo el contenido HTML
    //de la tabla clientes
    var contenido = '';
    $.ajax({
                type    : "GET",
                url     : "api/cliente/getAll"
            })
    .done(function (data) {
        if (data.error != null)
        {
            Swal.fire('Error', data.error, 'warning');
        } 
        else
        {
            clientes = data;
            //Recorremos el arreglo de clientes posicion por posicion:
            for (var i = 0; i < data.length; i++)
            {
                //Agregamos un nuevo renglon a la tabla contenido sus respectivas columnas y valores:
                contenido = contenido + '<tr>' +
                                            '<td>' + clientes[i].id + '</td>' +
                                            '<td>' + clientes[i].numeroUnico + '</td>' + 
                                            '<td>' + clientes[i].persona.nombre + ' ' + 
                                                     clientes[i].persona.apellidos + '</td> ' + 
                                                      
                                                      
//                                            '<td>' + clientes[i].calle + '</td>' + 
//                                            '<td>' + clientes[i].numeroUnico + '</td>' + 
                                            '<td>' + clientes[i].persona.domicilio + '</td>' + 
//                                            '<td>' + clientes[i].codigoPostal + '</td>' + 
                                            '<td>' + clientes[i].persona.telefono + '</td>' +
                                            '<td>' + clientes[i].correo + '</td>' +
                                            '<td>' + clientes[i].persona.genero + '</td>' +
                                            '<td>' + clientes[i].persona.rfc + '</td>' +
                                            '<td>' + clientes[i].estatus + '</td>' + 
                                            '<td>' + clientes[i].usuario.nombreUsuario + '</td>' + 
                                            '<td><a href="#" class="text-light" onclick="mostrarDetalle('+ clientes[i].id + ');">Mostrar detalle</a>' + '</td>' +
                                        '</tr>';
            }
    
    $('#tbodyClientes').html(contenido);
        }
    });
}

function mostrarDetalle(idCliente)
{
    //Buscamos la posicion del clinte
    var pos = buscarPosicionPorId(idCliente);
    //Obtenemos el objeto cliente en la posicion requerida 
    var c = clientes[pos];
   
    $('#txtPersona').val(c.persona.id);
    $('#txtUsuarioId').val(c.usuario.id);
    $('#txtUsuarioC').val(c.usuario.nombreUsuario);
    $('#txtContrasenaC').val(c.usuario.contrasenia);
    $('#txtIdCliente').val(c.id);
    $('#txtNumCliente').val(c.numeroUnico);
    $('#txtNombre').val(c.persona.nombre);
    $('#txtApePat').val(c.persona.apellidos);
    $('#txtTel').val(c.persona.telefono);
    $('#txtRfc').val(c.persona.rfc);
    $('#txtCorreo').val(c.correo);
    $('#txtDomicilio').val(c.persona.domicilio);
    $('#txtRol').val(c.usuario.rol);
    $('#cmbGenero').val(c.persona.genero);

    setDetalleVisible(true);
}

/*
 * Esta función busca la posición que tiene un cliente dentro del arreglo de clientes
 * con base en el id que se pasa como parámetro.
 * 
 * La función devuelve la posición que ocupa el elemento buscado.
 * Siel id buscado no coincide con algun id de los elementos del arreglo,
 * la funcion retornra -1,el cual es un valor no válido que indica que 
 * no se encontro un cliente con ese id.
 */
function buscarPosicionPorId(id)
{
    //Recorremos el arreglo posicion por posicion 
    for (var i = 0; i < clientes.length; i++)
    {
        //Evaluamos si el ID del producto en la posicion es igual que el buscado:
        if(clientes[i].id === id)
        {
            return i;
        }
    }
    //Si llegamos a este punto significa que no encontramos algun cliente con un ID
    //igual al buscado, en cuyo casa, regresamos un -1:
    return -1;
}

function limpiarFormulario()
{
    $('#txtIdCliente').val('');
    $('#txtCodigoPersona').val('');
    $('#txtCodigoUsuario').val('');
    $('#txtNumCliente').val('');
    $('#txtNombre').val('');
    $('#txtApePat').val('');
    $('#txtPersona').val('');
    $('#txtUsuarioId').val('');
    $('#txtDomicilio').val('');
    $('#txtTel').val('');
    $('#txtCorreo').val('');
    $('#txtRfc').val('');
    $('#cmbGenero').val('Seleccionar Genero');
    $('#txtUsuarioC').val('');
    $('#txtContrasenaC').val('');
}

/*
 * Esta funcion muestra u oculta el panel de detalle de un cliente
 * si el valor true, el panel de detelle se muestra si el valor es false 
 * se oculta
 */
function setDetalleVisible(valor)
{
    if(valor)
    {
        $('#divDetalle').show();
        $('#divTabla').hide();
    }
    else
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
    
    return texto;
}

function limpieza(texto){
    texto = normalizar(texto);
    texto = sanitizar(texto);
    
    return texto;
}