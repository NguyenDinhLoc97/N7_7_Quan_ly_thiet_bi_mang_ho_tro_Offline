-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 31, 2018 lúc 02:18 AM
-- Phiên bản máy phục vụ: 10.1.34-MariaDB
-- Phiên bản PHP: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `devicemanager`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `devices`
--

CREATE TABLE `devices` (
  `ID` int(11) DEFAULT NULL,
  `Name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Price` float DEFAULT NULL,
  `Count` int(11) DEFAULT NULL,
  `StartYear` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DeadYear` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Manufacturer` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `devices`
--

INSERT INTO `devices` (`ID`, `Name`, `Price`, `Count`, `StartYear`, `DeadYear`, `Manufacturer`) VALUES
(1000, 'Router', 125000, 120, '10/12/2017', '10/12/2020', 'Intel'),
(1001, 'Bridge', 1300000, 150, '10/12/2017', '10/12/2020', 'Asus'),
(1002, 'Swtich', 150000, 150, '10/12/2017', '10/12/2020', 'Del'),
(1003, 'Repeater', 150000, 130, '10/12/2017', '10/12/2020', 'Del'),
(1004, 'Hub', 150000, 220, '10/12/2017', '10/12/2020', 'intel'),
(1005, 'Gateway', 120000, 230, '10/12/2017', '10/12/2020', 'Nivia'),
(1006, 'Gateway', 120000, 230, '10/12/2017', '10/12/2020', 'Nivia');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employees`
--

CREATE TABLE `employees` (
  `ID` int(4) NOT NULL,
  `Name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Address` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NumberPhone` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Chức vụ` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `employees`
--

INSERT INTO `employees` (`ID`, `Name`, `Address`, `NumberPhone`, `Chức vụ`) VALUES
(1000, 'Tat Tien', 'Hưng Yên', '01686566020', 'Nhân Viên'),
(1001, 'Van Phuc', 'Tân Châu', '01655642662', 'Quản lý');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `userName` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`userName`, `password`, `level`) VALUES
('Tat Tien', 'abc123', 1),
('Van Phuc', 'abc123', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
