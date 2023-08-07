package motFlechés;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class IHM extends JFrame {
    private modeleGrilles modeleGrilles;
    private JTable table;
    private DefaultTableModel tableModel;

    public IHM(modeleGrilles modeleGrilles) {
        this.modeleGrilles = modeleGrilles;
        initUI();
    }

    private void initUI() {
        setTitle("Grille de Mots Croisés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Créer un tableau pour afficher la grille
        tableModel = new DefaultTableModel(modeleGrilles.getTaille(), modeleGrilles.getTaille());
        table = new JTable(tableModel);
        table.setTableHeader(null); // Masquer les en-têtes de colonnes
        table.setRowHeight(30); // Ajuster la hauteur des lignes pour une meilleure présentation

        // Remplir le tableau avec les lettres de la grille
        mettreAJourTableau();

        // Ajouter le tableau à une JScrollPane pour permettre le défilement si nécessaire
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Créer un bouton pour ajouter un mot à la grille
        JButton ajouterMotButton = new JButton("Ajouter un mot");
        ajouterMotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMotInteractive();
            }
        });

        // Ajouter le bouton en bas de la fenêtre
        add(ajouterMotButton, BorderLayout.SOUTH);

        pack(); // Ajuster la taille de la fenêtre en fonction du contenu
        setLocationRelativeTo(null); // Centrer la fenêtre à l'écran
        setVisible(true);
    }

    // Méthode pour mettre à jour l'affichage du tableau avec les lettres de la grille
    private void mettreAJourTableau() {
        for (int i = 0; i < modeleGrilles.getTaille(); i++) {
            for (int j = 0; j < modeleGrilles.getTaille(); j++) {
                Case cell = modeleGrilles.getCase(i, j);
                tableModel.setValueAt(cell.getLettre(), i, j);
            }
        }
    }

    // Méthode pour ajouter un mot à la grille de manière interactive en utilisant des boîtes de dialogue
    private void ajouterMotInteractive() {
        String mot = JOptionPane.showInputDialog(this, "Entrez le mot à ajouter :");
        if (mot != null && !mot.isEmpty()) {
            int x = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez la position x du mot :"));
            int y = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez la position y du mot :"));
            String directionStr = JOptionPane.showInputDialog(this, "Entrez la direction du mot (H pour horizontal, V pour vertical) :");
            Direction direction = (directionStr != null && directionStr.equalsIgnoreCase("V")) ? Direction.VERTICAL : Direction.HORIZONTAL;

            Position position = new Position(x, y);
            Mot motObj = new Mot(mot, position, direction);

            if (modeleGrilles.estEmplacementValide(motObj)) {
                modeleGrilles.insererMot(motObj, position, direction);
                mettreAJourTableau();
            } else {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter le mot à cet emplacement.");
            }
        }
    }

    public static void main(String[] args) {
        // Créer une instance de ModeleGrilles avec la taille de grille souhaitée
        modeleGrilles modeleGrilles = new modeleGrilles(6);

        // Créer une instance de IHM et afficher la fenêtre
        SwingUtilities.invokeLater(() -> new IHM(modeleGrilles));
    }
}
