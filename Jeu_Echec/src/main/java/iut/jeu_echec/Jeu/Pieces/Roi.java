package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe Roi héritant de la classe Piece.
 * Représente un Roi dans le jeu d'échec.
 * Une Roi peut se déplacer d'une case uniquement dans toutes les directions.
 */
public class Roi extends Piece{



    private boolean enEchec = false;


    /**
     * Constructeurs de la classe Roi
     *
     * @param equipe L'équipe à laquelle appartient le Roi.
     * @param x La position sur l'axe des abscisses de la Tour sur l'échiquier.
     * @param y
     */
    public Roi(byte equipe, int x, int y) {
        super(Pieces.ROI,equipe,x,y);
    }

    /**
     * Méthode calculant la liste des mouvements valides du Roi à sa
     * position actuelle.
     *
     * @return Les mouvements valides
     */
    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        // toutes les positions possibles du cavalier autour de lui
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

    public boolean estEnEchec() {
        return enEchec;
    }

    public void setEstEnEchec(boolean estEnEchec) {
        this.enEchec = estEnEchec;
    }


    /**
     * Récupere l'image du Roi selon le chemin indiquée
     * @return L'image du Roi
     */
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bk.png" : "/iut/jeu_echec/imgs/pions/wk.png";
    }


    /**
     *Redéfinition de "toString"
     *
     * @return Type piece et équipe
     */
    @Override
    public String toString() {
        return "R" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
