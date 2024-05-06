package Pages;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Components.InputField;
import Components.InputLabel;
import Components.StyledButton;
import Components.pageTitle;
import DataAccessObject.RapportDAO;
import Stock.Rapport;

public class pageRapports extends JPanel {
    RapportDAO rDAO = new RapportDAO();

    InputField startInput = new InputField();
    InputField endInput = new InputField();

    public pageRapports() {
        setLayout(new BorderLayout());
        add(new pageTitle("Rapports"), BorderLayout.NORTH);

        JPanel content = new JPanel();
        add(content, BorderLayout.CENTER);

        JPanel inputArea = new JPanel();
        content.add(inputArea);
        content.setLayout(new GridLayout(2, 1));
        content.setBorder(new EmptyBorder(50, 50, 50, 100));

        inputArea.setLayout(new GridLayout(2, 1, 100, 50));

        InputLabel startLabel = new InputLabel("Date début");
        InputLabel endLabel = new InputLabel("Date fin");

        inputArea.add(startLabel);
        inputArea.add(startInput);
        inputArea.add(endLabel);
        inputArea.add(endInput);

        // Button Area

        JPanel buttonArea = new JPanel();
        buttonArea.setBorder(new EmptyBorder(50, 0, 50, 0));
        content.add(buttonArea);

        StyledButton bouton = new StyledButton("Créer rapport");
        bouton.addActionListener(new ActionListener() { // Bouton ajouter produit
            public void actionPerformed(ActionEvent e) {
                String dateDébut = startInput.getText();
                String dateFin = endInput.getText();
                Rapport rapport = new Rapport(dateDébut, dateFin);
                rDAO.createRapport(rapport);
            }
        });
        buttonArea.add(bouton);
    }

}
