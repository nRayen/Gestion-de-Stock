package Pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Components.StyledButton;
import Components.pageTitle;
import DataAccessObject.RapportDAO;
import Stock.Rapport;

public class pageRapports extends JPanel {
    Rapport rapport = new Rapport("dq", "qd");
    RapportDAO rDAO = new RapportDAO();

    public pageRapports() {
        setLayout(new BorderLayout());
        add(new pageTitle("Rapports"), BorderLayout.NORTH);

        JPanel content = new JPanel();
        add(content, BorderLayout.CENTER);

        StyledButton bouton = new StyledButton("Créer rapport");
        bouton.addActionListener(new ActionListener() { // Bouton ajouter produit
            public void actionPerformed(ActionEvent e) {
                rDAO.createRapport(rapport);
            }
        });
        content.add(bouton);
    }
}
