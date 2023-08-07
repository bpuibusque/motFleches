package motFlechés;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;


public class Grille {
    private int taille;
    private Case[][] cases;
    private List<Definition> definitions;
    private List<Mot> mots; 

    public Grille(int taille) {
        this.taille = taille;
        this.cases = new Case[taille][taille];
        this.definitions = new ArrayList<>();
        // Initialiser toutes les cases de la grille avec des cases vides
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                this.cases[i][j] = new Case();
            }
        }
    }


    public void sauvegarderGrille(String nomFichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomFichier))) {
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {
                    char lettre = (cases[i][j].getLettre() != '\0') ? cases[i][j].getLettre() : '.';
                    writer.print(lettre);
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imprimerGrilleSansDefinitions() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                char lettre = (cases[i][j].getLettre() != '\0') ? cases[i][j].getLettre() : '.';
                System.out.print(lettre + " ");
            }
            System.out.println();
        }
    }

    public List<Mot> chercherMotParTaille(int taille) {
        List<Mot> motsTrouves = new ArrayList<>();
        // TODO: Implémentation pour chercher des mots par taille dans la grille
        return motsTrouves;
    }

    public List<Mot> chercherMotParLettre(String lettre) {
        List<Mot> motsTrouves = new ArrayList<>();
        // TODO: Implémentation pour chercher des mots par lettre dans la grille
        return motsTrouves;
    }

    public List<Mot> chercherMotParPosition(String lettre, int position) {
        List<Mot> motsTrouves = new ArrayList<>();
        // TODO: Implémentation pour chercher des mots par lettre et position dans la grille
        return motsTrouves;
    }

    public List<Mot> chercherMotParPositions(List<String> lettres, List<Integer> positions) {
        List<Mot> motsTrouves = new ArrayList<>();
        // TODO: Implémentation pour chercher des mots par lettres et positions dans la grille
        return motsTrouves;
    }

    public Case getCase(int x, int y) {
        if (x >= 0 && x < taille && y >= 0 && y < taille) {
            return cases[x][y];
        }
        return null;
    }

    public void setCase(int x, int y, Case c) {
        if (x >= 0 && x < taille && y >= 0 && y < taille) {
            cases[x][y] = c;
        }
    }

    public void ajouterDefinition(Definition definition) {
        definitions.add(definition);
    }

    public void supprimerDefinition(Position position) {
        Definition definitionASupprimer = null;
        for (Definition def : definitions) {
            if (def.getPosition().equals(position)) {
                definitionASupprimer = def;
                break;
            }
        }
        if (definitionASupprimer != null) {
            definitions.remove(definitionASupprimer);
        }
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public int getTaille() {
        return taille;
    }
    
    public boolean insererMot(Mot mot, Position position, Direction direction) {
        if (insererMot(mot, position, direction)) {
            int x = position.getX();
            int y = position.getY();

            for (char lettre : mot.getMot().toCharArray()) {
                cases[x][y].setLettre(lettre);

                // Mettre à jour les coordonnées x, y en fonction de la direction
                if (direction == Direction.HORIZONTAL_DIRECT || direction == Direction.HORIZONTAL_INDIRECT) {
                    x++;
                } else {
                    y++;
                }
            }

            // Ajouter la définition du mot à la liste des définitions de la grille
            Definition definition = new Definition();
            ajouterDefinition(definition);
        }
		return false;
    }

    public void chargerGrille(String nomFichier) {
        try (Scanner scanner = new Scanner(new File(nomFichier))) {
            int ligne = 0;
            while (scanner.hasNextLine() && ligne < taille) {
                String line = scanner.nextLine();
                for (int j = 0; j < taille && j < line.length(); j++) {
                    char lettre = line.charAt(j);
                    if (Character.isLetter(lettre)) {
                        cases[ligne][j].setLettre(lettre);
                    }
                }
                ligne++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Mot> chercherMotParTaille1(int taille) {
        List<Mot> motsTrouves = new ArrayList<>();
        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille; j++) {
                Case cell = cases[i][j];
                if (cell.getLettre() != '\0') {
                    // Rechercher le mot horizontal et vertical à partir de cette case
                    Mot motHorizontal = chercherMotHorizontal(i, j);
                    Mot motVertical = chercherMotVertical(i, j);

                    // Ajouter le mot trouvé à la liste s'il correspond à la taille recherchée
                    if (motHorizontal != null && motHorizontal.getTaille() == taille) {
                        motsTrouves.add(motHorizontal);
                    }
                    if (motVertical != null && motVertical.getTaille() == taille) {
                        motsTrouves.add(motVertical);
                    }
                }
            }
        }
        return motsTrouves;
    }

    private Mot chercherMotHorizontal(int x, int y) {
        int debut = y;
        int fin = y;
        while (debut >= 0 && cases[x][debut].getLettre() != '\0') {
            debut--;
        }
        while (fin < taille && cases[x][fin].getLettre() != '\0') {
            fin++;
        }
        if (fin - debut - 1 > 1) {
            String motString = "";
            for (int j = debut + 1; j < fin; j++) {
                motString += cases[x][j].getLettre();
            }
            return new Mot(motString, new Position(x, debut + 1), Direction.HORIZONTAL_DIRECT);
        }
        return null;
    }

    private Mot chercherMotVertical(int x, int y) {
        int debut = x;
        int fin = x;
        while (debut >= 0 && cases[debut][y].getLettre() != '\0') {
            debut--;
        }
        while (fin < taille && cases[fin][y].getLettre() != '\0') {
            fin++;
        }
        if (fin - debut - 1 > 1) {
            String motString = "";
            for (int i = debut + 1; i < fin; i++) {
                motString += cases[i][y].getLettre();
            }
            return new Mot(motString, new Position(debut + 1, y), Direction.VERTICAL_DIRECT);
        }
        return null;
    }


    public void supprimerMot(Position position) {
        int x = position.getX();
        int y = position.getY();

        // Vérifier que les coordonnées sont valides
        if (x >= 0 && x < taille && y >= 0 && y < taille) {
            // Vérifier si la case contient un mot
            Mot motASupprimer = null;
            for (Mot mot : mots) {
                if (mot.getPosition().equals(position)) {
                    motASupprimer = mot;
                    break;
                }
            }

            // Si un mot est présent à la position spécifiée, le supprimer
            if (motASupprimer != null) {
                mots.remove(motASupprimer);
            }
        }
    }

}
