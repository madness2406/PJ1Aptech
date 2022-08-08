-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 08, 2022 at 11:12 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `booklibrary`
--
CREATE DATABASE IF NOT EXISTS `booklibrary` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `booklibrary`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `AccountId` int(11) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `PhoneNo` varchar(10) DEFAULT NULL,
  `Role` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0: admin\r\n1: Employee'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`AccountId`, `UserName`, `Password`, `Email`, `PhoneNo`, `Role`) VALUES
(1, 'admin', 'admin', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `BookId` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Author` varchar(50) NOT NULL,
  `CategoryId` int(11) NOT NULL,
  `PublishYear` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` decimal(10,0) NOT NULL,
  `CreatedTime` datetime NOT NULL DEFAULT current_timestamp(),
  `CreatorId` int(11) NOT NULL,
  `ModifiedTime` datetime DEFAULT NULL,
  `ChangerId` int(11) DEFAULT NULL,
  `Note` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`BookId`, `Name`, `Author`, `CategoryId`, `PublishYear`, `Quantity`, `Price`, `CreatedTime`, `CreatorId`, `ModifiedTime`, `ChangerId`, `Note`) VALUES
(1, 'Test edit', 'Conan', 1, 1, 7, '10000', '2022-08-03 15:31:20', 0, '2022-08-08 09:51:23', NULL, ''),
(2, 'Conan', 'Conan', 1, 1, 10, '10000', '2022-08-03 15:36:11', 0, '2022-08-08 09:41:55', NULL, ''),
(3, 'Conan', 'Conan', 1, 1, 10, '10000', '2022-08-03 15:47:42', 0, '2022-08-08 09:41:55', NULL, ''),
(6, 'test edit lan 2', 'test', 1, 1, 10, '10000', '2022-08-08 15:20:18', 1, '2022-08-08 15:21:49', 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `BookingId` int(11) NOT NULL,
  `RenderId` int(11) NOT NULL,
  `LenderId` int(11) NOT NULL,
  `DateOfRent` date NOT NULL DEFAULT current_timestamp(),
  `ExpiredDay` date NOT NULL,
  `Status` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL DEFAULT 'Mượn',
  `Deposit` float DEFAULT NULL,
  `TotalMoney` float DEFAULT NULL,
  `Note` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`BookingId`, `RenderId`, `LenderId`, `DateOfRent`, `ExpiredDay`, `Status`, `Deposit`, `TotalMoney`, `Note`) VALUES
(3, 1, 1, '2022-08-08', '2022-02-02', 'Mượn', 10000, 30000, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `bookingdetail`
--

CREATE TABLE `bookingdetail` (
  `BookBookingId` int(11) NOT NULL,
  `BookId` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Money` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bookingdetail`
--

INSERT INTO `bookingdetail` (`BookBookingId`, `BookId`, `Quantity`, `Money`) VALUES
(3, 1, 2, 20000),
(3, 3, 1, 10000);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `CategoryId` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `CreatedTime` datetime NOT NULL DEFAULT current_timestamp(),
  `ModifyTime` datetime DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`CategoryId`, `Name`, `CreatedTime`, `ModifyTime`, `Description`) VALUES
(1, 'Trinh tham', '2022-08-03 15:10:35', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `Id` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Gender` varchar(5) NOT NULL,
  `DOB` date NOT NULL,
  `POB` varchar(15) NOT NULL,
  `PhoneNo` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`Id`, `Name`, `Gender`, `DOB`, `POB`, `PhoneNo`) VALUES
(1, 'Test', 'Nam', '2022-08-05', 'Test', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`AccountId`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`BookId`),
  ADD KEY `FK_Category_CategoryId` (`CategoryId`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`BookingId`),
  ADD KEY `FK_Account_LenderId` (`LenderId`),
  ADD KEY `FK_Student_StudentId` (`RenderId`);

--
-- Indexes for table `bookingdetail`
--
ALTER TABLE `bookingdetail`
  ADD PRIMARY KEY (`BookBookingId`,`BookId`),
  ADD KEY `FK_Book_BookId` (`BookId`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`CategoryId`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `AccountId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `BookId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `BookingId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `CategoryId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `FK_Category_CategoryId` FOREIGN KEY (`CategoryId`) REFERENCES `category` (`CategoryId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK_Account_LenderId` FOREIGN KEY (`LenderId`) REFERENCES `account` (`AccountId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Student_StudentId` FOREIGN KEY (`RenderId`) REFERENCES `student` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bookingdetail`
--
ALTER TABLE `bookingdetail`
  ADD CONSTRAINT `FK_Book_BookId` FOREIGN KEY (`BookId`) REFERENCES `book` (`BookId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Booking_BookingId` FOREIGN KEY (`BookBookingId`) REFERENCES `booking` (`BookingId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
