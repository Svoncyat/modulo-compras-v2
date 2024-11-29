**Módulo Compras**

**Seguridad:**
- Usuarios 
- Restablecer contraseña 

**Configuración / Maestros**
- Artículos / Productos 
    - Stock (campo, no tabla) 
- Proveedores
- Compradores
- Transacciones
  - Orden de compra _(De quien y a quien le estoy comprando)_
      - Estado _(*emitido)_
  - Ingreso (*depende de la orden de compra) **+ Stock**  
  _(*recibido / *backorder o parcialmente recibido)_ 
  - Devolución (indicar proveedor, detalle) **- Stock**

**Consultas**
- Stock (mostrar lista de artículos y campo de stock)
- Consulta de ordenes con filtro (Nro, proveedor, comprador, estado)

**Reportes (Imprimir)**
- Stock (sin filtro)
- Ordenes (Rango de fechas)

**O/C**
Articulo - Cantidad - PU - Subtotal