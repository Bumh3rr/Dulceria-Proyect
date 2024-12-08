-- -----------------------------------------------------
-- procedure RegisterSale
-- -----------------------------------------------------
USE Dulceria;
DROP procedure IF EXISTS RegisterSale;

DELIMITER $$

/**
 * Procedimiento para registrar una venta en la base de datos Dulceria.
 *
 * @param p_id_Trab INT - ID del empleado que realiza la venta.
 * @param p_cant_productos INT - Número de productos vendidos.
 * @param p_total_venta DOUBLE - Monto total de la venta.
 * @param p_fecha_venta DATETIME - Fecha y hora de la venta.
 * @param p_metodoPago VARCHAR(15) - Método de pago utilizado para la venta.
 * @param p_detalle JSON - Arreglo JSON que contiene los detalles de los productos vendidos.
 */
CREATE PROCEDURE RegisterSale(
    IN p_id_Trab INT,
    IN p_cant_productos INT,
    IN p_total_venta DOUBLE,
    IN p_fecha_venta DATETIME,
    IN p_metodoPago VARCHAR(15),
    IN p_detalle JSON
)
BEGIN
    DECLARE sale_id INT;

    -- Insertar los detalles de la venta en la tabla VENTAS
INSERT INTO VENTAS(EMPLEADO_idEmpleado, cant_productos, total_venta, fecha_venta, metodo_pago)
VALUES (p_id_Trab, p_cant_productos, p_total_venta, p_fecha_venta, p_metodoPago);

-- Obtener el ID de la última venta insertada
SET sale_id = LAST_INSERT_ID();

    -- Insertar los detalles de los productos en la tabla DETALLE_VENTAS
INSERT INTO DETALLE_VENTAS(VENTAS_id_Venta, PRODUCTO_id_Prod, cantidad_Prod, total_Venta)
SELECT sale_id, JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.id_producto')),
       JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.cantidad')),
       JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.total'))
FROM JSON_TABLE(p_detalle, '$[*]' COLUMNS (value JSON PATH '$')) d;

-- Actualizar el stock de los productos vendidos
UPDATE PRODUCTO p
    JOIN (
    SELECT JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.id_producto')) AS id_producto,
    JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.cantidad')) AS cantidad
    FROM JSON_TABLE(p_detalle, '$[*]' COLUMNS (value JSON PATH '$')) d
    ) t
ON p.id_Prod = t.id_producto
    SET p.stock_Disp = p.stock_Disp - t.cantidad
WHERE p.stock_Disp >= t.cantidad;

-- Verificar si la actualización del stock fue exitosa, si no, generar un error
IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Stock insuficiente para uno o más productos';
END IF;

END$$
DELIMITER ;