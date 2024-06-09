package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Cavalier héritant de la classe Piece.
 * Représente un Cavalier dans le jeu d'échecs.
 * Un cavalier peut se déplacer en L.
 */
public class Cavalier extends Piece {

    /**
     * Constructeur de la classe Cavalier.
     *
     * @param equipe L'équipe à laquelle appartient le Cavalier.
     * @param x La position sur l'axe des abscisses du Cavalier sur l'échiquier.
     * @param y La position sur l'axe des ordonnées du Cavalier sur l'échiquier.
     */
    public Cavalier(byte equipe, int x, int y) {
        super(Pieces.CAVALIER,equipe,x,y);
    }

    /**
     * Méthode calculant la liste des mouvements valides du Cavalier à sa
     * position actuelle.
     *
     * @return Une liste de paires (x,y) représentant toutes les positions
     * valides où le Cavalier peut se déplacer.
     */
    @Override
    public List<Pair<Integer, Integer>> mouvementValides() {
        final int posX = this.getX();
        final int posY = this.getY();
        List<Pair<Integer,Integer>> mvtValides = new ArrayList<>();

        // Toutes les positions possibles du cavalier autour de lui
        int[][] mvtPossibles = {
                {posX + 2, posY + 1}, {posX + 2, posY - 1},
                {posX - 2, posY + 1}, {posX - 2, posY - 1},
                {posX + 1, posY + 2}, {posX + 1, posY - 2},
                {posX - 1, posY + 2}, {posX - 1, posY - 2}
        };

        for (int[] move : mvtPossibles) {
            int newX = move[0];
            int newY = move[1];

            // Vérifie que le cavalier reste dans la grille
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
     * Méthode renvoyant l'URL de l'image représentant la pièce du Cavalier
     * en fonction de son équipe.
     *
     * @return Une chaîne de caractères correspondant au chemin vers
     * l'image du Cavalier.
     */
    public String getImage() {
        return this.getEquipe() == TableauEchec.eNoir ? "/iut/jeu_echec/imgs/pions/bn.png" : "/iut/jeu_echec/imgs/pions/wn.png";
    }

    /**
     * Méthode renvoyant une représentation en quelques caractères de la pièce
     * du Cavalier.
     *
     * @return Une chaîne de caractères correspondant au Cavalier noir ou blanc.
     */
    @Override
    public String toString() {
        return "C" + (this.getEquipe() == (byte)0 ? "_N" : "_B");
    }
}
