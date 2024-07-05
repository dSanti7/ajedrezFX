package org.dvd.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import org.dvd.ajedrez.model.Board;
import org.dvd.ajedrez.view.BoardView;


public class ViewBoardController {
    @FXML
    Rectangle backgroundBoard;
    @FXML
    Rectangle imgBoard;
    @FXML
    Rectangle box;
    @FXML
    Group groupBoard;

    private Board board;
    private BoardView boardView;

    public ViewBoardController() {
        board = Board.start();
        boardView = BoardView.start();
    }

    public void print() {

        //Cambiamos el tamaño del tablero para que sea cuadrado.
        boardView.changeSizeTablero(imgBoard);
        //Coloreamos el tablero

        boardView.printBoard(imgBoard, groupBoard);

        //Imprimir img de fichas
        boardView.printChessPieces(groupBoard);

    }

}
