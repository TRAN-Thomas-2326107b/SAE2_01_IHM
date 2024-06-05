package iut.jeu_echec.Jeu;

import iut.jeu_echec.Jeu.Pieces.*;

public class TableEchec {
    public static final int TAILLE = 8;

    public static int NOMBRE_COUPS = 0;
    public static final byte eNoir = 0;
    public static final byte eBlanc = 1;

    // Grille intiale
    public static final Piece[][] BOARD = {
            {new Tour(eNoir,0,0), new Cavalier(eNoir,0,1), new Fou(eNoir,0,2), new Roi(eNoir,0,3), new Dame(eNoir,0,4), new Fou(eNoir,0,5), new Cavalier(eNoir,0,6), new Tour(eNoir,0,7)},
            {new Pion(eNoir,1,0), new Pion(eNoir,1,1), new Pion(eNoir,1,2), new Pion(eNoir,1,3), new Pion(eNoir,1,4), new Pion(eNoir,1,5), new Pion(eNoir,1,6), new Pion(eNoir,1,7)},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new Pion(eBlanc,6,0), new Pion(eBlanc,6,1), new Pion(eBlanc,6,2), new Pion(eBlanc,6,3), new Pion(eBlanc,6,4), new Pion(eBlanc,6,5), new Pion(eBlanc,6,6), new Pion(eBlanc,6,7)},
            {new Tour(eBlanc,7,0), new Cavalier(eBlanc,7,1), new Fou(eBlanc,7,2), new Roi(eBlanc,7,3), new Dame(eBlanc,7,4), new Fou(eBlanc,7,5), new Cavalier(eBlanc,7,6), new Tour(eBlanc,7,7)}
    };

    public static void updateBoard(Piece piece, int oldX, int oldY) {
        // method is called each movement, just set the x and y of the piece
        BOARD[oldX][oldY] = null;
        BOARD[piece.getX()][piece.getY()] = piece;
    }


    public static void afficheBoard(Piece[][] board) {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                System.out.print(" [ " + board[i][j] + " ] ");
            }
            System.out.println();
        }
    }


}
