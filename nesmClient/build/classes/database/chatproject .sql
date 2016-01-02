-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 29, 2015 at 12:16 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `chatproject`
--

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE IF NOT EXISTS `friends` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`file_id`),
  KEY `USER_INDEX` (`user_id`),
  KEY `Friend_index` (`friend_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`file_id`, `user_id`, `friend_id`) VALUES
(1, 10, 9),
(2, 10, 11),
(3, 10, 12);

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE IF NOT EXISTS `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `send_request` int(11) NOT NULL,
  `recevier_id` int(11) NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `send_request` (`send_request`),
  KEY `recevier_id` (`recevier_id`) COMMENT 'fk_r'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL,
  `E-mail` varchar(50) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `E-mail` (`E-mail`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `E-mail`, `Password`, `state`) VALUES
(9, 'safaa', 'nehal@hasan', 'atef1234Eman', 0),
(10, 'atef', 'atef.com', '1234', 0),
(11, 'eman', 'eman.com', '1234', 1),
(12, 'nehal', 'nehal.com', '1234', 1),
(13, 'marwa', 'marwa.com', '1234', 0),
(14, 'hadia', 'hadia.com', '1234', 0),
(15, 'mahmoud', 'mahmoud.com', '1234', 0),
(17, 'mohsen', 'mohsen.com', '1234', 0),
(18, 'ok', 'ok.com', '1234', 0),
(19, 'donia', 'donia.com', '1234', 0),
(20, 'Mohamed', 'moh.said511@yahoo.com', '12345678', 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `Friend_FK` FOREIGN KEY (`friend_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `User_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`send_request`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `request_ibfk_2` FOREIGN KEY (`recevier_id`) REFERENCES `users` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
