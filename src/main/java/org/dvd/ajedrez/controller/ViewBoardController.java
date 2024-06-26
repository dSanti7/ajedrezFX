package org.dvd.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.dvd.ajedrez.model.Board;

import java.util.Objects;

import static org.dvd.ajedrez.utilitis.Paths.CHESS_PIECES;


public class ViewBoardController {
    @FXML
    Rectangle backgroundBoard;
    @FXML
    Rectangle imgBoard;
    private Board board;

    @FXML
    Rectangle box;
    @FXML
    Group groupBoard;
    Rectangle[][] boxes = new Rectangle[8][8];
    int[][] piezas = {
            {0, 0, 98, 211},    // Peón Blanco
            {98, 0, 98, 211},   // Torre Blanca
            {196, 0, 98, 211},  // Alfil Blanco
            {294, 0, 98, 211},  // Caballo Blanco
            {392, 0, 98, 211},  // Reina Blanca
            {490, 0, 98, 211},  // Rey Blanco
            {0, 211, 98, 211},  // Peón Negro
            {98, 211, 98, 211}, // Torre Negra
            {196, 211, 98, 211},// Alfil Negro
            {294, 211, 98, 211},// Caballo Negro
            {392, 211, 98, 211},// Reina Negra
            {490, 211, 98, 211} // Rey Negro
    };

    public ViewBoardController() {
        board = Board.start();
    }

    public void print() {

        //Cambiamos el tamaño del tablero para que sea cuadrado.
        changeSizeTablero();
        //Coloreamos el tablero
        printBoard();

        //Imprimir img de fichas
        Image imgPieces = new Image( Objects.requireNonNull(getClass().getResource(CHESS_PIECES)).toExternalForm());
        ImageView elemento = createSprite(imgPieces,0,0,98);
    }

    private void changeSizeTablero() {
        int size = (int) imgBoard.getHeight();
        int realSize = size / 8;
        //Definimos como cuadrado
        imgBoard.setWidth(realSize * 8);
        imgBoard.setHeight(realSize * 8);
    }

    public void printBoard() {

        int sizeBox = (int) (imgBoard.getHeight() / 8);

        boolean isColor = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color = isColor ? Color.BLACK : Color.WHITE;
                boxes[i][j] = createRectangle(sizeBox, color, i, j);
                groupBoard.getChildren().add(boxes[i][j]);
                isColor = !isColor;
            }
            isColor = !isColor;
        }
    }

    private Rectangle createRectangle(int sizeBox, Color color, int i, int j) {
        double xBoard = imgBoard.getLayoutX();
        double yBoard = imgBoard.getLayoutY();
        Rectangle rectangle = new Rectangle(xBoard + (i * sizeBox), yBoard + (j * sizeBox), sizeBox, sizeBox);

        rectangle.setFill(color);
        rectangle.setStroke(Color.WHITE); // Color del borde del Rectangle
        rectangle.setStrokeWidth(2); // Ancho del borde del Rectangle
        rectangle.toFront();
        return rectangle;
    }

    private ImageView createSprite(Image spritesheet, int x, int y, int width, int height) {
        ImageView imageView = new ImageView(spritesheet);
        Rectangle2D viewportRect = new Rectangle2D(x, y, width, height);
        imageView.setViewport(viewportRect);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
