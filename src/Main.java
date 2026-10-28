// Auteur : Badice (Menu principal)
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans le jeu Sokoban !");
        System.out.print("Entrez votre nom : ");
        String playerName = scanner.next();
        System.out.println("Bonjour, " + playerName + " ! Préparez-vous à relever le défi du Sokoban !");

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
        System.out.println("3. Afficher les règles");
        System.out.println("4. Quitter");
        System.out.print("\nVotre choix : ");
    }

    static void choisirNiveau(Scanner scanner) {
        System.out.println();
        for (int i = 1; i <= Niveaux.nombreNiveaux(); i++) {
            System.out.println(i + ". Niveau " + i);
        }
        System.out.print("Numéro du niveau : ");
        int numero = lireEntier(scanner);

        if (numero < 1 || numero > Niveaux.nombreNiveaux()) {
            System.out.println("Niveau inexistant.");
        } else {
            Jeu.jouer(numero, scanner);
        }
    }

    static void afficherRegles() {
        System.out.println();
        System.out.println("===== RÈGLES =====");
        System.out.println("But : placer toutes les caisses sur les objectifs.");
        System.out.println("Le joueur se déplace en haut, bas, gauche, droite.");
        System.out.println("Il peut pousser une caisse si la case derrière est libre.");
        System.out.println("Il ne peut jamais tirer une caisse ni traverser un mur.");
        System.out.println();
        System.out.println("Symboles : # mur | @ joueur | $ caisse | . objectif | * caisse sur objectif");
        System.out.println("Commandes : z haut | s bas | q gauche | d droite | x quitter");
    }

    // Lit un entier, redemande tant que l'entrée n'est pas un nombre
    static int lireEntier(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Entrée invalide, réessayez : ");
        }
        return scanner.nextInt();
    }
}
