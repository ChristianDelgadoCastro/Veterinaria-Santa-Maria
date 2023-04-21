
let d = document;

function leerFoto() {
    let archivo = d.getElementById("imgFotografia").files[0];
    let lector = new FileReader();
    lector.onload = function (event) {
        let fotoBase64 = event.target.result;
        d.getElementById("txtFotoBase64").value = fotoBase64;
        console.log(fotoBase64);
    };
    lector.readAsDataURL(archivo);
}

function registrarNuevoUsuario() {
    let datos = null;
    let params = null;

    let rolNuevo = d.getElementById("cmbRol").value;

    let persona = {
        idPersona: 0,
        nombre: d.getElementById("txtNombre").value,
        apellidoP: d.getElementById("txtApellidoP").value,
        apellidoM: d.getElementById("txtApellidoM").value,
        genero: d.getElementById("cmbGenero").value,
        domicilio: d.getElementById("txtDomicilio").value,
        telefono: d.getElementById("txtTelefono").value,
        rfc: d.getElementById("txtRFC").value,
        email: d.getElementById("txtEmail").value
    };

    let usuario = {
        idUsuario: 0,
        nombreUsuario: d.getElementById("txtUsuario").value,
        contrasenia: d.getElementById("txtContrasenia").value,
        rol: d.getElementById("cmbRol").value,
        fotografia: d.getElementById("txtFotoBase64").value
    };

    let empleado = {
        idEmpleado: 0,
        numeroUnico: "",
        puesto: d.getElementById("cmbPuesto").value,
        persona: persona,
        usuario: usuario
    };

    console.log(empleado);

    let cliente = {
        idCliente: 0,
        numeroUnico: "",
        persona: persona,
        usuario
    };

    console.log(cliente);

    if (rolNuevo === "Cliente") {
        alert("Soy cliente");
        datos = {
            datosCliente: JSON.stringify(cliente)
        };

        params = new URLSearchParams(datos);

        console.log(datos);
        console.log(params);

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
                    iniciarSesion();
                });
    } else {
        if (rolNuevo === "Empleado") {

            datos = {
                datosEmpleado: JSON.stringify(empleado)
            };

            params = new URLSearchParams(datos);

            console.log(datos);
            console.log(params);

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
                        console.log(d.getElementById("cmbPuesto").value)
                        iniciarSesion();
                    });
        }
    }    
}

function iniciarSesion() {
    let user = d.getElementById("txtUsuario").value;
    let password = d.getElementById("txtContrasenia").value;

    if (user === "" || password === "") {
        Swal.fire('Campos Vac&iacuteos', 'Llena los campos necesarios para acceder', 'warning');
    } else {

        let usuario = JSON.stringify({
            nombreUsuario: user,
            contrasenia: password
        });

        let params = new URLSearchParams(usuario);

        try {
            fetch('../servicio/log/in', {
                method: "POST",
                body: `nombreUsuario=${user}&contrasenia=${password}`, //params,
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
            }).then(response => {
                return response.json();
            }).then(function (data) {
                if (data.exception != null) {
                    alert("Error interno en el servidor");
                }
                if (data.error != null) {
                    Swal.fire('Datos Incorrectos', 'Usuario o Contrase&ntilde;a inexistentes', 'error');
                } else {
                    let puesto = data.puesto;
                    let idCliente = data.idCliente;
                    if (typeof idCliente === 'undefined') {
                        if (puesto === 'Administrador'){
                            window.location.replace("/santa_maria_web/admin/index.html");                        
                        } else {
                            if (puesto === 'Veterinario'){
                                window.location.replace("/santa_maria_web/veterinario/index.html");
                            }
                        }
                    } else {
                        window.location.replace("/santa_maria_web/cliente/index.html");
                    }
                    localStorage.setItem('user', JSON.stringify(data));
                    localStorage.setItem('idUsuario', data.usuario.idUsuario);
                    localStorage.setItem('nombre', data.persona.nombre + ' ' + data.persona.apellidoP + ' ' + data.persona.apellidoM);
                    localStorage.setItem('idCliente', idCliente);
                    localStorage.setItem('idEmpleado', data.idEmpleado);
                    localStorage.setItem('numeroUnico', data.numeroUnico);
                    localStorage.setItem('token', data.usuario.token);
                    localStorage.setItem('puesto', data.puesto);
                }
            });
        } catch (error) {
            console.error(error);
            alert("Error al realizar la petición al servidor");
        }
    }
}
