package motFlechés;

import java.util.ArrayList;
import java.util.List;

public class Dictionnaire {
    private List<String> mots;

    public Dictionnaire() {
        mots = new ArrayList<>();
    }

    public void ajouterMots(List<String> mots) {
        this.mots.addAll(mots);
    }

    public List<String> chercherParTaille(int taille) {
        List<String> motsTrouves = new ArrayList<>();
        for (String mot : mots) {
            if (mot.length() == taille) {
                motsTrouves.add(mot);
            }
        }
        return motsTrouves;
    }

    public List<String> chercherParLettre(String lettre) {
        List<String> motsTrouves = new ArrayList<>();
        for (String mot : mots) {
            if (mot.contains(lettre)) {
                motsTrouves.add(mot);
            }
        }
        return motsTrouves;
    }

    public List<String> chercherParPosition(String lettre, int position) {
        List<String> motsTrouves = new ArrayList<>();
        for (String mot : mots) {
            if (mot.length() >= position && mot.charAt(position - 1) == lettre.charAt(0)) {
                motsTrouves.add(mot);
            }
        }
        return motsTrouves;
    }

    public List<String> chercherParPositions(List<String> lettres, List<Integer> positions) {
        List<String> motsTrouves = new ArrayList<>();
        for (String mot : mots) {
            boolean motTrouve = true;
            for (int i = 0; i < lettres.size(); i++) {
                int position = positions.get(i);
                String lettre = lettres.get(i);
                if (mot.length() >= position && mot.charAt(position - 1) != lettre.charAt(0)) {
                    motTrouve = false;
                    break;
                }
            }
            if (motTrouve) {
                motsTrouves.add(mot);
            }
        }
        return motsTrouves;
    }
}
