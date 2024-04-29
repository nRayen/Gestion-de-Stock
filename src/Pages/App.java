package Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Components.StyledButton;
import Components.pageTitle;

public class App extends JFrame {
    JTextField loginEntry = new JTextField();
    JPasswordField pswdEntry = new JPasswordField();

    public App() {
        setTitle("Gestion des Stocks");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout());

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        add(content);

        // Titre de la page
        pageTitle title = new pageTitle("Gestion de stock");
        title.setBackground(new Color(61, 131, 197));
        title.setForeground(new Color(221, 230, 237));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(title, BorderLayout.NORTH);

        // Entry area
        Container connexionArea = new Container();
        content.add(connexionArea, BorderLayout.CENTER);
        connexionArea.setLayout(new GridBagLayout());
        GridBagConstraints gdbc = new GridBagConstraints();

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Montserrat", Font.PLAIN, 25));
        loginLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        loginEntry.setFont(new Font("Montserrat", Font.PLAIN, 25));
        loginEntry.setColumns(10);

        JLabel pswdLabel = new JLabel("Mot de passe");
        pswdLabel.setFont(new Font("Montserrat", Font.PLAIN, 25));
        pswdLabel.setHorizontalAlignment(SwingConstants.CENTER);

        pswdEntry.setFont(new Font("Montserrat", Font.PLAIN, 25));
        pswdEntry.setColumns(10);

        gdbc.insets = new Insets(15, 15, 15, 15);
        gdbc.gridx = 0;
        connexionArea.add(loginLabel, gdbc);

        gdbc.gridx = 1;
        connexionArea.add(loginEntry, gdbc);

        gdbc.gridy = 1;
        gdbc.gridx = 0;
        connexionArea.add(pswdLabel, gdbc);

        gdbc.gridx = 1;
        connexionArea.add(pswdEntry, gdbc);

        // Button Area
        StyledButton loginButton = new StyledButton("Connexion");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkLogins();
            }
        });

        content.add(loginButton, BorderLayout.SOUTH);

    }

    private void checkLogins() {
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "SELECT COUNT(*) AS count FROM user WHERE username = ? AND password = ?";

            PreparedStatement pstmt = con.prepareStatement(query);

            // Récupération des données saisies par l'utilisateur
            String user = loginEntry.getText();
            String pswd = String.valueOf(pswdEntry.getPassword());

            // Remplissage des paramètres de la requête SQL
            pstmt.setString(1, user);
            pstmt.setString(2, pswd);

            // Exécution de la requête SQL
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int count = rs.getInt("count");
                if (count == 0) {
                    JOptionPane.showMessageDialog(this, "Identifiants incorrects");
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            Interface Interface = new Interface(); // Crée et ouvre l'application
                            Interface.setVisible(true);

                            dispose(); // Ferme la fenêtre de connexion
                        }
                    });
                }
            }

            // Fermeture de la connexion et du PreparedStatement
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'utilisateur: " + ex.getMessage());
        }
    }

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
