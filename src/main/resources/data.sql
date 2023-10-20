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
INSERT INTO Usuario(id, email, nombre, password, rol)
VALUES (null, 'cliente1@mail.com', 'Matías', '1234', 'Cliente');
INSERT INTO Usuario(id, email, nombre, password, rol)
VALUES (null, 'cliente2@mail.com', 'Gustavo', '1234', 'Cliente');

INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id,urlImagen, condicion)
VALUES (null, 'Mi primer bicicleta', 'DISPONIBLE', 1, "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-tandem.jpg", "PERFECTO_ESTADO");
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id,urlImagen, condicion)
VALUES (null, 'Mi segunda bicicleta', 'REQUIERE_REPARACION', 1, "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-cruiser.jpg", "BUENO_ESTADO");
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id ,urlImagen, condicion)
VALUES (null, 'Bicicleta roja', 'EN_USO', 2, "https://www.seguroscatalanaoccidente.com/blog/assets/multimedia/2016/04/bicicleta-fixie.jpg", "MAL_ESTADO");
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 3, 1, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Muy buena bicicleta.', current_timestamp, 5, 1, 4);

INSERT INTO Alquiler(id,cantidadHoras, estadoAlquiler, bicicleta_id, usuario_id)
VALUES (1, 2, 'EN_USO', 1, 3);
INSERT INTO Alquiler(id,cantidadHoras, estadoAlquiler, bicicleta_id, usuario_id)
VALUES (2, 3, 'EN_USO', 1, 4);

