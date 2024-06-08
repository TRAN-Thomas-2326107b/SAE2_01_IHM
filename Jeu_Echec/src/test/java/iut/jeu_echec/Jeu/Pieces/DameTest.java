package iut.jeu_echec.Jeu.Pieces;

import iut.jeu_echec.Jeu.TableauEchec;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la pièce de la Dame.
 * Cette classe contient plusieurs tests unitaires permettant de vérifiere les
 * mouvements valides et les captures possibles de la Dame sur l'échiquier
 */
class DameTest {

    /**
     * Méthode exécutée avant chaque test pour initialiser l'échiquier.
     */
    @BeforeEach
    public void setUp() {
        TableauEchec.createBoard();
    }

    /**
     * Méthode testant la position initiale de la Dame sur l'échiquier
     * Vérifie que la Dame ne possède aucun mouvement valide au début de la partie.
     */
    @Test
    public void testPositionInitiale() {
        Dame dame = (Dame) TableauEchec.BOARD[0][3];

        //ne peut pas manger les pièces de la même équipe
        //ne peut pas passer au-dessus des pièces alliées
        assertTrue(dame.mouvementValides().isEmpty());
    }

    /**
     * Méthode testant les mouvements possibles de la Dame, placé au milieu du plateau.
     * Vérifie que la Dame possèdes 19 mouvements valides.
     */
    @Test
    public void testMouvementMilieu() {
        TableauEchec.BOARD[4][4] = new Dame(TableauEchec.eBlanc, 4, 4);
        Dame dame = (Dame) TableauEchec.BOARD[4][4];

        //peut se déplacer en diagonale, à la verticale et à l'horizontale
        assertEquals(dame.mouvementValides().size(), 19);
    }

    /**
     * Méthode testant les mouvements possibles de la Dame lors d'une capture.
     * Vérifie que la Dame peut capturer une pièce de l'équipe adverse.
     */
    @Test
    public void testDameCapture() {
        TableauEchec.BOARD[4][4] = new Dame(TableauEchec.eBlanc, 4, 4);
        Dame dame = (Dame) TableauEchec.BOARD[4][4];

        //peut capturer les pièces de l'équipe adverse
        assertEquals(dame.mouvementValides().size(), 19);
        assertTrue(dame.mouvementValides().contains(new Pair<>(1, 4)));
    }

}