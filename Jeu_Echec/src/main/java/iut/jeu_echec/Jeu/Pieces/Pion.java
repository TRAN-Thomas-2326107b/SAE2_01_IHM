package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
/**
 * Class Pion héritant de la classe Piece
 * Représente un pion dans le jeu d'échec.
 * Le pion peut se déplacer de deux case lors de son premier moove uniquement.
 * Il ne peut avanceé que verticalement et uniquement devant lui
 * Pour manger un pion adverse, il ne peut que manger en diagonal
 */
public class Pion extends Piece {

    /**
     * Constructeurs de la classe Pion
     *
     * @param equipe L'équipe a laquelle appartient le Pion.
     * @param x La position sur l'axe des abscisses du Pion sur l'échiquier.
     * @param y La position sur l'axe des ordonnées du Pion sur l'échiquier.
     */
    public Pion(byte equipe, int x, int y) {
        super(Pieces.PION,equipe,x,y);
    }

    /**
     * Méthode calculant la liste des mouvements valides du Pion à sa
     * @return Les mouvements valides
     */
    @Override
    public List<Pair<Integer,Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer, Integer>> mvtValides = new ArrayList<>();

        if (this.getEquipe() == TableauEchec.eNoir) {
            // pion noir avance de 2 cases
            if (posX == 1 && TableauEchec.BOARD[posX + 2][posY] == null && TableauEchec.BOARD[posX + 1][posY] == null) {
                mvtValides.add(new Pair<>(posX + 2, posY));
            }
            // pion noir avant d'1 cases
            if (posX + 1 < 8 && TableauEchec.BOARD[posX + 1][posY] == null) {
                mvtValides.add(new Pair<>(posX + 1, posY));
            }
            // pion noir capture en diagonale-gauche
            if (posX + 1 < 8 && posY - 1 >= 0) {
                Piece diag1 = TableauEchec.BOARD[posX + 1][posY - 1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe()) {
                    mvtValides.add(new Pair<>(posX + 1, posY - 1));
                }
            }
            // pion noir capture en diagonale-droite
            if (posX + 1 < 8 && posY + 1 < 8) {
                Piece diag2 = TableauEchec.BOARD[posX + 1][posY + 1];
                if (diag2 != null && this.getEquipe() != diag2.getEquipe()) {
                    mvtValides.add(new Pair<>(posX + 1, posY + 1));
                }
            }
        } else {
            // pion blanc avance de 2 cases
            if (posX == 6 && TableauEchec.BOARD[posX - 2][posY] == null && TableauEchec.BOARD[posX - 1][posY] == null) {
                mvtValides.add(new Pair<>(posX - 2, posY));
            }
            // pion blanc avance d'1 case
            if (posX - 1 >= 0 && TableauEchec.BOARD[posX - 1][posY] == null) {
                mvtValides.add(new Pair<>(posX - 1, posY));
            }
            // pion blanc capture en diagonale-gauche
            if (posX - 1 >= 0 && posY - 1 >= 0) {
                Piece diag1 = TableauEchec.BOARD[posX - 1][posY - 1];
                if (diag1 != null && this.getEquipe() != diag1.getEquipe()) {
                    mvtValides.add(new Pair<>(posX - 1, posY - 1));
                }
            }
            // pion blanc capture en diagonale-droite
            if (posX - 1 >= 0 && posY + 1 < 8) {
                Piece diag2 = TableauEchec.BOARD[posX - 1][posY + 1];
                if (diag2 != null && this.getEquipe() != diag2.getEquipe()) {
                    mvtValides.add(new Pair<>(posX - 1, posY + 1));
                }
            }
        }


        return mvtValides;
    };


    /**
     * Récupere l'image du Pion selon le chemin indiquée
     * @return L'image du Pion
     */
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bp.png" : "/iut/jeu_echec/imgs/pions/wp.png";
    }


    /**
     *Redéfinition de "toString"
     *
     * @return Type piece et équipe
     */
    @Override
    public String toString() {
        return "P" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
