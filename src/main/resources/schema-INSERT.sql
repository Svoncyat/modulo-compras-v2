USE Modulo_Compras;
GO

-- INSERT para Usuarios

INSERT INTO Usuarios
    (usuario, contrasena, esAdmin)
VALUES
    ('joy', 'correa', 0),
    ('efrain', 'torres', 1),
    ('ayachi', 'ayachi', 0);
GO

INSERT INTO Articulo
    (nombre, stock, descripcion)
VALUES
    ('Laptop HP Envy', 0, 'Laptop de alto rendimiento para profesionales.'),
    ('Taladro Bosch', 0, 'Taladro eléctrico para uso industrial.'),
    ('Sacos de Cemento', 0, 'Cemento Portland tipo I, ideal para construcción.'),
    ('Escritorio Modular', 0, 'Escritorio ergonómico con almacenamiento.'),
    ('Mouse Gamer RGB', 0, 'Mouse con luces RGB y botones programables.'),
    ('Paquete de A4', 0, 'Papel bond blanco para impresoras y copiadoras.'),
    ('Botella de Agua 1L', 0, 'Agua mineral natural en botella plástica.'),
    ('Madera Contrachapada', 0, 'Paneles de madera resistentes para carpintería.'),
    ('Camiseta Dry Fit', 0, 'Camiseta deportiva absorbente de sudor.'),
    ('Cargador Universal', 0, 'Cargador rápido para dispositivos móviles.'),
    ('Martillo de Acero', 0, 'Martillo con mango antideslizante.'),
    ('Pastillas Paracetamol', 0, 'Analgésico y antipirético de 500 mg.'),
    ('Parlantes Bluetooth', 0, 'Parlantes portátiles con conexión inalámbrica.'),
    ('Mascarillas KN95', 0, 'Mascarillas de protección certificada.'),
    ('Juego de Llaves', 0, 'Juego completo de llaves mecánicas.'),
    ('Reloj Inteligente', 0, 'Smartwatch con monitoreo de salud.'),
    ('Silla Giratoria', 0, 'Silla de oficina ajustable con ruedas.'),
    ('Pintura Acrílica', 0, 'Pintura acrílica de alta calidad para paredes.'),
    ('Lámpara LED', 0, 'Lámpara de escritorio con luz ajustable.'),
    ('Audífonos Inalámbricos', 0, 'Audífonos con cancelación de ruido.');
GO

INSERT INTO Comprador
    (nombre, contacto)
VALUES
    ('Luis Andrade', 'luis.andrade@compras.com'),
    ('María Pérez', 'maria.perez@compras.com'),
    ('Pedro Sánchez', 'pedro.sanchez@compras.com'),
    ('Claudia Ramos', 'claudia.ramos@compras.com'),
    ('Jorge Villegas', 'jorge.villegas@compras.com'),
    ('Ana Torres', 'ana.torres@compras.com'),
    ('Carlos Espinoza', 'carlos.espinoza@compras.com'),
    ('Verónica Huerta', 'veronica.huerta@compras.com'),
    ('José Medina', 'jose.medina@compras.com'),
    ('Lucía Romero', 'lucia.romero@compras.com'),
    ('Andrés Salas', 'andres.salas@compras.com'),
    ('Mónica Aguilar', 'monica.aguilar@compras.com'),
    ('Juan Vargas', 'juan.vargas@compras.com'),
    ('Diana Flores', 'diana.flores@compras.com'),
    ('Martín Rojas', 'martin.rojas@compras.com'),
    ('Elena Guzmán', 'elena.guzman@compras.com'),
    ('Sofía Gutiérrez', 'sofia.gutierrez@compras.com'),
    ('Roberto León', 'roberto.leon@compras.com'),
    ('Carmen Castillo', 'carmen.castillo@compras.com'),
    ('Ricardo Herrera', 'ricardo.herrera@compras.com');
GO

INSERT INTO Proveedor
    (nombre, contacto)
VALUES
    ('Insumos Médicos S.A.', 'contacto@insumosmedicos.com'),
    ('Ferretería Global', 'ventas@ferreteriaglobal.com'),
    ('AgroPerú SAC', 'agroperu@correo.com'),
    ('Distribuciones Vega', 'info@distrivega.com'),
    ('Tecnología Express', 'soporte@tecnologiaexpress.com'),
    ('Alimentos del Norte', 'ventas@alimentosnorte.com'),
    ('Hogar y Construcción', 'hogar@construccion.com'),
    ('Industrias Ramírez', 'contacto@industriasramirez.com'),
    ('Servicios Automotrices', 'autos@servauto.com'),
    ('Exportaciones López', 'ventas@exportlopez.com'),
    ('Papelería Universal', 'papel@universal.com'),
    ('Eléctricos del Sur', 'ventas@electricsur.com'),
    ('Agua Pura', 'ventas@aguapura.com'),
    ('Maderas Amazónicas', 'info@maderasamazonicas.com'),
    ('Textiles del Perú', 'contacto@textilesperu.com'),
    ('Plásticos y Más', 'ventas@plasticosymas.com'),
    ('Farmacéuticos Andinos', 'info@farmacosandinos.com'),
    ('Metales Finos SAC', 'ventas@metalesfinos.com'),
    ('Ropa Deportiva', 'ventas@ropadeportiva.com'),
    ('Repuestos Martínez', 'soporte@repuestosmartinez.com');
GO
