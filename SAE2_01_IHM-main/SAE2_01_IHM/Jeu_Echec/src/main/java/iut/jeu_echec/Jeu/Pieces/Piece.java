package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentan une pièce du jeu d'échecs.
 * Chaque pièce est spécifique hérite de cette classe.
 */
public abstract class Piece {
    private int typePiece;
    private int x;
    private int y;
    private final byte equipe;


    /**
     * Constructeur de la classe Piece.
     *
     * @param type Le type de pièce.
     * @param equipe L'équipe à laquelle appartient la pièce
     * @param x La position sur l'axe des abscisses de la pièce sur l'échiquier.
     * @param y La position sur l'axe des ordonnées de la pièce sur l'échiquier.
     */
    Piece(Pieces type, byte equipe, int x, int y) {
        this.x = x;
        this.y = y;
        this.typePiece = type.ordinal();
        this.equipe = equipe;
    }

    /**
     * Méthode abstraite calculant et retournant la liste des mouvements valides
     * pour la pièce.
     *
     * @return Une liste de paires (x,y) représentant les mouvements possibles
     * où la pièce peut se déplacer.
     */
    public abstract List<Pair<Integer,Integer>> mouvementValides();


    /**
     * Méthode affichant les mouvements valides de la pièce dans la console.
     *
     * @param mvtValides La liste des mouvements valides à afficher.
     */
    public void afficheMouvement(List<Pair<Integer,Integer>> mvtValides) {
        System.out.println("Mouvements valides: ");
        for (int i = 0; i < mvtValides.size(); ++i) {
            Pair<Integer, Integer> move = mvtValides.get(i);
            System.out.println("Move #" + i + ": (" + move.getKey() + ", " + move.getValue() + ")");
        }
    }

    /**
     * Méthode déplacant la pièce à une position souhaitée lorsque le
     * mouvement est valide.
     *
     * @param mvtValides La liste des mouvements valides de la pièce.
     * @param caseVoulue La position souhaitée pour déplacer la pièce.
     */
    public void mouvement(List<Pair<Integer,Integer>> mvtValides, Pair<Integer,Integer> caseVoulue) {
        afficheMouvement(mvtValides);

        boolean estValide = mvtValides.contains(caseVoulue);

        try {
            if (estValide) {
                final int oldX = this.getX();
                final int oldY = this.getY();
                this.setX(caseVoulue.getKey());
                this.setY(caseVoulue.getValue());
                TableauEchec.updateBoard(this,oldX,oldY);
            } else {
                System.out.println("Mouvement invalide");
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }


    /**
     * Méthode filtrant et retournant les mouvements valides qui permette de
     * capturer une pièce adverse.
     *
     * @param mouvementsValides La liste des mouvements valides de la pièce.
     * @return Une liste de paires (x,y) représentant les positions dans
     * lesquelles une pièce adverse peut être capturée.
     */
    public static List<Pair<Integer, Integer>> canEat(List<Pair<Integer, Integer>> mouvementsValides) {
        List<Pair<Integer, Integer>> eatable = new ArrayList<>();

        if (mouvementsValides == null) {
            return eatable;
        }
        for (Pair<Integer, Integer> move : mouvementsValides) {
            if (TableauEchec.BOARD[move.getKey()][move.getValue()] != null) {
                eatable.add(move);
            }
        }
        return eatable;
    }

    /**
     * Méthode retournant la position sur l'axe des abscisses de la pièce
     * dans l'échiquier.
     *
     * @return La position x de la pièce.
     */
    public int getX() {
        return x;
    }

    /**
     * Méthode modifiant la position sur l'axe des abscisses de la pièce
     * dans l'échiquier.
     *
     * @param x La nouvelle position x de la pièce.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Méthode retournant la position sur l'axe des ordonnées de la pièce
     * dans l'échiquier.
     *
     * @return La position y de la pièce.
     */
    public int getY() {
        return y;
    }

    /**
     * Méthode modifiant la position sur l'axe des ordonnées de la pièce
     * dans l'échiquier.
     *
     * @param y La nouvelle position y de la pièce.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Méthode abstraite obtenant l'image d'une pièce.
     *
     * @return Une chaîne de caractères correspondant au chemin de l'image
     * de la pièce.
     */
    public abstract String getImage();

    /**
     * Méthode retournant l'équipe à laquelle appartient une pièce.
     *
     * @return L'équipe de la pièce.
     */
    public byte getEquipe() {
        return equipe;
    }
}
