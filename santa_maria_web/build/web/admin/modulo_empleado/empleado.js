
let empleados = [];
let d = document;

export function inicializarEmpleado() {
    d.getElementById("divEmpleado").style.display = "";
    refrescarTabla();
}

export function refrescarTabla() {
    let url = "../servicio/empleado/getAll";
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

export function verInactivos() {
    let url = "../servicio/empleado/getAllInactives";
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

export function buscarEmpleados() {
    const btnBuscar = document.querySelector('button[onclick="emp.buscarEmpleados();"]');

    document.addEventListener('keypress', function (event) {
        if (event.keyCode === 13) { // Comprueba si se ha pulsado la tecla Enter
            event.preventDefault(); // Evita que se envíe el formulario
            btnBuscar.click(); // Simula un clic en el botón "Buscar Empleado"
        }
    });

    // Lógica existente de la función buscarEmpleados()
    let datoBuscado = d.getElementById("txtBuscador").value;
    let url = "../servicio/empleado/search?datoBusqueda=" + datoBuscado;

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
    empleados = data;
    let numero = 0;
    for (let i = 0; i < data.length; i++)
    {

        numero = i + 1;
        contenido += '<tr>' +
                '<td>' + numero + '</td> ' +
                '<td>' + data[i].persona.nombre + ' ' + data[i].persona.apellidoP + ' ' + data[i].persona.apellidoM + '</td>' +
                '<td class="' + (data[i].puesto === 'Administrador' ? 'text-success font-weight-bold' : (data[i].puesto === 'Veterinario' ? 'text-primary font-weight-bold' : 'text-orange font-weight-bold')) + '">' + data[i].puesto + '</td>' +
                '<td><img id="imgFotografia' + i + '" src="' + data[i].usuario.fotografia + '" width="50" height="50"></td>' +
                '<td class="' + (data[i].estatus === 1 ? 'Activo' : 'Inactivo') + '">' + (data[i].estatus === 1 ? 'Activo' : 'Inactivo') + '</td>' +
                '<td class="text-info">' +
                '<a title="Cargar Detalles" href="#" onclick="emp.cargarDetalles(' + i +
                ');"><img src="../media/lupa.png" width="40" height="40"></a>' +
                '</td>' +
                '</tr>';
    }
    console.log(contenido)
    d.getElementById('tbodyEmpleados').innerHTML = contenido;
}


export function cargarDetalles(i) {
    let e = empleados[i];
    if (i >= i) {
        d.getElementById("txtIdEmpleado").value = e.idEmpleado;
        d.getElementById("txtIdPersona").value = e.persona.idPersona;
        d.getElementById("txtIdUsuario").value = e.usuario.idUsuario;
        d.getElementById("txtNumeroEmpleado").value = e.numeroUnico;
        d.getElementById("txtNombre").value = e.persona.nombre;
        d.getElementById("txtApellidoP").value = e.persona.apellidoP;
        d.getElementById("txtApellidoM").value = e.persona.apellidoM;
        d.getElementById("cmbGenero").value = e.persona.genero;
        d.getElementById("txtDomicilio").value = e.persona.domicilio;
        d.getElementById("txtTelefono").value = e.persona.telefono;
        d.getElementById("txtRFC").value = e.persona.rfc;
        d.getElementById("txtEmail").value = e.persona.email;
        d.getElementById("imgFotografia" + i).setAttribute("src", e.usuario.fotografia);
        d.getElementById("txtUsuario").value = e.usuario.nombreUsuario;
        d.getElementById("txtContrasenia").value = e.usuario.contrasenia;
        d.getElementById("cmbPuesto").value = e.puesto;
        d.getElementById("txtEstatusEmpleado").value = e.estatus;
        console.log("Mi estatus es " + d.getElementById("txtEstatusEmpleado").value)
        // Agregamos esta línea para actualizar el valor de txtFotoBase64 con la fotografía almacenada
        d.getElementById("txtFotoBase64").value = e.usuario.fotografia;
    } else {
        Swal.fire('', 'No se encontró el empleado', 'warning');
    }
}

export function leerFoto() {
    let archivo = d.getElementById("imgFotografia").files[0];
    let lector = new FileReader();
    lector.onload = function (event) {
        let fotoBase64 = event.target.result;
        d.getElementById("txtFotoBase64").value = fotoBase64;
        console.log(fotoBase64);
    };
    lector.readAsDataURL(archivo);
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
        empleado.numeroUnico = "";
    } else {
        empleado.idEmpleado = parseInt(d.getElementById("txtIdEmpleado").value);
        empleado.persona.idPersona = parseInt(d.getElementById("txtIdPersona").value);
        empleado.usuario.idUsuario = parseInt(d.getElementById("txtIdUsuario").value);
        empleado.numeroUnico = d.getElementById("txtNumeroEmpleado").value;
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

    //Datos de usuaario
    empleado.usuario.nombreUsuario = d.getElementById("txtUsuario").value;
    empleado.usuario.contrasenia = d.getElementById("txtContrasenia").value;
    empleado.usuario.fotografia = d.getElementById("txtFotoBase64").value;//asignamos la foto

    //Datos del empleado
    empleado.puesto = d.getElementById("cmbPuesto").value;

    //preparamos nuestros objetos para llamar al servicio
    datos = {
        datosEmpleado: JSON.stringify(empleado)
    };

    params = new URLSearchParams(datos);
    fetch("../servicio/empleado/save",
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
                d.getElementById("txtIdEmpleado").value = data.idEmpleado;
                d.getElementById("txtIdPersona").value = data.persona.idPersona;
                d.getElementById("txtIdUsuario").value = data.usuario.idUsuario;
                d.getElementById("txtNumeroEmpleado").value = data.numeroUnico;
                Swal.fire('', 'Datos del empleado actualizados correctamente', 'success');
                refrescarTabla();
                limpiarFormulario();
            });

}

export function limpiarFormulario() {
    d.getElementById("txtIdEmpleado").value = "";
    d.getElementById("txtIdPersona").value = "";
    d.getElementById("txtIdUsuario").value = "";
    d.getElementById("txtNumeroEmpleado").value = "";
    d.getElementById("txtNombre").value = "";
    d.getElementById("txtApellidoP").value = "";
    d.getElementById("txtApellidoM").value = "";
    d.getElementById("cmbGenero").value = "";
    d.getElementById("txtDomicilio").value = "";
    d.getElementById("txtTelefono").value = "";
    d.getElementById("txtRFC").value = "";
    d.getElementById("txtEmail").value = "";
    d.getElementById("txtUsuario").value = "";
    d.getElementById("txtContrasenia").value = "";
    d.getElementById("imgFotografia").value = "";
    d.getElementById("txtFotoBase64").value = "";
    d.getElementById("cmbPuesto").value = "";
}

export function cambiarEstatus() {
    let idEmpleado = parseInt(document.getElementById("txtIdEmpleado").value);
    let estatusEmpleado = parseInt(document.getElementById("txtEstatusEmpleado").value);
    let url;

    if (estatusEmpleado === 0) {
        url = '../servicio/empleado/reactive?idEmpleado=' + idEmpleado;
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
    } else if (estatusEmpleado === 1) {
        url = '../servicio/cliente/delete?idCliente=' + idEmpleado;
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

export function generarPDF() {
    alert("Hola si funciono owowowo")
    //Variable para inicializar un nuevo pdf
    var doc = new jsPDF();
    //variales de todos los id's
    let txtEmpNombre = d.getElementById('txtNombre').value;
    let txtEmpApellidoP = d.getElementById('txtApellidoP').value;
    let txtEmpApellidoM = d.getElementById('txtApellidoM').value;
    let txtEmpGenero = d.getElementById('cmbGenero').value;
    let txtEmpDomicilio = d.getElementById('txtDomicilio').value;
    let txtEmpTelefono = d.getElementById('txtTelefono').value;
    let txtEmpRFC = d.getElementById('txtRFC').value;
    let txtEmpEmail = d.getElementById('txtEmail').value;
    let txtPuesto = d.getElementById('cmbPuesto').value;
    //Nombre
    doc.setFontType("bold");
    doc.text(20, 20, "Nombre: ");
    doc.setFontType("italic");
    doc.text(45, 20, txtEmpNombre);
    //Apellido paterno
    doc.setFontType("bold");
    doc.text(20, 30, "Apellido paterno: ");
    doc.setFontType("italic");
    doc.text(70, 30, txtEmpApellidoP);
    //Apellido materno
    doc.setFontType("bold");
    doc.text(20, 40, "Apellido materno: ");
    doc.setFontType("italic");
    doc.text(70, 40, txtEmpApellidoM);
    //Genero
    doc.setFontType("bold");
    doc.text(20, 50, "Genero: ");
    doc.setFontType("italic");
    doc.text(45, 50, txtEmpGenero);
    //Domicilio
    doc.setFontType("bold");
    doc.text(20, 60, "Domicilio: ");
    doc.setFontType("italic");
    doc.text(50, 60, txtEmpDomicilio);
    //Telefono
    doc.setFontType("bold");
    doc.text(20, 70, "Telefono: ");
    doc.setFontType("italic");
    doc.text(50, 70, txtEmpTelefono);
    //RFC
    doc.setFontType("bold");
    doc.text(20, 80, "RFC: ");
    doc.setFontType("italic");
    doc.text(35, 80, txtEmpRFC);
    //Email
    doc.setFontType("bold");
    doc.text(20, 90, "Email: ");
    doc.setFontType("italic");
    doc.text(45, 90, txtEmpEmail);
    //Puesto
    doc.setFontType("bold");
    doc.text(20, 100, "Puesto: ");
    doc.setFontType("italic");
    doc.text(55, 100, txtPuesto);

    doc.save('Reporte ' + txtEmpNombre + ' ' + txtEmpApellidoP + ' ' + txtEmpApellidoM + '.pdf');
}

