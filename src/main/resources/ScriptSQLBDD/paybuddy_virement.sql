-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: paybuddy
-- ------------------------------------------------------
-- Server version	5.7.30-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `virement`
--

DROP TABLE IF EXISTS `virement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `virement` (
  `id_virement` int(11) NOT NULL AUTO_INCREMENT,
  `date_precise` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `montant_commission` double DEFAULT NULL,
  `montant_transaction` double DEFAULT NULL,
  `pseudo_emetteur` varchar(255) DEFAULT NULL,
  `id_compte_personnel` int(11) DEFAULT NULL,
  `id_compte_personnel_emetteur` int(11) DEFAULT NULL,
  `id_compte_personnel_recepteur` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_virement`),
  KEY `FK9tf3cen81yd5gy41e0uakk7ig` (`id_compte_personnel`),
  KEY `FKfw02dlpfl1nx15dnhx3ai6o04` (`id_compte_personnel_emetteur`),
  KEY `FKj0iguvjg827iat76ft1q3ndyg` (`id_compte_personnel_recepteur`),
  CONSTRAINT `FK9tf3cen81yd5gy41e0uakk7ig` FOREIGN KEY (`id_compte_personnel`) REFERENCES `compte_personnel` (`id_compte_personnel`),
  CONSTRAINT `FKfw02dlpfl1nx15dnhx3ai6o04` FOREIGN KEY (`id_compte_personnel_emetteur`) REFERENCES `compte_personnel` (`id_compte_personnel`),
  CONSTRAINT `FKj0iguvjg827iat76ft1q3ndyg` FOREIGN KEY (`id_compte_personnel_recepteur`) REFERENCES `compte_personnel` (`id_compte_personnel`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-08 19:45:24
