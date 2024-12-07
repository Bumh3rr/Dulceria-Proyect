-- -----------------------------------------------------
-- Placeholder table for view View_DetalleVenta
-- -----------------------------------------------------
CREATE VIEW View_DetalleVenta AS
SELECT
    d.cantidad_Prod,
    p.nombre_Prod,
    p.precio_Venta,
    d.total_Venta,
    v.id_Venta
FROM DETALLE_VENTAS AS d
         JOIN PRODUCTO p ON d.PRODUCTO_id_Prod = p.id_Prod
         JOIN VENTAS v ON d.VENTAS_id_Venta = v.id_Venta;
SELECT * FROM View_DetalleVenta;

-- -----------------------------------------------------
-- Placeholder table for view View_Venta
-- -----------------------------------------------------
CREATE VIEW View_Venta AS
SELECT
    v.id_Venta,
    v.cant_productos,
    v.total_Venta,
    v.fecha_Venta,
    v.metodo_pago,
    e.idEmpleado,
    e.nombre,
    e.apellidos
FROM VENTAS AS v
         JOIN EMPLEADO e ON v.EMPLEADO_idEmpleado = e.idEmpleado;
SELECT * FROM View_Venta;