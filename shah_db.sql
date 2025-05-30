#Creacion de la base de datos

CREATE DATABASE SHAH; -- He visto ultimamente que la gente usa schemas pero he leido que es lo mismo, no sé
USE SHAH;

#CREACIÓN DE LA ESTRUCTURA DE TABLAS

#----------------------------------------------

CREATE TABLE administradores(
	correo VARCHAR(254) not null,
    usuario VARCHAR(30)not null,
    contraseña VARCHAR(150) not null,
    primary key(correo)
);

CREATE TABLE jugadores(
	id INT not null auto_increment,
	correo VARCHAR(254) not null,
    usuario VARCHAR(30)not null,
    contraseña VARCHAR(150) not null,
    ranking int DEFAULT 0,
    nivel int DEFAULT 1,
    monedas int DEFAULT 100,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    imagen TEXT, -- en casos de urls muy largas
    primary key(id)
);

CREATE TABLE amigos(
	id_jugador INT not null,
	id_amigo INT not null,
    foreign key(id_jugador) REFERENCES jugadores(id),
    foreign key(id_amigo) REFERENCES jugadores(id),
    primary key(id_jugador, id_amigo)
);

CREATE TABLE chats(
	id INT not null auto_increment,
    id_remitente INT not null,
    id_receptor INT not null,
    mensaje TEXT not null,
    foreign key(id_remitente) REFERENCES jugadores(id),
    foreign key(id_receptor) REFERENCES jugadores(id),
    primary key(id)
);

CREATE TABLE articulos(
	id INT not null auto_increment,
    tipo VARCHAR(7),
    nombre VARCHAR(50),
    precio INT,
    disponibilidad TINYINT, -- my sql trata los boolean internamente como tinyint osea 0 false 1 true y me parecio interesante trabajar directamente con este formato
    primary key(id)
);

CREATE TABLE articulos_adquiridas(
	id_articulo INT not null,
    id_jugador INT not null,
    fecha_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    foreign key(id_articulo) REFERENCES articulos(id),
    foreign key(id_jugador) REFERENCES jugadores(id),
    primary key(id_articulo, id_jugador)
);

CREATE TABLE partidas(
	id INT not null auto_increment,
    id_jugador1 int not null,
    id_jugador2 int not null,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key(id),
    foreign key(id_jugador1) REFERENCES jugadores(id),
    foreign key(id_jugador2) REFERENCES jugadores(id)
);

CREATE TABLE movimientos(
	id INT not null auto_increment,
    id_partida INT not null,
    id_jugador INT not null,
    turno INT not null,
    origen VARCHAR(2),
    destino VARCHAR(2),
    primary key(id),
    foreign key(id_partida) REFERENCES partidas(id),
    foreign key(id_jugador) REFERENCES jugadores(id)
);

#Inserts
INSERT INTO administradores (correo, usuario, contraseña) VALUES
('admin1@ajedrez.com', 'adminMaster', 'ChangeMe'), -- contraseña: Admin123
('supervisor@ajedrez.com', 'gameSupervisor', 'ChangeMe'); -- contraseña: Super123

INSERT INTO jugadores (correo, usuario, contraseña, ranking, nivel, monedas, imagen) VALUES
('jugador1@email.com', 'chessMaster', 'ChangeMe', 1200, 3, 250, 'avatar1.png'),
('jugador2@email.com', 'ajedrezLover', 'ChangeMe', 950, 2, 180, 'avatar2.png'),
('jugador3@email.com', 'reyDelAjedrez', 'ChangeMe', 1500, 5, 500, 'avatar3.png'),
('jugador4@email.com', 'peonValiente', 'ChangeMe', 800, 1, 100, 'avatar4.png');

INSERT INTO amigos (id_jugador, id_amigo) VALUES
(1, 2),
(1, 3),
(2, 1),
(3, 1),
(3, 4),
(4, 3);

INSERT INTO chats (id_remitente, id_receptor, mensaje) VALUES
(1, 2, 'Hola, ¿quieres jugar una partida?'),
(2, 1, '¡Claro! ¿En 10 minutos?'),
(3, 1, 'Buena partida la de ayer'),
(1, 3, 'Gracias, fue muy reñida'),
(4, 3, '¿Me puedes enseñar algunas estrategias?');

INSERT INTO articulos (tipo, nombre, precio, disponibilidad) VALUES
('avatar', 'Dragon Rojo', 200, 1),
('avatar', 'Mago Azul', 150, 1),
('fondo', 'Tablero Clásico', 100, 1),
('fondo', 'Tablero Espacial', 300, 0),
('pieza', 'Estilo Medieval', 250, 1);

INSERT INTO articulos_adquiridas (id_articulo, id_jugador) VALUES
(1, 1),
(3, 1),
(2, 2),
(5, 3),
(3, 4);

INSERT INTO partidas (id_jugador1, id_jugador2) VALUES
(1, 2),
(3, 1),
(3, 4),
(2, 4);

INSERT INTO movimientos (id_partida, id_jugador, turno, origen, destino) VALUES
-- Partida 1
(1, 1, 1, 'e2', 'e4'),
(1, 2, 2, 'e7', 'e5'),
(1, 1, 3, 'g1', 'f3'),
(1, 2, 4, 'b8', 'c6'),

-- Partida 2
(2, 3, 1, 'd2', 'd4'),
(2, 1, 2, 'd7', 'd5'),
(2, 3, 3, 'c1', 'f4'),
(2, 1, 4, 'e7', 'e6');
#Consultas a la bd

USE shah;

SHOW TABLES;
SHOW DATABASES;