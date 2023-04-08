var intentos = 1;
function validarEmpleado()
{
    var usuario = document.getElementById("txtUsuario").value;
    var contrasena = document.getElementById("txtContrasena").value;
    
    if(usuario === "DOGICO" && contrasena === "Dogico2022")
    {
        window.location = "../../main.html";
    }
    else
    {
                Swal.fire({
                            icon: 'error',
                            title: 'Datos Incorrectos',
                            text: 'Vuelve a intentarlo'
                          })
                    
    }
}

function validarCliente()
{
    var usuario = document.getElementById("txtUsuario").value;
    var contrasena = document.getElementById("txtContrasena").value;
    
    if(usuario === "cliente" && contrasena === "Cliente")
    {
        window.location = "../../main.html";
    }
    else
    {
                Swal.fire({
                            icon: 'error',
                            title: 'Datos Incorrectos',
                            text: 'Vuelve a intentarlo'
                          });
                    
    }
}
    
