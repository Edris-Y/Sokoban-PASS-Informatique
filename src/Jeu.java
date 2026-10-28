import java.util.Scanner;

public class Jeu {

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
                System.out.println("Partie abandonnée.");
                return;
            }

            if (g.deplacerJoueur(commande)) {
                mouvements++;
            } else {
                System.out.println("Déplacement impossible.");
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
