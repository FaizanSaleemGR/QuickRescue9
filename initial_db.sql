-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: quickrescue
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `accountId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email_domain` varchar(100) DEFAULT NULL,
  `time_zone_city` varchar(100) DEFAULT NULL,
  `account_contract` int(11) DEFAULT NULL,
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'QuickRescue','quickrescue.com','Islamabad',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_contracts`
--

DROP TABLE IF EXISTS `account_contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_contracts` (
  `contractId` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `contacts_limit` int(11) DEFAULT NULL,
  `logins_limit` int(11) DEFAULT NULL,
  `accountId` int(11) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`contractId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_contracts`
--

LOCK TABLES `account_contracts` WRITE;
/*!40000 ALTER TABLE `account_contracts` DISABLE KEYS */;
INSERT INTO `account_contracts` VALUES (1,'2017-09-10 00:00:00','2017-12-31 00:00:00',50000,50000,1,1);
/*!40000 ALTER TABLE `account_contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alert_profile`
--

DROP TABLE IF EXISTS `alert_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert_profile` (
  `profileId` int(11) NOT NULL AUTO_INCREMENT,
  `profile_name` varchar(255) DEFAULT NULL,
  `locationId` int(11) DEFAULT NULL,
  `accountId` int(11) DEFAULT NULL,
  PRIMARY KEY (`profileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert_profile`
--

LOCK TABLES `alert_profile` WRITE;
/*!40000 ALTER TABLE `alert_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `contactId` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email_address` varchar(45) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `street_address` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `accountId` int(11) DEFAULT NULL,
  `hasLogin` tinyint(4) DEFAULT NULL,
  `contact_login_details` int(11) DEFAULT NULL,
  PRIMARY KEY (`contactId`),
  UNIQUE KEY `contactLoginId_UNIQUE` (`contact_login_details`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'Faizan','Saleem','fsaleem','M','03360109767',1,'A-5, 25/2, H.M.C. Housing Colony','Taxila','Punjab','Pakistan',1,1,1);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_login_details`
--

DROP TABLE IF EXISTS `contact_login_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_login_details` (
  `contactLoginId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(75) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `contactId` int(11) DEFAULT NULL,
  PRIMARY KEY (`contactLoginId`),
  UNIQUE KEY `contactId_UNIQUE` (`contactId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_login_details`
--

LOCK TABLES `contact_login_details` WRITE;
/*!40000 ALTER TABLE `contact_login_details` DISABLE KEYS */;
INSERT INTO `contact_login_details` VALUES (1,'fsaleem@quickrescue.com','123',1);
/*!40000 ALTER TABLE `contact_login_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `locationId` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(75) DEFAULT NULL,
  `country` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_location`
--

DROP TABLE IF EXISTS `profile_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_location` (
  `profileId` int(11) NOT NULL,
  `locationId` int(11) NOT NULL,
  PRIMARY KEY (`profileId`,`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_location`
--

LOCK TABLES `profile_location` WRITE;
/*!40000 ALTER TABLE `profile_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile_location` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-11 11:46:16
