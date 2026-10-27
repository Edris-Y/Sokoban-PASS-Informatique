// Auteur : Edris (Personne 1 - Mécanique du jeu)
public class Grille {

    static final char MUR = '#';
    static final char LIBRE = ' ';
    static final char JOUEUR = '@';
    static final char CAISSE = '$';
    static final char OBJECTIF = '.';
    static final char CAISSE_OBJ = '*';

    char[][] grille;          // murs, caisses, joueur
    boolean[][] objectifs;    // true si la case est un objectif
    int ligneJoueur, colonneJoueur;

    // Construit la grille à partir d'un niveau (une String par ligne)
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
                char ch = (c < niveau[l].length()) ? niveau[l].charAt(c) : LIBRE;

                if (ch == OBJECTIF) {
                    objectifs[l][c] = true;
                    grille[l][c] = LIBRE;
                } else if (ch == CAISSE_OBJ) {
                    objectifs[l][c] = true;
                    grille[l][c] = CAISSE;
                } else if (ch == JOUEUR) {
                    grille[l][c] = JOUEUR;
                    ligneJoueur = l;
                    colonneJoueur = c;
                } else {
                    grille[l][c] = ch;
                }
            }
        }
    }

    // Affiche la grille ('.' sous un objectif vide, '*' pour une caisse sur objectif)
    void afficherGrille() {
        for (int l = 0; l < grille.length; l++) {
            for (int c = 0; c < grille[l].length; c++) {
                char ch = grille[l][c];
                if (objectifs[l][c]) {
                    if (ch == LIBRE) {
                        ch = OBJECTIF;
                    } else if (ch == CAISSE) {
                        ch = CAISSE_OBJ;
                    }
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

    // Il y a une caisse en (l, c) et la case derrière est libre
    boolean peutPousser(int l, int c, int dl, int dc) {
        if (grille[l][c] != CAISSE) {
            return false;
        }
        int nl = l + dl;
        int nc = c + dc;
        return deplacementValide(nl, nc) && grille[nl][nc] == LIBRE;
    }

    // Déplace la caisse de (l, c) vers (l+dl, c+dc)
    void deplacerCaisse(int l, int c, int dl, int dc) {
        grille[l][c] = LIBRE;
        grille[l + dl][c + dc] = CAISSE;
    }

    // Commande z/q/s/d. Retourne true si le joueur a réellement bougé.
    boolean deplacerJoueur(char commande) {
        int dl = 0;
        int dc = 0;

        switch (commande) {
            case 'z': dl = -1; break;
            case 's': dl = 1;  break;
            case 'q': dc = -1; break;
            case 'd': dc = 1;  break;
            default:  return false;
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

    // Victoire : aucune caisse hors d'un objectif
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
