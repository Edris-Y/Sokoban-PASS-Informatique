import java.util.Scanner;

public class Main {

    static class Grille {

        static final char MUR = '#';
        static final char LIBRE = ' ';
        static final char JOUEUR = '@';
        static final char CAISSE = '$';
        static final char OBJECTIF = '.';
        static final char CAISSE_OBJ = '*';

        char[][] grille;
        boolean[][] objectifs;
        int ligneJoueur;
        int colonneJoueur;

        Grille(String[] niveau) {
            int hauteur = niveau.length;
            int largeur = 0;
            for (int i = 0; i < hauteur; i++) {
                if (niveau[i].length() > largeur) {
                    largeur = niveau[i].length();
                }
            }

            grille = new char[hauteur][largeur];
            objectifs = new boolean[hauteur][largeur];

            for (int l = 0; l < hauteur; l++) {
                for (int c = 0; c < largeur; c++) {
                    char ch = LIBRE;
                    if (c < niveau[l].length()) {
                        ch = niveau[l].charAt(c);
                    }

                    if (ch == OBJECTIF) {
                        objectifs[l][c] = true;
                        ch = LIBRE;
                    } else if (ch == CAISSE_OBJ) {
                        objectifs[l][c] = true;
                        ch = CAISSE;
                    } else if (ch == JOUEUR) {
                        ligneJoueur = l;
                        colonneJoueur = c;
                    }
                    grille[l][c] = ch;
                }
            }
        }

        // Affiche la grille
        void afficherGrille() {
            for (int l = 0; l < grille.length; l++) {
                for (int c = 0; c < grille[l].length; c++) {
                    char ch = grille[l][c];
                    if (objectifs[l][c] && ch == LIBRE) {
                        ch = OBJECTIF;
                    }
                    if (objectifs[l][c] && ch == CAISSE) {
                        ch = CAISSE_OBJ;
                    }
                    System.out.print(ch);
                }
                System.out.println();
            }
        }

        // La case est dans la grille et n'est pas un mur
        boolean deplacementValide(int l, int c) {
            if (l < 0 || l >= grille.length || c < 0 || c >= grille[0].length) {
                return false;
            }
            return grille[l][c] != MUR;
        }

        // La caisse en (l, c) peut etre pousser si la case derriere est libre
        boolean peutPousser(int l, int c, int dl, int dc) {
            if (grille[l][c] != CAISSE) {
                return false;
            }
            int nl = l + dl;
            int nc = c + dc;
            return deplacementValide(nl, nc) && grille[nl][nc] == LIBRE;
        }

        // Deplace la caisse de (l, c) vers (l+dl, c+dc)
        void deplacerCaisse(int l, int c, int dl, int dc) {
            grille[l][c] = LIBRE;
            grille[l + dl][c + dc] = CAISSE;
        }

        // Deplace le joueur (z, q, s, d) et renvoie true s'il a bouger.
        boolean deplacerJoueur(char commande) {
            int dl = 0;
            int dc = 0;

            if (commande == 'z') {
                dl = -1;
            } else if (commande == 's') {
                dl = 1;
            } else if (commande == 'q') {
                dc = -1;
            } else if (commande == 'd') {
                dc = 1;
            } else {
                return false;
            }

            int nl = ligneJoueur + dl;
            int nc = colonneJoueur + dc;

            if (!deplacementValide(nl, nc)) {
                return false;
            }

            if (grille[nl][nc] == CAISSE) {
                if (!peutPousser(nl, nc, dl, dc)) {
                    return false;
                }
                deplacerCaisse(nl, nc, dl, dc);
            }

            grille[ligneJoueur][colonneJoueur] = LIBRE;
            grille[nl][nc] = JOUEUR;
            ligneJoueur = nl;
            colonneJoueur = nc;
            return true;
        }

        // Victoire quand aucune caisse reste
        boolean partieTerminee() {
            for (int l = 0; l < grille.length; l++) {
                for (int c = 0; c < grille[l].length; c++) {
                    if (grille[l][c] == CAISSE && !objectifs[l][c]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    static class Niveaux {

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

    static class Jeu {

        static boolean commandeValide(String saisie) {
            if (saisie.length() != 1) {
                return false;
            }
            char c = saisie.charAt(0);
            return c == 'z' || c == 'q' || c == 's' || c == 'd' || c == 'x';
        }

        // Boucle pr un niveau
        static void jouer(int numeroNiveau, Scanner scanner) {
            Grille g = new Grille(Niveaux.chargerNiveau(numeroNiveau));
            int mouvements = 0;

            while (true) {
                System.out.println();
                g.afficherGrille();
                System.out.println("Mouvements : " + mouvements);
                System.out.print("Commande (z=haut, s=bas, q=gauche, d=droite, x=quitter) : ");

                String saisie = scanner.next().toLowerCase();

                if (!commandeValide(saisie)) {
                    System.out.println("Commande invalide.");
                    continue;
                }

                char commande = saisie.charAt(0);

                if (commande == 'x') {
                    System.out.println("Partie abandonnee.");
                    return;
                }

                if (g.deplacerJoueur(commande)) {
                    mouvements++;
                } else {
                    System.out.println("Deplacement impossible.");
                }

                if (g.partieTerminee()) {
                    System.out.println();
                    g.afficherGrille();
                    System.out.println("Victoire !");
                    System.out.println("Nombre de mouvements : " + mouvements);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans le jeu Sokoban !");
        System.out.print("Entrez votre nom : ");
        String playerName = scanner.next();
        System.out.println("Bonjour, " + playerName + " ! Preparez-vous a relever le defi du Sokoban !");

        boolean continuer = true;

        while (continuer) {
            afficherMenu();
            int choix = lireEntier(scanner);

            switch (choix) {
                case 1:
                    Jeu.jouer(1, scanner);
                    break;
                case 2:
                    choisirNiveau(scanner);
                    break;
                case 3:
                    afficherRegles();
                    break;
                case 4:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        System.out.println("Au revoir !");
    }

    static void afficherMenu() {
        System.out.println();
        System.out.println("================================");
        System.out.println("          SOKOBAN JAVA");
        System.out.println("================================");
        System.out.println("1. Nouvelle partie");
        System.out.println("2. Choisir un niveau");
        System.out.println("3. Afficher les regles");
        System.out.println("4. Quitter");
        System.out.print("\nVotre choix : ");
    }

    static void choisirNiveau(Scanner scanner) {
        System.out.println();
        for (int i = 1; i <= Niveaux.nombreNiveaux(); i++) {
            System.out.println(i + ". Niveau " + i);
        }
        System.out.print("Numero du niveau : ");
        int numero = lireEntier(scanner);

        if (numero < 1 || numero > Niveaux.nombreNiveaux()) {
            System.out.println("Niveau inexistant.");
        } else {
            Jeu.jouer(numero, scanner);
        }
    }

    static void afficherRegles() {
        System.out.println();
        System.out.println("===== REGLES =====");
        System.out.println("But : placer toutes les caisses sur les objectifs.");
        System.out.println("Le joueur se deplace en haut, bas, gauche, droite.");
        System.out.println("Il peut pousser une caisse si la case derriere est libre.");
        System.out.println("Il ne peut jamais tirer une caisse ni traverser un mur.");
        System.out.println();
        System.out.println("Symboles : # mur | @ joueur | $ caisse | . objectif | * caisse sur objectif");
        System.out.println("Commandes : z haut | s bas | q gauche | d droite | x quitter");
    }

    // lit un entier redemande tant que l'entrer n'est pas un nombre
    static int lireEntier(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Entree invalide, reessayez : ");
        }
        return scanner.nextInt();
    }
}
