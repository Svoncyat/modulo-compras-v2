USE Modulo_Compras;
GO

-- Tablas
CREATE TABLE Usuarios (
    id INT PRIMARY KEY IDENTITY(1,1),
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    esAdmin BIT NOT NULL DEFAULT 0
);


CREATE TABLE Proveedor (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100) NOT NULL
);

CREATE TABLE Comprador (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100) NOT NULL
);

CREATE TABLE Articulo (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    descripcion TEXT,
    CONSTRAINT CHK_Stock CHECK (stock >= 0),
    
);

CREATE TABLE OrdenCompra (
    id INT PRIMARY KEY IDENTITY(1,1),
    numero VARCHAR(20) NOT NULL UNIQUE,
    fecha DATETIME NOT NULL DEFAULT GETDATE(),
    estado VARCHAR(20) NOT NULL,
    proveedorId INT NOT NULL,
    compradorId INT NOT NULL,
	importeTotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT FK_OrdenCompra_Proveedor FOREIGN KEY (proveedorId) REFERENCES Proveedor(id),
    CONSTRAINT FK_OrdenCompra_Comprador FOREIGN KEY (compradorId) REFERENCES Comprador(id),
    CONSTRAINT CHK_Estado CHECK (estado IN ('Emitido', 'Recibido', 'Backorder')),
	CONSTRAINT CHK_PrecioUnitario CHECK (importeTotal > 0)
);

CREATE TABLE DetalleOrdenCompra (
    id INT PRIMARY KEY IDENTITY(1,1),
    ordenCompraId INT NOT NULL,
    articuloId INT NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT UK_DetalleOrdenCompra UNIQUE (ordenCompraId, articuloId),
    CONSTRAINT FK_DetalleOrdenCompra_OrdenCompra FOREIGN KEY (ordenCompraId) REFERENCES OrdenCompra(id),
    CONSTRAINT FK_DetalleOrdenCompra_Articulo FOREIGN KEY (articuloId) REFERENCES Articulo(id),
    CONSTRAINT CHK_Cantidad CHECK (cantidad > 0),
    CONSTRAINT CHK_PrecioUnitario_Detalle CHECK (precioUnitario > 0)
);

CREATE TABLE Ingreso (
    id INT PRIMARY KEY IDENTITY(1,1),
    ordenCompraId INT NOT NULL,
    articuloId INT NOT NULL,
    cantidadRecibida INT DEFAULT 0, -- Valor predeterminado modificado a 0
    fechaIngreso DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_Ingreso_OrdenCompra FOREIGN KEY (ordenCompraId) REFERENCES OrdenCompra(id),
    CONSTRAINT FK_Ingreso_Articulo FOREIGN KEY (articuloId) REFERENCES Articulo(id),
    CONSTRAINT CHK_CantidadRecibida CHECK (cantidadRecibida >= 0)
);

CREATE TABLE Devolucion (
    id INT PRIMARY KEY IDENTITY(1,1),
    ordenCompraId INT NOT NULL,
    proveedorId INT NOT NULL,
    articuloId INT NOT NULL,
    cantidadDevuelta INT NOT NULL,
    fechaDevolucion DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_Devolucion_OrdenCompra FOREIGN KEY (ordenCompraId) REFERENCES OrdenCompra(id),
    CONSTRAINT FK_Devolucion_Proveedor FOREIGN KEY (proveedorId) REFERENCES Proveedor(id),
    CONSTRAINT FK_Devolucion_Articulo FOREIGN KEY (articuloId) REFERENCES Articulo(id),
    CONSTRAINT CHK_CantidadDevuelta CHECK (cantidadDevuelta > 0)
);