INSERT INTO Usuario(id, email, password, rol)
VALUES (null, 'propietario1@mail.com', '1234', 'Propietario');
INSERT INTO Usuario(id, email, password, rol)
VALUES (null, 'propietario2@mail.com', '1234', 'Propietario');
INSERT INTO Usuario(id, email, password, rol)
VALUES (null, 'cliente1@mail.com', '1234', 'Cliente');
INSERT INTO Usuario(id, email, password, rol)
VALUES (null, 'cliente2@mail.com', '1234', 'Cliente');
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id)
VALUES (null, 'Mi primer bicicleta', 'DISPONIBLE', 1);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id)
VALUES (null, 'Mi segunda bicicleta', 'REQUIERE_REPARACION', 1);
INSERT INTO Bicicleta(id, descripcion, estadoBicicleta, usuario_id)
VALUES (null, 'Bicicleta roja', 'EN_USO', 2);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Podría ser mejor.', current_timestamp, 3, 1, 3);
INSERT INTO Resena(id, comentario, fecha, puntaje, bicicleta_id, usuario_id)
VALUES (null, 'Muy buena bicicleta.', current_timestamp, 5, 1, 4);