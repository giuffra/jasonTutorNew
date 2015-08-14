-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 14-Ago-2015 às 14:08
-- Versão do servidor: 5.5.41-0ubuntu0.14.04.1
-- versão do PHP: 5.5.9-1ubuntu4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `moodle_cecilia`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_bedel_curso`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_bedel_curso` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_curso` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `mdl_tutor_bedel_curso`
--

INSERT INTO `mdl_tutor_bedel_curso` (`id`, `id_curso`) VALUES
(1, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_dependencia`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_dependencia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curso_id` int(11) NOT NULL,
  `rec_ativ_id` int(11) NOT NULL,
  `pre_req_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=330 ;

--
-- Extraindo dados da tabela `mdl_tutor_dependencia`
--

INSERT INTO `mdl_tutor_dependencia` (`id`, `curso_id`, `rec_ativ_id`, `pre_req_id`) VALUES
(281, 2, 1, 0),
(282, 2, 73, 0),
(283, 2, 3, 1),
(284, 2, 3, 73),
(288, 2, 7, 1),
(289, 2, 7, 3),
(290, 2, 7, 73),
(295, 2, 6, 1),
(296, 2, 6, 3),
(297, 2, 6, 73),
(298, 2, 8, 1),
(299, 2, 8, 3),
(300, 2, 8, 73),
(301, 2, 9, 6),
(302, 2, 9, 7),
(303, 2, 9, 8),
(309, 2, 15, 13),
(310, 2, 15, 10),
(311, 2, 15, 11),
(312, 2, 15, 12),
(313, 2, 10, 9),
(314, 2, 10, 8),
(315, 2, 11, 9),
(316, 2, 11, 8),
(317, 2, 12, 9),
(318, 2, 12, 8),
(319, 2, 16, 13),
(320, 2, 16, 10),
(321, 2, 16, 11),
(322, 2, 16, 12),
(323, 2, 13, 10),
(324, 2, 13, 11),
(325, 2, 13, 12),
(326, 2, 14, 13),
(327, 2, 14, 10),
(328, 2, 14, 11),
(329, 2, 14, 12);

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_media_aluno`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_media_aluno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_aluno` int(11) NOT NULL,
  `id_curso` int(11) NOT NULL,
  `media_aluno` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_media_perfis`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_media_perfis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curso_id` int(11) NOT NULL,
  `perfil_id` int(11) NOT NULL,
  `nota` double NOT NULL,
  `nro_calculo` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `mdl_tutor_media_perfis`
--

INSERT INTO `mdl_tutor_media_perfis` (`id`, `curso_id`, `perfil_id`, `nota`, `nro_calculo`) VALUES
(1, 2, 2, 78.52, 1),
(2, 2, 1, 72.52, 1),
(3, 2, 3, 84.52, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_nota_perfil`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_nota_perfil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curso_id` int(11) NOT NULL,
  `id_grade_item` bigint(20) NOT NULL,
  `aluno_id` int(11) NOT NULL,
  `nota` double NOT NULL,
  `nro_calculo` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=16 ;

--
-- Extraindo dados da tabela `mdl_tutor_nota_perfil`
--

INSERT INTO `mdl_tutor_nota_perfil` (`id`, `curso_id`, `id_grade_item`, `aluno_id`, `nota`, `nro_calculo`) VALUES
(1, 2, 6, 7, 89, 1),
(2, 2, 6, 9, 94, 1),
(3, 2, 6, 5, 104, 1),
(4, 2, 10, 7, 88, 2),
(5, 2, 10, 9, 76.5, 2),
(6, 2, 10, 5, 91.5, 2),
(7, 2, 30, 7, 76.66, 3),
(8, 2, 30, 9, 55.66, 3),
(9, 2, 30, 5, 87.66, 3),
(10, 2, 31, 7, 83.49, 4),
(11, 2, 31, 9, 47.74, 4),
(12, 2, 31, 5, 87.99, 4),
(13, 2, 35, 7, 87.59, 5),
(14, 2, 35, 9, 58.99, 5),
(15, 2, 35, 5, 88.99, 5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_perfil`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_perfil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `mdl_tutor_perfil`
--

INSERT INTO `mdl_tutor_perfil` (`id`, `nome`) VALUES
(1, 'Básico'),
(2, 'Médio'),
(3, 'Avançado'),
(4, 'Geral');

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_rec_at_perfil`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_rec_at_perfil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curso_id` int(11) NOT NULL,
  `rec_ativ_id` int(11) NOT NULL,
  `perfil_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=127 ;

--
-- Extraindo dados da tabela `mdl_tutor_rec_at_perfil`
--

INSERT INTO `mdl_tutor_rec_at_perfil` (`id`, `curso_id`, `rec_ativ_id`, `perfil_id`) VALUES
(59, 2, 1, 4),
(60, 2, 3, 4),
(61, 2, 6, 4),
(62, 2, 7, 4),
(63, 2, 9, 4),
(64, 2, 13, 4),
(65, 2, 17, 4),
(66, 2, 19, 4),
(67, 2, 21, 4),
(68, 2, 23, 4),
(69, 2, 25, 4),
(70, 2, 30, 4),
(71, 2, 31, 4),
(72, 2, 33, 4),
(73, 2, 34, 4),
(74, 2, 38, 4),
(75, 2, 42, 4),
(76, 2, 43, 4),
(77, 2, 47, 4),
(78, 2, 48, 4),
(79, 2, 49, 4),
(80, 2, 50, 4),
(81, 2, 55, 4),
(82, 2, 56, 4),
(83, 2, 57, 4),
(84, 2, 58, 4),
(85, 2, 59, 4),
(86, 2, 60, 4),
(87, 2, 61, 4),
(88, 2, 62, 4),
(89, 2, 63, 4),
(90, 2, 64, 4),
(91, 2, 65, 4),
(92, 2, 66, 4),
(93, 2, 67, 4),
(94, 2, 68, 4),
(95, 2, 69, 4),
(96, 2, 70, 4),
(97, 2, 73, 4),
(98, 2, 72, 4),
(99, 2, 8, 4),
(100, 2, 10, 1),
(101, 2, 11, 2),
(102, 2, 12, 3),
(103, 2, 14, 1),
(104, 2, 15, 2),
(105, 2, 16, 3),
(106, 2, 18, 4),
(107, 2, 20, 4),
(108, 2, 22, 4),
(109, 2, 24, 4),
(110, 2, 26, 1),
(111, 2, 27, 2),
(112, 2, 28, 4),
(113, 2, 32, 4),
(114, 2, 35, 1),
(115, 2, 36, 2),
(116, 2, 37, 3),
(117, 2, 39, 1),
(118, 2, 40, 2),
(119, 2, 41, 3),
(120, 2, 44, 1),
(121, 2, 45, 2),
(122, 2, 46, 3),
(123, 2, 52, 2),
(124, 2, 51, 1),
(125, 2, 53, 3),
(126, 2, 54, 4);

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_tarefas_avaliadas`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_tarefas_avaliadas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `curso_id` bigint(20) NOT NULL,
  `id_grade_items` bigint(20) NOT NULL COMMENT 'id dos itens de nota',
  `avaliada` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=32 ;

--
-- Extraindo dados da tabela `mdl_tutor_tarefas_avaliadas`
--

INSERT INTO `mdl_tutor_tarefas_avaliadas` (`id`, `curso_id`, `id_grade_items`, `avaliada`) VALUES
(1, 2, 2, 0),
(2, 2, 3, 0),
(3, 2, 4, 0),
(4, 2, 5, 0),
(5, 2, 6, 1),
(6, 2, 7, 0),
(7, 2, 8, 0),
(8, 2, 9, 0),
(9, 2, 10, 1),
(10, 2, 11, 0),
(11, 2, 12, 0),
(12, 2, 13, 0),
(13, 2, 14, 0),
(14, 2, 15, 0),
(15, 2, 16, 0),
(16, 2, 17, 0),
(17, 2, 18, 0),
(18, 2, 19, 0),
(19, 2, 20, 0),
(20, 2, 21, 0),
(21, 2, 22, 0),
(22, 2, 23, 0),
(23, 2, 24, 0),
(24, 2, 25, 0),
(25, 2, 26, 0),
(26, 2, 27, 0),
(27, 2, 28, 0),
(28, 2, 29, 0),
(29, 2, 30, 1),
(30, 2, 31, 1),
(31, 2, 35, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `mdl_tutor_tutor_aluno`
--

CREATE TABLE IF NOT EXISTS `mdl_tutor_tutor_aluno` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_aluno` bigint(20) NOT NULL,
  `id_curso` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `mdl_tutor_tutor_aluno`
--

INSERT INTO `mdl_tutor_tutor_aluno` (`id`, `id_aluno`, `id_curso`) VALUES
(1, 7, 2),
(2, 9, 2),
(3, 5, 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
