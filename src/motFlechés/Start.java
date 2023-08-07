package motFlechés;

public class Start {

    public static void main(String[] args) {
        // Créer une instance de ModeleGrilles avec la taille de grille souhaitée
        modeleGrilles modeleGrilles = new modeleGrilles(6);

        // Créer une instance de IHM et afficher la fenêtre
        IHM ihm = new IHM(modeleGrilles);
        ihm.setVisible(true);
    }
}
