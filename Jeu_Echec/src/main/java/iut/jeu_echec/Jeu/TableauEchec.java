package iut.jeu_echec.Jeu;

import iut.jeu_echec.Jeu.Pieces.*;
import javafx.util.Pair;

import java.util.List;

public class TableauEchec {
    public static final int TAILLE = 8;

    public static int NOMBRE_COUPS = 0;
    public static final byte eNoir = 0;
    public static final byte eBlanc = 1;

    public static int whiteTimeSeconds = 600; // Example: 10 minutes for each player
    public static int blackTimeSeconds = 600;




    private static boolean isEndGame = false;
    private static byte tourActuel = eBlanc;



    // liste avec les différents coups [(equipe,typePiece,(xdebut,ydebut),(xfin,yfin)]


    // Grille intiale
    public static Piece[][] BOARD;


    public static void createBoard() {
        BOARD = new Piece[][]{
                {new Tour(eNoir, 0, 0), new Cavalier(eNoir, 0, 1), new Fou(eNoir, 0, 2), new Dame(eNoir, 0, 3), new Roi(eNoir, 0, 4), new Fou(eNoir, 0, 5), new Cavalier(eNoir, 0, 6), new Tour(eNoir, 0, 7)},
                {new Pion(eNoir, 1, 0), new Pion(eNoir, 1, 1), new Pion(eNoir, 1, 2), new Pion(eNoir, 1, 3), new Pion(eNoir, 1, 4), new Pion(eNoir, 1, 5), new Pion(eNoir, 1, 6), new Pion(eNoir, 1, 7)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pion(eBlanc, 6, 0), new Pion(eBlanc, 6, 1), new Pion(eBlanc, 6, 2), new Pion(eBlanc, 6, 3), new Pion(eBlanc, 6, 4), new Pion(eBlanc, 6, 5), new Pion(eBlanc, 6, 6), new Pion(eBlanc, 6, 7)},
                {new Tour(eBlanc, 7, 0), new Cavalier(eBlanc, 7, 1), new Fou(eBlanc, 7, 2), new Dame(eBlanc, 7, 3), new Roi(eBlanc, 7, 4), new Fou(eBlanc, 7, 5), new Cavalier(eBlanc, 7, 6), new Tour(eBlanc, 7, 7)}
        };
        whiteTimeSeconds = 600;
        blackTimeSeconds = 600;
    }

    public static void updateBoard(Piece piece, int oldX, int oldY) {
        // method is called each movement, just set the x and y of the piece
        BOARD[oldX][oldY] = null;
        BOARD[piece.getX()][piece.getY()] = piece;

        byte opponent = (piece.getEquipe() == eBlanc) ? eNoir : eBlanc;
        if (isCheckmate(opponent)) {
            System.out.println((opponent == eBlanc ? "White" : "Black") + " is checkmated. Game over!");
            isEndGame = true;
        } else if (isStalemate(opponent)) {
            System.out.println("Stalemate. Game over!");
            isEndGame = true;
        }
    }




    public static void changeTour() {
        if(isEndGame) return;
        tourActuel = (tourActuel == eBlanc ? eNoir : eBlanc);
    }
    public static byte getTourActuel() {
        return tourActuel;
    }


    public static void afficheBoard(Piece[][] board) {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                System.out.print(" [ " + board[i][j] + " ] ");
            }
            System.out.println();
        }
    }

    public static boolean isKingInCheck(byte equipe) {
        int kingX = -1, kingY = -1;

        // Find the king's position
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Piece piece = BOARD[i][j];
                if (piece != null && piece.getEquipe() == equipe && piece instanceof Roi) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
        }

        // Check if any opponent piece can capture the king
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Piece piece = BOARD[i][j];
                if (piece != null && piece.getEquipe() != equipe) {
                    List<Pair<Integer, Integer>> validMoves = piece.mouvementValides();
                    for (Pair<Integer, Integer> move : validMoves) {
                        if (move.getKey() == kingX && move.getValue() == kingY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean didGameEnd() {
        return isEndGame;
    }

    public static boolean isCheckmate(byte equipe) {
        if (!isKingInCheck(equipe)) {
            return false;
        }

        afficheBoard(BOARD);
        // Check if any valid move can get the king out of check
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Piece piece = BOARD[i][j];
                if (piece != null && piece.getEquipe() == equipe) {
                    List<Pair<Integer, Integer>> validMoves = piece.mouvementValides();
                    for (Pair<Integer, Integer> move : validMoves) {
                        Piece original = BOARD[move.getKey()][move.getValue()];
                        BOARD[piece.getX()][piece.getY()] = null;
                        BOARD[move.getKey()][move.getValue()] = piece;
                        boolean stillInCheck = isKingInCheck(equipe);
                        BOARD[move.getKey()][move.getValue()] = original;
                        BOARD[piece.getX()][piece.getY()] = piece;
                        if (!stillInCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean isStalemate(byte equipe) {
        if (isKingInCheck(equipe)) {
            return false;
        }
        // Check if the player has any valid moves
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Piece piece = BOARD[i][j];
                if (piece != null && piece.getEquipe() == equipe) {
                    List<Pair<Integer, Integer>> validMoves = piece.mouvementValides();
                    if (!validMoves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



}
