package iut.jeu_echec.Jeu;

import iut.jeu_echec.Jeu.Pieces.*;
import javafx.scene.control.Tab;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int R = Pieces.RIEN.ordinal();

        // 0 noir 1 blanc

        Piece[][] test_board = TableEchec.BOARD;


        TableEchec.afficheBoard(test_board);

        Scanner in = new Scanner(System.in);


        int x,y = -1;

        x = in.nextInt();
        y = in.nextInt();

        Piece pp = test_board[x][y];

        pp.mouvement(pp.mouvementValides());

        TableEchec.afficheBoard(test_board);




        //mouvementPion(board,x,y);
    }

    public static void mouvementPion(int[][] board, int x, int y) {
        System.out.println("pion à xy: " + board[x][y]);

        if (board[x][y] != Pieces.PION.ordinal())
            return;


    }



}