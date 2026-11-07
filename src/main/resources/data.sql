-- Inserts de las Comunidades Autónomas, ignora si se produce un error en la inserción

INSERT IGNORE INTO regions (id, code, name) VALUES
(1, '01', 'ANDALUCÍA'),
(2, '02', 'ARAGÓN'),
(3, '03', 'ASTURIAS'),
(4, '04', 'BALEARES'),
(5, '05', 'CANARIAS'),
(6, '06', 'CANTABRIA'),
(7, '07', 'CASTILLA Y LEÓN'),
(8, '08', 'CASTILLA-LA MANCHA'),
(9, '09', 'CATALUÑA'),
(10, '10', 'COMUNIDAD VALENCIANA'),
(11, '11', 'EXTREMADURA'),
(12, '12', 'GALICIA'),
(13, '13', 'MADRID'),
(14, '14', 'MURCIA'),
(15, '15', 'NAVARRA'),
(16, '16', 'PAÍS VASCO'),
(17, '17', 'LA RIOJA'),
(18, '18', 'CEUTA Y MELILLA');



INSERT IGNORE INTO estanteria (id, codigo, ubicacion, material, estado) VALUES
(1, 'EST-001', 'Pasillo A', 'Metal', 'Disponible'),
(2, 'EST-002', 'Pasillo B', 'Madera', 'Ocupada'),
(3, 'EST-003', 'Pasillo C', 'Metal', 'Ocupada'),
(4, 'EST-004', 'Pasillo D', 'Plastico', 'Disponible'),
(5, 'EST-005', 'Pasillo E', 'Madera', 'Ocupada');



INSERT IGNORE INTO caja (id, codigo, fecha_empaque, es_fragil, peso_kg, contenido, id_estanteria) VALUES
(1, 'CAJ-001', '2026-10-01', TRUE, 5.50, 'Libros', 1),
(2, 'CAJ-002', '2026-10-02', FALSE, 12.00, 'Herramientas', 1),
(3, 'CAJ-003', '2026-10-03', TRUE, 2.30, 'Vajilla', 2),
(4, 'CAJ-004', '2026-10-04', FALSE, 8.70, 'Ropa', 3),
(5, 'CAJ-005', '2026-10-05', TRUE, 1.20, 'Cristaleria', 3),
(6, 'CAJ-006', '2026-10-06', FALSE, 15.00, 'Juguetes', 5);