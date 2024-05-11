drop database if exists EjercicioDB;

create database EjercicioDB;

Use EjercicioDB;

-- ********************************** TABLA ********************************** --

create table Clientes(
	clienteId int not null auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    telefono varchar(80) not null,
    direccion varchar(50) not null,
    primary key pk_clienteId(clienteId)
);

-- ********************************** DATOS ********************************** --

insert into Clientes(nombre, apellido, telefono, direccion) values
	('Pedro', 'perez', '4589-4562', 'pueblo'),
	('juan', 'lopez', '2033-5687', 'ciudad'),
	('alberto', 'castillo', '8496-7532', 'ciudad');
    
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
			Clientes. ciudad,
            Clientes.clienteID

                FROM Clientes;
    END$$
DELIMITER ;

CALL sp_ListarClientes
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarClientes(IN cliID INT)
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

CALL sp_BuscarClientes(6);

DELIMITER $$

CREATE PROCEDURE sp_EliminarClientes(IN cliID INt)

	BEGIN
		DELETE FROM Clientes
			WHERE clienteId = cliId;

	END$$

DELIMITER ;
CALL sp_EliminarClientes(6);


-- Editar Empleados----

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
 
DELIMITER ;