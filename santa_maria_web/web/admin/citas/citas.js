
let clientes = [];
let empleados = [];
let citas = [];
let d = document;

export function inicializarCitas() {
    d.getElementById("divCitas").style.display = "";
    refrescarTabla();
    refrescarClientes();
    refrescarEmpleados();
}

export function refrescarTabla() {
    let url = "../servicio/cita/getAll";
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

export function verActivos() {
    let url = "../servicio/cita/getAllActives";
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

export function verInactivos() {
    let url = "../servicio/cita/getAllInactives";
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

export function buscarCitas() {

    let datoBuscado = d.getElementById("txtBuscador").value;
    let url = "../servicio/cita/search?datoBusqueda=" + datoBuscado;

    if (datoBuscado === "") {
        Swal.fire('Ups! No hay a quien buscar', 'Escribe primero a quien quieres buscar', 'warning');
    } else {
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
}

function fillTable(data) {
    let contenido = '';
    citas = data;
    let numero = 0;
    for (let i = 0; i < data.length; i++)
    {
        numero = i + 1;
        contenido += '<tr>' +
                '<td>' + numero + '</td> ' +
                '<td>' + data[i].fechaCita + '</td>' +
                '<td> Cliente: ' + data[i].cliente.persona.nombre + ' ' + data[i].cliente.persona.apellidoP + ' ' + data[i].cliente.persona.apellidoM + '. Empleado: ' + data[i].empleado.persona.nombre + ' ' + data[i].empleado.persona.apellidoP + ' ' + data[i].empleado.persona.apellidoM + '</td>' +
                '<td class="' + (data[i].estatus === 1 ? 'Activo' : 'Inactivo') + '">' + (data[i].estatus === 1 ? 'Agendada' : 'Cancelada o Concluida') + '</td>' +
                '<td class="text-info">' +
                '<a title="Cargar Detalles" href="#" onclick="cit_emp.cargarDetalles(' + i +
                ');"><img src="../media/lupa.png" width="40" height="40"></a>' +
                '</td>';
        '</tr>';
    }
    console.log(contenido)
    d.getElementById('tbodyCitas').innerHTML = contenido;

}

export function cargarDetalles(i) {
    let c = citas[i];
    if (i >= i) {
        d.getElementById("txtIdCita").value = c.idCita;
        d.getElementById("dtFecha").value = c.fechaCita;
        d.getElementById("hrHora").value = c.horaCita;
        d.getElementById("txtEstatusCita").value = c.estatus;
        d.getElementById("cmbCliente").value = c.cliente.idCliente;
        d.getElementById("cmbEmpleado").value = c.empleado.idEmpleado;
        d.getElementById("txtEstatusCita").value = c.estatus;
        
        console.log(d.getElementById("txtEstatusCita").value)

    } else {
        Swal.fire('', 'No se encontró la cita', 'warning');
    }
}

export function refrescarClientes() {
    let url = "../servicio/cliente/getAllActives";
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
                seleccionarCliente(data);
            });
}

function seleccionarCliente(data) {
    let seleccionable = '';
    clientes = data;

    let anuncio = '<option style="font-weight:bold;">Busca al cliente</option>';

    for (var i = 0; i < data.length; i++) {
        seleccionable += '<option value="' + data[i].idCliente + '">'
                + data[i].persona.nombre + ' ' + data[i].persona.apellidoP + ', '
                + 'Tel&eacute;fono: ' + data[i].persona.telefono + '</option>';
    }

    d.getElementById('cmbCliente').innerHTML = anuncio + seleccionable;
}

export function refrescarEmpleados() {
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
                seleccionarEmpleado(data);
            });
}

function seleccionarEmpleado(data) {
    let seleccionable = '';
    empleados = data;

    let anuncio = '<option style="font-weight:bold;">Busca al empleado:</option>';

    for (var i = 0; i < data.length; i++) {
        if (data[i].puesto === "Veterinario") {
            seleccionable += '<option value="' + data[i].idEmpleado + '">'
                + data[i].persona.nombre + ' ' + data[i].persona.apellidoP + ', '
                + 'Tel&eacute;fono: ' + data[i].persona.telefono + '</option>';
        }
    }

    d.getElementById('cmbEmpleado').innerHTML = anuncio + seleccionable;
}


export function limpiarFormulario() {
    d.getElementById("txtIdCita").value = "";
    d.getElementById("dtFecha").value = "";
    d.getElementById("hrHora").value = "";
    d.getElementById("txtEstatusCita").value = "";
    d.getElementById("cmbCliente").value = "";
    d.getElementById("cmbEmpleado").value = "";
}

export function agendarCita() {

    let datos = null;
    let params = null;

    let cita = new Object();

    let fechaActual = new Date();
    let horaActual = fechaActual.getHours();

    let fechaCita = new Date(d.getElementById("dtFecha").value + "T" + d.getElementById("hrHora").value);

    console.log(d.getElementById("hrHora").value);
    console.log(d.getElementById("dtFecha").value)

    if (fechaCita < fechaActual) {
        Swal.fire('', 'La fecha y hora seleccionadas deben ser mayores o iguales a la fecha y hora actual', 'warning');
        return;
    }

    if (horaActual < 8 || horaActual >= 16) {
        Swal.fire('', 'La hora de la cita debe estar entre las 8:00 y las 16:00', 'warning');
        return;
    }

    if (d.getElementById("txtIdCita").value.trim().length < 1) {
        cita.idCita = 0;
    } else {
        cita.idCita = parseInt(d.getElementById("txtIdCita").value);
    }
    console.log(fechaActual, horaActual)

    cita = {
        idCita: cita.idCita,
        fechaCita: d.getElementById("dtFecha").value,
        horaCita: d.getElementById("hrHora").value,
        cliente: {
            idCliente: d.getElementById("cmbCliente").value
        },
        empleado: {
            idEmpleado: d.getElementById("cmbEmpleado").value
        }
    };
    
    console.log(cita)

    //preparamos nuestros objetos para llamar al servicio
    datos = {
        datosCita: JSON.stringify(cita)
    };
    
    params = new URLSearchParams(datos);

    fetch("../servicio/cita/save",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            }).then(response => {
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
                if (data.errorperm != null) {
                    Swal.fire('', 'No tienes permiso para realizar esta operación.', 'warning');
                    return;
                }
                d.getElementById("txtIdCita").value = data.idEmpleado;
                Swal.fire('', 'La cita se actualiz&oacute; correctamente', 'success');
                refrescarTabla();
                limpiarFormulario();
            });

}


export function cambiarEstatus() {
    
    let idCita = parseInt(d.getElementById("txtIdCita").value);
    let estatusCita = parseInt(d.getElementById("txtEstatusCita").value);
    let url;

    if (estatusCita === 0) {
        url = '../servicio/cita/reactive?idCita=' + idCita;
        Swal.fire({
            title: 'Reactivar registro',
            text: 'Seguro que quieres reactivar este registro',
            icon: "warning",
            showCancelButton: true,
            preConfirm: res => {
                if (res) {
                    fetch(url)
                            .then(res => res.ok ? res : Promise.reject(res))
                            .catch(err => console.log(err));
                    Swal.fire({
                        title: "Todo ha salido bien",
                        text: "Se ha reactivado el registro deseado",
                        icon: "success"
                    })
                    verActivos();
                    limpiarFormulario();
                }
            }
        });
    } else if (estatusCita === 1) {
        url = '../servicio/cita/delete?idCita=' + idCita;
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
                    verInactivos();
                    limpiarFormulario();
                }
            }
        });
    }

}
