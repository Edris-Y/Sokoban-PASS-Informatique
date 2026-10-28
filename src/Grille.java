// Auteur : Edris (Personne 1 - Mécanique du jeu)
class Grille {

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

    // Transforme un niveau (texte) en grille jouable
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

    // La caisse en (l, c) peut être poussée si la case derrière est libre
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

    // Déplace le joueur (z, q, s, d). Renvoie true s'il a bougé.
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

    // Victoire : plus aucune caisse hors d'un objectif
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