-- -----------------------------------------------------
-- Placeholder table for view View_DetalleVenta
-- -----------------------------------------------------
-- Vista que muestra el detalle de las ventas
CREATE VIEW View_DetalleVenta AS
SELECT
    d.cantidad_Prod,  -- Cantidad de producto vendido
    p.nombre_Prod,    -- Nombre del producto
    p.precio_Venta,   -- Precio de venta del producto
    d.total_Venta,    -- Total de la venta
    v.id_Venta        -- ID de la venta
FROM DETALLE_VENTAS AS d
         JOIN PRODUCTO p ON d.PRODUCTO_id_Prod = p.id_Prod
         JOIN VENTAS v ON d.VENTAS_id_Venta = v.id_Venta;

-- Selecciona todos los registros de la vista View_DetalleVenta
SELECT * FROM View_DetalleVenta;

-- -----------------------------------------------------
-- Placeholder table for view View_Venta
-- -----------------------------------------------------
-- Vista que muestra la información de las ventas
CREATE VIEW View_Venta AS
SELECT
    v.id_Venta,         -- ID de la venta
    v.cant_productos,   -- Cantidad de productos vendidos
    v.total_Venta,      -- Total de la venta
    v.fecha_Venta,      -- Fecha de la venta
    v.metodo_pago,      -- Método de pago utilizado
    e.idEmpleado,       -- ID del empleado que realizó la venta
    e.nombre,           -- Nombre del empleado
    e.apellidos         -- Apellidos del empleado
FROM VENTAS AS v
         JOIN EMPLEADO e ON v.EMPLEADO_idEmpleado = e.idEmpleado;

-- Selecciona todos los registros de la vista View_Venta
SELECT * FROM View_Venta;