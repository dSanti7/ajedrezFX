package org.dvd.ajedrez.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.dvd.ajedrez.model.Board;
import org.dvd.ajedrez.model.Input;
import org.dvd.ajedrez.model.Output;
import org.dvd.ajedrez.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static org.dvd.ajedrez.utilitis.Paths.CHESS_PIECES;


public class BoardView {
    private static BoardView boardView;
    private Board board;
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardView.class);
    private BoxView selectedboxes;

    public static BoardView start() {
        if (boardView == null) {
            boardView = new BoardView();
        }

        return boardView;
    }

    public BoardView() {
        boxViews = new BoxView[8][8];

    }

    private final int[][] positionsPiecesInit = {
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
    private BoxView[][] boxViews;

    public void printChessPieces(Group groupBoard) {
        board = Board.start();
        //Cargamos img de fichas negras y blancas
        Image imgPieces = new Image(Objects.requireNonNull(getClass().getResource(CHESS_PIECES)).toExternalForm());
        int x = 0;
        int y = 0;
        for (int i = 0; i < 16; i++) {
            PieceView pieceView = new PieceView();
            ImageView imageView = createSprite(imgPieces, positionsPiecesInit[i], boxViews[x][y].getBoxSelected());
            pieceView.setImageView(imageView);
            pieceView.setX(x);
            pieceView.setY(y);
            pieceView.setId(i + 1);

            boxViews[x][y].setPieceView(pieceView);

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
            PieceView pieceView = new PieceView();

            ImageView imageView = createSprite(imgPieces, positionsPiecesInit[i], boxViews[x][y].getBoxSelected());

            pieceView.setImageView(imageView);
            pieceView.setX(x);
            pieceView.setY(y);
            pieceView.setId(i + 1);

            boxViews[x][y].setPieceView(pieceView);

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
        int widthPiece = 23;
        int heightPiece = 35;
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

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                BoxView boxView = new BoxView();

                Color color = isColor ? Color.BLACK : Color.WHITE;
                Rectangle boxChess = createRectangle(sizeBox, color, x, y, imgBoard);

                Rectangle selectedBox = createRectangleSelected(sizeBox, x, y, imgBoard);
                boxView.setBoxSelected(selectedBox);
                boxView.setX(x);
                boxView.setY(y);
                selectedBox.setOnMouseClicked(event -> handleRectangleClick(event, boxView));
                groupBoard.getChildren().add(boxChess);
                groupBoard.getChildren().add(boxView.getBoxSelected());

                boxViews[x][y] = boxView;
                isColor = !isColor;
            }
            isColor = !isColor;
        }
    }

    private Rectangle createRectangle(int sizeBox, Color color, int i, int j, Rectangle imgBoard) {
        double xBoard = imgBoard.getLayoutX();
        double yBoard = imgBoard.getLayoutY();
        Rectangle rectangle = new Rectangle(xBoard + (i * sizeBox), yBoard + (j * sizeBox), sizeBox, sizeBox);

        rectangle.setFill(color);
        rectangle.setStroke(Color.WHITE); // Color del borde del Rectangle
        rectangle.setStrokeWidth(2); // Ancho del borde del Rectangle
        rectangle.toFront();
        return rectangle;
    }

    private Rectangle createRectangleSelected(int sizeBox, int i, int j, Rectangle imgBoard) {
        double xBoard = imgBoard.getLayoutX();
        double yBoard = imgBoard.getLayoutY();
        Rectangle rectangle = new Rectangle(xBoard + (i * sizeBox), yBoard + (j * sizeBox), sizeBox, sizeBox);
        Color paint = new Color(0.13, 1.0, 0.246, 0.0);
        rectangle.setFill(paint);
        rectangle.toFront();
        return rectangle;
    }

    private void handleRectangleClick(MouseEvent event, BoxView boxView) {


        //desseleccionamos casilla
        if (selectedboxes != null && boxView.getX() == selectedboxes.getX() && boxView.getY() == selectedboxes.getY()) {
            LOGGER.info("handleRectangleClick - boxView x:" + boxView.getX() + " y:" + boxView.getY() + " - selectedboxes x:" + selectedboxes.getX() + " y:" + selectedboxes.getY());
            LOGGER.info("handleRectangleClick - id boxView: " + boxView.getPieceView().getId() + " selectedboxes:" + selectedboxes.getPieceView().getId());
            //quitamos el color a la casilla seleccionada
            Color paint = new Color(0.13, 1.0, 0.246, 0);
            selectedboxes.getBoxSelected().setFill(paint);

            //Quitamos selección
            selectedboxes = null;
            return;

        }

        //comprobar que la primera seleccion es una ficha
        if (boxView.getPieceView() == null && selectedboxes == null) {
            return;
        }
        //Si hemos seleccionado la segunda casilla comprobamos que podemos mover
        if (selectedboxes != null) {

            Input input = new Input();
            //todo corregir movimiento alfiles
            //todo implementar ataque entre fichas - implementar borrar de img
            //todo implementar turnos
            //todo implementar movimientos especiales
            //todo implementar que las cajas se iluminen para saber que movimientos se pueden hacer

            input.setIdPiece(selectedboxes.getPieceView().getId());
            input.setActualPosition(new Position(selectedboxes.getX(), selectedboxes.getY()));
            input.setNewPosition(new Position(boxView.getX(), boxView.getY()));
            Output output = board.updatePiece(input);
            LOGGER.info("handleRectangleClick - Resultado {}",output.toString());
            if (output.getError() == null || output.getError().isEmpty()) {

                boxView.setPieceView(selectedboxes.getPieceView());
                selectedboxes.setPieceView(null);
                int widthPiece = 18;
                int heightPiece = 30;
                double size = boxView.getBoxSelected().getHeight();
                double paddingWidth = (size - widthPiece) / 2;
                double paddingHeight = (size - heightPiece) / 2;

                boxView.getPieceView().getImageView().setX(paddingWidth + boxView.getBoxSelected().getX());
                boxView.getPieceView().getImageView().setY(paddingHeight + boxView.getBoxSelected().getY());


                //mover ficha

                //quitamos el color a la casilla seleccionada
                Color paint = new Color(0.13, 1.0, 0.246, 0);
                selectedboxes.getBoxSelected().setFill(paint);

                //Quitamos selección
                selectedboxes = null;
            }
        } else {
            Color paint = new Color(0.13, 1.0, 0.246, 0.2132);
            boxView.getBoxSelected().setFill(paint);
            selectedboxes = boxView;
        }

    }

    public void changeSizeTablero(Rectangle imgBoard) {
        int size = (int) imgBoard.getHeight();
        int realSize = size / 8;
        //Definimos como cuadrado
        imgBoard.setWidth(realSize * 8);
        imgBoard.setHeight(realSize * 8);
    }
}
