package org.dvd.ajedrez.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

import static org.dvd.ajedrez.utilitis.Paths.CHESS_PIECES;

public class BoardView {
    private static BoardView boardView;

    public static BoardView start() {
        if (boardView == null) {
            boardView = new BoardView();
        }

        return boardView;
    }

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
    Rectangle[][] boxes = new Rectangle[8][8];

    public void printChessPieces(Group groupBoard) {

        //imagenes de fichas negras
        Image imgPieces = new Image(Objects.requireNonNull(getClass().getResource(CHESS_PIECES)).toExternalForm());
        int x = 0;
        int y = 0;
        for (int i = 0; i < 16; i++) {

            ImageView imageView = createSprite(imgPieces, piezas[i], boxes[x][y]);

            //Evento cuando se hace clic

            groupBoard.getChildren().add(imageView);


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
            x++;
            if (x == 8) {
                x = 0;
                y = 7;
            }

            //Añadimos img al tablero
            groupBoard.getChildren().add(imageView);
        }

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

    public void printBoard(Rectangle imgBoard, Group groupBoard) {


        int sizeBox = (int) (imgBoard.getHeight() / 8);

        boolean isColor = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color = isColor ? Color.BLACK : Color.WHITE;
                boxes[i][j] = createRectangle(sizeBox, color, i, j,imgBoard,groupBoard);
                groupBoard.getChildren().add(boxes[i][j]);
                isColor = !isColor;
            }
            isColor = !isColor;
        }
    }

    private Rectangle createRectangle(int sizeBox, Color color, int i, int j, Rectangle imgBoard, Group groupBoard) {
        double xBoard = imgBoard.getLayoutX();
        double yBoard = imgBoard.getLayoutY();
        Rectangle rectangle = new Rectangle(xBoard + (i * sizeBox), yBoard + (j * sizeBox), sizeBox, sizeBox);

        rectangle.setFill(color);
        rectangle.setStroke(Color.WHITE); // Color del borde del Rectangle
        rectangle.setStrokeWidth(2); // Ancho del borde del Rectangle
        rectangle.toFront();
        return rectangle;
    }
    public void changeSizeTablero(Rectangle imgBoard) {
        int size = (int) imgBoard.getHeight();
        int realSize = size / 8;
        //Definimos como cuadrado
        imgBoard.setWidth(realSize * 8);
        imgBoard.setHeight(realSize * 8);
    }
}
