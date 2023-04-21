
let mascotas = [];
let clientes = [];
let d = document;

export function inicializarMascotas() {
    d.getElementById("divMascotas").style.display = "";
    refrescarTabla();
    refrescarClientes();
}

export function refrescarTabla() {
    let url = "../servicio/mascota/getAll";
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
    let url = "../servicio/mascota/getAllActives";
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
    let url = "../servicio/mascota/getAllInactives";
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

export function buscarMascotas() {

    let datoBuscado = d.getElementById("txtBuscador").value;
    let url = "../servicio/mascota/search?datoBusqueda=" + datoBuscado;

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
    console.log('Datos recibidos:', data);
    let contenido = '';
    mascotas = data;
    let numero = 0;
    for (let i = 0; i < data.length; i++)
    {
        numero = i + 1;
        contenido += '<tr>' +
                '<td>' + numero + '</td> ' +
                '<td>' + data[i].nombre + '</td>' +
                '<td>' + data[i].cliente.persona.nombre + ' ' + data[i].cliente.persona.apellidoP + ' ' + data[i].cliente.persona.apellidoM + '</td>' +
                '<td><img id="imgFotografia' + i + '" src="' + data[i].fotografia + '" width="50" height="50"></td>' +
                '<td class="' + (data[i].estatus === 1 ? 'Activo' : 'Inactivo') + '">' + (data[i].estatus === 1 ? 'Activo' : 'Inactivo') + '</td>' +
                '<td class="text-info">' +
                '<a title="Cargar Detalles" href="#" onclick="adm_mas.cargarDetalles(' + i +
                ');"><img src="../media/lupa.png" width="40" height="40"></a>' +
                '</td>';
        '</tr>';
    }
    d.getElementById('tbodyMascotas').innerHTML = contenido;
    console.log(contenido)
}

export function cargarDetalles(i) {
    let m = mascotas[i];
    if (i >= i) {
        d.getElementById("txtIdMascota").value = m.idMascota;
        d.getElementById("cmbCliente").value = m.cliente.idCliente;
        d.getElementById("txtNumeroMascota").value = m.numeroUnico;
        d.getElementById("txtNombre").value = m.nombre;
        d.getElementById("cmbEspecie").value = m.especie;
        d.getElementById("txtRaza").value = m.raza;
        d.getElementById("cmbGenero").value = m.genero;
        d.getElementById("txtCollar").value = m.collar;
        d.getElementById("txtEdad").value = m.edad;
        d.getElementById("txtPeso").value = m.peso;
        d.getElementById("txtDetalles").value = m.detalles;
        d.getElementById("imgFotografia" + i).setAttribute("src", m.fotografia);
        d.getElementById("txtEstatusMascota").value = m.estatus;
        console.log("Mi estatus es " + d.getElementById("txtEstatusMascota").value)
        // Agregamos esta línea para actualizar el valor de txtFotoBase64 con la fotografía almacenada
        d.getElementById("txtFotoBase64").value = m.fotografia;
        console.log("Lo que tengo en la base de datos: " + m.fotografia)
        console.log("Lo que tiene el formulario: " + d.getElementById("txtFotoBase64").value)
    } else {
        Swal.fire('', 'No se encontró la mascota', 'warning');
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
                + data[i].persona.nombre + ' ' + data[i].persona.apellidoP + ' ' + data[i].persona.apellidoM + '</option>';
    }

    d.getElementById('cmbCliente').innerHTML = anuncio + seleccionable;

    console.log(d.getElementById('cmbCliente').innerHTML)
}

export function limpiarFormulario() {
    d.getElementById("txtIdMascota").value = "";
    d.getElementById("cmbCliente").value = "";
    d.getElementById("txtNumeroMascota").value = "";
    d.getElementById("txtNombre").value = "";
    d.getElementById("cmbEspecie").value = "";
    d.getElementById("txtRaza").value = "";
    d.getElementById("txtCollar").value = "";
    d.getElementById("cmbGenero").value = "";
    d.getElementById("txtEdad").value = "";
    d.getElementById("txtPeso").value = "";
    d.getElementById("txtDetalles").value = "";
    d.getElementById("imgFotografia").value = "";
    d.getElementById("txtFotoBase64").value = "";
    d.getElementById("txtEstatusMascota").value = "";
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

export function guardarMascota() {
    let mascota = {
        idMascota: d.getElementById("txtIdMascota").value,
        collar: d.getElementById("txtCollar").value,
        fotografia: d.getElementById("txtFotoBase64").value,
        nombre: d.getElementById("txtNombre").value,
        especie: d.getElementById("cmbEspecie").value,
        raza: d.getElementById("txtRaza").value,
        genero: d.getElementById("cmbGenero").value,
        edad: d.getElementById("txtEdad").value,
        peso: d.getElementById("txtPeso").value,
        detalles: d.getElementById("txtDetalles").value,
        cliente: {
            idCliente: d.getElementById("cmbCliente").value
        }
    };

    //preparamos nuestros objetos para llamar al servicio
    let datos = {
        datosMascota: JSON.stringify(mascota)
    };

    let params = new URLSearchParams(datos);

    fetch("../servicio/mascota/save", {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    }).then(response => {
        return response.json();
    }).then(data => {
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
        if (data.idMascota != null) {
            Swal.fire('', 'La mascota se actualizó correctamente', 'success');
            refrescarTabla();
            limpiarFormulario();
            console.log("La mascota se actualizo correctamente")
        }
    }).catch(error => {
        Swal.fire('', 'Error al procesar la solicitud. Intente nuevamente más tarde.', 'error');
    });
}

export function cambiarEstatus() {

    let idMascota = parseInt(d.getElementById("txtIdMascota").value);
    let estatusMascota = parseInt(d.getElementById("txtEstatusMascota").value);
    let url;

    if (estatusMascota === 0) {
        url = '../servicio/mascota/reactive?idMascota=' + idMascota;
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
    } else if (estatusMascota === 1) {
        url = '../servicio/mascota/delete?idMascota=' + idMascota;
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

export function hacerPDF(){
    var doc = new jsPDF('l', '', 'letter');
    doc.setFillColor(255, 0, 0);

    let txtMascotaNombre = d.getElementById('txtNombre').value;
    let txtEspecie = d.getElementById("cmbEspecie").value;
    let txtRaza = d.getElementById("txtRaza").value;
    let txtCliGenero = d.getElementById('cmbGenero').value;
    if (txtCliGenero === 'Macho') {
        txtCliGenero = 'Macho';
    } else if (txtCliGenero === 'Hembra') {
        txtCliGenero = 'Hembra';
    } else {
        txtCliGenero = 'Desconocido';
    }
    let txtEdad = d.getElementById('txtEdad').value;
    let txtPeso = d.getElementById('txtPeso').value;
    let txtDetalles = d.getElementById('txtDetalles').value;
    //let fotoVete = "../../media/logo.png";

    let fotoVete = new Image();
    fotoVete.src = "../media/logo.png";
    let fotoMascota = new Image();
    let fotoMascotaValor = d.getElementById("txtFotoBase64").value;
    if (fotoMascotaValor === "") {
        // Si el campo está vacío, establecemos una imagen por defecto
        Swal.fire({
            icon: "warning",
            title: "Carga una fotografía primero",
            showConfirmButton: true,
            confirmButtonText: "Aceptar",
            allowOutsideClick: false,
        });
    } else {
        // Si el campo tiene valor, usamos la imagen correspondiente
        fotoMascota.src = fotoMascotaValor;
    }

    fotoVete.onload = () => {
        //Fotografia Veterinaria
        doc.addImage(fotoVete, "PNG", 15, 0, 80, 70);
        // Establecer el color de relleno del rectángulo como azul claro
        doc.setFillColor(200, 220, 255);
        // Dibujar un rectángulo 
        doc.rect(200, 60, 50, 50, 'F');
        //Fotografia del cliente
        doc.addImage(fotoMascota, "JPEG", 202.5, 62.5, 45, 45);
        //Encabezado
        doc.setFont("courier");
        doc.setFontSize(28);
        doc.text(80, 35, 'Bienvenido a la Veterinaria');
        doc.setFont("courier");
        doc.setFontSize(28);
        doc.setFontType("bold");
        doc.text(110, 45, '"Santa María"');
        doc.setFont("courier");
        doc.setFontType("italic");
        doc.setFontSize(14);
        doc.text(80, 55, 'Este documento contiene los datos de nuestra mascota');
        //Nombre
        doc.setFontType("bold");
        doc.setFontSize(14);
        doc.text(20, 80, "Nombre: ");
        doc.setFontType("italic");
        doc.text(45, 80, txtMascotaNombre);
        //Apellido paterno
        doc.setFontType("bold");
        doc.text(20, 90, "Especie: ");
        doc.setFontType("italic");
        doc.text(70, 90, txtEspecie);
        //Apellido materno
        doc.setFontType("bold");
        doc.text(20, 100, "Raza: ");
        doc.setFontType("italic");
        doc.text(70, 100, txtRaza);
        //Genero
        doc.setFontType("bold");
        doc.text(20, 110, "Genero: ");
        doc.setFontType("italic");
        doc.text(45, 110, txtCliGenero);
        //Domicilio
        doc.setFontType("bold");
        doc.text(20, 120, "Edad: ");
        doc.setFontType("italic");
        doc.text(50, 120, txtEdad);
        //Telefono
        doc.setFontType("bold");
        doc.text(20, 130, "Pesp: ");
        doc.setFontType("italic");
        doc.text(50, 130, txtPeso);
        //RFC
        doc.setFontType("bold");
        doc.text(20, 140, "RFC: ");
        doc.setFontType("italic");
        doc.text(35, 140, txtDetalles);

        doc.save('Reporte de mascota ' + txtMascotaNombre +'.pdf');
    };
}
