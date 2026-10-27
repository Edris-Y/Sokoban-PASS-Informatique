// Auteur : Badice (Personne 3 - Niveaux)
public class Niveaux {

    // Chaque niveau : 1 joueur, autant de caisses que d'objectifs, murs, cases libres
    static final String[][] NIVEAUX = {
        {   // Niveau 1 : 1 caisse
            "########",
            "#      #",
            "#  .   #",
            "#  $   #",
            "# @    #",
            "#      #",
            "########"
        },
        {   // Niveau 2 : 2 caisses
            "#########",
            "#       #",
            "# .  $  #",
            "#   @   #",
            "# .  $  #",
            "#       #",
            "#########"
        },
        {   // Niveau 3 : 2 caisses et des murs intérieurs
            "##########",
            "#  #     #",
            "#  # $ . #",
            "#    $ . #",
            "# @  #   #",
            "##########"
        }
    };

    static int nombreNiveaux() {
        return NIVEAUX.length;
    }

    // numero va de 1 à nombreNiveaux()
    static String[] chargerNiveau(int numero) {
        return NIVEAUX[numero - 1];
    }
}
