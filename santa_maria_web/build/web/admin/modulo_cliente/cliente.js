
let clientes = [];
let d = document;

export function inicializarCliente() {
    d.getElementById("divCliente").style.display = "";
    refrescarTabla();
}

export function refrescarTabla() {
    let url = "../servicio/cliente/getAll";
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
                fillTable(data);
            });
}

export function verInactivos() {
    let url = "../servicio/cliente/getAllInactives";
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

export function buscarClientes() {

    let datoBuscado = d.getElementById("txtBuscador").value;
    let url = "../servicio/cliente/search?datoBusqueda=" + datoBuscado;

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
    clientes = data;
    let numero = 0;
    for (let i = 0; i < data.length; i++)
    {
        numero = i + 1;
        contenido += '<tr>' +
                '<td>' + numero + '</td> ' +
                '<td>' + data[i].persona.nombre + ' ' + data[i].persona.apellidoP + ' ' + data[i].persona.apellidoM + '</td>' +
                '<td>' + data[i].persona.email + '</td>' +
                '<td><img id="imgFotografia' + i + '" src="' + data[i].usuario.fotografia + '" width="50" height="50"></td>' +
                '<td class="' + (data[i].estatus === 1 ? 'Activo' : 'Inactivo') + '">' + (data[i].estatus === 1 ? 'Activo' : 'Inactivo') + '</td>' +
                '<td class="text-info">' +
                '<a title="Cargar Detalles" href="#" onclick="cli.cargarDetalles(' + i +
                ');"><img src="../media/lupa.png" width="40" height="40"></a>' +
                '</td>';
        '</tr>';
    }
    console.log(contenido)
    d.getElementById('tbodyClientes').innerHTML = contenido;
}

export function cargarDetalles(i) {
    let c = clientes[i];
    if (i >= i) {
        d.getElementById("txtIdCliente").value = c.idCliente;
        d.getElementById("txtIdPersona").value = c.persona.idPersona;
        d.getElementById("txtIdUsuario").value = c.usuario.idUsuario;
        d.getElementById("txtNumeroCliente").value = c.numeroUnico;
        d.getElementById("txtNombre").value = c.persona.nombre;
        d.getElementById("txtApellidoP").value = c.persona.apellidoP;
        d.getElementById("txtApellidoM").value = c.persona.apellidoM;
        d.getElementById("cmbGenero").value = c.persona.genero;
        d.getElementById("txtDomicilio").value = c.persona.domicilio;
        d.getElementById("txtTelefono").value = c.persona.telefono;
        d.getElementById("txtRFC").value = c.persona.rfc;
        d.getElementById("txtEmail").value = c.persona.email;
        d.getElementById("imgFotografia" + i).setAttribute("src", c.usuario.fotografia);
        d.getElementById("txtUsuario").value = c.usuario.nombreUsuario;
        d.getElementById("txtContrasenia").value = c.usuario.contrasenia;
        d.getElementById("txtEstatusCliente").value = c.estatus;
        console.log("Mi estatus es " + d.getElementById("txtEstatusCliente").value)
        // Agregamos esta línea para actualizar el valor de txtFotoBase64 con la fotografía almacenada
        d.getElementById("txtFotoBase64").value = c.usuario.fotografia;
        console.log("Lo que tengo en la base de datos: "+c.usuario.fotografia)
        console.log("Lo que tiene el formulario: "+d.getElementById("txtFotoBase64").value)
    } else {
        Swal.fire('', 'No se encontró el cliente', 'warning');
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

export function guardarCliente() {
    let datos = null;
    let params = null;

    let cliente = new Object();
    cliente.persona = new Object();
    cliente.usuario = new Object();

    if (d.getElementById("txtIdCliente").value.trim().length < 1) {
        cliente.idCliente = 0;
        cliente.persona.idPersona = 0;
        cliente.usuario.idUsuario = 0;
        cliente.numeroUnico = "";
    } else {
        cliente.idCliente = parseInt(d.getElementById("txtIdCliente").value);
        cliente.persona.idPersona = parseInt(d.getElementById("txtIdPersona").value);
        cliente.usuario.idUsuario = parseInt(d.getElementById("txtIdUsuario").value);
        cliente.numeroUnico = d.getElementById("txtNumeroCliente").value;
    }

    //datos de persona
    cliente.persona.nombre = d.getElementById("txtNombre").value;
    cliente.persona.apellidoP = d.getElementById("txtApellidoP").value;
    cliente.persona.apellidoM = d.getElementById("txtApellidoM").value;
    cliente.persona.genero = d.getElementById("cmbGenero").value;
    cliente.persona.domicilio = d.getElementById("txtDomicilio").value;
    cliente.persona.telefono = d.getElementById("txtTelefono").value;
    cliente.persona.rfc = d.getElementById("txtRFC").value;
    cliente.persona.email = d.getElementById("txtEmail").value;

    //Datos de usuario
    cliente.usuario.nombreUsuario = d.getElementById("txtUsuario").value;
    cliente.usuario.contrasenia = d.getElementById("txtContrasenia").value;
    cliente.usuario.fotografia = d.getElementById("txtFotoBase64").value;//asignamos la foto

    //preparamos nuestros objetos para llamar al servicio
    datos = {
        datosCliente: JSON.stringify(cliente)
    };

    params = new URLSearchParams(datos);
    fetch("../servicio/cliente/save",
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
                d.getElementById("txtIdCliente").value = data.idEmpleado;
                d.getElementById("txtIdPersona").value = data.persona.idPersona;
                d.getElementById("txtIdUsuario").value = data.usuario.idUsuario;
                d.getElementById("txtNumeroCliente").value = data.numeroUnico;
                Swal.fire('', 'Datos del cliente actualizados correctamente', 'success');
                refrescarTabla();
                limpiarFormulario();
            });
}

export function limpiarFormulario() {
    d.getElementById("txtIdCliente").value = "";
    d.getElementById("txtIdPersona").value = "";
    d.getElementById("txtIdUsuario").value = "";
    d.getElementById("txtNumeroCliente").value = "";
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
}

export function cambiarEstatus() {
    
    let idCliente = parseInt(document.getElementById("txtIdCliente").value);
    let estatusCliente = parseInt(document.getElementById("txtEstatusCliente").value);
    let url;

    if (estatusCliente === 0) {
        url = '../servicio/cliente/reactive?idCliente=' + idCliente;
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
    } else if (estatusCliente === 1) {
        url = '../servicio/cliente/delete?idCliente=' + idCliente;
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
    let txtCliNombre = d.getElementById('txtNombre').value;
    let txtCliApellidoP = d.getElementById('txtApellidoP').value;
    let txtCliApellidoM = d.getElementById('txtApellidoM').value;
    let txtCliGenero = d.getElementById('cmbGenero').value;
    let txtCliDomicilio = d.getElementById('txtDomicilio').value;
    let txtCliTelefono = d.getElementById('txtTelefono').value;
    let txtCliRFC = d.getElementById('txtRFC').value;
    let txtCliEmail = d.getElementById('txtEmail').value;
    //Nombre
    doc.setFontType("bold");
    doc.text(20, 20, "Nombre: ");
    doc.setFontType("italic");
    doc.text(45, 20, txtCliNombre);
    //Apellido paterno
    doc.setFontType("bold");
    doc.text(20, 30, "Apellido paterno: ");
    doc.setFontType("italic");
    doc.text(70, 30, txtCliApellidoP);
    //Apellido materno
    doc.setFontType("bold");
    doc.text(20, 40, "Apellido materno: ");
    doc.setFontType("italic");
    doc.text(70, 40, txtCliApellidoM);
    //Genero
    doc.setFontType("bold");
    doc.text(20, 50, "Genero: ");
    doc.setFontType("italic");
    doc.text(45, 50, txtCliGenero);
    //Domicilio
    doc.setFontType("bold");
    doc.text(20, 60, "Domicilio: ");
    doc.setFontType("italic");
    doc.text(50, 60, txtCliDomicilio);
    //Telefono
    doc.setFontType("bold");
    doc.text(20, 70, "Telefono: ");
    doc.setFontType("italic");
    doc.text(50, 70, txtCliTelefono);
    //RFC
    doc.setFontType("bold");
    doc.text(20, 80, "RFC: ");
    doc.setFontType("italic");
    doc.text(35, 80, txtCliRFC);
    //Email
    doc.setFontType("bold");
    doc.text(20, 90, "Email: ");
    doc.setFontType("italic");
    doc.text(45, 90, txtCliEmail);

    doc.save('Reporte ' + txtCliNombre + ' ' + txtCliApellidoP + ' ' + txtCliApellidoM + '.pdf');
}
