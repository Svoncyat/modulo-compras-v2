USE tempdb;
GO

DROP DATABASE IF EXISTS Modulo_Compras;
GO

CREATE DATABASE Modulo_Compras;
GO

USE Modulo_Compras;

CREATE TABLE Usuarios (
    id INT IDENTITY(1,1),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    is_admin BIT NOT NULL DEFAULT 0,

	CONSTRAINT PK_Usuarios PRIMARY KEY (id),
);
GO