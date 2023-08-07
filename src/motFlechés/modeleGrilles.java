package motFlechés;
import java.util.ArrayList;
import java.util.List;

public class modeleGrilles {
    private Grille grille;
    private Dictionnaire dictionnaire;
    private int taille;
    private Case[][] cases;

    public modeleGrilles(int taille) {
        this.grille = null;
        this.dictionnaire = new Dictionnaire();
        chargerDictionnaire();
        this.taille = taille;
        cases = new Case[taille][taille];
        initialiserGrilleVide();
        
    }
    
    private void initialiserGrilleVide() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j] = new Case();
            }
        }
    }

    public boolean creerGrille(int taille) {
        if (taille <= 0 || taille > 10) {
            return false;
        }
        this.grille = new Grille(taille);
        this.taille = taille;
        return true;
    }

    public void insererMot(Mot mot, Position position, Direction direction) {
        if (grille != null) {
            grille.insererMot(mot, position, direction);
        }
    }

    public void supprimerMot(Position position) {
        if (grille != null) {
            grille.supprimerMot(position);
        }
    }

    public void sauvegarderGrille(String nomFichier) {
        if (grille != null) {
            // Code pour sauvegarder la grille dans un fichier
        }
    }

    public void chargerGrille(String nomFichier) {
        // Code pour charger la grille depuis un fichier
    }

    public void imprimerGrilleSansDefinitions() {
        if (grille != null) {
            grille.imprimerGrilleSansDefinitions();
        }
    }

    public void modifierGrille(Grille grilleModifiee) {
        if (grille != null) {
            grille = grilleModifiee;
        }
    }

    public List<Mot> chercherMotParTaille(int taille) {
        if (grille != null) {
            return grille.chercherMotParTaille(taille);
        }
        return new ArrayList<>();
    }

    public List<Mot> chercherMotParLettre(String lettre) {
        if (grille != null) {
            return grille.chercherMotParLettre(lettre);
        }
        return new ArrayList<>();
    }

    public List<Mot> chercherMotParPosition(String lettre, int position) {
        if (grille != null) {
            return grille.chercherMotParPosition(lettre, position);
        }
        return new ArrayList<>();
    }

    public List<Mot> chercherMotParPositions(List<String> lettres, List<Integer> positions) {
        if (grille != null) {
            return grille.chercherMotParPositions(lettres, positions);
        }
        return new ArrayList<>();
    }

    private void chargerDictionnaire() {
        Fichier fichier = new Fichier();
        List<String> motsFrancais = fichier.getListeDeMots();
        dictionnaire.ajouterMots(motsFrancais);
    }

	public int getTaille() {
		return taille;
	}

	public Case getCase(int i, int j) {
        if (i >= 0 && i < taille && j >= 0 && j < taille) {
            return cases[i][j];
        } else {
            throw new IndexOutOfBoundsException("Position hors des limites de la grille.");
        }
    }

	public boolean estEmplacementValide(Mot mot) {
        int x = mot.getPosition().getX();
        int y = mot.getPosition().getY();
        int longueurMot = mot.getMot().length();

        if (mot.getDirection() == Direction.HORIZONTAL) {
            if (x < 0 || x >= taille || y < 0 || y + longueurMot > taille) {
                return false; // Mot hors des limites de la grille horizontalement
            }
            for (int i = 0; i < longueurMot; i++) {
                Case cell = cases[x][y + i];
                if (cell.getLettre() != null && cell.getLettre() != mot.getMot().charAt(i)) {
                    return false; // Il y a déjà une lettre différente dans la case
                }
            }
        } else if (mot.getDirection() == Direction.VERTICAL) {
            if (x < 0 || x + longueurMot > taille || y < 0 || y >= taille) {
                return false; // Mot hors des limites de la grille verticalement
            }
            for (int i = 0; i < longueurMot; i++) {
                Case cell = cases[x + i][y];
                if (cell.getLettre() != null && cell.getLettre() != mot.getMot().charAt(i)) {
                    return false; // Il y a déjà une lettre différente dans la case
                }
            }
        }

        return true;
    }

}
