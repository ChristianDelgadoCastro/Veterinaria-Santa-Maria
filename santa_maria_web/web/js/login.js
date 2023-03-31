

/* global fetch */

let d = document;

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
            fetch('./servicio/log/in', {
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
                    Swal.fire('Datos Incorrectos', 'Usuario o Contrasen&aacute inexistentes', 'error');
                } else {
                    let idCliente = data.idCliente;
                    if (typeof idCliente === 'undefined') {
                        window.location.replace("./admin/index.html");
                    } else {
                        window.location.replace("./cliente/index.html");
                    }
                    localStorage.setItem('user', JSON.stringify(data));
                    localStorage.setItem('idUsuario', data.usuario.idUsuario);
                    localStorage.setItem('nombre', data.persona.nombre);
                    localStorage.setItem('idCliente', idCliente);
                    localStorage.setItem('idEmpleado', data.idEmpleado);
                }

            });
        } catch (error) {
            console.error(error);
            alert("Error al realizar la petición al servidor");
        }
    }
}

async function cerrarSesion() {

    let idUsuarioLocal = localStorage.getItem('idUsuario');

    const data = {idUsuario: idUsuarioLocal};
    const params = new URLSearchParams(data);

    // Mostrar SweetAlert2 de confirmación de cierre de sesión
    const result = await Swal.fire({
        title: '¿Desea cerrar sesión?',
        text: 'Se cerrará la sesión actual',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, cerrar sesión',
        cancelButtonText: 'No, cancelar'
    });

    if (result.isConfirmed) {
        // Mostrar SweetAlert2 de cuenta atrás antes de cerrar la sesión
        let timerInterval
        Swal.fire({
            title: 'Cerrando sesión',
            html: 'Se cerrará la sesión en <b></b> segundos.',
            timer: 5000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading()
                timerInterval = setInterval(() => {
                    const content = Swal.getHtmlContainer()
                    if (content) {
                        const b = content.querySelector('b')
                        if (b) {
                            b.textContent = Math.ceil(Swal.getTimerLeft() / 1000)
                        }
                    }
                }, 100)
            },
            willClose: () => {
                clearInterval(timerInterval)
            },
            showCancelButton: true,
            confirmButtonText: 'Confirmar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                // Consumir API para cerrar sesión y redirigir a la página principal
                fetch('http://localhost:8080/santa_maria_web/servicio/log/out', {
                    method: "POST",
                    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                    body: params
                }).then(response => {
                    localStorage.clear();
                    window.location.replace("/santa_maria_web/");
                }).catch(error => {
                    Swal.fire("Cierre de sesión fallido", "vuelve a intentarlo", "error");
                });
            }
        });

        // Cerrar sesión automáticamente después de 5 segundos
        setTimeout(() => {
            fetch('http://localhost:8080/santa_maria_web/servicio/log/out', {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            }).then(response => {
                localStorage.clear();
                window.location.replace("/santa_maria_web/");
            }).catch(error => {
                console.log("Cierre de sesión fallido");
            });
        }, 5000);
    }
    //Si el usuario cancela, no pasa nada
}

/*
 * NOTA: Estas funciones son para inicializar los módulos
 */
let emp; //para inicializar empleados
function cargarModuloEmpleados(){
    fetch("empleado/empleado.html")
            .then(response => {
                return response.text();
            })
            .then(function (html)
            {
                document.getElementById('contenedor_principal').innerHTML = html;
                import('../admin/empleado/empleado.js')
                        .then(obj => {
                            emp = obj;
                            emp.inicializarEmpleado();
                        });
            });
}