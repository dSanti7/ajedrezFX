package org.dvd.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.dvd.ajedrez.model.Board;
import org.dvd.ajedrez.model.Piece;

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
    String [] tipos = {
            "T","C","A","Q","K","A","C","T","P","P","P","P","P","P","P","P",
            "P","P","P","P","P","P","P","P","T","C","A","Q","K","A","C","T"};
    int[][] piezas = {
            {98, 211, 98, 211}, // Torre Negra
            {294, 211, 98, 211},// Caballo Negro
            {196, 211, 98, 211},// Alfil Negro
            {392, 211, 98, 211},// Reina Negra
            {490, 211, 98, 211}, // Rey Negro
            {196, 211, 98, 211},// Alfil Negro
            {294, 211, 98, 211},// Caballo Negro
            {98, 211, 98, 211}, // Torre Negra
            {0, 211, 98, 211},  // Peón Negro
            {0, 211, 98, 211},  // Peón Negro
            {0, 211, 98, 211},  // Peón Negro
            {0, 211, 98, 211},  // Peón Negro
            {0, 211, 98, 211},  // Peón Negro
            {0, 211, 98, 211},  // Peón Negro
            {0, 211, 98, 211},  // Peón Negro
            {0, 211, 98, 211},  // Peón Negro
            {0, 0, 98, 211},    // Peón Blanco
            {0, 0, 98, 211},    // Peón Blanco
            {0, 0, 98, 211},    // Peón Blanco
            {0, 0, 98, 211},    // Peón Blanco
            {0, 0, 98, 211},    // Peón Blanco
            {0, 0, 98, 211},    // Peón Blanco
            {0, 0, 98, 211},    // Peón Blanco
            {0, 0, 98, 211},    // Peón Blanco
            {98, 0, 98, 211},   // Torre Blanca
            {294, 0, 98, 211},  // Caballo Blanco
            {196, 0, 98, 211},  // Alfil Blanco
            {392, 0, 98, 211},  // Reina Blanca
            {490, 0, 98, 211},  // Rey Blanco
            {196, 0, 98, 211},  // Alfil Blanco
            {294, 0, 98, 211},  // Caballo Blanco
            {98, 0, 98, 211},   // Torre Blanca
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
        printChessPieces();
    }

    private void printChessPieces() {

        //imagenes de fichas negras
        Image imgPieces = new Image(Objects.requireNonNull(getClass().getResource(CHESS_PIECES)).toExternalForm());
        int x = 0;
        int y = 0;
        for (int i = 0; i < 16; i++) {
//            System.out.println("i:"+i+" x:"+x+" y:"+y);
            ImageView imageView = createSprite(imgPieces, piezas[i], boxes[x][y]);
            Piece piece = new Piece();
//            piece.setX(x);
//            piece.setY(y);
           // piece.setImageView(imageView);
            piece.setTipo(tipos[i]);

            //Evento cuando se hace clic
            imageView.setOnMouseClicked(event -> handleMouseClick(event));
            groupBoard.getChildren().add(imageView);
//            board.setBlackPiece(piece, i);
            ++x;
            if (x == 8) {
                x = 0;
                y = 1;
            }
        }

        //Imagenes de fichas blancas
        x = 0;
        y = 6;
        for (int i = 16; i < 32; i++) {
            ImageView imageView = createSprite(imgPieces, piezas[i], boxes[x][y]);

            Piece piece = new Piece();
//            piece.setX(x);
//            piece.setY(y);
           // piece.setImageView(imageView);
            piece.setTipo(tipos[i]);

//            board.setWhitePiece(piece, i - 16);
            x++;
            if (x == 8) {
                x = 0;
                y = 7;
            }

            //Añadimos img al tablero
            groupBoard.getChildren().add(imageView);
        }

    }

    private void handleMouseClick(MouseEvent event) {


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

    private ImageView createSprite(Image spritesheet, int[] pieza, Rectangle rectangle) {
        // int x, int y, int width, int height)

        ImageView imageView = new ImageView(spritesheet);
        Rectangle2D viewportRect = new Rectangle2D(pieza[0], pieza[1], pieza[2], pieza[3]);
        imageView.setViewport(viewportRect);
        int widthPiece = 18;
        int heightPiece = 30;
        imageView.setFitWidth(widthPiece);
        imageView.setFitHeight(heightPiece);
        double size = rectangle.getHeight();
        double paddingWidth = (size - widthPiece) / 2;
        double paddingHeight = (size - heightPiece) / 2;
        imageView.setX(paddingWidth + rectangle.getX());
        imageView.setY(paddingHeight + rectangle.getY());
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
