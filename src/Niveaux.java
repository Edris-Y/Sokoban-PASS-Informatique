public class Niveaux {

    static final String[][] NIVEAUX = {
        {   // Niveau 1
            "########",
            "#      #",
            "#  .   #",
            "#  $   #",
            "# @    #",
            "#      #",
            "########"
        },
        {   // Niveau 2 
            "#########",
            "#       #",
            "# .  $  #",
            "#   @   #",
            "# .  $  #",
            "#       #",
            "#########"
        },
        {   // Niveau 3 
            "##########",
            "#  #     #",
            "#  #   $ #",
            "#    $ . #",
            "# @  # . #",
            "##########"
        }
    };

    static int nombreNiveaux() {
        return NIVEAUX.length;
    }

    static String[] chargerNiveau(int numero) {
        return NIVEAUX[numero - 1];
    }
}
