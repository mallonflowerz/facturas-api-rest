-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: almacen
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `almacen`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `almacen` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `almacen`;

--
-- Table structure for table `detallefactura`
--

DROP TABLE IF EXISTS `detallefactura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detallefactura` (
  `id` binary(16) NOT NULL,
  `cantidad` decimal(30,2) NOT NULL,
  `nombre_producto` varchar(100) NOT NULL,
  `valor_total` decimal(30,2) NOT NULL,
  `valor_venta` decimal(30,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallefactura`
--

LOCK TABLES `detallefactura` WRITE;
/*!40000 ALTER TABLE `detallefactura` DISABLE KEYS */;
INSERT INTO `detallefactura` VALUES (_binary '3.Y\0•/Es∫Ñûπ)b',5.00,'Caja Agua x50',19.95,3.99),(_binary '^h~%\ÊMæπÑ°rê\ˆ\€W',2.00,'Producto 1',21.00,10.50),(_binary '\⁄\»\Ÿk¡/EU£≥UïN@\',3.00,'Producto 2',45.00,15.00);
/*!40000 ALTER TABLE `detallefactura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `id` binary(16) NOT NULL,
  `fecha_defactura` datetime(6) DEFAULT NULL,
  `tercero_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `almacen_tercero_idx` (`tercero_id`),
  CONSTRAINT `almacen_tercero` FOREIGN KEY (`tercero_id`) REFERENCES `tercero` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (_binary '£<\ÃæD\Ìö\ƒ.E\Îo\Z','2023-07-14 20:45:48.726090',_binary '¥™4\’\÷G“ÖOM\„_D'),(_binary '£\ƒ>éDdÜ\Ó†B4V','2023-07-14 20:43:16.729564',_binary '¥™4\’\÷G“ÖOM\„_D'),(_binary '§\”\Ÿ@PΩFÕüWYß∫g','2023-07-14 20:43:27.964906',_binary '¥™4\’\÷G“ÖOM\„_D');
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura_detalles`
--

DROP TABLE IF EXISTS `factura_detalles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura_detalles` (
  `factura_id` binary(16) NOT NULL,
  `detalles_id` binary(16) NOT NULL,
  KEY `FKcpvtpmeq2sp743gpphwttmypf` (`detalles_id`),
  KEY `FKleh290ibrx3229llrip2f0gv8` (`factura_id`),
  CONSTRAINT `FKcpvtpmeq2sp743gpphwttmypf` FOREIGN KEY (`detalles_id`) REFERENCES `detallefactura` (`id`),
  CONSTRAINT `FKleh290ibrx3229llrip2f0gv8` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura_detalles`
--

LOCK TABLES `factura_detalles` WRITE;
/*!40000 ALTER TABLE `factura_detalles` DISABLE KEYS */;
INSERT INTO `factura_detalles` VALUES (_binary '£\ƒ>éDdÜ\Ó†B4V',_binary '3.Y\0•/Es∫Ñûπ)b'),(_binary '£\ƒ>éDdÜ\Ó†B4V',_binary '^h~%\ÊMæπÑ°rê\ˆ\€W'),(_binary '£\ƒ>éDdÜ\Ó†B4V',_binary '\⁄\»\Ÿk¡/EU£≥UïN@\'),(_binary '§\”\Ÿ@PΩFÕüWYß∫g',_binary '3.Y\0•/Es∫Ñûπ)b'),(_binary '§\”\Ÿ@PΩFÕüWYß∫g',_binary '^h~%\ÊMæπÑ°rê\ˆ\€W'),(_binary '§\”\Ÿ@PΩFÕüWYß∫g',_binary '\⁄\»\Ÿk¡/EU£≥UïN@\'),(_binary '£<\ÃæD\Ìö\ƒ.E\Îo\Z',_binary '^h~%\ÊMæπÑ°rê\ˆ\€W'),(_binary '£<\ÃæD\Ìö\ƒ.E\Îo\Z',_binary '\⁄\»\Ÿk¡/EU£≥UïN@\');
/*!40000 ALTER TABLE `factura_detalles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial_logins`
--

DROP TABLE IF EXISTS `historial_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historial_logins` (
  `id` binary(36) NOT NULL,
  `email` varchar(60) NOT NULL,
  `fecha_inicio_sesion` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial_logins`
--

LOCK TABLES `historial_logins` WRITE;
/*!40000 ALTER TABLE `historial_logins` DISABLE KEYS */;
INSERT INTO `historial_logins` VALUES (_binary 'p\'ƒîMFCm≤¶\"\Ë\'\ﬁZ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-13 02:51:12'),(_binary 'k\'e\Ú\ˆ#Btæo˝r5\ÍUQ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-13 02:59:09'),(_binary '\ÙT\ÚÅKG*≤˚u`\rµ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-13 14:28:20'),(_binary 'ê1\√7ú\ÍI∏å©h\Â™\⁄\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-13 17:21:17'),(_binary 'h>£ê8êJÜâD\’\Ïç\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 16:53:54'),(_binary '\“!îˇ\ZBÕç8\Î\◊/òf\Ê\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 16:59:19'),(_binary 'f\rq[L˛´≥}\È¿\Ò\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:00:44'),(_binary 'gjt?b$@)ß\n\—sèΩ\∆\Ÿ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:05:34'),(_binary '\Ÿu|\≈y)COê\◊›ìu(8\Û\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:07:08'),(_binary '≥í†›öG\‰£0\‘◊ä\"≤h\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:10:24'),(_binary '∂˛(«°éDùÉº€£Re\Û@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:12:40'),(_binary 'S\◊=\¬\”dDÀ†\Áò$\Á6ê\≈\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:24:22'),(_binary ']kóryDfõ\Ë\ˆ«†0Bs\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:29:42'),(_binary '\Õ\ŒìdBäü´R\Îí◊¶\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 17:40:27'),(_binary 's£\√wfKI$Æˇ∫\ıÑè\ˆy\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 22:13:18'),(_binary '\·\≈6YÑóL\Ù£=LD\‹\ıÄ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 22:18:17'),(_binary '\«&8àíJ≠∂0Œ≤∏F\“k\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 22:47:25'),(_binary '\–\˜†\ÿO˙µ\Ÿß\ﬁ^v\—\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-14 23:25:21'),(_binary '´\»\÷{\ÔG√ºÿíèœ≠\◊\Ô\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-15 14:57:58'),(_binary '\”,?\r\"J|æµ%∏ÄC\∆/\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','admin@empresa.com','2023-07-16 00:34:35');
/*!40000 ALTER TABLE `historial_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` binary(16) NOT NULL,
  `codigo` varchar(20) NOT NULL,
  `nombre_producto` varchar(100) NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `cantidad` decimal(30,2) NOT NULL,
  `valor_compra` decimal(30,2) NOT NULL,
  `valor_venta` decimal(30,2) NOT NULL,
  `disponible` tinyint(1) NOT NULL,
  `fecha_creacion` timestamp NOT NULL,
  `fecha_modificacion` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `productos_uq` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (_binary 'R9\ı\À∞Büñ4¢D\À[:','ABC123','Caja Agua x50','agua pa la familia',50.00,0.34,3.99,1,'2023-07-13 14:36:48','2023-07-13 14:36:48');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tercero`
--

DROP TABLE IF EXISTS `tercero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tercero` (
  `id` binary(16) NOT NULL,
  `ciudad_residencia` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `nit` varchar(20) NOT NULL,
  `pais_residencia` varchar(255) DEFAULT NULL,
  `primer_apellido` varchar(30) DEFAULT NULL,
  `primer_nombre` varchar(30) DEFAULT NULL,
  `razon_social` varchar(100) DEFAULT NULL,
  `segundo_apellido` varchar(30) DEFAULT NULL,
  `segundo_nombre` varchar(30) DEFAULT NULL,
  `ultima_modificacion` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tercero_uq` (`nit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tercero`
--

LOCK TABLES `tercero` WRITE;
/*!40000 ALTER TABLE `tercero` DISABLE KEYS */;
INSERT INTO `tercero` VALUES (_binary '¥™4\’\÷G“ÖOM\„_D','Bogot√°','Calle Principal 123','2023-07-14 12:13:53.980881','1990-05-15','123456789','Colombia','G√≥mez','Juan','Empresa XYZ','P√©rez','Carlos','2023-07-14 12:13:53.980881');
/*!40000 ALTER TABLE `tercero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` binary(36) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(25) NOT NULL,
  `telefono` varchar(16) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(120) NOT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `activo` tinyint(1) DEFAULT '0',
  `rol` varchar(155) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ultima_modificacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario_uq` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (_binary '\ﬁ=\\\ÛXKò≥<øP4w\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','Admin','Administrado','0000000000','admin@empresa.com','$2a$10$eRdC258q7ARdVnlD6YXUhee.eomDKlDC0HykWhijN36aT3Oao8MSC',1,0,'ADMIN','2023-07-12 22:47:02','2023-07-12 22:47:02');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-15 19:34:46
