package org.dvd.ajedrez.model;

public class Board {
    private static Board board;
    private Piece[] white;
    private Piece[] black ;

    private Box[][] boxes = new Box[8][8];


    public static Board start() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    private Board() {

    }

    public void setPieces() {


        //Fichas negras
        Piece towerLeftBlack = new Piece(0,0,"T");

        Piece horseLeftBlack = new Piece(1,0,"H");
        Piece bishopLeftBlack = new Piece(2,0,"B");
        Piece queenBlack = new Piece(3,0,"Q");
        Piece kingBlack = new Piece(4,0,"K");
        Piece bishopRightBlack = new Piece(5,0,"B");
        Piece horseRightBlack = new Piece(6,0,"H");
        Piece towerRightBlack = new Piece(7,0,"T");

        Piece pawnBlack1 = new Piece(0,1,"P");
        Piece pawnBlack2 = new Piece(1,1,"P");
        Piece pawnBlack3 = new Piece(2,1,"P");
        Piece pawnBlack4 = new Piece(3,1,"P");
        Piece pawnBlack5 = new Piece(4,1,"P");
        Piece pawnBlack6 = new Piece(5,1,"P");
        Piece pawnBlack7 = new Piece(6,1,"P");
        Piece pawnBlack8 = new Piece(7,1,"P");
        white = new Piece[]{towerLeftBlack, horseLeftBlack, bishopLeftBlack, queenBlack, kingBlack,
                bishopRightBlack, horseRightBlack, towerRightBlack,pawnBlack1,pawnBlack2,pawnBlack3,
                pawnBlack4,pawnBlack5,pawnBlack6,pawnBlack7,pawnBlack8};

        //Fichas Blancas

        Piece towerLeftWhite = new Piece(0,7,"T");
        Piece horseLeftWhite = new Piece(1,7,"H");
        Piece bishopLeftWhite = new Piece(2,7,"B");
        Piece queenWhite = new Piece(3,7,"Q");
        Piece kingWhite = new Piece(4,7,"K");
        Piece bishopRightWhite = new Piece(5,7,"B");
        Piece horseRightWhite = new Piece(6,7,"H");
        Piece towerRightWhite = new Piece(7,7,"T");

        Piece pawnWhite1 = new Piece(0,6,"P");
        Piece pawnWhite2 = new Piece(1,6,"P");
        Piece pawnWhite3 = new Piece(2,6,"P");
        Piece pawnWhite4 = new Piece(3,6,"P");
        Piece pawnWhite5 = new Piece(4,6,"P");
        Piece pawnWhite6 = new Piece(5,6,"P");
        Piece pawnWhite7 = new Piece(6,6,"P");
        Piece pawnWhite8 = new Piece(7,6,"P");

        black =  new Piece[]{towerLeftWhite, horseLeftWhite, bishopLeftWhite, queenWhite, kingWhite,
                bishopRightWhite, horseRightWhite, towerRightWhite,pawnWhite1,pawnWhite2,pawnWhite3,
                pawnWhite4,pawnWhite5,pawnWhite6,pawnWhite7,pawnWhite8};
    }

}