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
VALUES (null, 'Mi primer bicicleta', 'DISPONIBLE', 1,
        "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-tandem.jpg",
        "PERFECTO_ESTADO", 50000.0, 600.0, 8);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Mi segunda bicicleta', 'DISPONIBLE', 1,
        "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-tandem.jpg",
        "PERFECTO_ESTADO", 50000.0, 600.0, 5);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Mi tercer bicicleta', 'DISPONIBLE', 1,
        "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-tandem.jpg",
        "PERFECTO_ESTADO", 50000.0, 600.0, 5);

INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Mi segunda bicicleta', 'REQUIERE_REPARACION', 1,
        "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-cruiser.jpg", "BUENO_ESTADO",
        50000.0, 600.0, 4);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id, urlImagen, condicion, precioVenta,
                      precioAlquilerPorHora, puntaje)
VALUES (null, 'Bicicleta roja', 'EN_USO', 2,
        "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-fixie.jpg", "MAL_ESTADO",
        50000.0, 600.0, 2);

INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 3, 1, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Muy buena bicicleta.', current_timestamp, 5, 1, 4);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 5, 2, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 2, 3, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 1, 3, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 1, 3, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 1, 3, 3);


INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, bicicleta_id, usuario_id)
VALUES (1, 2, 'EN_USO', 1, 3);
INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, bicicleta_id, usuario_id)
VALUES (2, 3, 'EN_USO', 1, 4);
INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, fechaAlquiler, precioAlquiler, bicicleta_id, usuario_id)
VALUES (3, 4, 'DISPONIBLE', '2023-11-22 10:30:30', 2400, 1, 11);
INSERT INTO Alquiler(id, cantidadHoras, estadoAlquiler, fechaAlquiler, precioAlquiler, bicicleta_id, usuario_id)
VALUES (4, 8, 'DISPONIBLE', '2023-11-23 17:45:10', 4800, 1, 11);
