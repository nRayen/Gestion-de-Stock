package Pages;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.formdev.flatlaf.FlatLightLaf;

import Components.exitButton;

public class App extends JFrame {

    public App() {

        // Paramètres de l'application
        setTitle("Gestion des Stocks");
        setSize(1080, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du TabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Page Quitter
        JPanel pageExit = new JPanel();
        pageExit.setLayout(new BorderLayout());
        pageExit.add(new exitButton(), BorderLayout.CENTER);

        // Ajout des Pages au TabbedPane
        tabbedPane.addTab("Produits", new pageProduits());
        tabbedPane.addTab("Fournisseurs", new pageFournisseurs());
        tabbedPane.addTab("Ventes", new pageVentes());
        tabbedPane.addTab("Rapports", new pageRapports());
        tabbedPane.addTab("Quitter", pageExit);
        add(tabbedPane);

        // removeButton = new JButton("Supprimer"); // Bouton Supprimer
        // removeButton.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // removeUser();
        // }
        // });

        // editButton = new JButton("Mettre à jour"); // Bouton Modifier
        // editButton.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // editUser();
        // }
        // });

        // panel.add(infoPanel);
        // panel.add(buttonPanel);

        // infoPanel.add(idLabel);
        // infoPanel.add(idField);
        // infoPanel.add(nameLabel);
        // infoPanel.add(nameField);
        // infoPanel.add(stockLabel);
        // infoPanel.add(stockField);
        // buttonPanel.add(addButton);
        // buttonPanel.add(removeButton);
        // buttonPanel.add(editButton);
    }

    // private void addUser() { // Ajout Utilisateur
    // try {
    // // Connexion à la base de données
    // Connection con =
    // DriverManager.getConnection("jdbc:mysql://localhost:3306/testProjet", "root",
    // "");

    // // Préparation de la requête SQL
    // String query = "INSERT INTO user (user_id, user_name, user_age) VALUES (?, ?,
    // ?)";
    // PreparedStatement pstmt = con.prepareStatement(query);

    // // Récupération des données saisies par l'utilisateur
    // int id = Integer.parseInt(idField.getText());
    // String name = nameField.getText();
    // int age = Integer.parseInt(stockField.getText());

    // // Remplissage des paramètres de la requête SQL
    // pstmt.setInt(1, id);
    // pstmt.setString(2, name);
    // pstmt.setInt(3, age);

    // // Exécution de la requête SQL
    // int rowsAffected = pstmt.executeUpdate();

    // // Fermeture de la connexion et du PreparedStatement
    // pstmt.close();
    // con.close();

    // // Affichage d'un message de succès
    // JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès!");
    // } catch (SQLException ex) {
    // ex.printStackTrace();
    // JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'utilisateur:
    // " + ex.getMessage());
    // }
    // }

    // private void removeUser() { // Suppression Utilisateur
    // try {
    // // Connexion à la base de données
    // Connection con =
    // DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb", "root",
    // "");

    // // Préparation de la requête SQL
    // String query = "DELETE from `user` WHERE `user_id` = (?)";
    // PreparedStatement pstmt = con.prepareStatement(query);

    // // Récupération des données saisies par l'utilisateur
    // int id = Integer.parseInt(idField.getText());

    // // Remplissage des paramètres de la requête SQL
    // pstmt.setInt(1, id);

    // // Exécution de la requête SQL
    // int rowsAffected = pstmt.executeUpdate();

    // // Fermeture de la connexion et du PreparedStatement
    // pstmt.close();
    // con.close();

    // // Affichage d'un message de succès
    // JOptionPane.showMessageDialog(this, "Utilisateur avec l'id " + id + " à été
    // supprimé avec succès!");
    // } catch (SQLException ex) {
    // ex.printStackTrace();
    // JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de
    // l'utilisateur: " + ex.getMessage());
    // }
    // }

    // private void editUser() { // Modification Utilisateur
    // try {
    // // Connexion à la base de données
    // Connection con =
    // DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb", "root",
    // "");

    // // Préparation de la requête SQL
    // String query = "UPDATE `user` SET user_name = (?), user_age = (?) WHERE
    // user_id = (?)";
    // PreparedStatement pstmt = con.prepareStatement(query);

    // // Récupération des données saisies par l'utilisateur
    // int id = Integer.parseInt(idField.getText());
    // String name = nameField.getText();
    // int age = Integer.parseInt(stockField.getText());

    // // Remplissage des paramètres de la requête SQL
    // pstmt.setString(1, name);
    // pstmt.setInt(2, age);
    // pstmt.setInt(3, id);

    // // Exécution de la requête SQL
    // int rowsAffected = pstmt.executeUpdate();

    // // Fermeture de la connexion et du PreparedStatement
    // pstmt.close();
    // con.close();

    // // Affichage d'un message de succès
    // JOptionPane.showMessageDialog(this, "Utilisateur mis à jour avec succès!");
    // } catch (SQLException ex) {
    // ex.printStackTrace();
    // JOptionPane.showMessageDialog(this, "Erreur lors de la modification de
    // l'utilisateur: " + ex.getMessage());
    // }
    // }

    public static void main(String[] args) {

        // UIManager.setLookAndFeel(new FlatLightLaf());
        try {

            // Parcourir les Look and Feel disponibles
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    // Utiliser le Look and Feel "Nimbus"
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
                    break;
                }
            }
        } catch (Exception e) {
            // En cas d'erreur, afficher la trace
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                App Interface = new App();
                Interface.setVisible(true);

            }
        });
    }
}
