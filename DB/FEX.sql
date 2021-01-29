-- phpMyAdmin SQL Dump
-- version 4.0.10deb1ubuntu0.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 27-01-2021 a las 17:05:16
-- Versión del servidor: 5.5.62-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `FEX`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE IF NOT EXISTS `categorias` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `ICONO` varchar(255) DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `fkFoto` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_categorias_fkFoto` (`fkFoto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE IF NOT EXISTS `clientes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `DIRECCIONENTREGA` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FECHAALTA` datetime DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `OBSERVACIONES` varchar(255) DEFAULT NULL,
  `PASS` varchar(255) DEFAULT NULL,
  `TELEFONO` varchar(255) DEFAULT NULL,
  `TOKEN` varchar(255) DEFAULT NULL,
  `fkFoto` int(11) DEFAULT NULL,
  `fkGrupo` int(11) DEFAULT NULL,
  `fkInstalacion` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_clientes_fkGrupo` (`fkGrupo`),
  KEY `FK_clientes_fkFoto` (`fkFoto`),
  KEY `FK_clientes_fkInstalacion` (`fkInstalacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuraciones`
--

CREATE TABLE IF NOT EXISTS `configuraciones` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ENABLED` tinyint(1) DEFAULT '0',
  `IP` varchar(255) DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `NOMBREPROYECTO` varchar(255) DEFAULT NULL,
  `PROTOCOLO` varchar(255) DEFAULT NULL,
  `PUERTO` varchar(255) DEFAULT NULL,
  `SUBCARPETAIMAGENES` varchar(255) DEFAULT NULL,
  `VARSESSIONHTTP` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `configuraciones`
--

INSERT INTO `configuraciones` (`ID`, `ENABLED`, `IP`, `NOMBRE`, `NOMBREPROYECTO`, `PROTOCOLO`, `PUERTO`, `SUBCARPETAIMAGENES`, `VARSESSIONHTTP`) VALUES
(1, 0, 'viewdevs.com.ar', 'produccion', 'ecommerce', 'http://', ':8081', '.', 'usrEcommerce'),
(2, 1, 'localhost', 'debug', 'ecommerce', 'http://', ':80', '.', 'usrEcommerce');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotoproductos`
--

CREATE TABLE IF NOT EXISTS `fotoproductos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `ORDEN` int(11) DEFAULT NULL,
  `fkFoto` int(11) DEFAULT NULL,
  `fkProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_fotoproductos_fkProducto` (`fkProducto`),
  KEY `FK_fotoproductos_fkFoto` (`fkFoto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotos`
--

CREATE TABLE IF NOT EXISTS `fotos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `URLPROVISORIA` varchar(255) DEFAULT NULL,
  `fkInstalacion` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_fotos_fkInstalacion` (`fkInstalacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupooperador`
--

CREATE TABLE IF NOT EXISTS `grupooperador` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `JERARQUIA` int(11) DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hashs`
--

CREATE TABLE IF NOT EXISTS `hashs` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `AUMENTO` double DEFAULT NULL,
  `ESDECOLOR` tinyint(1) DEFAULT '0',
  `ESDEUNIDAD` tinyint(1) DEFAULT '0',
  `MINIMO` double DEFAULT NULL,
  `MULTIPLICADORUNIDADANT` double DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `NOMBREUNIDADANT` varchar(255) DEFAULT NULL,
  `URLAWESOM` varchar(255) DEFAULT NULL,
  `VALORFLOAT` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instalaciones`
--

CREATE TABLE IF NOT EXISTS `instalaciones` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `URLDOMINIO` varchar(255) DEFAULT NULL,
  `urlDominioDos` text NOT NULL,
  `ALIAS` varchar(255) DEFAULT NULL,
  `CALLE` varchar(255) DEFAULT NULL,
  `CARPETAWEB` varchar(255) DEFAULT NULL,
  `fkFavicon` int(11) DEFAULT NULL,
  `LAT` double DEFAULT NULL,
  `LONGI` double DEFAULT NULL,
  `fkLogo` int(11) DEFAULT NULL,
  `CC` varchar(255) DEFAULT NULL,
  `CIUDAD` varchar(255) DEFAULT NULL,
  `CLIENTID` varchar(255) DEFAULT NULL,
  `CLIENTSECRET` varchar(255) DEFAULT NULL,
  `fkClienteVolatil` int(11) DEFAULT NULL,
  `COLORPRI` varchar(255) DEFAULT NULL,
  `COLORSEC` varchar(255) DEFAULT NULL,
  `COLORTER` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRMAMAIL` varchar(255) DEFAULT NULL,
  `GOOGLEMAPS` varchar(255) DEFAULT NULL,
  `MSG` varchar(255) DEFAULT NULL,
  `PAIS` varchar(255) DEFAULT NULL,
  `PASS` varchar(255) DEFAULT NULL,
  `PREFIJO` varchar(255) DEFAULT NULL,
  `TELEFONOFIJO` varchar(255) DEFAULT NULL,
  `TELEFONOWPP` varchar(255) DEFAULT NULL,
  `TXTTIEMPOENVIO` text,
  `ACTIVO` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_instalaciones_fkLogo` (`fkLogo`),
  KEY `FK_instalaciones_fkClienteVolatil` (`fkClienteVolatil`),
  KEY `FK_instalaciones_fkFavicon` (`fkFavicon`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Volcado de datos para la tabla `instalaciones`
--

INSERT INTO `instalaciones` (`ID`, `NOMBRE`, `URLDOMINIO`, `urlDominioDos`, `ALIAS`, `CALLE`, `CARPETAWEB`, `fkFavicon`, `LAT`, `LONGI`, `fkLogo`, `CC`, `CIUDAD`, `CLIENTID`, `CLIENTSECRET`, `fkClienteVolatil`, `COLORPRI`, `COLORSEC`, `COLORTER`, `EMAIL`, `FIRMAMAIL`, `GOOGLEMAPS`, `MSG`, `PAIS`, `PASS`, `PREFIJO`, `TELEFONOFIJO`, `TELEFONOWPP`, `TXTTIEMPOENVIO`, `ACTIVO`) VALUES
(1, 'La Varense', 'distribuidoralavarense.com.ar', 'lavarenseonline.com.ar', 'DISTRIBUIDORA LA VARENSE', 'Onelli 1700', 'varense', 20, -41.149183, -71.299531, 368, 'viewdevscompany@gmail.com', 'Bariloche', '4838174922975515', 'NrTcaLWOdqDSsaIL1tmEJIvFfYWmQLlo', 390, '#eb3b5a', '#D33551', '#F38A46', 'distribuidoralavarense@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\n  \n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\n<h2>\n¿Dudas? ¿Consultas? \n<br>\nContactanos vía', 'https://goo.gl/maps/asMdJfqQ4xYKQN3SA', '<h3>\n	<i class="fas fa-truck"></i>\n	ENVIOS A DOMICILIO\n</h3>\n\n<hr class="hr">\n\n\n<h3>\n	Zona Centro y Barrios hasta KM4\n	<br>\n	Comprando $2000 el envio es Gratis!\n	<br>\n	Compras menores, el envio sale $160\n	<br>\n	<hr class="hr">\n	KM4 a KM12 el envio es grat', 'Argentina', 'l4v4r3ns3', '2944', '714160', '209262', '<h6 class="texto-envios">             Si realiza su pedido antes del 12:00 será entregado en el dia.             <br>             <br>             Si realiza su pedido luego de las 12:00 será entregado al dia siguiente.         </h6>         <h6 class="te', 1),
(2, 'Mr Potato', 'mrpotato.com.ar', 'xx4', 'Mr Potato', 'Beltran 277', 'potato', 213, -41.1332511, -71.2804571, 208, 'viewdevscompany@gmail.com', 'Bariloche', '', '', 391, '#F38A46', '#DA7C3F', '#ff0000', 'pedidosmrpotatobrc@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\r\n  \r\n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\r\n<h2>\r\n¿Dudas? ¿Consultas? \r\n<br>\r\nContactan', 'https://goo.gl/maps/ug3EJL3Jyr3rH4wy9', '<h3>\r\n	<i class="fas fa-truck"></i>\r\n	ENVIOS A DOMICILIO\r\n</h3>\r\n\r\n<hr class="hr">\r\n\r\n\r\n<h3>\r\n	Zona Centro y Barrios hasta KM4\r\n	<br>\r\n	Comprando $2000 el envio es Gratis!\r\n	<br>\r\n	Compras menores, el envio sale $160\r\n	<br>\r\n	<hr class="hr">\r\n	KM4 a KM12 ', 'Argentina', 'asepmnxemaxesxsx', '261 ', NULL, '3637318', '<h6 class="texto-envios">             Si realiza su pedido antes del 12:00 será entregado en el dia.             <br>             <br>             Si realiza su pedido luego de las 12:00 será entregado al dia siguiente.         </h6>         <h6 class="te', 1),
(3, 'La Gran Feria Del Alto', 'lagranferiadelalto.com.ar', 'xxx3', 'La Gran Feria Del Alto', 'Beschedt y Ruta 258', 'granferia', 1192, -41.1551502, -71.3060508, 1193, 'viewdevscompany@gmail.com', 'Bariloche', '', '', 451, '#415993', '#2c4788', '#ff0000', 'lagranferiadelalto@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\r\n \r\n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\r\n<h2>\r\n¿Dudas? ¿Consultas? \r\n<br>\r\nContactano', 'https://goo.gl/maps/7v8p2wbEcK761qUp7', '<h3>\r\n <i class="fas fa-truck"></i>\r\n ENVIOS A DOMICILIO\r\n</h3>\r\n\r\n<hr class="hr">\r\n\r\n\r\n<h3>\r\n Zona Centro y Barrios hasta KM4\r\n <br>\r\n Comprando $2000 el envio es Gratis!\r\n <br>\r\n Compras menores, el envio sale $160\r\n <br>\r\n <hr class="hr">\r\n KM4 a KM12 ', 'Argentina', 'mjfckuecgciuettg\r\n', '2944', '414090', '414090', '<h6 class="texto-envios">             Si realiza su pedido antes del 12:00 será entregado en el dia.             <br>             <br>             Si realiza su pedido luego de las 12:00 será entregado al dia siguiente.         </h6>         <h6 class="te', 1),
(4, 'Moon Make Up', 'moonmakeup.com.ar', 'xxx2', 'MOON MAKE UP', 'Frey 468', 'moonmakeup', 1840, -41.1372211, -71.3017605, 1841, 'viewdevscompany@gmail.com', 'Bariloche', '6968860017817763', 'c7qM1bNGIpgaU7oydfc0758FGMFKGXqz', 498, '#f3acc2', '#ce6491', '#B0ADF2', 'ventas.moonmakeup@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\r\n \r\n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\r\n<h2>\r\n¿Dudas? ¿Consultas? \r\n<br>\r\nContactano', 'https://goo.gl/maps/RknB7S1tWGWL6z5P6', '<h3>\r\n <i class="fas fa-truck"></i>\r\n ENVIOS A DOMICILIO\r\n</h3>\r\n\r\n<hr class="hr">\r\n\r\n\r\n<h3>\r\n Zona Centro y Barrios hasta KM4\r\n <br>\r\n Comprando $2000 el envio es Gratis!\r\n <br>\r\n Compras menores, el envio sale $160\r\n <br>\r\n <hr class="hr">\r\n KM4 a KM12 ', 'Argentina', 'appqopbhgzjqtacg', '2920', '245405', '245405', '<h6 class="texto-envios">             Si realiza su pedido antes del 14:00 será entregado en el dia.             <br>             <br>             Si realiza su pedido luego de las 14:00 será entregado al dia siguiente.         </h6>         <h6 class="te', 1),
(5, 'Venta de Madera', 'ventademadera.com.ar', 'xxx', 'VENTA DE MADERA', 'Frey XXX', 'ventademadera', 2678, -41.1372211, -71.3017605, 2694, '\r\nFffgfg\r\n', 'Bariloche', '', '', 684, '#D0A35F', '#BB9255', '#f1c40f', 'jorgeiglesiasinformacion@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\r\n \r\n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\r\n<h2>\r\n¿Dudas? ¿Consultas? \r\n<br>\r\nContactano', 'https://goo.gl/maps/RknB7S1tWGWL6z5P6', '<h3>\r\n <i class="fas fa-truck"></i>\r\n ENVIOS A DOMICILIO\r\n</h3>\r\n\r\n<hr class="hr">\r\n\r\n\r\n<h3>\r\n Zona Centro y Barrios hasta KM4\r\n <br>\r\n Comprando $2000 el envio es Gratis!\r\n <br>\r\n Compras menores, el envio sale $160\r\n <br>\r\n <hr class="hr">\r\n KM4 a KM12 ', 'Argentina', 'covidcovid', '11', '54210021', '54210021', '<h6 class="texto-envios">             Si realiza su pedido antes del 14:00 será entregado en el dia.             <br>             <br>             Si realiza su pedido luego de las 14:00 será entregado al dia siguiente.         </h6>         <h6 class="te', 1),
(6, 'Agencia Luma', 'agencialuma.com.ar', 'xxx', 'AGENCIA LUMA', 'PASAJE GUTIERREZ 1010', 'luma', 2744, -41.1372211, -71.3017605, 2743, '\r\nFffgfg\r\n', 'Bariloche', '', '', 684, '#c6872d', '#BB9255', '#c6872d', 'infoagencialuma@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\r\n \r\n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\r\n<h2>\r\n¿Dudas? ¿Consultas? \r\n<br>\r\nContactano', 'https://goo.gl/maps/RknB7S1tWGWL6z5P6', '<h3>\n <i class="fas fa-truck"></i>\n ENVIOS A DOMICILIO\n</h3>\n\n<hr class="hr">\n\n\n<h3>\n Zona Centro y Barrios hasta KM4\n <br>\n Comprando $2000 el envio es Gratis!\n <br>\n Compras menores, el envio sale $160\n <br>\n <hr class="hr">\n KM4 a KM12 ', 'Argentina', 'covidcovid', '11', '59330535', '59330535', '<h6 class="texto-envios">\nLUNES A SABADOS: 9:00 A 19:00 HS\n</h6>\n', 1),
(7, 'Saludable Cosecha', 'saludablecosecha.com.ar', 'localhost', 'SALUDABLE COSECHA', 'Constitución 1264', 'saludable-cosecha', 2761, -34.4420803, -58.5574266, 2762, '\r\nFffgfg\r\n', 'San Fernando', '', '', 702, '#7CA21F', '#7CA21F', '#9bcb27', 'saludablecosechaverduleria@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\r\n \r\n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\r\n<h2>\r\n¿Dudas? ¿Consultas? \r\n<br>\r\nContactano', 'https://goo.gl/maps/MMEqE6weCY3tFPxB9', '<h3>\n <i class="fas fa-truck"></i>\n ENVIOS A DOMICILIO\n</h3>\n\n<hr class="hr">\n\n\n<h3>\n Zona Centro y Barrios hasta KM4\n <br>\n Comprando $2000 el envio es Gratis!\n <br>\n Compras menores, el envio sale $160\n <br>\n <hr class="hr">\n KM4 a KM12 ', 'Buenos Aires', 'covidcovid', '11', '-34.4420803', '-58.5574266', '<h6 class="texto-envios">             Si realiza su pedido antes del 14:00 será entregado en el dia.             <br>             <br>             Si realiza su pedido luego de las 14:00 será entregado al dia siguiente. <BR><br>\nLUNES A SABADOS: 8:00 A 13:00 <BR>DE 16:00 A 20:30 HS<BR><BR>\nDOMINGOS DE 9:00 A 13:00 HS', 1),
(8, 'Inmall', 'inmall.com.ar', 'xxx2', 'INMALL', 'Guido 1890', 'inmall', 2757, -41.1372211, -71.3017605, 2756, 'viewdevscompany@gmail.com', 'CABA', '', '', 703, '#B41137', '#e11645', '#B41137', 'inmallarg@gmail.com', '<script src="https://kit.fontawesome.com/01ed377a67.js" crossorigin="anonymous"></script>\r\n \r\n<div class="contenedor" style="width: 500px;text-align: center;padding-top: 50px;font-family: ''Lato'', sans-serif;">\r\n<h2>\r\n¿Dudas? ¿Consultas? \r\n<br>\r\nContactano', 'https://goo.gl/maps/RknB7S1tWGWL6z5P6', '<h3>\n <i class="fas fa-truck"></i>\n ENVIOS A DOMICILIO\n</h3>\n\n<hr class="hr">\n\n\n<h3>\n Zona Centro y Barrios hasta KM4\n <br>\n Comprando $2000 el envio es Gratis!\n <br>\n Compras menores, el envio sale $160\n <br>\n <hr class="hr">\n KM4 a KM12 ', 'Argentina', 'appqopbhgzjqtacg', '11', '53443205', '53443205', '<h6 class="texto-envios">HORARIO DE ATENCION DE 9:00 A 21:00HS</H6>\n', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opcionesproductos`
--

CREATE TABLE IF NOT EXISTS `opcionesproductos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `PORCENTAJEGANANCIA` double DEFAULT NULL,
  `PRECIO` double DEFAULT NULL,
  `PRECIOCOSTO` double DEFAULT NULL,
  `VALORFLOAT` double DEFAULT NULL,
  `fkProducto` int(11) DEFAULT NULL,
  `fkHashtag` int(11) DEFAULT NULL,
  `fkUnidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_opcionesproductos_fkProducto` (`fkProducto`),
  KEY `FK_opcionesproductos_fkUnidad` (`fkUnidad`),
  KEY `FK_opcionesproductos_fkHashtag` (`fkHashtag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE IF NOT EXISTS `productos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `CODIGO` varchar(255) DEFAULT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `ELIMINADO` tinyint(1) DEFAULT '0',
  `NOMBRE` varchar(255) DEFAULT NULL,
  `ORDEN` int(11) DEFAULT NULL,
  `SOYFAVORITO` tinyint(1) DEFAULT '0',
  `SUBTITULO` varchar(255) DEFAULT NULL,
  `fkCategoria` int(11) DEFAULT NULL,
  `fkInstalacion` int(11) DEFAULT NULL,
  `fkProveedor` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_productos_fkProveedor` (`fkProveedor`),
  KEY `FK_productos_fkInstalacion` (`fkInstalacion`),
  KEY `FK_productos_fkCategoria` (`fkCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE IF NOT EXISTS `proveedores` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `CUIT` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `ESDEFAULT` tinyint(1) DEFAULT '0',
  `NOMBRE` varchar(255) DEFAULT NULL,
  `ORDEN` int(11) DEFAULT NULL,
  `fkInstalacion` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_proveedores_fkInstalacion` (`fkInstalacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relscategoriasinstalacion`
--

CREATE TABLE IF NOT EXISTS `relscategoriasinstalacion` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `fkInstalacion` int(11) DEFAULT NULL,
  `fkCategoria` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_relscategoriasinstalacion_fkInstalacion` (`fkInstalacion`),
  KEY `FK_relscategoriasinstalacion_fkCategoria` (`fkCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relshashcategoria`
--

CREATE TABLE IF NOT EXISTS `relshashcategoria` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `fkCategoria` int(11) DEFAULT NULL,
  `fkHashtag` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_relshashcategoria_fkCategoria` (`fkCategoria`),
  KEY `FK_relshashcategoria_fkHashtag` (`fkHashtag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relshashproducto`
--

CREATE TABLE IF NOT EXISTS `relshashproducto` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `fkHashtag` int(11) DEFAULT NULL,
  `fkProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_relshashproducto_fkProducto` (`fkProducto`),
  KEY `FK_relshashproducto_fkHashtag` (`fkHashtag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidades`
--

CREATE TABLE IF NOT EXISTS `unidades` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACLARACION` varchar(255) DEFAULT NULL,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `APB` varchar(255) DEFAULT NULL,
  `APBS` varchar(255) DEFAULT NULL,
  `AUMENTO` double DEFAULT NULL,
  `MINIMO` double DEFAULT NULL,
  `NOMBREPOS` varchar(255) DEFAULT NULL,
  `NOMBREPRE` varchar(255) DEFAULT NULL,
  `NOMBRESIESMENORAUNO` varchar(255) DEFAULT NULL,
  `NOMBREUNIDADSINGULAR` varchar(255) DEFAULT NULL,
  `NOMBREUNIDADSINGULARS` varchar(255) DEFAULT NULL,
  `ORDEN` int(11) DEFAULT NULL,
  `VALORINICIAL` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zonasenvios`
--

CREATE TABLE IF NOT EXISTS `zonasenvios` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTIVO` tinyint(1) DEFAULT '0',
  `DISTANCIAMAX` int(11) DEFAULT NULL,
  `DISTANCIAMIN` int(11) DEFAULT NULL,
  `MINIMODECOMPRA` double DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `ORDEN` int(11) DEFAULT NULL,
  `PORDEFAULT` tinyint(1) DEFAULT '0',
  `PRECIOENVIO` double DEFAULT NULL,
  `TEXTO` varchar(255) DEFAULT NULL,
  `fkInstalacion` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_zonasenvios_fkInstalacion` (`fkInstalacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD CONSTRAINT `FK_categorias_fkFoto` FOREIGN KEY (`fkFoto`) REFERENCES `fotos` (`ID`);

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `FK_clientes_fkFoto` FOREIGN KEY (`fkFoto`) REFERENCES `fotos` (`ID`),
  ADD CONSTRAINT `FK_clientes_fkGrupo` FOREIGN KEY (`fkGrupo`) REFERENCES `grupooperador` (`ID`),
  ADD CONSTRAINT `FK_clientes_fkInstalacion` FOREIGN KEY (`fkInstalacion`) REFERENCES `instalaciones` (`ID`);

--
-- Filtros para la tabla `fotoproductos`
--
ALTER TABLE `fotoproductos`
  ADD CONSTRAINT `FK_fotoproductos_fkFoto` FOREIGN KEY (`fkFoto`) REFERENCES `fotos` (`ID`),
  ADD CONSTRAINT `FK_fotoproductos_fkProducto` FOREIGN KEY (`fkProducto`) REFERENCES `productos` (`ID`);

--
-- Filtros para la tabla `fotos`
--
ALTER TABLE `fotos`
  ADD CONSTRAINT `FK_fotos_fkInstalacion` FOREIGN KEY (`fkInstalacion`) REFERENCES `instalaciones` (`ID`);

--
-- Filtros para la tabla `opcionesproductos`
--
ALTER TABLE `opcionesproductos`
  ADD CONSTRAINT `FK_opcionesproductos_fkHashtag` FOREIGN KEY (`fkHashtag`) REFERENCES `hashs` (`ID`),
  ADD CONSTRAINT `FK_opcionesproductos_fkProducto` FOREIGN KEY (`fkProducto`) REFERENCES `productos` (`ID`),
  ADD CONSTRAINT `FK_opcionesproductos_fkUnidad` FOREIGN KEY (`fkUnidad`) REFERENCES `unidades` (`ID`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `FK_productos_fkCategoria` FOREIGN KEY (`fkCategoria`) REFERENCES `categorias` (`ID`),
  ADD CONSTRAINT `FK_productos_fkInstalacion` FOREIGN KEY (`fkInstalacion`) REFERENCES `instalaciones` (`ID`),
  ADD CONSTRAINT `FK_productos_fkProveedor` FOREIGN KEY (`fkProveedor`) REFERENCES `proveedores` (`ID`);

--
-- Filtros para la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD CONSTRAINT `FK_proveedores_fkInstalacion` FOREIGN KEY (`fkInstalacion`) REFERENCES `instalaciones` (`ID`);

--
-- Filtros para la tabla `relscategoriasinstalacion`
--
ALTER TABLE `relscategoriasinstalacion`
  ADD CONSTRAINT `FK_relscategoriasinstalacion_fkCategoria` FOREIGN KEY (`fkCategoria`) REFERENCES `categorias` (`ID`),
  ADD CONSTRAINT `FK_relscategoriasinstalacion_fkInstalacion` FOREIGN KEY (`fkInstalacion`) REFERENCES `instalaciones` (`ID`);

--
-- Filtros para la tabla `relshashcategoria`
--
ALTER TABLE `relshashcategoria`
  ADD CONSTRAINT `FK_relshashcategoria_fkCategoria` FOREIGN KEY (`fkCategoria`) REFERENCES `categorias` (`ID`),
  ADD CONSTRAINT `FK_relshashcategoria_fkHashtag` FOREIGN KEY (`fkHashtag`) REFERENCES `hashs` (`ID`);

--
-- Filtros para la tabla `relshashproducto`
--
ALTER TABLE `relshashproducto`
  ADD CONSTRAINT `FK_relshashproducto_fkHashtag` FOREIGN KEY (`fkHashtag`) REFERENCES `hashs` (`ID`),
  ADD CONSTRAINT `FK_relshashproducto_fkProducto` FOREIGN KEY (`fkProducto`) REFERENCES `productos` (`ID`);

--
-- Filtros para la tabla `zonasenvios`
--
ALTER TABLE `zonasenvios`
  ADD CONSTRAINT `FK_zonasenvios_fkInstalacion` FOREIGN KEY (`fkInstalacion`) REFERENCES `instalaciones` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
