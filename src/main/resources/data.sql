INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario1@mail.com', 'Juan', '1234', 'Propietario', -34.6523007, -58.6235996);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario2@mail.com', 'Pedro', '1234', 'Propietario', -34.6523051, -58.6210247);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario3@mail.com', 'Leonel', '1234', 'Propietario', -34.652111, -58.618521);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario4@mail.com', 'Rodrigo', '1234', 'Propietario', -34.632180, -58.608109);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario4@mail.com', 'Federico', '1234', 'Propietario', -34.642509, -58.638187);

INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario5@mail.com', 'Gustavo', '1234', 'Propietario', -34.66, -58.66);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario6@mail.com', 'Oscar', '1234', 'Propietario', -34.67, -58.695);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario7@mail.com', 'Julián', '1234', 'Propietario', -34.69, -58.69);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario8@mail.com', 'Lautaro', '1234', 'Propietario', -34.67, -58.67);
INSERT INTO Usuario(id, email, nombre, password, rol, latitud, longitud)
VALUES (null, 'propietario9@mail.com', 'Lucas', '1234', 'Propietario', -34.6691, -58.6691);

INSERT INTO Usuario(id, email, nombre, password, rol)
VALUES (null, 'cliente1@mail.com', 'Matías', '1234', 'Cliente');
INSERT INTO Usuario(id, email, nombre, password, rol)
VALUES (null, 'cliente2@mail.com', 'Gustavo', '1234', 'Cliente');

INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Mountain Bike Roja', 'DISPONIBLE', 1,
        "https://www.heavenimagenes.com/heavencommerce/68ac9d04-8767-4aca-9951-49f2fea1383b/images/v2/SLP/27845_medium.jpg",
        "PERFECTO_ESTADO", 50000.0, 600.0, 20);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Mountain Bike', 'DISPONIBLE', 1,
        "https://http2.mlstatic.com/D_NQ_NP_682936-MLA44939295785_022021-O.webp",
        "PERFECTO_ESTADO", 50000.0, 600.0, 14);

INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Playera con canasto', 'DISPONIBLE', 1,
        "https://acdn.mitiendanube.com/stores/052/201/products/bic14006-bicicleta-playera-canasto-soporte-parrila-paseo1-fb3e00a27bc212fada15870362344533-1024-1024.jpeg",
        "PERFECTO_ESTADO", 50000.0, 600.0, 8);

INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Playera freno contra-pedal', 'DISPONIBLE', 1,
        "https://biciurbana.com.ar/4892-thickbox_default/bicicleta-playera-contrapedal.jpg",
        "PERFECTO_ESTADO", 50000.0, 600.0, 6);

INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Con silla para niño', 'DISPONIBLE', 1,
        "https://falabella.scene7.com/is/image/Falabella/gsc_115894352_1306693_1?wid=1500&hei=1500&qlt=70",
        "PERFECTO_ESTADO", 50000.0, 600.0, 12);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'BMX', 'DISPONIBLE', 1,
        "https://http2.mlstatic.com/D_NQ_NP_908386-MLA47007741860_082021-O.webp",
        "PERFECTO_ESTADO", 50000.0, 600.0, 9);

INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Bicicleta para 2', 'REQUIERE_REPARACION', 1,
        "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-cruiser.jpg", "BUENO_ESTADO",
        50000.0, 600.0, 4);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Bicicletas negra con llantas fuxias', 'EN_USO', 2,
        "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-fixie.jpg", "MAL_ESTADO",
        50000.0, 600.0, 2);

-- Bicicleta 1 Mountain Bike Roja
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Tambalea un poco al doblar.', current_timestamp, 3, 1, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Muy buena bicicleta.', current_timestamp, 5, 1, 4);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, ' LE DARIA 5 PERO NO ME GUSTA K NO TENGA BOSINA, PUEDEN HAGREGARLE? GRASIA.', current_timestamp, 4, 1, 4);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Deje de usar Uber para ir al laburo y de paso hago cardio 10/10.', current_timestamp, 5, 1, 4);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'No quiero ser hater, pero el color es muy feo.', current_timestamp, 3, 1, 4);

-- Bicicleta 2 Mountain Bike
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'La cadena oxidada, por lo demas... un lujo.', current_timestamp, 5, 2, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Excelente elección.', current_timestamp, 4, 2, 5);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'La utilizo siempre que esta disponible para alquilar, siento que estoy formando vinculos con esta bici.',
        current_timestamp, 5, 2, 2);

-- Bicicleta 3 Playera con canasto
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Si quieren tentar a la muerte, alquilenla. Yo casi no la cuento.', current_timestamp, 2, 3, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Buena relación calidad-precio.', current_timestamp, 3, 3, 2);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'El canasto es ideal si tienen mochilas o bolsas.', current_timestamp, 3, 3, 2);

-- Bicicleta 4 Playera freno contra-pedal
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Buena bicicleta para principiantes.', current_timestamp, 4, 4, 1);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 2, 4, 6);

-- Bicicleta 5 Con silla para niño
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null,
        'Ideal para llevar al peque al jardin. El grupo de mamis me consulto donde la habia alquilado. Recomendada!.',
        current_timestamp, 5, 5, 2);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Mejor de lo esperado.', current_timestamp, 5, 5, 2);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Frena solo contra los arboles, peligrosa, pero razonable por el precio.', current_timestamp, 2, 5, 4);

-- Bicicleta 6 BMX
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Supero mis prejuicios iniciales.', current_timestamp, 4, 6, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Satisfecho con el alquiler.', current_timestamp, 4, 6, 5);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'La sensacion de no saber si llegas vivo por la falta de frenos es lo mejor.', current_timestamp, 1, 6,
        5);

INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, bicicleta_id, usuario_id)
VALUES (1, 2, 'EN_USO', 1, 3);
INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, bicicleta_id, usuario_id)
VALUES (2, 3, 'EN_USO', 1, 4);
INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, fechaAlquiler, precioAlquiler, bicicleta_id, usuario_id)
VALUES (3, 4, 'DISPONIBLE', '2023-11-22 10:30:30', 2400, 1, 11);
INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, fechaAlquiler, precioAlquiler, bicicleta_id, usuario_id)
VALUES (4, 8, 'DISPONIBLE', '2023-11-23 17:45:10', 4800, 1, 11);