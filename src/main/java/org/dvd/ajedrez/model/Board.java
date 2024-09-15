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
    private String colorTurno = "W";


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
        Optional<Piece> thePiece = pieceList.stream().filter(piece -> piece.getId() == id).findAny();
        if (thePiece.isEmpty()) {
            Output output = new Output();
            output.setError("getMoves - No se encontró la ficha indicada");
            output.setCorrect(false);
            return output;
        }
        Output output = new Output();
        output.setIdPiece(id);
        if (!thePiece.get().getColor().equals(colorTurno)) {

            output.setCorrect(false);
            output.setError("Ficha incorrecta. No tiene turno");
            return output;
        }

        output.setCorrect(true);

        //Creamos ficha auxiliar para hacer la busqueda de los movimientos
        Piece piece = new Piece();
        piece.setFirstMovement(thePiece.get().isFirstMovement());
        piece.setPosition(input.getActualPosition());
        piece.setTipo(thePiece.get().getTipo());
        piece.setColor(thePiece.get().getColor());

        List<Position> moves = piece.getMoves(pieceList.stream().filter(p -> !p.isDead()).toList());

        LOGGER.info("getMoves - MOVIMIENTOS DE PIEZA - {}", moves);


        output.setPosiblesMoves(moves);
        output.setInformation("getMoves - Devolvemos los posibles movimientos");

        return output;
    }

    public Output updatePiece(Input input) {
        LOGGER.info("updatePiece - Entrada : {}", input);
        int id = input.getIdPiece();

        Optional<Piece> thePiece = pieceList.stream().filter(piece -> piece.getId() == id).findAny();
        Output output = new Output();
        output.setIdPiece(id);
        if (thePiece.isEmpty()) {
            output.setError("No se encontró la ficha indicada");
            output.setCorrect(false);
            return output;
        }
        String theColor = thePiece.get().getColor();
        output.setColor(theColor);
        List<Position> theMoves = thePiece.get().getMoves(pieceList.stream().filter(piece -> !piece.isDead()).toList());
        //Comprobamos si tiene el turno
        if (!thePiece.get().getColor().equals(colorTurno)) {
            output.setCorrect(false);
            output.setError("Ficha incorrecta, no tiene el turno");
            return output;
        }
        output.setCorrect(true);


        LOGGER.info("MOVIMIENTOS DE PIEZA - {}", theMoves);

        //comprobamos si es un movimiento valido
        if (theMoves.stream().noneMatch(position -> position.equals(input.getNewPosition()))) {
            output.setCorrect(false);
            output.setError("La posición no es posible");
            return output;
        }


        killEnemy(input.getNewPosition(), theColor, output, theMoves);

        //Comprobamos si el rey enemigo es amenazado
        Piece enemyKing = pieceList.stream().filter(piece ->
                !piece.getColor().equals(theColor)
                        && piece.getTipo().equals("K")).findAny().get();

        Piece updatePiece = new Piece();
        updatePiece.setPosition(input.getNewPosition());
        updatePiece.setTipo(thePiece.get().getTipo());
        updatePiece.setColor(theColor);
        boolean canKillKing = updatePiece.canPieceMove(enemyKing.getPosition(), pieceList);
        if (canKillKing) {
            output.setCheckKing(true);
            output.setInformation("Se amenaza al rey");
        }


        //Comprobar si nuestro rey es amenazado
        Piece ourKing = pieceList.stream().filter(piece ->
                piece.getColor().equals(theColor)
                        && piece.getTipo().equals("K")).findAny().get();

        Optional<Piece> pieceCanKillKing = pieceList.stream()
                .filter(piece -> !theColor.equals(piece.getColor()))
                .filter(piece -> !piece.isDead())
                .filter(piece -> piece.canPieceMove(ourKing.getPosition(), pieceList))
                .findAny();


        if (pieceCanKillKing.isPresent()) {
            pieceList.stream().filter(piece -> piece.getId()==output.getIdPieceDeleted());
            output.restart(thePiece.get().getId(), theColor);
            output.setError("No se puede mover porque genera jaque en contra");
            output.setCorrect(false);
        }

        //Comprobamos si podemos salvar al rey con el movimiento del usuario.
        boolean canSaveKing = saveKing(input.getNewPosition(), thePiece.get(), theColor, output, theMoves, ourKing);

        //Si no, existe algún movimiento que pueda salvar al rey?
        if (!canSaveKing) {

            //Comprobamos todos los casos para ver si podemos salvar al rey
            for (Piece piecesMov : pieceList) {

                if (piecesMov.isDead()) {
                    continue;
                }
                if (!piecesMov.getColor().equals(theColor)) {
                    continue;
                }
                List<Position> moves = piecesMov.getMoves(pieceList.stream().filter(piece -> !piece.isDead()).toList());
                boolean result = false;

                for (Position newPositionP : moves) {
                    result = saveKing(newPositionP, piecesMov, theColor, output, moves, ourKing);
                    if (result) {
                        break;
                    }
                }
                if (result) {
                    output.setIdPieceDeleted(0);//quitamos de la salida los id de borrar, al no
                    break;
                }

            }
        }

        //Comprobamos la posición nueva de nuestro rey genera jacque y si si, de vuelva error
        //porque no puedes mover tu rey a una posición peligrosa.
        if (ourKing.getId() == input.getIdPiece()) {
            Optional<Piece> enemyCanKillKing = pieceList.stream()
                    .filter(piece -> !theColor.equals(piece.getColor()))
                    .filter(piece -> piece.canPieceMove(input.getNewPosition(), pieceList)).findAny();

            enemyCanKillKing.ifPresent(piece -> {
                output.setCorrect(false);
                output.setError("Movimiento invalido. El rey en peligro");
            });
        }
        if (enemyKing.isDead()) {
            output.setWinnerColor(theColor);
        }

        //Actualizamos estado de la ficha
        if ((output.getError() == null || output.getError().isEmpty()) && output.isCorrect()) {
            Position newPosition = input.getNewPosition();
            thePiece.get().setPosition(newPosition);
            thePiece.get().setFirstMovement(false);
            updateTurno();

        }
        return output;
    }

    private static boolean canSaveKing(Position newPosition, Piece thePiece, String theColor, Piece ourKing) {

        thePiece.setPosition(newPosition);


        //Comprobamos si hay ficha que puede matar a nuestro rey
        Optional<Piece> pieceCanKillKing2 = pieceList.stream()
                .filter(piece -> !piece.isDead())
                .filter(piece -> !theColor.equals(piece.getColor()))
                .filter(piece -> piece.canPieceMove(ourKing.getPosition(), pieceList))
                .findAny();


        LOGGER.info("{}", thePiece.getPosition());

        return pieceCanKillKing2.isEmpty();

    }

    private static boolean saveKing(Position newPosition, Piece thePiece, String theColor, Output output, List<Position> theMoves, Piece ourKing) {
        //comprobamos si el movimiento protege al rey
        Position prePosition = thePiece.getPosition();

        //matamos al enemigo si hubiera
         killEnemy(newPosition, theColor, output, theMoves);

        if (canSaveKing(newPosition, thePiece, theColor, ourKing)) {
            output.setInformation("Se ha salvado al rey");
            output.setError("");
            output.setCorrect(true);
            return true;
        } else {

            output.restart(thePiece.getId(), theColor);
            output.setError("No se puede mover porque genera jaque en contra, no se puede salvar moviendo");
            output.setCorrect(false);
            thePiece.setPosition(prePosition);
            return false;
        }

    }

    private static Optional<Piece> getEnemyCanKill(Position input, String theColor, Output output, List<Position> theMoves){
        List<Piece> pieces = pieceList.stream() //lista de piezas que no están muertas y están en nuestros posibles movimientos
                .filter(piece -> theMoves.stream().anyMatch(position -> piece.getPosition().equals(position)))
                .filter(piece -> !piece.isDead())
                .toList();
        return pieces.stream().filter(piece -> piece.getPosition().equals(input) && !piece.getColor().equals(theColor)).findAny();

    }
    private static boolean canKillEnemy(Position input, String theColor, Output output, List<Position> theMoves) {
        //comprobamos si mata a alguna ficha
        Optional<Piece> enemy =  getEnemyCanKill(input,theColor,output,theMoves);
        enemy.ifPresent(piece -> output.setIdPieceDeleted(piece.getId()));

        return enemy.isPresent();
    }

    private static void killEnemy(Position input, String theColor, Output output, List<Position> theMoves) {
        //comprobamos si mata a alguna ficha
        boolean canKillEnemy = canKillEnemy(input, theColor, output, theMoves);
        //Si hay enemigo y es un movimiento valido
        if (canKillEnemy) {
            //Matamos a una ficha
            Piece enemy = pieceList.stream().filter(piece -> piece.getId() == output.getIdPieceDeleted()).findAny().get();
            enemy.setDead(true);

        } else {
            output.setIdPieceDeleted(0);
        }
    }

    private void updateTurno() {
        if (colorTurno.equals("W"))
            colorTurno = "B";
        else if (colorTurno.equals("B"))
            colorTurno = "W";
    }

}