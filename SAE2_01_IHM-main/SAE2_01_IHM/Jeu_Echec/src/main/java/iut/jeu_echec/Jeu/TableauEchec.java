package iut.jeu_echec.Jeu;

import iut.jeu_echec.Jeu.Pieces.*;
import iut.jeu_echec.controllers.GameController;
import iut.jeu_echec.controllers.PlayersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'échiquier et gérant la logique du jeu
 */
public class TableauEchec {
    public static final int TAILLE = 8;

    public static final byte eNoir = 1;
    public static final byte eBlanc = 0;

    public static int baseTimer = 10;

    public static int whiteTimeSeconds = 60*baseTimer; // 10 minutes par joueurs par exemple
    public static int blackTimeSeconds = 60*baseTimer;

    private static boolean gameStarted = false;
    private static boolean isEndGame = false;

    private static byte tourActuel = eBlanc;

    public static GameManager.Player joueurBlanc;

    public static GameManager.Player joueurNoir;

    // liste avec les différents coups [(equipe,typePiece,(xdebut,ydebut),(xfin,yfin)]
    public static ObservableList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> movesPlayed = FXCollections.observableArrayList();

    // Grille intiale
    public static Piece[][] BOARD;

    /**
     * Méthode initialisant le plateau d'échecs avec les pièces à leur position initiale.
     */
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
    }

    /**
     * Méthode mettant à jour le plateau d'échecs après le mouvement d'une pièce.
     *
     * @param piece La pièce déplacée
     * @param oldX La position d'origine sur l'axe des abscisses de la pièce
     *             sur l'échiquier.
     * @param oldY La position d'origine sur l'axe des ordonnées de la pièce
     *             sur l'échiquier.
     */
    public static void updateBoard(Piece piece, int oldX, int oldY) {
        BOARD[oldX][oldY] = null;
        BOARD[piece.getX()][piece.getY()] = piece;

        TableauEchec.isKingInCheck(piece.getEquipe(), true);

        byte opponent = (piece.getEquipe() == eBlanc) ? eNoir : eBlanc;
        if (isCheckmate(opponent)) {
            System.out.println((opponent == eBlanc ? "White" : "Black") + " is checkmated. Game over!");
            setEndGame(true);
        } else if (isStalemate(opponent)) {
            System.out.println("Stalemate. Game over!");
            setEndGame(true);
        }
    }

    /**
     * Méthode déclarant la fin de la partie et enregistre les résultats.
     *
     * @param endGame Etat de fin de la partie.
     */
    public static void setEndGame(boolean endGame) {
        isEndGame = endGame;

        if (endGame) {
            // gagnant
            GameManager.Player winner = (tourActuel == eBlanc) ? joueurNoir : joueurBlanc;
            GameManager.Player loser = (tourActuel == eBlanc) ? joueurBlanc : joueurNoir;

            winner.incrementGamesWon();
            winner.incrementGamesPlayed();
            loser.incrementGamesPlayed();

            System.out.println("Winner: " + winner.getName());
            GameManager.getInstance().savePlayers();

            // sauvegarde la partie
            int gameId = GameManager.getInstance().addGame(PlayersController.getSelectedPlayers());
            GameManager.Game game = GameManager.getInstance().getGames().get(gameId);

            game.setMoves(movesPlayed.stream().toList());


            game.setWinner(winner);
            game.setWinnerTeam((tourActuel == eBlanc) ? eNoir : eBlanc);
            game.setTimer(baseTimer);

            GameManager.getInstance().saveGames();

            gameStarted = false;
            isEndGame = false;
        }
    }

    /**
     * Méthode changeant le tour du joueur.
     */
    public static void changeTour() {
        if(isEndGame) return;
        tourActuel = (tourActuel == eBlanc ? eNoir : eBlanc);
    }

    /**
     * Méthode retournant le tour actuel.
     *
     * @return Le tour actuel.
     */
    public static byte getTourActuel() {
        return tourActuel;
    }

    /**
     * Méthode affichant l'échiquier.
     *
     * @param board L'échiquier.
     */
    public static void afficheBoard(Piece[][] board) {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                System.out.print(" [ " + board[i][j] + " ] ");
                if (board[i][j] instanceof Roi) {
                    System.out.print(((Roi) board[i][j]).estEnEchec() ? "E" : "N");
                }
            }
            System.out.println();
        }
    }

    /**
     * Méthode vérifiant si le roi d'une équipe est en échec.
     *
     * @param equipe La couleur de la pièce à vérifier.
     * @param setCheck Indique si le roi est en état d'échec.
     * @return true si le roi est en échec, false s'il ne l'est pas
     */
    public static boolean isKingInCheck(byte equipe, boolean setCheck) {
        int kingX = -1, kingY = -1;

        // Trouve la position du roi
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

        if (kingX == -1 || kingY == -1) {
            return false;
        }

        // Vérifie si n'importe quelle pièce adverse peut capturer le roi
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Piece piece = BOARD[i][j];
                if (piece != null && piece.getEquipe() != equipe) {
                    List<Pair<Integer, Integer>> validMoves = piece.mouvementValides();
                    for (Pair<Integer, Integer> move : validMoves) {
                        if (move.getKey() == kingX && move.getValue() == kingY) {
                            if (setCheck) {
                                ((Roi)TableauEchec.BOARD[kingX][kingY]).setEstEnEchec(true);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        if (setCheck) {
            ((Roi)TableauEchec.BOARD[kingX][kingY]).setEstEnEchec(false);
        }
        return false;
    }

    /**
     * Méthode vérifiant si la partie est terminée.
     *
     * @return true si la partie est terminée, false si elle ne l'est pas.
     */
    public static boolean didGameEnd() {
        return isEndGame;
    }

    /**
     * Méthode vérifiant si une équipe est en échec et mat.
     *
     * @param equipe La couleur de la pièce à vérifier.
     * @return true si l'équipe est en échec et mat, false si elle ne l'est pas.
     */
    public static boolean isCheckmate(byte equipe) {
        if (!isKingInCheck(equipe, true)) {
            return false;
        }

        // Vérifie si un mouvement valide peut sortir le roi de l'état d'échec.
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Piece piece = BOARD[i][j];
                if (piece != null && piece.getEquipe() == equipe) {
                    List<Pair<Integer, Integer>> validMoves = piece.mouvementValides();
                    for (Pair<Integer, Integer> move : validMoves) {
                        Piece original = BOARD[move.getKey()][move.getValue()];
                        BOARD[piece.getX()][piece.getY()] = null;
                        BOARD[move.getKey()][move.getValue()] = piece;

                        boolean stillInCheck = isKingInCheck(equipe, false);

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

    /**
     * Méthode filtrant les momuvements valides d'une pièce afin d'éviter une
     * condition d'échec.
     *
     * @param piece La pièce dont les mouvements sont filtrer.
     * @return Une liste de mouvements valides ne mettant pas le roi en état
     * d'échec.
     */
    public static List<Pair<Integer, Integer>> filterMovesToPreventCheck(Piece piece) {
        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
        for (Pair<Integer, Integer> move : piece.mouvementValides()) {
            int newX = move.getKey();
            int newY = move.getValue();

            Piece original = BOARD[newX][newY];
            BOARD[piece.getX()][piece.getY()] = null;
            BOARD[newX][newY] = piece;

            boolean stillInCheck = isKingInCheck(piece.getEquipe(), false);

            BOARD[newX][newY] = original;
            BOARD[piece.getX()][piece.getY()] = piece;

            if (!stillInCheck) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    /**
     * Méthode vérifiant si la partie est nulle
     *
     * @param equipe L'équipe à vérifier.
     * @return true si la partie est nulle, false si elle ne l'est pas
     */
    public static boolean isStalemate(byte equipe) {
        if (isKingInCheck(equipe, true)) {
            return false;
        }
        // Vérifie si le joueur possède encore des mouvements valides
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

    /**
     * Méthode vérifant si la partie à débuter.
     *
     * @return true si la partie est commencée, false si elle ne l'est pas
     */
    public static boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Méthode définissant l'état de début de la partie.
     *
     * @param gameStarted L'etat de début de la partie.
     */
    public static void setGameStarted(boolean gameStarted) {
        TableauEchec.gameStarted = gameStarted;
    }
}
