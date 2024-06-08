package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Tour héritant de la classe Piece.
 * Représente une Tour dans le jeu d'échec.
 * Une tour peut se déplacer horizontalement et verticalement.
 */
public class Tour extends Piece {

    /**
     * Constructeur de la classe Tour.
     *
     * @param equipe L'équipe à laquelle appartient la Tour.
     * @param x      La position sur l'axe des abscisses de la Tour sur l'échiquier.
     * @param y      La position sur l'axe des ordonnées de la Tour sur l'échiquier.
     */
    public Tour(byte equipe, int x, int y) {
        super(Pieces.TOUR, equipe, x, y);
    }

    /**
     * Méthode calculant la liste des mouvements valides de la Tour à sa
     * position actuelle.
     *
     * @return Les mouvements valides
     */
    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer, Integer>> mvtValides = new ArrayList<>();

        // Mouvements horizontaux
        for (int i = posX + 1; i < 8; i++) {
            if (TableauEchec.BOARD[i][posY] == null) {
                mvtValides.add(new Pair<>(i, posY));
            } else if (TableauEchec.BOARD[i][posY].getEquipe() != this.getEquipe()) {
                mvtValides.add(new Pair<>(i, posY));
                break;
            } else {
                break;
            }
        }

        for (int i = posX - 1; i >= 0; i--) {
            if (TableauEchec.BOARD[i][posY] == null) {
                mvtValides.add(new Pair<>(i, posY));
            } else if (TableauEchec.BOARD[i][posY].getEquipe() != this.getEquipe()) {
                mvtValides.add(new Pair<>(i, posY));
                break;
            } else {
                break;
            }
        }

        // Mouvements verticaux
        for (int i = posY + 1; i < 8; i++) {
            if (TableauEchec.BOARD[posX][i] == null) {
                mvtValides.add(new Pair<>(posX, i));
            } else if (TableauEchec.BOARD[posX][i].getEquipe() != this.getEquipe()) {
                mvtValides.add(new Pair<>(posX, i));
                break;
            } else {
                break;
            }
        }

        for (int i = posY - 1; i >= 0; i--) {
            if (TableauEchec.BOARD[posX][i] == null) {
                mvtValides.add(new Pair<>(posX, i));
            } else if (TableauEchec.BOARD[posX][i].getEquipe() != this.getEquipe()) {
                mvtValides.add(new Pair<>(posX, i));
                break;
            } else {
                break;
            }
        }

        return mvtValides;
    }

    /**
     * Récupere l'image de la tour selon le chemin indiquée
     * @return L'image de la tour
     */
    @Override
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/tn.png" : "/iut/jeu_echec/imgs/pions/wn.png";
    }

    /**
     *Redéfinition de "toString"
     *
     * @return Type piece et équipe
     */
    @Override
    public String toString() {
        return "T" + (this.getEquipe() == (byte) 0 ? "_N" : "_B");
    }
}