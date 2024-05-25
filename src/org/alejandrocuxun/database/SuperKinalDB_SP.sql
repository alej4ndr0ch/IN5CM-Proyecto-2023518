Use SuperKinalDB;
-- ********************************** CRUD ********************************** --

-- ********************************** Clientes ********************************** --
 
DELIMITER $$
 
Create procedure sp_AgregarClientes(IN nom varchar(30), IN ape varchar(30),IN tel varchar(15), in dir varchar(150), in nit varchar(15))
    BEGIN
        INSERT INTO Clientes (nombre, apellido, telefono, direccion, nit)
            VALUES (nom, ape, tel, direccion, nit);
     END$$
DELIMITER ;

 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarClientes()
    BEGIN
        SELECT Clientes.clienteID
            Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion,
            Clientes.nit

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
                Clientes.direccion,
                Clientes.nit

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
                    direccion = dir,
                    nit = nit
                    where clienteId = cliId;
		END$$
Delimiter ;

-- ********************************** Cargo ********************************** --
 
DELIMITER $$
create procedure sp_AgregarCargos(IN nomC varchar(30), IN descC varchar(100))
    BEGIN
        INSERT INTO Cargos (nombreCargo, descripcionCargo)
            VALUES (nomC, descC);
     END$$
DELIMITER ;
 
DELIMITER $$ 
CREATE PROCEDURE sp_ListarCargos()
    BEGIN
        SELECT
            Cargos.cargoId
            Cargos.nombreCargo,
            Cargos.descripcionCargo
 
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
            nombreCargo = nomC,
            descripcionCargo = descC
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
            Empleados.empleadoId, 
            Empleados.nombreEmpleado, 
            Empleados.apellidoEmpleado, 
            Empleados.sueldo, 
            Empleados.horaEntrada, 
            Empleados.horaSalida,
			CONCAT("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) AS cargo, 
			CONCAT(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) AS nombreEncargado
			FROM Empleados EP
			JOIN Cargos Ca ON EP.cargoId = Ca.cargoId
			LEFT JOIN Empleados EE ON EP.encargadoId = EE.empleadoId;
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
                Empleados.horaSalida,
                CONCAT("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) AS cargo, 
                CONCAT(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) AS nombreEncargado
		FROM Empleados EP
		JOIN Cargos Ca ON EP.cargoId = Ca.cargoId
		LEFT JOIN Empleados EE ON EP.encargadoId = EE.empleadoId
		WHERE EP.empleadoId = empId;
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
	create procedure sp_EditarEmpleados(IN empID int, in nom varchar(30), in ape varchar(30),IN sue decimal, in horE time, in horS time, in cargoId int)
		begin
		update Empleados
		set
                    nombreEmpleado = nom,
                    apellidoEmpleado = ape,
                    sueldo = sue,
                    horaEntrada = horE,
                    horaSalida = horS,
                    cargoId = cargId
                    where empleadoId = empId;
		END$$
Delimiter ;

Delimiter $$
create procedure sp_AsignarEncargado(In empId Int, In encarId int)
begin

	Update Empleados  
		Set 
			Empleados.
            encargadoId = encarId
			Where empleadoId = empId;
end$$
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
            F.facturaId, 
            F.fecha, 
            F.hora, 
			CONCAT("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) AS cliente,
			CONCAT("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) AS empleado,
			F.total
			FROM Facturas F
			JOIN Clientes C ON F.clienteId = C.clienteId
			JOIN Empleados E ON F.empleadoId = E.empleadoId;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarFacturas(IN facId INT)
	BEGIN
		SELECT
            F.facturaId, F.fecha, F.hora, 
			CONCAT("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) AS cliente,
			CONCAT("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) AS empleado,
			F.total
			FROM Facturas F
			JOIN Clientes C ON F.clienteId = C.clienteId
			JOIN Empleados E ON F.empleadoId = E.empleadoId
			WHERE facturaId = factId;
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

-- ********************************** Procedimientos Facturas ********************************** --
Delimiter $$
create procedure sp_asignarTotalFactura(in factId int, in totalFact decimal (10,2))
Begin
	Update facturas
		set total = totalFact
			where facturaId =factId; 
End $$
Delimiter ;

Delimiter $$
create procedure sp_modificarCantidad(in detaFactId int, in stockActual int)
begin
	Update productos
		set cantidadStock = stockActual
			where productoId = detaFactId;
end $$
Delimiter ;

Delimiter $$
create procedure sp_asignarTotalCompra(in compId int, in totalC decimal (10,2))
Begin
	Update compras
		set totalCompra = totalC
			where compraId =compId; 
End $$
Delimiter ;

Delimiter $$
create procedure sp_modificarCantidadCompra(in productId int, in stockActual int)
begin
	Update productos
		set cantidadStock = stockActual
			where productoId = productId;
end $$
Delimiter ;

-- ********************************** TicketSoportes ********************************** --
 
DELIMITER $$
 
create procedure sp_AgregarTicketSoportes(IN desT varchar(250), IN est varchar(30),IN cliId int, in facId int)
    BEGIN
        INSERT INTO TicketSoportes (descripcionTicket, estatus, clienteId, facturaId)
            VALUES (des, est, cliId, facid);
     END$$
DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_ListarTicketSoportes()
    BEGIN
        SELECT
			TS.ticketSoporteId, 
                        TS.descripcionTicket, 
                        TS.estatus, 
			CONCAT("Id: ", C.clienteId," | ", C.nombre, " ", C.apellido) AS cliente, TS.facturaId FROM TicketSoporte TS
			JOIN Clientes C on TS.clienteId = C.clienteId;
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
	create procedure sp_EditarTicketSoportes(IN ticID int, in desT varchar(250), in est varchar(30),IN cliId int, in facId int)
		begin
		update TicketSoportes
                    set
                    descripcionTicket = desT,
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
            Promociones.promocionId,
 
			CONCAT("Id: ", P.productoId," | ", P.nombreProducto) AS Producto
            
            FROM 
			Promociones PR
			JOIN 
			Productos P ON PR.productoId = P.productoId;

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
            Promociones.productoId,

			CONCAT("Id: ", P.productoId," | ", P.nombreProducto) AS Producto
			FROM 
			Promociones PR
			JOIN 
			Productos P ON PR.productoId = P.productoId
			WHERE promocionId = promId;
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
create procedure sp_EditarPromociones(IN proID int, in preP decimal, in desP varchar(100),IN fecI date, in fecF date, in prodId int)
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
 
create procedure sp_AgregarCategoriaProductos(IN nomCP varchar(30), IN desCP varchar(100))
    BEGIN
        INSERT INTO CategoriaProductos (nombreCategoria, descripcionCategoria)
            VALUES (nomCP, desCP);
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
create procedure sp_EditarCategoriaProductos(IN catId int, in nomCP varchar(30), in desCP varchar(100))
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
create procedure sp_AgregarDistribuidores(IN nomD varchar(30), IN dirD varchar(200),IN nitD varchar(15), in tel varchar(15),in we varchar(50))
    BEGIN
        INSERT INTO Distribuidores (nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefono, web)
            VALUES (nomD, dirD, nitD, tel, we);
     END$$
DELIMITER ;
 
DELIMITER $$
CREATE PROCEDURE sp_ListarDistribuidores()
    BEGIN
        SELECT
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefono,
            Distribuidores.web,
            Distribuidores.distribuidorId
 
            FROM Distribuidores;
    END$$
DELIMITER ;
 
 
DELIMITER $$
CREATE PROCEDURE sp_BuscarDistribuidores(IN disId INT)
	BEGIN
            SELECT
            Distribuidores.distribuidorId,
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefono,
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
create procedure sp_EditarDistribuidores(IN disID int, in nomD varchar(30), in desD varchar(200),IN nitD varchar(15), in tel varchar(15), in we varchar(50))
    begin
        update Distribuidores
	set
            nombreDistribuidor = nom,
            direccionDistribuidor = des,
            nitDistribuidor = nit,
            telefono = tel,
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
            compraId, 
            fechaCompra, 
            totalCompra
	FROM Compras;
    END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarCompras(IN compId INT)
    BEGIN
        DELETE FROM Compras 
        WHERE compraId = compId;
    END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarCompras(IN compId INT)
    BEGIN
        SELECT 
            compraId, 
            fechaCompra, 
            totalCompra
	FROM Compras 
	WHERE compraId = compId;
    END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_EditarCompras(IN compId INT, IN fechaComp DATE)
    BEGIN
        UPDATE Compras
        SET fechaCompra = fechaComp
        WHERE compraId = compId;
    END$$
DELIMITER ;

-- ********************************** DetalleCompras ********************************** --
DELIMITER $$

CREATE PROCEDURE sp_AgregarDetalleCompras(IN canC INT, IN proId INT, IN comId INT)
    BEGIN
        INSERT INTO DetalleCompras (cantidadCompra, productoId, compraId)
            VALUES (canC, proId, comId);
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

CREATE PROCEDURE sp_AgregarProductos(IN nomPro VARCHAR(50), IN desPro VARCHAR(100), IN canPro INT, IN preVenUni DECIMAL(10,2), IN preVenMay DECIMAL(10,2), IN preCom DECIMAL(10,2), IN imaPro BLOB, IN disId INT, IN catProId INT)
    BEGIN
        INSERT INTO Productos (nombreProducto, descripcionProducto, cantidadProducto, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductoId)
            VALUES (nomPro, desPro, canSto, preVenUni, preVenMay, preCom, imaPro, disId, catProId);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarProductos()
    BEGIN
        SELECT
		P.productoId, 
                P.nombreProducto, 
                P.descripcionProducto, 
                P.cantidadProducto, 
                P.precioVentaUnitario, 
                P.precioVentaMayor,  
                P.precioCompra,
                P.imagenProducto, 
		CONCAT("Distribuidor: ", D.nombreDistribuidor) AS distribuidor,
		CONCAT("Categoría: ", CP.nombreCategoria) AS categoria
                    FROM Productos P
                    LEFT JOIN Distribuidores D ON P.distribuidorId = D.distribuidorId
                    LEFT JOIN CategoriaProductos CP ON P.categoriaproductosId = CP.categoriaproductosId;

    END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarProductos(IN proId INT)
	BEGIN
	SELECT
            P.productoId, P.nombreProducto, P.descripcionProducto, P.cantidadProducto, P.precioVentaUnitario, P.precioVentaMayor,  P.precioCompra,P.imagenProducto, 
			CONCAT("Distribuidor: ", D.nombreDistribuidor) AS distribuidor,
			CONCAT("Categoría: ", CP.nombreCategoria) AS categoria
			FROM Productos P
			LEFT JOIN Distribuidores D ON P.distribuidorId = D.distribuidorId
			LEFT JOIN CategoriaProductos CP ON P.categoriaproductosId = CP.categoriaproductosId
			WHERE productoId = prodId;
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
            cantidadProducto = canPro,
            precioVentaUnitario = preVenUni,
            precioVentaMayor = preVenMay,
            precioCompra = preCom,
            imagenProducto = imaPro,
            distribuidorId = disId,
            categoriaProductoId = catProId

        WHERE productoId = proId;
    END$$
DELIMITER ;

set global time_zone = '-6:00';