-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 07 mai 2024 à 03:27
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `stock`
--

-- --------------------------------------------------------

--
-- Structure de la table `fournisseur`
--

CREATE TABLE `fournisseur` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `fournisseur`
--

INSERT INTO `fournisseur` (`id`, `name`, `country`) VALUES
(15, 'Nike', 'Californie'),
(16, 'Adidas', 'Allemagne'),
(17, 'Puma', 'Hongrie'),
(18, 'Kipsta', 'Londres'),
(19, 'Under Armour', 'USA'),
(20, 'Valarmorghulis', 'Polognes'),
(21, 'Jordan', 'USA'),
(22, 'Dior', 'France');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `quantité` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `id_fournisseur` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `name`, `quantité`, `price`, `id_fournisseur`) VALUES
(20, 'Chaussettes', 531, 7, 15),
(21, 'Jogging Tech Fleece', 53, 110, 15),
(22, 'Maillot PSG Domicile', 849, 99, 21),
(23, 'Crampons', 9983, 12, 18),
(24, 'Chasuble', 874, 5, 17),
(25, 'Casquette Valar', 51, 69, 20),
(26, 'Doudoune Maison Valar', 14, 250, 20),
(27, 'Maillot Domicile Allemagne', 608, 89, 16),
(28, 'Casquette', 60, 30, 19),
(29, 'Jordan 1 Bleue / Argentée', 784, 120, 21),
(30, 'B22 Noir / Argentée', 1, 1400, 22);

-- --------------------------------------------------------

--
-- Structure de la table `produitsvendu`
--

CREATE TABLE `produitsvendu` (
  `id` int(11) NOT NULL,
  `id_vente` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantité` int(11) NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `produitsvendu`
--

INSERT INTO `produitsvendu` (`id`, `id_vente`, `id_produit`, `quantité`, `total`) VALUES
(15, 11, 22, 3, 297),
(16, 11, 21, 1, 110),
(17, 11, 27, 1, 89),
(18, 12, 20, 2, 14),
(19, 12, 23, 2, 24),
(20, 12, 24, 5, 25),
(21, 12, 29, 1, 120),
(22, 13, 25, 2, 138),
(23, 13, 26, 1, 250),
(24, 13, 30, 2, 2800),
(25, 13, 28, 1, 30),
(26, 13, 21, 1, 110),
(27, 14, 27, 1, 89),
(28, 15, 20, 13, 91);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`username`, `password`) VALUES
('admin', 'admin'),
('user', 'password'),
('admin', 'admin'),
('user', 'password');

-- --------------------------------------------------------

--
-- Structure de la table `vente`
--

CREATE TABLE `vente` (
  `id` int(11) NOT NULL,
  `client` varchar(50) NOT NULL,
  `total` float NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `vente`
--

INSERT INTO `vente` (`id`, `client`, `total`, `date`) VALUES
(11, 'Dodou', 496, '2024-04-14'),
(12, 'Nicolas', 183, '2024-03-15'),
(13, 'Rayen', 3328, '2024-05-05'),
(14, 'Rayen', 89, '2023-12-12'),
(15, 'Marcel', 91, '2024-05-07');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `étrangère` (`id_fournisseur`);

--
-- Index pour la table `produitsvendu`
--
ALTER TABLE `produitsvendu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ID du produit` (`id_produit`),
  ADD KEY `ID de la vente` (`id_vente`);

--
-- Index pour la table `vente`
--
ALTER TABLE `vente`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pour la table `produitsvendu`
--
ALTER TABLE `produitsvendu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT pour la table `vente`
--
ALTER TABLE `vente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `Fournisseur du produit` FOREIGN KEY (`id_fournisseur`) REFERENCES `fournisseur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `produitsvendu`
--
ALTER TABLE `produitsvendu`
  ADD CONSTRAINT `ID de la vente` FOREIGN KEY (`id_vente`) REFERENCES `vente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ID du produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
