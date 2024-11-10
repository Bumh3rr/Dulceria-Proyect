CREATE DATABASE dulceria_1;
USE dulceria_1;

CREATE TABLE proveedor(
    id_Prov INT PRIMARY KEY AUTO_INCREMENT,
    nom_Prov VARCHAR (20) NOT NULL,
    tlf_Prov VARCHAR (10) NOT NULL,
    cant_Compra NUMERIC(5) NOT NULL
);
CREATE TABLE categoria(
    id_Categoria INT PRIMARY KEY AUTO_INCREMENT, 
    tipo VARCHAR(20) NOT NULL
);
CREATE TABLE producto(
    id_Prod INT PRIMARY KEY AUTO_INCREMENT,
    nombre_Prod VARCHAR(20) NOT NULL,
    id_Prov INT NOT NULL,
    precio_Compra DECIMAL(8,2) NOT NULL,
    precio_Venta DECIMAL(8,2) NOT NULL,	
    id_Categoria INT NOT NULL ,
    stock_Disp NUMERIC (5) NOT NULL CHECK (stock_disp >=0),
    descripcion VARCHAR (50),
    FOREIGN KEY (id_Prov) REFERENCES proveedor(id_Prov),
    FOREIGN KEY (id_Categoria) REFERENCES categoria(id_Categoria)
);
CREATE TABLE trabajador(
    id_Trab INT PRIMARY KEY AUTO_INCREMENT,
    nombre_Trab VARCHAR(20),
    puesto_Trab VARCHAR(15),
    sueldo DECIMAL(8,2),
    venta_semanal DECIMAL(8,2),
    comision DECIMAL(8,2) GENERATED ALWAYS AS (
        CASE 
            WHEN puesto_Trab = 'Vendedor' THEN venta_semanal * 0.10 
            ELSE 0 
        END
    ) STORED
);
CREATE TABLE cliente(
	id_Cliente INT PRIMARY KEY AUTO_INCREMENT,
    nom_Cliente VARCHAR (20)
);
CREATE TABLE ventas(
	id_Venta INT PRIMARY KEY AUTO_INCREMENT,
    id_Cliente INT,
    id_Trab INT,
    total_Venta DECIMAL (8,2),
    fecha_Venta DATETIME,
	FOREIGN KEY (id_Cliente) REFERENCES cliente(id_Cliente),
    FOREIGN KEY (id_Trab) REFERENCES trabajador(id_Trab)
);
CREATE TABLE detalle_Ventas(
	id_Detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_Venta INT NOT NULL,
    id_Prod INT NOT NULL,
    cantidad_Prod INT NOT NULL CHECK (cantidad_Prod > 0),
    precio_Venta DECIMAL(8,2) NOT NULL,
    total_Venta DECIMAL (8,2) NOT NULL,
    FOREIGN KEY (id_Venta) REFERENCES ventas(id_Venta),
    FOREIGN KEY (id_Prod) REFERENCES producto(id_Prod)
);

INSERT INTO proveedor (nom_Prov, tlf_Prov, cant_Compra) VALUES 
('Dulces SA', '1234567890', 100),
('CandyCo', '0987654321', 200),
('SweetTreats', '1122334455', 150),
('MegaDulces', '2233445566', 300),
('HappyTreats', '3344556677', 180);

INSERT INTO categoria (tipo) VALUES 
('Chocolates'),
('Caramelos'),
('Gomitas'),
('Paletas'),
('Chicles'),
('Galletas');

INSERT INTO producto (nombre_Prod, id_Prov, precio_Compra, precio_Venta, id_Categoria, stock_Disp, descripcion) VALUES
('Choco Bar', 1, 5.50, 8.00, 1, 120, 'Barra de chocolate con almendras'),
('Caramelo de Menta', 2, 1.00, 1.50, 2, 500, 'Caramelo sabor menta'),
('Gomitas Frutales', 3, 3.75, 5.00, 3, 300, 'Gomitas sabor frutal'),
('Paleta de Fresa', 4, 2.00, 3.50, 4, 200, 'Paleta con sabor a fresa'),
('Chicle de Frutas', 5, 0.75, 1.25, 5, 600, 'Chicle con diferentes sabores de frutas'),
('Galleta de Chocolate', 1, 2.50, 3.75, 6, 250, 'Galleta crujiente con chispas de chocolate'),
('Chocolate Blanco', 2, 4.00, 6.50, 1, 150, 'Chocolate blanco con nueces'),
('Paleta de Mango', 3, 1.80, 2.80, 4, 180, 'Paleta sabor mango con chile'),
('Gomitas Ácidas', 4, 3.90, 5.50, 3, 350, 'Gomitas con sabor ácido'),
('Caramelo de Café', 5, 1.25, 2.00, 2, 450, 'Caramelo sabor a café');

INSERT INTO trabajador (nombre_Trab, puesto_Trab, sueldo, venta_semanal) VALUES
('Juan Pérez', 'Vendedor', 350.00, 1500.00),
('Ana Gomez', 'Vendedor', 380.00, 1600.00),
('Carlos Ruiz', 'Supervisor', 450.00, 0.00),
('María Lara', 'Vendedor', 360.00, 1400.00),
('José Rivas', 'Vendedor', 370.00, 1300.00),
('Laura Santos', 'Supervisor', 470.00, 0.00);

INSERT INTO cliente (nom_Cliente) VALUES 
('Luis Santos'),
('Carla Torres'),
('Jorge Castillo'),
('Paola Rey'),
('Alejandra Morales'),
('Miguel Herrera'),
('Lucía Fernández'),
('Lucy Salgado'),
('Pedro Jiménez');

INSERT INTO ventas (id_Cliente, id_Trab, total_Venta, fecha_Venta) VALUES
(1, 1, 25.50, '2024-11-01 10:15:00'),
(2, 2, 45.75, '2024-11-02 11:30:00'),
(3, 3, 60.00, '2024-11-03 14:45:00'),
(4, 1, 30.00, '2024-11-04 09:10:00'),
(5, 4, 50.50, '2024-11-05 13:25:00'),
(6, 5, 20.00, '2024-11-06 15:00:00'),
(7, 1, 35.00, '2024-11-07 16:10:00'),
(8, 2, 55.00, '2024-11-08 17:45:00');

INSERT INTO detalle_Ventas (id_Venta, id_Prod, cantidad_Prod, precio_Venta, total_Venta) VALUES
(1, 1, 3, 8.00, 24.00),
(1, 2, 1, 1.50, 1.50),
(2, 3, 5, 5.00, 25.00),
(2, 4, 3, 3.50, 10.50),
(3, 5, 6, 1.25, 7.50),
(3, 6, 2, 3.75, 7.50),
(4, 1, 2, 8.00, 16.00),
(4, 4, 4, 3.50, 14.00),
(5, 3, 3, 5.00, 15.00),
(5, 2, 10, 1.50, 15.00),
(6, 8, 4, 2.80, 11.20),
(6, 7, 3, 6.50, 19.50),
(7, 10, 4, 2.00, 8.00),
(7, 9, 2, 5.50, 11.00),
(8, 1, 5, 8.00, 40.00),
(8, 6, 3, 3.75, 11.25);

-- Consultas para PROVEEDORES
-- 1. Obtener todos los proveedores con una cantidad de compra superior a 150
SELECT * FROM proveedor WHERE cant_Compra > 150;
-- 2. Buscar el nombre y teléfono de proveedores que suministren más de 200 unidades
SELECT nom_Prov, tlf_Prov FROM proveedor WHERE cant_Compra > 200;
-- 3. Contar el número total de proveedores registrados
SELECT COUNT(*) AS total_proveedores FROM proveedor;
-- 4. Mostrar proveedores ordenados por la cantidad de compra en orden descendente
SELECT * FROM proveedor ORDER BY cant_Compra DESC;

-- Consultas para CATEGORIA
-- 1. Obtener todas las categorías de productos disponibles
SELECT * FROM categoria;
-- 2. Buscar el nombre de las categorías que incluyen la palabra "Choco"
SELECT * FROM categoria WHERE tipo LIKE '%Choco%';
-- 3. Contar el número de categorías diferentes
SELECT COUNT(*) AS total_categorias FROM categoria;

-- Consultas para PRODUCTO
-- 1. Obtener todos los productos cuyo precio de venta sea mayor a 5.00
SELECT * FROM producto WHERE precio_Venta > 5.00;
-- 2. Mostrar nombre, stock disponible y descripción de productos con stock menor a 200 unidades
SELECT nombre_Prod, stock_Disp, descripcion FROM producto WHERE stock_Disp < 200;
-- 3. Calcular el margen de ganancia para cada producto (precio de venta - precio de compra)
SELECT nombre_Prod, (precio_Venta - precio_Compra) AS margen_ganancia FROM producto;
-- 4. Buscar productos específicos en la categoría de "Chocolates"
SELECT p.nombre_Prod, p.precio_Venta, p.stock_Disp FROM producto p JOIN categoria c
ON p.id_Categoria = c.id_Categoria WHERE c.tipo = 'Chocolates';

-- Consultas para TRABAJADOR
-- 1. Obtener todos los trabajadores con una comisión calculada
SELECT * FROM trabajador WHERE comision > 0;
-- 2. Mostrar nombre y comisión de vendedores con ventas semanales mayores a 1200
SELECT nombre_Trab, comision FROM trabajador WHERE puesto_Trab = 'Vendedor' AND venta_semanal > 1200;
-- 3. Calcular el salario total (sueldo + comisión) de cada trabajador
SELECT nombre_Trab, (sueldo + comision) AS salario_total FROM trabajador;
-- 4. Contar el número de trabajadores en cada puesto
SELECT puesto_Trab, COUNT(*) AS total_trabajadores FROM trabajador GROUP BY puesto_Trab;

-- Consultas para CLIENTE
-- 1. Obtener todos los clientes registrados
SELECT * FROM cliente;
-- 2. Buscar clientes cuyo nombre empiece con la letra "L"
SELECT * FROM cliente WHERE nom_Cliente LIKE 'L%';
-- 3. Contar el número total de clientes en la base de datos
SELECT COUNT(*) AS total_clientes FROM cliente;

-- Consultas para VENTAS
-- 1. Obtener todas las ventas realizadas por el trabajador con ID 1
SELECT * FROM ventas WHERE id_Trab = 1;
-- 2. Calcular el total de ventas realizadas en noviembre de 2024
SELECT SUM(total_Venta) AS total_noviembre  FROM ventas WHERE MONTH(fecha_Venta) = 11 AND YEAR(fecha_Venta) = 2024;
-- 3. Obtener las ventas ordenadas por fecha en orden descendente
SELECT * FROM ventas ORDER BY fecha_Venta DESC;
-- 4. Mostrar el cliente, trabajador y total de venta de cada transacción
SELECT c.nom_Cliente, t.nombre_Trab, v.total_Venta FROM ventas v JOIN cliente c ON v.id_Cliente = c.id_Cliente JOIN trabajador t ON v.id_Trab = t.id_Trab;

-- Consultas para DETALLE_VENTAS
-- 1. Obtener el detalle de ventas para una venta específica
SELECT * FROM detalle_Ventas WHERE id_Venta = 1;
-- 2. Calcular el total de productos vendidos en todas las ventas
SELECT SUM(cantidad_Prod) AS total_productos_vendidos FROM detalle_Ventas;
-- 3. Obtener el total de venta generado por cada producto
SELECT p.nombre_Prod, SUM(dv.total_Venta) AS total_generado FROM detalle_Ventas dv
JOIN producto p ON dv.id_Prod = p.id_Prod GROUP BY p.nombre_Prod;
-- 4. Mostrar el total de cada venta en detalle con el nombre del producto vendido
SELECT dv.id_Venta, p.nombre_Prod, dv.cantidad_Prod, dv.total_Venta
FROM detalle_Ventas dv JOIN producto p ON dv.id_Prod = p.id_Prod;

-- Consultas randoms
SELECT p.nombre_Prod,  SUM(dv.cantidad_Prod) AS total_cantidad_vendida,
SUM(dv.total_Venta) AS total_generado,  t.nombre_Trab, c.nom_Cliente
FROM detalle_Ventas dv JOIN producto p ON dv.id_Prod = p.id_Prod
JOIN ventas v ON dv.id_Venta = v.id_Venta JOIN trabajador t ON v.id_Trab = t.id_Trab
JOIN cliente c ON v.id_Cliente = c.id_Cliente GROUP BY p.nombre_Prod, t.nombre_Trab, c.nom_Cliente
HAVING SUM(dv.cantidad_Prod) > 10 ORDER BY total_generado DESC;

SELECT c.tipo AS categoria, p.nombre_Prod, SUM(dv.cantidad_Prod) AS total_cantidad_vendida, 
SUM(dv.total_Venta) AS total_generado, pr.nom_Prov FROM detalle_Ventas dv
JOIN producto p ON dv.id_Prod = p.id_Prod JOIN categoria c ON p.id_Categoria = c.id_Categoria
JOIN proveedor pr ON p.id_Prov = pr.id_Prov GROUP BY c.tipo, p.nombre_Prod, pr.nom_Prov
ORDER BY total_generado DESC;

SELECT pr.nom_Prov,  SUM(dv.cantidad_Prod) AS total_cantidad_proporcionada, SUM(dv.total_Venta) AS total_ventas_generadas
FROM proveedor pr JOIN producto p ON pr.id_Prov = p.id_Prov JOIN detalle_Ventas dv ON p.id_Prod = dv.id_Prod
GROUP BY pr.nom_Prov ORDER BY total_cantidad_proporcionada DESC LIMIT 1;








