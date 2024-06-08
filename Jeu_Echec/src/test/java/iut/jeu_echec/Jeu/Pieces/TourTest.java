package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la pièce de la Tour.
 * Cette classe contient plusieurs tests unitaires permettant de vérifier les
 * mouvements valides et les captures possibles de la Tour sur l'échiquier.
 */
class TourTest {

    /**
     * Méthode éxécutée avant chaque test pour initialiser l'échiquier.
     */
    @BeforeEach
    public void setUp() {
        TableauEchec.createBoard();
    }

    /**
     * Méthode testant la position intiale de la Tour sur l'échiquier.
     * Vérifie que la Tour ne possède aucun mouvement valide au début de la partie
     */
    @Test
    public void testPositionInitiale() {
        Tour tour = (Tour) TableauEchec.BOARD[0][0];

        //ne peut pas manger les pièces de la même équipe
        assertTrue(tour.mouvementValides().isEmpty());
    }

    /**
     * Méthode testant les mouvements possibles de la Tour, placé au milieu du plateau.
     * Vérifie que la Tour possède 11 mouvements valides.
     */
    @Test
    public void testMouvementMilieu() {
        TableauEchec.BOARD[4][4] = new Tour(TableauEchec.eBlanc, 4, 4);
        Tour tour = (Tour) TableauEchec.BOARD[4][4];

        //peut se déplacer à la verticale ou à l'horizontale
        assertEquals(11, tour.mouvementValides().size());
    }

    /**
     * Méthode testant les mouvements possibles de la Tour lors d'une capture.
     * Vérifie que la Tour peut capturer une pièce de l'équipe adverse.
     */
    @Test
    public void testTourCapture() {
        TableauEchec.BOARD[4][4] = new Tour(TableauEchec.eBlanc, 4, 4);
        Tour tour = (Tour) TableauEchec.BOARD[4][4];

        //peut capturer les pièces de l'équipe adverse
        assertEquals(11, tour.mouvementValides().size());
        assertTrue(tour.mouvementValides().contains(new Pair<>(1, 4)));
    }
}