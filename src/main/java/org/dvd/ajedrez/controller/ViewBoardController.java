package org.dvd.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class ViewBoardController {
    @FXML
    Rectangle backgroundBoard;
    @FXML
    Rectangle board;
    @FXML
    Rectangle box;
    @FXML
    Group groupBoard;

    Rectangle[][] boxes = new Rectangle[8][8];

    public void changeSizeTablero() {
        int size = (int) board.getHeight();
        int realSize= size/8;
        //Definimos como cuadrado
        board.setWidth(realSize*8);
        board.setHeight(realSize*8);
    }

    public void putBoxes() {

        int sizeBox = (int) (board.getHeight() / 8);


        boolean isColor = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color= isColor?Color.BLACK:Color.WHITE;
                boxes[i][j] = createRectangle(sizeBox, color, i, j);
                groupBoard.getChildren().add(boxes[i][j]);
                isColor = !isColor;
            }
            isColor = !isColor;
        }
    }

    private Rectangle createRectangle(int sizeBox, Color color, int i, int j) {
        double xBoard = board.getLayoutX();
        double yBoard = board.getLayoutY();
        Rectangle rectangle = new Rectangle(xBoard + (i * sizeBox), yBoard + (j * sizeBox), sizeBox, sizeBox);

        rectangle.setFill(color);
        rectangle.setStroke(Color.WHITE); // Color del borde del Rectangle
        rectangle.setStrokeWidth(2); // Ancho del borde del Rectangle
        rectangle.toFront();
        return rectangle;
    }
}
