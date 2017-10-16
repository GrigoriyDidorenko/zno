-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: zno
-- ------------------------------------------------------
-- Server version	5.7.17-log

use zno;

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
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'\0','34',NULL,5,1),(2,'\0','А. 1/(x−2)=0',NULL,5,2),(3,'\0','Б. x2+4=0',NULL,0,2),(4,'\0','В. 5x+12=0',NULL,0,2),(5,'\0','Г. x+2=x',NULL,0,2),(6,'\0','Д. (3x−6)/x=0',NULL,0,2),(7,'\0','А. n - 8',NULL,5,3),(8,'\0','Б. 6 - n',NULL,0,3),(9,'\0','В. 8 - n',NULL,0,3),(10,'\0','Г. n - 6',NULL,0,3),(11,'\0','Д. 6 + n',NULL,0,3),(12,'\0','А. 2m',NULL,1,5),(13,'\0','Б.	0',NULL,1,5),(14,'\0','В.	1/m',NULL,1,5),(15,'\0','Г.	m',NULL,1,5),(16,'\0','Д.	m2',NULL,0,5),(17,'\0','true',NULL,3,9),(18,'\0','true',NULL,3,9),(19,'\0','false',NULL,0,9),(20,'\0','false',NULL,0,9),(21,'\0','15/4',NULL,5,10),(22,'\0','3/20',NULL,0,10),(23,'\0','3/8',NULL,0,10),(24,'\0','4/15',NULL,0,10),(25,'\0','11/20',NULL,0,10),(26,'\0','у = -x + 2',NULL,1,12),(27,'\0','у = x2 - 2',NULL,1,12),(28,'\0','у = -1/x',NULL,1,12),(29,'\0','у = 3x',NULL,1,12),(30,'\0','у = cosx',NULL,0,12),(31,'\0','7',NULL,5,16),(32,'\0','m a 1',NULL,1,17),(33,'\0','m a 2',NULL,1,17),(34,'\0','ma 3',NULL,1,17),(35,'\0','m a 4',NULL,1,17),(36,'\0','m a 5',NULL,0,17),(37,'\0','велике сузір..я, роз..ятрена рана',NULL,5,18),(38,'\0','очікувана прем..єра, духм..яна страва',NULL,0,18),(39,'\0','гарне ім..я, швидкий пор..ятунок',NULL,0,18),(40,'\0','досвідчений різьб..яр, високий бур..ян',NULL,0,18),(41,'\0','істотний',NULL,1,20),(42,'\0','всеосяжний',NULL,1,20),(43,'\0','передовий',NULL,1,20),(44,'\0','підроблений',NULL,1,20),(45,'\0','запобіжний',NULL,0,20),(46,'\0','open answer',NULL,5,24),(47,'\0','кропива',NULL,5,25),(48,'\0','периметр',NULL,0,25),(49,'\0','підошва',NULL,0,25),(50,'\0','однаковий',NULL,0,25),(51,'\0','..ціпити, ..палити, ..чинити',NULL,5,26),(52,'\0','..хопити, ..цідити, ..чистити',NULL,0,26),(53,'\0',' ..сувати, ..пиляти, ..чесати',NULL,0,26),(54,'\0','..садити, ..чепити, ..варити',NULL,0,26),(55,'\0','Open answer',NULL,5,27),(56,'\0','1',NULL,1,28),(57,'\0','2',NULL,2,28),(58,'\0','3',NULL,2,28),(59,'\0','4',NULL,0,28);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `failed_questions`
--

LOCK TABLES `failed_questions` WRITE;
/*!40000 ALTER TABLE `failed_questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `failed_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'\0',NULL,NULL,1,'Початкова вартість сукні становила 144 грн. В результаті уцінки вартість цього плаття зменшилася на 80% Обчисліть вартість плаття після уцінки (у грн).  ',3,1),(2,'\0',NULL,NULL,2,'Укажіть рівняння, коренем якого є число 2.',0,1),(3,'\0',NULL,NULL,3,'Якщо m=n−1m=n−1, то 7−m=',0,1),(4,'\0',NULL,NULL,4,'До кожного виразу (1-4) доберіть тотожно йому рівний (А-Д), якщо m>2m>2,  mm  - натуральне число.',2,1),(5,'\0',NULL,4,NULL,'(m+1)2−m2−1',0,1),(6,'\0',NULL,4,NULL,'m∗cos2(α)+m∗sin2(α)',0,1),(7,'\0',NULL,4,NULL,'100lg(m)100lg(m) ',0,1),(8,'\0',NULL,4,NULL,'log2(2–√m)log2(2m) ',0,1),(9,'\0',NULL,NULL,5,'Multy',1,1),(10,'\0',NULL,NULL,1,'У геометричній прогресії (bn) задано b3 = 0,2; b4 = 3/4. Знайдіть знаменник цієї  прогресії',0,2),(11,'\0',NULL,NULL,2,'Установіть відповідність між твердженням (1-4) та функцією (А-Д), для якої це твердження є правильним.',2,2),(12,'\0',NULL,11,NULL,'1. графік функції не перетинає жодну з осей координат ',0,2),(13,'\0',NULL,11,NULL,'областю значень функції є проміжок (0; + ∞)',0,2),(14,'\0',NULL,11,NULL,'функція спадає на всій області визначення',0,2),(15,'\0',NULL,11,NULL,' навідрізку [-1,5; 1,5] функція має два нулі',0,2),(16,'\0',NULL,NULL,3,'У прямокутний трикутник АВС вписано коло, яке дотикається катетів АС таВС у точках K і М відповідно. Знайдіть радіус кола, описаного навколо трикутника АВС (у см), якщо АК = 4,5 см, МВ = 6 см.  ',3,2),(17,'\0',NULL,NULL,4,'one more mult question',1,2),(18,'\0',NULL,NULL,1,'Апостроф слід писати на місці обох пропусків у рядку',0,3),(19,'\0',NULL,NULL,2,'Доберіть синоніми.',2,3),(20,'\0',NULL,19,NULL,'превентивний',0,3),(21,'\0',NULL,19,NULL,'тотальний',0,3),(22,'\0',NULL,19,NULL,'фіктивний',0,3),(23,'\0',NULL,19,NULL,'кардинальний',0,3),(24,'\0',NULL,NULL,3,'Open q',3,3),(25,'\0',NULL,NULL,1,'На третій склад падає наголос у слові',0,4),(26,'\0',NULL,NULL,2,'Префікс з- треба писати в усіх словах рядка',0,4),(27,'\0',NULL,NULL,3,'Open q',3,4),(28,'\0',NULL,NULL,4,'Mult q',1,4);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'\0','{\"adviceTitle\": \"Корисні поради\",\"advices\": {\"Елена Кононенко, учитель математики Технического лицея НТУУ \'КПИ\'\" : \"Прогоните дополнительно тесты посамым проблемным темам, а перед тестированием все полностью, чтобы довести навыки до автоматизма.\",\"Елена Кирдягенко\" \"блааа\"},\"additionalResourcesTitle\" : \"Додаткові матеріали\",\"additionalResources\": [\"Критерії оцінювання завдання з розгорнутою відповіддю з математики (2016 рік)\",\"Критерії оцінювання завдання з розгорнутою відповіддю з математики (2017 рік)\"]}','Математика'),(2,'\0','{\"adviceTitle\": \"Корисні поради\",\"advices\": {\"Елена Кононенко, учитель математики Технического лицея НТУУ \'КПИ\'\" : \"Прогоните дополнительно тесты посамым проблемным темам, а перед тестированием все полностью, чтобы довести навыки до автоматизма.\",\"Елена Кирдягенко\" \"блааа\"},\"additionalResourcesTitle\" : \"Додаткові матеріали\",\"additionalResources\": [\"Критерії оцінювання завдання з розгорнутою відповіддю з математики (2016 рік)\",\"Критерії оцінювання завдання з розгорнутою відповіддю з математики (2017 рік)\"]}','Українська мова');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `test_results`
--

LOCK TABLES `test_results` WRITE;
/*!40000 ALTER TABLE `test_results` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tests`
--

LOCK TABLES `tests` WRITE;
/*!40000 ALTER TABLE `tests` DISABLE KEYS */;
INSERT INTO `tests` VALUES (1,'\0',100,'Математика 2016',2016,1),(2,'\0',100,'Математика 2017',2017,1),(3,'\0',120,'Українська мова і література 2016',2016,2),(4,'\0',120,'Українська мова і література 2017',2017,2);
/*!40000 ALTER TABLE `tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_authorities`
--

LOCK TABLES `user_authorities` WRITE;
/*!40000 ALTER TABLE `user_authorities` DISABLE KEYS */;
INSERT INTO `user_authorities` VALUES (1,0),(2,0);
/*!40000 ALTER TABLE `user_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'\0','2017-12-11 14:59:34','obalitskyi@gmail.com','','obalitskyi','Alex','fffzzz23'),(2,'\0','2017-10-16 13:28:27','alexbalitskyformore@gmail.com','','107908030398741291456','Олександр Баліцький','$2a$12$.Jpn7xD3BRh5zzHr0aPqruL/YS7Ny5trdyzbX7fQcLER6BmmkVO.m');
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

-- Dump completed on 2017-10-17  0:02:15
