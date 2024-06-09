package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la pièce du Cavalier.
 * Cette classe contient plusieurs tests unitaires permettant de vérifier les
 * mouvements valides et les captures possibles du Cavalier sur l'échiquier.
 */
class CavalierTest {

    /**
     * Méthode exécutée avant chaque test pour initialiser l'échiquier.
     */
    @BeforeEach
    public void setUp() {
        TableauEchec.createBoard();
    }

    /**
     * Méthode testant la position initale du Cavalier sur l'échiquier.
     * Vérifie que le Cavalier ne possède que 2 mouvements valides au début
     * de la partie.
     */
    @Test
    public void testPositionInitiale() {
        Cavalier cavalier = (Cavalier) TableauEchec.BOARD[0][1];

        //ne peut pas manger les pièces de la même équipe
        //peut passer au-dessus des pièces alliées
        assertEquals(2, cavalier.mouvementValides().size());
    }

    /**
     * Méthode testant les mouvements possibles du Cavalier, placé au milieu du plateau.
     * Vérifie que le Cavalier possède 8 mouvements valides.
     */
    @Test
    public void testMouvementMilieu() {
        TableauEchec.BOARD[4][4] = new Cavalier(TableauEchec.eBlanc, 3, 4);
        Cavalier cavalier = (Cavalier) TableauEchec.BOARD[4][4];

        //peut se déplacer en L
        assertEquals(8, cavalier.mouvementValides().size());
    }

    /**
     * Méthode testant les mouvements possibles du Cavalier lors d'une capture.
     * Vérifie que le Cavalier peut capturer une pièce de l'équipe adverse.
     */
    @Test
    public void testCavalierCapture() {
        TableauEchec.BOARD[3][4] = new Cavalier(TableauEchec.eBlanc, 3, 4);
        Cavalier cavalier = (Cavalier) TableauEchec.BOARD[3][4];

        //peut capturer les pièces de l'équipe adverse
        assertEquals(8, cavalier.mouvementValides().size());
        assertTrue(cavalier.mouvementValides().contains(new Pair<>(1, 5)));
    }
}