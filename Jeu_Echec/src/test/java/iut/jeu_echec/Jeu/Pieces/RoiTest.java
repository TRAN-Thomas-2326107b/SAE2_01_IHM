package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la pièce du Roi.
 * Cette classe contient plusieurs tests unitaires permettant de vérifier les
 * mouvements valides, les captures possibles du Roi sur l'échiquier et la
 * condition d'échec et mat.
 */
class RoiTest {

    /**
     * Méthode executée avant chaque test pour initialiser l'échiquier.
     */
    @BeforeEach
    public void setUp() {
        TableauEchec.createBoard();
    }

    /**
     * Méthode testant la position initiale du Roi sur l'échiquier.
     * Vérifie que le Roi ne possède aucun mouvement valide au début de la partie.
     */
    @Test
    public void testPositionInitiale() {
        Roi roi = (Roi) TableauEchec.BOARD[0][4];
        assertTrue(roi.mouvementValides().isEmpty()); //ne peut pas manger les pieces de la même équipe
    }

    /**
     * Méthode testant les mouvements possibles du Roi, placé au milieu.
     * Vérifie que le Roi possède 8 mouvements valides.
     */
    @Test
    public void testMouvementMilieu() {
        TableauEchec.BOARD[4][4] = new Roi(TableauEchec.eBlanc, 4, 4);
        Roi roi = (Roi) TableauEchec.BOARD[4][4];

        //Toutes les cases autour du roi
        assertEquals(8, roi.mouvementValides().size());
    }

    /**
     * Méthode testant les mouvements possibles du Roi lors d'une capture.
     * Vérifie que le Roi peut capturer une pièce de l'équipe adverse.
     */
    @Test
    public void testRoiCapture() {
        TableauEchec.BOARD[4][4] = new Roi(TableauEchec.eBlanc, 4, 4);
        TableauEchec.BOARD[5][5] = new Pion(TableauEchec.eNoir, 3, 3);
        Roi roi = (Roi) TableauEchec.BOARD[4][4];

        //peut capturer les pièces de l'équipe adverse
        assertEquals(8, roi.mouvementValides().size());
        assertTrue(roi.mouvementValides().contains(new Pair<>(3, 3)));
    }

    /**
     * Méthode testant le Roi en situation d'échec.
     * Vérifie que la condition d'échec est vraie.
     */
    @Test
    public void testRoiEstEnEchec() {
        TableauEchec.BOARD[4][4] = new Roi(TableauEchec.eBlanc, 4, 4);
        TableauEchec.BOARD[5][4] = new Tour(TableauEchec.eNoir, 5, 4);
        TableauEchec.BOARD[7][4] = null;

        //roi en echec
        assertTrue(TableauEchec.isKingInCheck(TableauEchec.eBlanc, true));
    }

    /**
     * Méthode testant le Roi pas en situation d'échec.
     * Vérifie que la condition d'échec est fausse.
     */
    @Test
    public void testRoiNEstPasEnEchec() {
        TableauEchec.BOARD[4][4] = new Roi(TableauEchec.eBlanc, 4, 4);
        TableauEchec.BOARD[6][6] = new Tour(TableauEchec.eNoir, 6, 6);

        //roi pas en echec
        assertFalse(TableauEchec.isKingInCheck(TableauEchec.eBlanc, true));
    }

    /**
     * Méthode testant le Roi en situation d'échec et mat.
     * Vérifie que la condition d'échec et mat est vraie.
     */
    @Test
    public void testRoiEstCheckmate() {
        TableauEchec.BOARD[0][0] = new Roi(TableauEchec.eBlanc, 0, 0);
        TableauEchec.BOARD[7][4] = null;
        TableauEchec.BOARD[1][1] = new Tour(TableauEchec.eNoir, 1, 1);
        TableauEchec.BOARD[0][2] = new Tour(TableauEchec.eNoir, 0, 2);
        TableauEchec.BOARD[2][0] = new Tour(TableauEchec.eNoir, 2, 0);

        // roi en echec et mat
        assertTrue(TableauEchec.isCheckmate(TableauEchec.eBlanc));
    }

    /**
     * Méthode testant le Roi pas en situation d'échec et mat.
     * Vérifie que la condition d'échec et mat est fausse.
     */
    @Test
    public void testRoiNEstPasCheckmate() {
        TableauEchec.BOARD[0][0] = new Roi(TableauEchec.eBlanc, 0, 0);
        TableauEchec.BOARD[1][1] = new Tour(TableauEchec.eNoir, 1, 1);

        // roi pas en echec et mat
        assertFalse(TableauEchec.isCheckmate(TableauEchec.eBlanc));
    }
}