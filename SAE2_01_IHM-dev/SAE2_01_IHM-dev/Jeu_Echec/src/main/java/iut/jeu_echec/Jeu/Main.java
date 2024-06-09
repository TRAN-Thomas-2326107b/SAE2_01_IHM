package iut.jeu_echec.Jeu;

import iut.jeu_echec.Jeu.Pieces.*;
import java.util.Scanner;

/**
 * Classe Main pour lancer le jeu d'échecs dans le terminal.
 */
public class Main {

    /**
     * Méthode principale exécutant le jeu d'échecs.
     *
     * @param args Les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        // 0 noir 1 blanc
        Piece[][] test_board = TableauEchec.BOARD;

        // Affiche l'échiquier initial
        TableauEchec.afficheBoard(test_board);

        Scanner in = new Scanner(System.in);

        boolean exitGame = false;

        // Boucle principale du jeu
        while (!exitGame) {
            int x,y = -1;
            x = in.nextInt();
            if (x == 69)
                break;
            y = in.nextInt();

            Piece pp = test_board[x][y];
            if (pp == null)
                continue;

            // Affiche l'échiquier après un mouvement
            TableauEchec.afficheBoard(test_board);
        }
    }
}