package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la pièce du Pion.
 * Cette classe contient plusieurs tests unitaires permettant de vérifie les
 * mouvements valides et les captures possibles du Pion sur l'échiquier.
 */
class PionTest {

    /**
     * Méthode exécutée avant chaque test pour initialiser l'échiquier.
     */
    @BeforeEach
    public void setUp() {
        TableauEchec.createBoard();
    }

    /**
     * Méthode testant la position initiale du Pion sur l'échiquier.
     * Vérifie que le Pion ne possède que 2 mouvements valides au début
     * de la partie.
     */
    @Test
    public void testPositionInitiale() {
        Pion pion = (Pion) TableauEchec.BOARD[1][0];

        //peut avancer d'une ou 2 cases en position de départ
        assertEquals(2, pion.mouvementValides().size());
    }

    /**
     * Méthode testant les mouvements possible du Pion, placé au milieu du plateau.
     * Vérifie que le Pion possède 1 seul mouvement valide.
     */
    @Test
    public void testMouvementSimple() {
        TableauEchec.BOARD[4][4] = new Pion(TableauEchec.eBlanc, 4, 4);
        Pion pion = (Pion) TableauEchec.BOARD[4][4];

        //peut avancer d'une seule case, hors position de départ
        assertEquals(1, pion.mouvementValides().size());
    }

    /**
     * Méthode testant les mouvements possibles du Pion lors d'une capture.
     * Vérifie que le Pion peut capturer une pièce de l'équipe adverse.
     */
    @Test
    public void testPionCapture() {
        TableauEchec.BOARD[4][4] = new Pion(TableauEchec.eBlanc, 4, 4);
        TableauEchec.BOARD[5][5] = new Pion(TableauEchec.eNoir, 5, 5);
        Pion pion = (Pion) TableauEchec.BOARD[4][4];

        //peut capturer en diagonale devant
        assertEquals(1, pion.mouvementValides().size());
        assertTrue(pion.mouvementValides().contains(new Pair<>(5, 5)));
    }

    /**
     * Méthode testant les mouvements possibles du Pion lorsqu'il est en face
     * d'une pièce adverse.
     * Vérifie que le Pion ne peut pas capturer une pièce de l'équipe adverse
     * lorsqu'elle est en face du Pion.
     */
    @Test
    public void testPionPasCapture() {
        TableauEchec.BOARD[4][4] = new Pion(TableauEchec.eBlanc, 4, 4);
        TableauEchec.BOARD[5][4] = new Pion(TableauEchec.eNoir, 5, 4);
        Pion pion = (Pion) TableauEchec.BOARD[4][4];

        //ne peut pas capturer devant
        assertFalse(pion.mouvementValides().contains(new Pair<>(5, 4)));
    }
}