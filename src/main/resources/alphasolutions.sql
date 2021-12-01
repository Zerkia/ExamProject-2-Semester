-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: alphasolutions
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `projectID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `projectName` varchar(100) NOT NULL,
  `deadline` datetime NOT NULL,
  PRIMARY KEY (`projectID`),
  UNIQUE KEY `projectName_UNIQUE` (`projectName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,3,'adminProject','2021-11-02 00:00:00'),(3,4,'johnsProject','2021-02-12 00:00:01');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subprojects`
--

DROP TABLE IF EXISTS `subprojects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subprojects` (
  `subprojectID` int NOT NULL AUTO_INCREMENT,
  `projectID` int NOT NULL,
  `userID` int NOT NULL,
  `subprojectName` varchar(100) NOT NULL,
  `deadline` datetime NOT NULL,
  PRIMARY KEY (`subprojectID`),
  UNIQUE KEY `subprojectName_UNIQUE` (`subprojectName`),
  KEY `fkprojectsubproject_idx` (`projectID`),
  CONSTRAINT `fkprojectsubproject` FOREIGN KEY (`projectID`) REFERENCES `projects` (`projectID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subprojects`
--

LOCK TABLES `subprojects` WRITE;
/*!40000 ALTER TABLE `subprojects` DISABLE KEYS */;
INSERT INTO `subprojects` VALUES (1,1,3,'Sub1','2021-02-02 00:00:00'),(2,1,3,'Sub2','2021-02-02 23:59:59'),(3,3,4,'sub3','2021-02-02 00:00:00');
/*!40000 ALTER TABLE `subprojects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subtasks`
--

DROP TABLE IF EXISTS `subtasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subtasks` (
  `subtaskID` int NOT NULL AUTO_INCREMENT,
  `taskID` int NOT NULL,
  `userID` int NOT NULL,
  `subtaskName` varchar(100) NOT NULL,
  `deadline` datetime NOT NULL,
  PRIMARY KEY (`subtaskID`),
  UNIQUE KEY `subtaskscol_UNIQUE` (`subtaskName`),
  KEY `fksubtaskTask_idx` (`taskID`),
  CONSTRAINT `fksubtaskTask` FOREIGN KEY (`taskID`) REFERENCES `tasks` (`taskID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subtasks`
--

LOCK TABLES `subtasks` WRITE;
/*!40000 ALTER TABLE `subtasks` DISABLE KEYS */;
INSERT INTO `subtasks` VALUES (1,1,3,'subtask1','2021-12-12 00:00:00'),(2,1,3,'subtask2','2021-10-10 01:00:00'),(3,2,3,'subtask3','2021-11-10 00:00:00');
/*!40000 ALTER TABLE `subtasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `taskID` int NOT NULL AUTO_INCREMENT,
  `subprojectID` int NOT NULL,
  `userID` int NOT NULL,
  `taskName` varchar(100) NOT NULL,
  `deadline` datetime NOT NULL,
  PRIMARY KEY (`taskID`),
  UNIQUE KEY `taskName_UNIQUE` (`taskName`),
  KEY `fktasksubproject_idx` (`subprojectID`),
  CONSTRAINT `fktasksubproject` FOREIGN KEY (`subprojectID`) REFERENCES `subprojects` (`subprojectID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,1,3,'task1','2021-02-02 00:00:00'),(2,1,3,'task2','2021-02-02 00:00:00'),(3,2,4,'task3','2021-02-02 00:00:00');
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userroles` (
  `userroleID` int NOT NULL AUTO_INCREMENT,
  `userrole` varchar(100) NOT NULL,
  PRIMARY KEY (`userroleID`),
  UNIQUE KEY `userrole_UNIQUE` (`userrole`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES (2,'Admin'),(1,'SuperAdmin'),(3,'Worker');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `userroleID` int NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  KEY `fkusersUserRoles_idx` (`userroleID`),
  CONSTRAINT `fkusersuserroles` FOREIGN KEY (`userroleID`) REFERENCES `userroles` (`userroleID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'SuperAdmin','superadmin',1),(4,'John Wick','john',3),(5,'test','test',3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-29 17:03:13
