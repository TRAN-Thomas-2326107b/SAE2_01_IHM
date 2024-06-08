package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la pièce du Fou.
 * Cette classe contient plusierus tests unitaires permettant de vérifier les
 * mouvements valides et les captures possibles du Fou sur l'échiquier.
 */
class FouTest {

    /**
     * Méthode exécutée avant chaque test pour initialiser l'échiquier.
     */
    @BeforeEach
    public void setUp() {
        TableauEchec.createBoard();
    }

    /**
     * Méthode testant la position initiale du Fou sur l'échiquier.
     * Vérifier que le Fou ne possède aucun mouvement valide au début de la partie.
     */
    @Test
    public void testPositionInitiale() {
        Fou fou = (Fou) TableauEchec.BOARD[0][2];

        //ne peut pas manger les pièces de la même équipe
        assertTrue(fou.mouvementValides().isEmpty());
    }

    /**
     * Méthode testant les mouvements possibles du Fou, placé au milieu du plateau.
     * Vérifie que le Fou possède 13 mouvements possibles.
     */
    @Test
    public void testMouvementMilieu() {
        TableauEchec.BOARD[4][4] = new Fou(TableauEchec.eBlanc, 4, 4);
        Fou fou = (Fou) TableauEchec.BOARD[4][4];

        //peut se déplacer en diagonale
        assertEquals(13, fou.mouvementValides().size());
    }

    /**
     * Méthode testant les mouvements possibles du Fou lors d'une capture.
     * Vérifie que le Fou peut capturer une pièce de l'équipe adverse.
     */
    @Test
    public void testFouCapture() {
        TableauEchec.BOARD[4][4] = new Fou(TableauEchec.eBlanc, 4, 4);
        TableauEchec.BOARD[6][6] = new Pion(TableauEchec.eNoir, 6, 6);
        Fou fou = (Fou) TableauEchec.BOARD[4][4];

        //peut capturer les pièces de l'équipe adverse
        assertEquals(12, fou.mouvementValides().size());
        assertTrue(fou.mouvementValides().contains(new Pair<>(6, 6)));
    }
}