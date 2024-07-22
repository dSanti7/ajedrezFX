package org.dvd.ajedrez.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Board {
    private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

    private static Board board;
    private static List<Piece> pieceList;


    public static Board start() {
        if (board == null) {
            board = new Board();
        }

        return board;
    }

    private Board() {
        initPieces();
    }

    private void initPieces() {
        pieceList = new LinkedList<>();
        //   Fichas negras
        Piece towerLeftBlack = new Piece(1, 0, 0, "T", "B");
        Piece horseLeftBlack = new Piece(2, 1, 0, "H", "B");
        Piece bishopLeftBlack = new Piece(3, 2, 0, "B", "B");
        Piece queenBlack = new Piece(4, 3, 0, "Q", "B");
        Piece kingBlack = new Piece(5, 4, 0, "K", "B");
        Piece bishopRightBlack = new Piece(6, 5, 0, "B", "B");
        Piece horseRightBlack = new Piece(7, 6, 0, "H", "B");
        Piece towerRightBlack = new Piece(8, 7, 0, "T", "B");

        Piece pawnBlack1 = new Piece(9, 0, 1, "P", "B");
        Piece pawnBlack2 = new Piece(10, 1, 1, "P", "B");
        Piece pawnBlack3 = new Piece(11, 2, 1, "P", "B");
        Piece pawnBlack4 = new Piece(12, 3, 1, "P", "B");
        Piece pawnBlack5 = new Piece(13, 4, 1, "P", "B");
        Piece pawnBlack6 = new Piece(14, 5, 1, "P", "B");
        Piece pawnBlack7 = new Piece(15, 6, 1, "P", "B");
        Piece pawnBlack8 = new Piece(16, 7, 1, "P", "B");
        pieceList.addAll(Stream.of(towerLeftBlack, horseLeftBlack, bishopLeftBlack, queenBlack, kingBlack, bishopRightBlack,
                horseRightBlack, towerRightBlack, pawnBlack1, pawnBlack2, pawnBlack3, pawnBlack4, pawnBlack5, pawnBlack6, pawnBlack7, pawnBlack8).toList());
        //Fichas Blancas
        Piece pawnWhite1 = new Piece(17, 0, 6, "P", "W");
        Piece pawnWhite2 = new Piece(18, 1, 6, "P", "W");
        Piece pawnWhite3 = new Piece(19, 2, 6, "P", "W");
        Piece pawnWhite4 = new Piece(20, 3, 6, "P", "W");
        Piece pawnWhite5 = new Piece(21, 4, 6, "P", "W");
        Piece pawnWhite6 = new Piece(22, 5, 6, "P", "W");
        Piece pawnWhite7 = new Piece(23, 6, 6, "P", "W");
        Piece pawnWhite8 = new Piece(24, 7, 6, "P", "W");
        Piece towerLeftWhite = new Piece(25, 0, 7, "T", "W");
        Piece horseLeftWhite = new Piece(26, 1, 7, "H", "W");
        Piece bishopLeftWhite = new Piece(27, 2, 7, "B", "W");
        Piece queenWhite = new Piece(28, 3, 7, "Q", "W");
        Piece kingWhite = new Piece(29, 4, 7, "K", "W");
        Piece bishopRightWhite = new Piece(30, 5, 7, "B", "W");
        Piece horseRightWhite = new Piece(31, 6, 7, "H", "W");
        Piece towerRightWhite = new Piece(32, 7, 7, "T", "W");


        pieceList.addAll(Stream.of(towerLeftWhite, horseLeftWhite, bishopLeftWhite, queenWhite, kingWhite, bishopRightWhite,
                horseRightWhite, towerRightWhite, pawnWhite1, pawnWhite2, pawnWhite3, pawnWhite4, pawnWhite5, pawnWhite6, pawnWhite7, pawnWhite8).toList());

    }

    public Output getMoves(Input input) {
        LOGGER.info("getMoves - Entrada : {}", input);
        int id = input.getIdPiece();
        Output output = new Output();
        output.setIdPiece(id);
        output.setCorrect(true);
        Optional<Piece> thePiece = pieceList.stream().filter(piece -> piece.getId() == id).findAny();
        if (thePiece.isEmpty()) {
            output.setError("getMoves - No se encontró la ficha indicada");
            return output;
        }
        //Creamos ficha auxiliar para hacer la busqueda de los movimientos
        Piece piece = new Piece();
        piece.setFistMovement(thePiece.get().isFistMovement());
        piece.setPosition(input.getActualPosition());
        piece.setTipo(thePiece.get().getTipo());
        piece.setColor(thePiece.get().getColor());
        List<Position> moves = piece.getMoves(pieceList);

        LOGGER.info("getMoves - MOVIMIENTOS DE PIEZA - {}", moves);


        output.setPosiblesMoves(moves);
        output.setInformation("getMoves - Devolvemos los posibles movimientos");

        return output;
    }

    public Output updatePiece(Input input) {
        LOGGER.info("updatePiece - Entrada : {}", input);
        int id = input.getIdPiece();

        Output output = new Output();
        output.setIdPiece(id);
        output.setCorrect(true);

        Optional<Piece> thePiece = pieceList.stream().filter(piece -> piece.getId() == id).findAny();
        if (thePiece.isEmpty()) {
            output.setError("No se encontró la ficha indicada");
            return output;
        }

        List<Position> theMoves = thePiece.get().getMoves(pieceList);

        LOGGER.info("MOVIMIENTOS DE PIEZA - {}", theMoves);

        if (input.getNewPosition() == null) {
            output.setPosiblesMoves(theMoves);
            output.setInformation("Devolvemos los posibles movimientos");
            return output;
        }
        //comprobamos si es un movimiento valido
        if (theMoves.stream().noneMatch(position -> position.equals(input.getNewPosition()))) {
            output.setCorrect(false);
            output.setError("La posición no es posible");
            return output;
        }

        String theColor = thePiece.get().getColor();
        output.setColor(theColor);
        output.setIdPiece(thePiece.get().getId());

        //comprobamos si mata a alguna ficha
        List<Piece> pieces = pieceList.stream().filter(piece -> theMoves.stream().anyMatch(position -> piece.getPosition().equals(position))).toList();
        Optional<Piece> enemy = pieces.stream().filter(piece -> piece.getPosition().equals(input.getNewPosition()) && !piece.getColor().equals(theColor)).findAny();

        //Si hay enemigo y es un movimiento valido
        enemy.ifPresent(piece -> {
            //Matamos a una ficha
            piece.setDead(true);
            output.setIdPieceDeleted(piece.getId());

        });

        //Comprobamos si el rey enemigo es amenazado
        Piece enemyKing = pieceList.stream().filter(piece ->
                !piece.getColor().equals(theColor)
                        && piece.getTipo().equals("K")).findAny().get();


        Optional<Piece> pieceStream = pieceList.stream()
                .filter(piece -> !theColor.equals(piece.getColor()))
                .filter(piece -> piece.canPieceMove(enemyKing.getPosition(), pieceList)).findAny();

        pieceStream.ifPresent(piece -> {
            output.setCheckKing(true);
            String info = output.getInformation();
            output.setInformation(info + " " + piece);
        });

        //Comprobar si nuestro rey es amenazado
        Piece ourKing = pieceList.stream().filter(piece ->
                piece.getColor().equals(theColor)
                        && piece.getTipo().equals("K")).findAny().get();

        pieceList.stream()
                .filter(piece -> !theColor.equals(piece.getColor()))
                .filter(piece -> piece.canPieceMove(ourKing.getPosition(), pieceList))
                .findAny().ifPresent(
                        piece -> {
                            enemy.ifPresent(pieceEnemy -> piece.setDead(false));
                            output.restart(thePiece.get().getId(), theColor);
                            output.setError("No se puede mover porque genera jaque en contra");
                            output.setCorrect(false);
                        }
                );


        //Actualizamos estado de la ficha
        if ((output.getError() == null || output.getError().isEmpty()) && output.isCorrect()) {
            Position newPosition = input.getNewPosition();
            thePiece.get().setPosition(newPosition);
            thePiece.get().setFistMovement(false);
        }
        return output;
    }

}