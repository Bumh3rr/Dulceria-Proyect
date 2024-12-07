
-- -----------------------------------------------------
-- procedure RegisterSale
-- -----------------------------------------------------
USE Dulceria;
DROP procedure IF EXISTS RegisterSale;

DELIMITER $$
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

    -- Insert into VENTAS table
    INSERT INTO VENTAS(EMPLEADO_idEmpleado, cant_productos, total_venta, fecha_venta, metodo_pago)
    VALUES (p_id_Trab, p_cant_productos, p_total_venta, p_fecha_venta, p_metodoPago);

    -- Get the last inserted sale ID
    SET sale_id = LAST_INSERT_ID();

    -- Insert into DETALLE_VENTA table
    INSERT INTO DETALLE_VENTAS(VENTAS_id_Venta, PRODUCTO_id_Prod, cantidad_Prod, total_Venta)
    SELECT sale_id, JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.id_producto')),
           JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.cantidad')),
           JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.total'))
    FROM JSON_TABLE(p_detalle, '$[*]' COLUMNS (value JSON PATH '$')) d;
    

    UPDATE PRODUCTO p
    JOIN (
        SELECT JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.id_producto')) AS id_producto,
               JSON_UNQUOTE(JSON_EXTRACT(d.value, '$.cantidad')) AS cantidad
        FROM JSON_TABLE(p_detalle, '$[*]' COLUMNS (value JSON PATH '$')) d
    ) t
    ON p.id_Prod = t.id_producto
    SET p.stock_Disp = p.stock_Disp - t.cantidad
    WHERE p.stock_Disp >= t.cantidad;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Insufficient stock for one or more products';
    END IF;

END$$
DELIMITER ;