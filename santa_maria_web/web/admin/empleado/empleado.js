
let empleados = [];
let d = document;

export function inicializarEmpleado() {
    d.getElementById("divEmpleado").style.display = "";
    refrescarTabla();
}

export function refrescarTabla() {
    let url = "../servicio/empleado/getAllActives";
    fetch(url)
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                if (data.exception != null) {
                    Swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    window.location.replace('../index.html');
                    return;
                }
                fillTable(data);
            });
}

function fillTable(data) {
    let contenido = '';
    empleados = data;
    for (let i = 0; i < data.length; i++)
    {
        contenido += '<tr>' +
                '<td style="background-color: rgba(201, 246, 234 ,0.7);">' + data[i].idEmpleado + '</td>' +
                '<td style="background-color: rgba(201, 243, 246, 0.7)">' + data[i].persona.nombre + ' ' + data[i].persona.apellidoP + ' ' + data[i].persona.apellidoM + '</td> ' +
                '<td style="background-color: rgba(201, 246, 234 ,0.7);">' + data[i].persona.telefono + '</td>' +
                '<td style="background-color: rgba(201, 243, 246, 0.7)">' + data[i].persona.email + '</td>' +
                '<td style="background-color: rgba(201, 246, 234 ,0.7);" class="' + (data[i].estatus === 1 ? 'activo' : 'inactivo') + '">' + (data[i].estatus === 1 ? 'activo' : 'inactivo') + '</td>' +
                '<td style="background-color: rgba(201, 243, 246, 0.7)" class="text-info">' +
                '<a href="#" onclick="emp.cargarDetalles(' + i +
                ');"><i class="fa-file text-info"></i>' +
                '</a>' +
                '</td>';
        '</tr>';
    }
    d.getElementById('tbodyEmpleados').innerHTML = contenido;
}


export function cargarDetalles(i) {
    let e = empleados[i];

    if (i >= i) {
        d.getElementById("txtIdEmpleado").value = e.idEmpleado;
        d.getElementById("txtIdPersona").value = e.persona.idPersona;
        d.getElementById("txtIdUsuario").value = e.usuario.idUsuario;
        d.getElementById("txtNumeroEmpleado").value = e.numeroEmpleado;
        d.getElementById("txtNombre").value = e.persona.nombre;
        d.getElementById("txtApellidoP").value = e.persona.apellidoP;
        d.getElementById("txtApellidoM").value = e.persona.apellidoM;
        d.getElementById("cmbGenero").value = e.persona.genero;
        d.getElementById("txtDomicilio").value = e.persona.domicilio;
        d.getElementById("txtTelefono").value = e.persona.telefono;
        d.getElementById("txtRFC").value = e.persona.rfc;
        d.getElementById("txtEmail").value = e.persona.email;
        d.getElementById("imgFotografia").value = e.persona.fotografia;
        d.getElementById("txtUsuario").value = e.usuario.nombreUsuario;
        d.getElementById("txtContrasenia").value = e.usuario.contrasenia;
        d.getElementById("cmbPuesto").value = e.puesto;
    } else {
        Swal.fire('', 'No se encontró el empleado', 'warning');
    }
}

export function guardarEmpleado() {

    let datos = null;
    let params = null;

    let empleado = new Object();
    empleado.persona = new Object();
    empleado.usuario = new Object();

    if (d.getElementById("txtIdEmpleado").value.trim().length < 1) {
        empleado.idEmpleado = 0;
        empleado.persona.idPersona = 0;
        empleado.usuario.idUsuario = 0;
        empleado.numeroEmpleado = "";
    } else {
        empleado.idEmpleado = parseInt(d.getElementById("txtIdEmpleado").value);
        empleado.persona.idPersona = parseInt(d.getElementById("txtIdPersona").value);
        empleado.usuario.idUsuario = parseInt(d.getElementById("txtIdUsuario").value);
        empleado.numeroEmpleado = d.getElementById("txtNumeroEmpleado").value;
    }
    //datos de persona
    empleado.persona.nombre = d.getElementById("txtNombre").value;
    empleado.persona.apellidoP = d.getElementById("txtApellidoP").value;
    empleado.persona.apellidoM = d.getElementById("txtApellidoM").value;
    empleado.persona.genero = d.getElementById("cmbGenero").value;
    empleado.persona.domicilio = d.getElementById("txtDomicilio").value;
    empleado.persona.telefono = d.getElementById("txtTelefono").value;
    empleado.persona.rfc = d.getElementById("txtRFC").value;
    empleado.persona.email = d.getElementById("txtEmail").value;
    empleado.persona.fotografia = ""; // se inicializa la variable fotografia

    // se agrega el evento onChange al input de tipo archivo
    d.getElementById("imgFotografia").addEventListener("change", function () {
        let reader = new FileReader();
        reader.onload = function () {
            empleado.persona.fotografia = reader.result; // se asigna la cadena base64 a la variable fotografia
            console.log(empleado.persona.fotografia); // se muestra la cadena base64 en la consola
            // se valida que se haya seleccionado una imagen antes de enviar la petición fetch
            if (empleado.persona.fotografia.trim().length > 0) {
                //ahora nos preparamos para la petición al fetch
                datos = {
                    datosEmpleado: JSON.stringify(empleado)
                };
                params = new URLSearchParams(datos);

                fetch("../servicio/empleado/save", {
                    method: "POST",
                    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                    body: params
                })
                .then(response => response.json())
                .then(function (data) {
                    if (data.exception != null) {
                        Swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'error');
                        return;
                    }
                    if (data.error != null) {
                        Swal.fire('', data.error, 'warning');
                        return;
                    }
                    if (data.errorperm != null) {
                        Swal.fire('', 'No tienes permiso para realizar esta operación.', 'warning');
                        return;
                    }
                    document.getElementById("txtIdEmpleado").value = data.idEmpleado;
                    document.getElementById("txtIdPersona").value = data.persona.idPersona;
                    document.getElementById("txtIdUsuario").value = data.usuario.idUsuario;
                    document.getElementById("txtNumeroEmpleado").value = data.numeroEmpleado;
                    Swal.fire('', 'Datos del empleado actualizados correctamente', 'success');
                    refrescarTabla();
                });
            }
        };
        reader.readAsDataURL(d.getElementById("imgFotografia").files[0]); // leemos el contenido de la imagen seleccionada
    });
}


export function cambiarEstatus() {
    let idEmpleado = parseInt(document.getElementById("txtIdEmpleado").value);
    let url = '../servicio/empleado/delete?idEmpleado=' + idEmpleado;

    Swal.fire({
        title: 'Borrar registro',
        text: 'Seguro que quieres borrar este registro',
        icon: "warning",
        showCancelButton: true,
        preConfirm: res => {
            if (res) {
                fetch(url)
                        .then(res => res.ok ? res : Promise.reject(res))
                        .catch(err => console.log(err));
                Swal.fire({
                    title: "Todo ha salido bien",
                    text: "Se ha borrado el registro deseado",
                    icon: "success"
                })
                refrescarTabla();
            }
        }
    });
}
