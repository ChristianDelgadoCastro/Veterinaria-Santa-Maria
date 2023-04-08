-- BUSCAR CITAS
DROP PROCEDURE IF exists buscarCitas;
DELIMITER $$
CREATE procedure buscarCitas(in datoBusqueda varchar(100))
begin
	select * from v_citas
	where (
    fechaCita like concat('%', datoBusqueda, '%') or
    horaCita like concat('%', datoBusqueda, '%') or
    clienteNumeroUnico like concat('%', datoBusqueda, '%') or
    clienteNombre like concat('%', datoBusqueda, '%') or
    clienteApellidoP like concat('%', datoBusqueda, '%') or
    clienteApellidoM like concat('%', datoBusqueda, '%') or
    clienteGenero like concat('%', datoBusqueda, '%') or
    clienteDomicilio like concat('%', datoBusqueda, '%') or
    clienteTelefono like concat('%', datoBusqueda, '%') or
    clienteRFC like concat('%', datoBusqueda, '%') OR
    clienteEmail like concat('%', datoBusqueda, '%') OR
    empleadoNumeroUnico like concat('%', datoBusqueda, '%') OR
    empleadoPuesto like concat('%', datoBusqueda, '%') OR
    empleadoNombre like concat('%', datoBusqueda, '%') OR 
    empleadoApellidoP like concat('%', datoBusqueda, '%') OR 
    empleadoApellidoM like concat('%', datoBusqueda, '%') OR 
    empleadoDomicilio like concat('%', datoBusqueda, '%') OR 
    empleadoTelefono like concat('%', datoBusqueda, '%') OR 
    empleadoRFC like concat('%', datoBusqueda, '%') OR 
    empleadoEmail like concat('%', datoBusqueda, '%'));
end
$$
delimiter ;