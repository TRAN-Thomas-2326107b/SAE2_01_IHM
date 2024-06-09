package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Roi héritant de la classe Piece.
 * Représente un Roi dans le jeu d'échecs.
 * Un roi peut se déplacer d'une case peut importe la direction.
 */
public class Roi extends Piece{

    private boolean enEchec = false;

    /**
     * Constructeur de la classe Roi
     *
     * @param equipe L'équipe à laquelle appartient le Roi.
     * @param x La position sur l'axe des abscisses du Roi sur l'échiquier.
     * @param y La position sur l'axe des ordonnées du Roi sur l'échiquier.
     */
    public Roi(byte equipe, int x, int y) {
        super(Pieces.ROI,equipe,x,y);
    }

    /**
     * Méthode calculant les mouvements valides du Roi à sa position actuelle.
     *
     * @return Une liste de paires (x,y) représentant les mouvements possibles
     * où le Roi peut se déplacer.
     */
    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        // Toutes les positions possibles du Roi autour de lui
        int[][] mvtPossibles = {
                {posX - 1, posY}, {posX - 1, posY + 1},
                {posX, posY + 1}, {posX + 1, posY + 1},
                {posX + 1, posY}, {posX + 1, posY - 1},
                {posX, posY - 1}, {posX - 1, posY - 1}
        };

        for (int[] move : mvtPossibles) {
            int newX = move[0];
            int newY = move[1];

            // Vérifie que le roi reste dans la grille
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Piece destinationPiece = TableauEchec.BOARD[newX][newY];
                // Si la case de destination est vide ou contient un ennemi, le mouvement est valide
                if (destinationPiece == null || (destinationPiece != null && destinationPiece.getEquipe() != this.getEquipe())) {
                    mvtValides.add(new Pair<>(newX, newY));
                }
            }
        }
        return mvtValides;
    }

    /**
     * Méthode vérifiant si le Roi est en échec.
     *
     * @return true si le roi est en échec, false s'il ne l'est pas.
     */
    public boolean estEnEchec() {
        return enEchec;
    }

    /**
     * Méthode modifiant la condition d'échec sur le Roi.
     *
     * @param estEnEchec true si le Roi est en échec, false s'il ne l'est pas.
     */
    public void setEstEnEchec(boolean estEnEchec) {
        this.enEchec = estEnEchec;
    }

    /**
     * Méthode renvoyant l'URL de l'image représentant la pièce du Roi
     * en fonction de son équipe.
     *
     * @return Une chaîne de caractères correspondant au chemin vers
     * l'image du Roi.
     */
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bk.png" : "/iut/jeu_echec/imgs/pions/wk.png";
    }

    /**
     * Méthode renvoyant une représentation en quelques caractères de la pièce
     * du Roi.
     *
     * @return Une chaîne de caractères correspondant au Roi noir ou blanc.
     */
    @Override
    public String toString() {
        return "R" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
