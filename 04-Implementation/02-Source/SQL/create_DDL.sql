-- Creacion de la BD

CREATE DATABASE InmoDB
ON
primary
	(name = inmobiliaria1primary,
	filename = 'c:\Inmobiliaria_Data\inmobiliaria1primary.mdf',
	size = 50MB,
	maxsize = 400,
	filegrowth = 20),
filegroup Inmobiliaria1FG
	(name = inmobiliaria1Data,
	filename = 'c:\Inmobiliaria_Data\inmobiliaria1data.ndf',
	size = 100MB,
	maxsize = 700,
	filegrowth = 50)
log ON 
	(name = inmobiliaria1log,
	filename = 'c:\Inmobiliaria_Data\inmobiliaria1log.ldf',
	size = 50MB,
	maxsize = 400,
	filegrowth = 20)


--TABLAS 
USE InmoDB

-------------------
-- Tabla Persona -- 1
-------------------

CREATE TABLE Persona(
	idPersona int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	nombre varchar(50) NOT NULL,
	apellido varchar(50) NOT NULL,
	dni bigint UNIQUE NOT NULL,
	domicilio varchar(70) NOT NULL,
	ciudad varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	nacionalidad varchar(50) NOT NULL,
	tipo_persona varchar(20),
	profesion varchar(50),
	sexo nchar(1),
	observaciones text
)


--------------------
-- Tabla Contacto -- 2
--------------------

CREATE TABLE Contacto(
	idContacto int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	telefono bigint,
	celular bigint,
	mail varchar(100),
	idPersona int NOT NULL
)

ALTER TABLE Contacto
ADD CONSTRAINT FK_Contacto_IdPersona
FOREIGN KEY (idPersona) REFERENCES Persona(idPersona);


-----------------------
-- Tabla Seguimiento -- 3
-----------------------

CREATE TABLE Seguimiento(
	idSeguimiento int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	fecha date NOT NULL,
	estado varchar(50) NOT NULL,
	detalle text NOT NULL,
	idPersona int NOT NULL
)

ALTER TABLE Seguimiento
ADD CONSTRAINT FK_Seguimiento_IdPersona
FOREIGN KEY (idPersona) REFERENCES Persona(idPersona);


--------------------
-- Tabla Inmueble -- 4
--------------------

CREATE TABLE Inmueble(
	idInmueble int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	tipo varchar(30) NOT NULL,
	codigo varchar(50) UNIQUE NOT NULL,
	fecha date NOT NULL,
	idPropetario int NOT NULL,
	nomenclatura varchar(100),
	estado varchar(30), -- Puede ser un int tambien, VER despues
	cartelPublicado bit,
	descripcion text
)

ALTER TABLE Inmueble ADD CONSTRAINT FK_Inmueble_IdPropetario
FOREIGN KEY (idPropetario) REFERENCES Persona(idPersona);


------------------------------
-- Tabla Ubicacion_Inmueble -- 5
------------------------------

CREATE TABLE Ubicacion_Inmueble(
	idUbicacion int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	pais varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	ciudad varchar(50) NOT NULL,
	barrio varchar(50) NOT NULL,
	calle varchar(70) NOT NULL,
	numero int NOT NULL,
	piso int,
	dpto nchar(1),
	cp int NOT NULL,
	idInmueble int NOT NULL
)

ALTER TABLE Ubicacion_Inmueble ADD CONSTRAINT FK_Ubicacion_IdInmueble
FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);


---------------------
-- Tabla Operacion -- 6
---------------------

CREATE TABLE Operacion(
	idOperacion int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	tipoOperacion int NOT NULL,
	precio money NOT NULL,
	moneda varchar(30) NOT NULL,
	idInmueble int NOT NULL
)

ALTER TABLE Operacion ADD CONSTRAINT FK_Operacion_IdInmueble
FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);


--------------------------
-- Tabla Tipo_Operacion -- 7
--------------------------

CREATE TABLE Tipo_Operacion(
	id int PRIMARY KEY NOT NULL,
	nombre varchar(30) NOT NULL
)

ALTER TABLE Operacion ADD CONSTRAINT FK_Operacion_TipoOperacion
FOREIGN KEY (tipoOperacion) REFERENCES Tipo_Operacion(id);


----------------------
-- Tabla Gasto_Fijo -- 8
----------------------

CREATE TABLE Gasto_Fijo(
	idGastoFijo int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	tipoGasto int NOT NULL,
	monto money NOT NULL,
	mesesAplica varchar(24) NOT NULL,
	idInmueble int NOT NULL
)

ALTER TABLE Gasto_Fijo ADD CONSTRAINT FK_GastoFijo_IdInmueble
FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);


--------------------------
-- Tabla Tipo_GastoFijo -- 9
--------------------------

CREATE TABLE Tipo_GastoFijo(
	id int PRIMARY KEY NOT NULL,
	nombre varchar(50) NOT NULL
)

ALTER TABLE Gasto_Fijo ADD CONSTRAINT FK_GastoFijo_IdTipo
FOREIGN KEY (tipoGasto) REFERENCES Tipo_GastoFijo(id);


--------------------------
-- Tabla Tipo_GastoFijo -- 10
--------------------------

CREATE TABLE Imagen(
	idImagen int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	foto image NOT NULL,
	principal bit NOT NULL,
	idInmueble int NOT NULL
)

ALTER TABLE Imagen ADD CONSTRAINT FK_Imagen_IdInmueble
FOREIGN KEY (idImagen) REFERENCES Inmueble(idInmueble);


-----------------------------
-- Tabla Contrato_Alquiler -- 11
-----------------------------

CREATE TABLE Contrato_Alquiler(
	idContrato int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	idInmueble int NOT NULL,
	idInquilino int NOT NULL,
	idGarante int NOT NULL,
	montoTotal money NOT NULL,
	fechaInicio date NOT NULL,
	fechaFin date NOT NULL,
	porcentajeMora decimal NOT NULL,
	diaVto int NOT NULL,
	estadoPago varchar(30) NOT NULL,
	estadoAlquiler varchar(30) NOT NULL,
	mesesDeposito int,
	precioDeposito money,
	observaciones text
)

ALTER TABLE Contrato_Alquiler ADD CONSTRAINT FK_ContratoAlquiler_IdInmueble
FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);

ALTER TABLE Contrato_Alquiler ADD CONSTRAINT FK_ContratoAlquiler_IdInquilino
FOREIGN KEY (idInquilino) REFERENCES Persona(idPersona);

/* Hay que definir los tipos de estados de alquiler que se van a agregar, y si va ser varchar30 o int */


--------------------------
-- Tabla Pagos_Contrato -- 12
--------------------------

CREATE TABLE Pagos_Contrato(
	idPago int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	mesCorrespondiente int NOT NULL,
	anioCorrespondiente int NOT NULL,
	fechaPago date NOT NULL,
	monto money NOT NULL,
	montoPagado money NOT NULL,
	pagado bit NOT NULL,
	idContrato int NOT NULL
)

ALTER TABLE Pagos_Contrato ADD CONSTRAINT FK_PagosContrato_IdContrato
FOREIGN KEY (idContrato) REFERENCES Contrato_Alquiler(idContrato);


--------------------------
-- Tabla Contrato_Venta -- 13
--------------------------

CREATE TABLE Contrato_Venta(
	idVenta int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	idInmueble int NOT NULL, --FK
	idComprador int NOT NULL, --FK
	idMartillero int NOT NULL,
	fecha date NOT NULL,
	reservado bit NOT NULL,
	idConyuge int,
	comision decimal,
	diasEscrituracion int,
	observaciones text
)

ALTER TABLE Contrato_Venta ADD CONSTRAINT FK_ContratoVenta_IdInmueble
FOREIGN KEY (idInmueble) REFERENCES Inmueble(idInmueble);

ALTER TABLE Contrato_Venta ADD CONSTRAINT FK_ContratoVenta_IdComprador
FOREIGN KEY (idComprador) REFERENCES Persona(idPersona);


------------------
-- Tabla Cuotas -- 14
------------------

CREATE TABLE Cuotas(
	idCuota int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	concepto varchar(100) NOT NULL,
	monto money NOT NULL,
	fechaInicio date NOT NULL,
	fechaFin date NOT NULL,
	pagado bit NOT NULL,
	honorarios money,
	intereses decimal,
	idContratoVta int NOT NULL
)

ALTER TABLE Cuotas ADD CONSTRAINT FK_Cuotas_IdContratoVta
FOREIGN KEY (idContratoVta) REFERENCES Contrato_Venta(idVenta);


----------------------
-- Tabla Pago_Venta -- 15
----------------------

CREATE TABLE Pago_Venta(
	idPago int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	montoTotal money NOT NULL,
	montoPagado money NOT NULL,
	saldo money NOT NULL,
	estadoVta varchar(30),
	estadoPago varchar(30),
	idContratoVta int NOT NULL
)

ALTER TABLE Pago_Venta ADD CONSTRAINT FK_PagoVenta_IdContratoVta
FOREIGN KEY (idContratoVta) REFERENCES Contrato_Venta(idVenta);


----------------------
-- Tabla Solicitud -- 16
----------------------

CREATE TABLE Solicitud(
	idSolicitud int PRIMARY KEY NOT NULL,
	fecha date NOT NULL,
	idSolicitante int NOT NULL,
	tipoInmueble varchar(30) NOT NULL,
	altInmueble1 varchar(30),
	altInmueble2 varchar(30),
	pais varchar(50),
	provincia varchar(50),
	ciudad varchar(50),
	barrio varchar(50),
	operacion int,
	desdePrecio money,
	hastaPrecio money,
	moneda varchar(30),
	detalle text
)

ALTER TABLE Solicitud ADD CONSTRAINT FK_Solicitud_IdSolicitante
FOREIGN KEY (idSolicitante) REFERENCES Persona(idPersona);