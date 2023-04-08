function cargarModuloEmpleado()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/empleados/empleado.html"
           }).done(function(data)
                  {
                      $("#contenedorPrincipal").html(data);
                  });
}

function cargarModuloCliente()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/clientes/cliente.html"
           }).done(function(data)
                  {
                      $("#contenedorPrincipal").html(data);
                  });
}

function cargarModuloMascota()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/mascotas/mascota.html"
           }).done(function(data)
                  {
                      $("#contenedorPrincipal").html(data);
                  });
}
function cargarModuloCitas()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/citas/citas.html"
           }).done(function(data)
                  {
                      $("#contenedorPrincipal").html(data);
                  });
}
function cargarModuloProducto()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/producto/Producto.html"
           }).done(function(data)
                  {
                      //document.getElementById("contenedorPrincipal").innerHTML = data;
                      $("#contenedorPrincipal").html(data);
                  });
}
function cargarModuloHorario()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/horario/horario.html"
           }).done(function(data)
                  {
                      //document.getElementById("contenedorPrincipal").innerHTML = data;
                      $("#contenedorPrincipal").html(data);
                  });
}

function cargarModuloCliente()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/clientes/cliente.html"
           }).done(function(data)
                  {
                      //document.getElementById("contenedorPrincipal").innerHTML = data;
                      $("#contenedorPrincipal").html(data);
                  });
}

function cargarModuloDCitas()
{
        $.ajax({
        contex : document.body,
        url    : "gestiones/detallecitas/detallecitas.html"
           }).done(function(data)
                  {
                      //document.getElementById("contenedorPrincipal").innerHTML = data;
                      $("#contenedorPrincipal").html(data);
                  });
}