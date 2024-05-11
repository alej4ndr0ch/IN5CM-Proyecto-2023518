drop database if exists SuperkinalDB;
 
create database SuperkinalDB;
 
Use SuperkinalDB;
 
-- ********************************** TABLA ********************************** --
 
create table Clientes(
	clienteId int not null auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    telefono varchar(80) not null,
    direccion varchar(50) not null,
    nit varchar(15),
    primary key pk_clienteId(clienteId)
);
 
create table Cargos(
	cargoId int not null auto_increment,
    nombreCargo varchar(30) not null,
    descripcionCargo varchar(100) not null,
    primary key pk_cargoId(cargoId)
);
 
create table Empleados(
	empleadoId int not null auto_increment,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar(30) not null,
    sueldo decimal(10.2) not null,
    encargadoId int not null,
    horaEntrada time not null,
    horaSalida time not null,
    cargoId int not null,
    primary key pk_empleadoId(empleadoId),
    constraint fk_Empleados_Cargos foreign key(cargoId)
		references Cargos(cargoId)
	 -- constraint pk_Empleados_encargadoId foreign key(encargadoId)
		-- references encargadoId(encargadoId)
);

create table Distribuidores(
	distribuidorId int not null,
    nombreDistribuidor varchar(30) not null,
    descripcionDistribuidor varchar(200),
    nitDistribuidor varchar(15) not null,
    telefonoDistribuidor varchar(15) not null,
    web varchar(50),
    primary key pk_distribuidorId(distribuidorId)
);

create table categoriaProductos(
	categoriaProductoId int not null auto_increment,
    nombreCategoria varchar(30) not null,
    descripcionCategoria varchar(100) not null,
    primary key pk_categoriaProductoId(categoriaProductoId)
);

create table productos(
	productoId int not null auto_increment,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100),
    cantidadStock int not null,
    precioVentaUnitaria decimal not null,
    precioVentaMayor decimal not null,
    precioCompra decimal not null,
    imagenProducto Blob,
    distribuidorId int not null,
    categoriaProductoId int not null,
    primary key pk_productoId(productoId),
    constraint pk_Productos_Distribuidores foreign key(distribuidorId)
		references Distribuidores(distribuidorId),
	constraint pk_Productos_categoriaProductos foreign key(categoriaProductoId)
		references categoriaProductos(categoriaProductoId)
);

create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    clienteId int not null,
    empleadoId int not null,
    total decimal,
    primary key pk_facturaId(facturaId),
    constraint fk_Facturas_Clientes foreign key(clienteId)
		references Clientes(clienteId),
	constraint fk_Facturas_Empleados foreign key(empleadoId)
		references Empleados(empleadoId)
);

create table DetalleFaturas(
	detalleFacturaId int not null auto_increment,
    facturaId int not null,
    productoId int not null,
    primary key pk_detalleFacturaId(detalleFacturaId),
    constraint fk_DetalleFactura_Facturas foreign key(facturaId)
		references Facturas(facturaId),
	constraint fk_DetalleFacturas_Productos foreign key(productoId)
		references Productos(productoId)
);

create table TicketSoporte(
	ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(250) not null,
    estatus varchar(30) not null,
    clienteId int not null,
    facturaId int,
    primary key pk_ticketSoporteId(ticketSoporteId),
    constraint fk_TicketSoporte_Clientes foreign key(clienteId)
		references Clientes(clienteId),
	constraint fk_TicketSoporte_Facturas foreign key(facturaId)
		references Facturas(facturaId)
);

create table Promociones(
	promocionId int not null auto_increment,
    precioPromocion decimal(10.2),
    descripcionPromocion varchar(100),
    fechaInicio date not null,
    fechaFinalizacion date not null,
    productoId int not null,
    primary key pk_promocionId(promocionId),
    constraint fk_Promociones_Productos foreign key (productoId)
		references Productos(productoId)
);

create table Compras(
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal,
    primary key pk_compraId(compraId) 
);

create table detalleCompras(
	detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    primary key pk_detalleCompraId(detalleCompraId),
    constraint fk_detalleCompras_Productos foreign key(productoId)
		references Productos(productoId),
	constraint fk_detalleCompras_Compras foreign key(compraId)
		references Compras(compraId)
);


-- ********************************** CRUD ********************************** --

-- ********************************** Clientes ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarClientes(IN nom varchar(30), IN ape varchar(30),IN tel varchar(80), in dir varchar(50))
    BEGIN
        INSERT INTO Clientes (nombre, apellido, telefono, ciudad)
            VALUES (nom, ape, tel, ciu);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarClientes()
    BEGIN
        SELECT
            Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
			Clientes.ciudad,
            Clientes.clienteID
 
                FROM Clientes;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarClientes(IN cliId INT)
	BEGIN
		SELECT
			Clientes.clienteId,
			Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion
				FROM Clientes
					Where clienteId = cliId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarClientes(IN cliId INt)
 
	BEGIN
		DELETE FROM Clientes
			WHERE clienteId = cliId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarClientes(IN cliID int, in nom varchar(30), in ape varchar(30),IN tel varchar(80), in dir varchar(50))
		begin
			update Clientes
				set
					nombre = nom,
                    apellido = ape,
                    telefono = tel,
                    direccion = dir
                    where clienteId = cliId;
		END$$
Delimiter ;

-- ********************************** Cargo ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarCargos(IN nom varchar(30), IN des varchar(100))
    BEGIN
        INSERT INTO Cargos (nombreCargo, descripcionCargo)
            VALUES (nom, des);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarCargos()
    BEGIN
        SELECT
            Cargos.nombreCargo,
            Cargos.descripcionCargo,
            Cargos.cargoId
 
                FROM Cargos;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarCargos(IN carId INT)
	BEGIN
		SELECT
			Cargos.cargoId,
			Cargos.nombreCargo,
            Cargos.descripcionCargo
				FROM Cargos
					Where cargoId = carId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarCargos(IN carId INt)
 
	BEGIN
		DELETE FROM Cargos
			WHERE cargoId = carId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarCargos(IN carId int, in nom varchar(30), in des varchar(100))
		begin
			update Cargos
				set
					nombreCargo = nom,
                    descripcionCargo = des
                    where cargoId = carId;
		END$$
Delimiter ;

-- ********************************** Empleados ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarEmpleados(IN nom varchar(30), IN ape varchar(30),IN sue decimal, in horE time, in horS time)
    BEGIN
        INSERT INTO Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida)
            VALUES (nom, ape, sue, horE, horS);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarEmpleados()
    BEGIN
        SELECT
            Empleados.nombreEmpleado,
            Empleados.apellidoEmpleado,
            Empleados.sueldo,
			Empleados.horaEntrada,
            Empleados.horaSalida,
            Empleados.clienteID
 
                FROM Empleados;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleados(IN empId INT)
	BEGIN
		SELECT
			Empleados.empleadoId,
			Empleados.nombreEmpleado,
            Empleados.apellidoEmpleado,
            Empleados.sueldo,
            Empleados.horaEntrada,
            Empleados.horaSalida
				FROM Empleados
					Where empleadoId = empId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarEmpleados(IN empId INt)
 
	BEGIN
		DELETE FROM Empleados
			WHERE empleadoId = empId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarEmpleados(IN empID int, in nom varchar(30), in ape varchar(30),IN sue decimal, in horE time, in horS time)
		begin
			update Empleados
				set
					nombreEmpleado = nom,
                    apellidoEmpleado = ape,
                    sueldo = sue,
                    horaEntrada = horE,
                    horaSalida = horS
                    where empleadoId = empId;
		END$$
Delimiter ;

-- ********************************** Facturas ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarFacturas(IN fec date, IN hor time,IN cliId int, in empId int, in tot decimal)
    BEGIN
        INSERT INTO Facturas (fecha, hora, clienteId, empleadoId, total)
            VALUES (fec, hor, cliId, empId, tot);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarFacturas()
    BEGIN
        SELECT
            Facturas.fecha,
            Facturas.hora,
            Facturas.clienteId,
			Facturas.empleadoId,
            Facturas.total,
            Facturas.facturaID
 
                FROM Facturas;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarFacturas(IN facId INT)
	BEGIN
		SELECT
			Facturas.facturaId,
			Facturas.fecha,
            Facturas.hora,
            Facturas.clienteId,
            Facturas.empleadoId,
            Facturas.total
				FROM Facturas
					Where facturaId = facId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarFacturas(IN facId INt)
 
	BEGIN
		DELETE FROM Facturas
			WHERE facturaId = facId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarFacturas(IN facId int, in fec date, in hor time,IN cliId int, in empId int,in tot decimal)
		begin
			update Facturas
				set
					fecha = fec,
                    hora = hor,
                    clienteId = cliId,
                    empleadoId = empId,
                    total = tot
                    where facturaId = facId;
		END$$
Delimiter ;

-- ********************************** TicketSoportes ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarTicketSoportes(IN des varchar(250), IN est varchar(30),IN cliId int, in facId int)
    BEGIN
        INSERT INTO TicketSoportes (descripcionTicket, estatus, clienteId, facturaId)
            VALUES (des, est, cliId, facid);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarTicketSoportes()
    BEGIN
        SELECT
            TicketSoportes.descripcionTicket,
            TicketSoportes.estatus,
            TicketSoportes.clienteId,
			TicketSoportes.facturaId,
            TicketSoportes.ticketSoporteId
 
                FROM TicketSoportes;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarTicketSoportes(IN ticId INT)
	BEGIN
		SELECT
			TicketSoportes.ticketSoporteId,
			TicketSoportes.descripcionTicket,
            TicketSoportes.estatus,
            TicketSoportes.clienteId,
            TicketSoportes.facturaId
				FROM TicketSoportes
					Where ticketSoporteId = ticId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarTicketSoportes(IN ticId INt)
 
	BEGIN
		DELETE FROM TicketSoportes
			WHERE ticketSoporteId = ticId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarTicketSoportes(IN ticID int, in des varchar(250), in est varchar(30),IN cliId int, in facId int)
		begin
			update TicketSoportes
				set
					descripcionTicket = des,
                    estatus = est,
                    clienteId = cliId,
                    facturaId = facId
                    where ticketSoporteId = ticId;
		END$$
Delimiter ;

-- ********************************** DetalleFacturas ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarDetalleFacturas(IN facId int, IN proId int)
    BEGIN
        INSERT INTO DetalleFacturas (facturaId, productoId)
            VALUES (facId, proId);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarDetalleFacturas()
    BEGIN
        SELECT
            DetalleFacturas.facturaId,
            DetalleFacturas.productoId,
            DetalleFacturas.detalleFacturaId
 
                FROM DetalleFacturas;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleFacturas(IN detId INT)
	BEGIN
		SELECT
			DetalleFacturas.clienteId,
			DetalleFacturas.facturaId,
            DetalleFacturas.productoId
				FROM DetalleFacturas
					Where detalleFacturaId = detId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarDetalleFacturas(IN detId INt)
 
	BEGIN
		DELETE FROM DetalleFacturas
			WHERE detalleFacturaId = detId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarDetalleFacturas(IN detId int, in cliId int, in facId int)
		begin
			update DetalleFacturas
				set
					clienteId = cliId,
                    facturaId = facId
                    where detalleFacturaId = detId;
		END$$
Delimiter ;

-- ********************************** Promociones ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarPromociones(IN pre decimal, IN des varchar(100),IN fecI date, in fecF date, in proId int)
    BEGIN
        INSERT INTO Promociones (precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId)
            VALUES (pre, des, fecI, fecF, proId);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarPromociones()
    BEGIN
        SELECT
            Promociones.precioPromocion,
            Promociones.descripcionPromocion,
            Promociones.fechaInicio,
			Promociones.fechaFinalizacion,
            Promociones.productoId,
            Promociones.promocionId
 
                FROM Promociones;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarPromociones(IN proId INT)
	BEGIN
		SELECT
			Promociones.promocionId,
			Promociones.precioPromocion,
            Promociones.descripcionPromocion,
            Promociones.fechaInicio,
            Promociones.fechaFinalizacion,
            Promociones.productoId
				FROM Promociones
					Where promocionId = proId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarPromociones(IN proId INt)
 
	BEGIN
		DELETE FROM Promociones
			WHERE promocionId = proId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarPromociones(IN proID int, in pre decimal, in des varchar(100),IN fecI date, in fecF date, in prodId int)
		begin
			update Promociones
				set
					precioPromociones = pre,
                    descripcionPromociones = des,
                    fechaInicio = fecI,
                    fechaFinalizacion = fecF,
                    productoId = prodId
                    where promocionId = proId;
		END$$
Delimiter ;

-- ********************************** CategoriaProductos ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarCategoriaProductos(IN nom varchar(30), IN des varchar(100))
    BEGIN
        INSERT INTO CategoriaProductos (nombreCategoria, descripcionCategoria)
            VALUES (nom, des);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarCategoriaProductos()
    BEGIN
        SELECT
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria,
            CategoriaProductos.categoriaProductoID
 
                FROM CategoriaProductos;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarCategoriaProductos(IN catId INT)
	BEGIN
		SELECT
			CategoriaProductos.categoriaProductoId,
			CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
				FROM CategoriaProductos
					Where categoriaProductoId = catId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarCategoriaProductos(IN catId INt)
 
	BEGIN
		DELETE FROM CategoriaProductos
			WHERE categoriaProductoId = catId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarCategoriaProductos(IN catId int, in nom varchar(30), in des varchar(100))
		begin
			update CategoriaProductos
				set
					nombreCategoria = nom,
                    descripcionCategoria = des
                    where categoriaProductoId = catId;
		END$$
Delimiter ;

-- ********************************** Distribuidores ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarDistribuidores(IN nom varchar(30), IN des varchar(200),IN nit varchar(15), in tel varchar(15),in we varchar(50))
    BEGIN
        INSERT INTO Distribuidores (nombreDistribuidor, descripcionDistribuidor, nitDistribuidor, telefonoDistribuidor, web)
            VALUES (nom, des, nit, tel, we);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarDistribuidores()
    BEGIN
        SELECT
            Distribuidores.nombreDistribuidor,
            Distribuidores.descripcionDistribuidor,
            Distribuidores.nitDistribuidor,
			Distribuidores.telefonoDistribuidor,
            Distribuidores.web,
            Distribuidores.distribuidorID
 
                FROM Distribuidores;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarDistribuidores(IN disId INT)
	BEGIN
		SELECT
			Distribuidores.distribuidorId,
			Distribuidores.nombreDistribuidor,
            Distribuidores.descripcionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
				FROM Distribuidores
					Where distribuidorId = disId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarDistribuidores(IN disId INt)
 
	BEGIN
		DELETE FROM Distribuidores
			WHERE distribuidorId = disId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarDistribuidores(IN disID int, in nom varchar(30), in des varchar(200),IN nit varchar(15), in tel varchar(15), in we varchar(50))
		begin
			update Distribuidores
				set
					nombreDistribuidor = nom,
                    descripcionDistribuidor = des,
                    nitDistribuidor = nit,
                    telefonoDistribuidor = tel,
                    web = we
                    where distribuidorId = disId;
		END$$
Delimiter ;

-- ********************************** Compras ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarCompras(IN fec date, IN tot decimal)
    BEGIN
        INSERT INTO Compras (fechaCompra, totalCompra)
            VALUES (fec, tot);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarCompras()
    BEGIN
        SELECT
            Compras.fechaCompra,
            Compras.totalCompra,
            Compras.compraID
 
                FROM Compras;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarCompras(IN comId INT)
	BEGIN
		SELECT
			Compras.compraId,
			Compras.fechaCompra,
            Compras.totalCompras
				FROM Compras
					Where compraId = comId;
 
	END$$
DELIMITER ;
 
 
DELIMITER $$
 
CREATE PROCEDURE sp_EliminarCompras(IN comId INt)
 
	BEGIN
		DELETE FROM Compras
			WHERE compraId = comId;
 
	END$$
 
DELIMITER ;
 
 
DELIMITER $$
	create procedure sp_EditarCompras(IN comId int, in fec date, in tot decimal)
		begin
			update Compras
				set
					fechaCompra = fec,
                    totalCompra = tot
                    where compraId = comId;
		END$$
Delimiter ;

-- ********************************** DetalleCompras ********************************** --
DELIMITER $$

CREATE PROCEDURE sp_AgregarDetalleCompras(IN canCom INT, IN proId INT, IN comId INT)
    BEGIN
        INSERT INTO DetalleCompras (cantidadCompra, productoId, compraId)
            VALUES (canCom, proId, comId);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarDetalleCompras()
    BEGIN
        SELECT
			DetalleCompras.detalleCompraId,
            DetalleCompras.cantidadCompra,
            DetalleCompras.productoId,
            DetalleCompras.compraId
                FROM DetalleCompras;
    END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarDetalleCompras(IN detComId INT)
	BEGIN
		SELECT
            DetalleCompras.detalleCompraId,
            DetalleCompras.cantidadCompra,
            DetalleCompras.productoId,
            DetalleCompras.compraId
				FROM DetalleCompras
					WHERE detalleCompraId = detComId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarDetalleCompras(IN detComId INT)

	BEGIN
		DELETE FROM DetalleCompras
			WHERE detalleCompraId = detComId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarDetalleCompras(IN detComId INT, IN canCom INT, IN proId INT, IN comId INT)
	BEGIN
		UPDATE DetalleCompras
			SET
				cantidadCompra = canCom,
				productoId = proId,
				compraId = comId
					WHERE detalleCompraId = detComId;
	END$$

DELIMITER ;

set global time_zone = '-6:00';

-- ********************************** PRODUCTOS ********************************** --

DELIMITER $$

CREATE PROCEDURE sp_AgregarProductos(IN nomPro VARCHAR(50), IN desPro VARCHAR(100), IN canSto INT, IN preVenUni DECIMAL(10,2), IN preVenMay DECIMAL(10,2), IN preCom DECIMAL(10,2), IN imaPro BLOB, IN disId INT, IN catProId INT)
    BEGIN
        INSERT INTO Productos (nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductoId)
            VALUES (nomPro, desPro, canSto, preVenUni, preVenMay, preCom, imaPro, disId, catProId);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarProductos()
    BEGIN
        SELECT
			Productos.productoId,
            Productos.nombreProducto,
            Productos.descripcionProducto,
            Productos.cantidadStock,
            Productos.precioVentaUnitario,
            Productos.precioVentaMayor,
            Productos.precioCompra,
            Productos.imagenProducto,
            Productos.distribuidorId,
            Productos.categoriaProductoId
                FROM Productos;
    END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarProductos(IN proId INT)
	BEGIN
		SELECT
            Productos.productoId,
            Productos.nombreProducto,
            Productos.descripcionProducto,
            Productos.cantidadStock,
            Productos.precioVentaUnitario,
            Productos.precioVentaMayor,
            Productos.precioCompra,
            Productos.imagenProducto,
            Productos.distribuidorId,
            Productos.categoriaProductoId
				FROM Productos
					WHERE productoId = proId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarProductos(IN proId INT)

	BEGIN
		DELETE FROM Productos
			WHERE productoId = proId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarProductos(IN proId INT, IN nomPro VARCHAR(50), IN desPro VARCHAR(100), IN canSto INT, IN preVenUni DECIMAL(10,2), IN preVenMay DECIMAL(10,2), IN preCom DECIMAL(10,2), IN imaPro BLOB, IN disId INT, IN catProId INT)
	BEGIN
		UPDATE Productos
			SET
				nombreProducto = nomPro,
				descripcionProducto = desPro,
				cantidadStock = canSto,
				precioVentaUnitario = preVenUni,
				precioVentaMayor = preVenMay,
                precioCompra = preCom,
                imagenProducto = imaPro,
                distribuidorId = disId,
                categoriaProductoId = catProId
					WHERE productoId = proId;
	END$$

DELIMITER ;