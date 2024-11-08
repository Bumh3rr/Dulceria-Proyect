CREATE DATABASE dulceria_1;
USE dulceria_1;

CREATE TABLE proveedor(
    id_prov NUMERIC(5) PRIMARY KEY NOT NULL,
    cant_compra NUMERIC(3) NOT NULL
);

CREATE TABLE trabajador(
    id_trab NUMERIC(5) PRIMARY KEY NOT NULL,
    nom_trab VARCHAR(20),
    puesto_trab VARCHAR(15),
    sueldo NUMERIC(6,2),
    venta_semanal NUMERIC(6,2), 
    comis NUMERIC(6,2)
);

CREATE TABLE categoria(
    id_categoria NUMERIC(2) PRIMARY KEY NOT NULL, 
    tipo VARCHAR(20) NOT NULL
);

CREATE TABLE dulce(
    id_dulce NUMERIC(5) PRIMARY KEY NOT NULL,
    nombre_dulce VARCHAR(20),
    prov NUMERIC(5),
    precio_compra NUMERIC(4,2),
    precio_venta NUMERIC(4,2),	
    tipo NUMERIC(2),
    FOREIGN KEY (prov) REFERENCES proveedor(id_prov),
    FOREIGN KEY (tipo) REFERENCES categoria(id_categoria)
);


INSERT INTO proveedor (id_prov, cant_compra) VALUES 
(10001, 50),
(10002, 75),
(10003, 100),
(10004, 200),
(10005, 150);

INSERT INTO trabajador (id_trab, nom_trab, puesto_trab, sueldo, venta_semanal, comis) VALUES
(20001, 'Juan Perez', 'Vendedor', 5100.50, 1550.00, 155.00),
(20002, 'Maria Lopez', 'Cajera', 4800.75, 1205.25, 120.50),
(20003, 'Carlos Ruiz', 'Supervisor', 6300.00, 1830.50, 183.50),
(20004, 'Laura Mendez', 'Vendedor', 4500.00, 1025.00, 102.50),
(20005, 'Ana Torres', 'Cajera', 4900.00, 1300.00, 130.00),
(20006, 'Pedro Soto', 'Supervisor', 7000.25, 2100.75, 210.50);

INSERT INTO categoria (id_categoria, tipo) VALUES
(1, 'chocolate'),
(2, 'goma'),
(3, 'caramelo'),
(4, 'mazapan'),
(5, 'chicle');

INSERT INTO dulce (id_dulce, nombre_dulce, prov, precio_compra, precio_venta, tipo) VALUES
(30001, 'Choco Delicia', 10001, 5.00, 8.50, 1),
(30002, 'Goma Dulce', 10002, 3.50, 5.00, 2),
(30003, 'Caramelo Suave', 10003, 2.00, 3.50, 3),
(30004, 'Mazapan Real', 10004, 4.00, 6.50, 4),
(30005, 'Chicle Burbuja', 10005, 1.00, 2.00, 5),
(30006, 'Chocolate Oscuro', 10001, 6.50, 10.00, 1),
(30007, 'Gomitas Sabor Fruta', 10002, 2.75, 4.50, 2),
(30008, 'Caramelo Duro', 10003, 2.25, 3.75, 3),
(30009, 'Mazapan Natural', 10004, 4.50, 7.00, 4),
(30010, 'Chicle Frutal', 10005, 1.25, 2.25, 5);

-- consultas dulces --
SELECT d.nombre_dulce, p.id_prov AS proveedor, d.precio_venta, c.tipo AS categoria FROM dulce d JOIN proveedor p ON d.prov = p.id_prov
JOIN categoria c ON d.tipo = c.id_categoria ORDER BY d.precio_venta DESC;

SELECT c.tipo AS categoria, COUNT(d.id_dulce) * 100.0 / (SELECT COUNT(*) FROM dulce) AS porcentaje_dulces FROM categoria c
JOIN dulce d ON c.id_categoria = d.tipo GROUP BY c.tipo;

SELECT p.id_prov AS proveedor, d.nombre_dulce, p.cant_compra FROM proveedor p JOIN dulce d ON p.id_prov = d.prov ORDER BY p.cant_compra DESC;

-- consultas de trabajadores --
SELECT puesto_trab, COUNT(id_trab) AS cantidad_trabajadores FROM trabajador GROUP BY puesto_trab;
SELECT nom_trab, puesto_trab, venta_semanal FROM trabajador ORDER BY venta_semanal DESC;
SELECT puesto_trab, SUM(venta_semanal) AS total_venta_semanal, SUM(comis) AS total_comisiones FROM trabajador GROUP BY puesto_trab;

