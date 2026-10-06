import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu Sokuban !");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez votre nom : ");
        String playerName = scanner.nextLine();
        System.out.println("Bonjour, " + playerName + " ! Préparez-vous à relever le défi du Sokuban !");
        boolean continuer = true;
        afficherMenu();

        while (continuer) {
            int choix = lireEntier(scanner);
            switch (choix) {
                case 1:
                    System.out.println("Nouvelle partie sélectionnée.");
                    break;
                case 2:
                    System.out.println("Tu as choisi de sélectionner un niveau.");
                    break;
                case 3:
                    System.out.println("Tu as choisi d'afficher les règles.");
                    break;
                case 4:
                    continuer = false;
                    System.out.println("Tu as choisi de quitter le jeu.");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        System.out.println("Au revoir !");
    }

    static int lireEntier(Scanner scanner) {
        return scanner.nextInt();
    }
    static void afficherMenu() {
        System.out.println("================================");
        System.out.println("          SOKOBAN JAVA");
        System.out.println("================================");
        System.out.println("1. Nouvelle partie");
        System.out.println("2. Choisir un niveau");
        System.out.println("3. Afficher les règles");
        System.out.println("4. Quitter");
        System.out.print("\nVotre choix : ");
    }
}
