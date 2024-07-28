package org.dvd.ajedrez.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Piece {
    private static final Logger LOGGER = LoggerFactory.getLogger(Piece.class);

    private int id;

    private Position position;
    private String tipo;
    private String color;
    private boolean isDead;
    private boolean attack;
    private boolean fistMovement;

    public Piece(int id, int x, int y, String tipo, String color) {
        this.id = id;
        position = new Position(x, y);
        this.tipo = tipo;
        this.color = color;
        this.isDead = false;
        if ("P".equals(tipo)) {
            fistMovement = true;
        }
    }


    public Piece() {

    }

    public boolean isFistMovement() {
        return fistMovement;
    }

    public void setFistMovement(boolean fistMovement) {
        this.fistMovement = fistMovement;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Position> getMoves(List<Piece> pieceList) {

        LOGGER.info("Posición {}", position);
        return switch (tipo) {
            case "T" ->//Torre
                    getTowerMoves(pieceList);
            case "H" ->//Caballo
                    getHorseMoves(pieceList);
            case "B" ->//Alfil
                    getBishopMoves(pieceList);
            case "Q" ->//Reina
                    getQueenMoves(pieceList);
            case "K" ->//Rey
                    getKingMoves(pieceList);
            case "P" ->//Peon
                    getPawnAll(pieceList);
            default -> null;
        };

    }

    public boolean canPieceMove(Position newPosition, List<Piece> pieceList) {
        if (newPosition.equals(position)) {
            return false;
        }
        switch (tipo) {
            case "T"://Torre
                Optional<Position> towerPosition = getTowerMoves(pieceList).stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (towerPosition.isPresent()) {
                    return true;
                }

                break;
            case "H"://Caballo
                Optional<Position> horsePosition = getHorseMoves(pieceList).stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (horsePosition.isPresent()) {
                    return true;
                }
                break;
            case "B"://Alfil
                Optional<Position> bishopMove = getBishopMoves(pieceList).stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (bishopMove.isPresent()) {
                    return true;
                }

                break;
            case "Q"://Reina
                Optional<Position> queenPosition = getQueenMoves(pieceList).stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (queenPosition.isPresent()) {
                    return true;
                }

                break;
            case "K"://Rey
                Optional<Position> kingPosition = getKingMoves(pieceList).stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (kingPosition.isPresent()) {
                    return true;
                }
                break;
            case "P"://Peon
                Optional<Position> pawnPosition = getPawnMovesAtack().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (pawnPosition.isPresent()) {
                    return true;
                }
                break;
        }

        return false;
    }

    public List<Position> getKingMoves(List<Piece> pieceList) {
        List<Position> positions = new LinkedList<>();

        positions.add(new Position(position.getX() + 1, position.getY()));
        positions.add(new Position(position.getX() - 1, position.getY()));
        positions.add(new Position(position.getX() + 1, position.getY() + 1));
        positions.add(new Position(position.getX(), position.getY() + 1));
        positions.add(new Position(position.getX() - 1, position.getY() + 1));
        positions.add(new Position(position.getX() + 1, position.getY() - 1));
        positions.add(new Position(position.getX() - 1, position.getY() - 1));
        positions.add(new Position(position.getX(), position.getY() - 1));

        List<Position> salida = new ArrayList<>(positions.stream().toList());

        for (Position position1 : positions) {

            for (Piece piece : pieceList) {
                if (piece.getColor().equals(color) && piece.getPosition().equals(position1)) {

                    salida.remove(position1);
                    break;
                }
            }

        }


        return salida.stream().filter(Position::isValid).toList();
    }

    public List<Position> getPawnMovesAtack() {
        List<Position> positions = new LinkedList<>();

        if (color.equals("B")) {
            positions.add(new Position(position.getX() + 1, position.getY() + 1));
            positions.add(new Position(position.getX() - 1, position.getY() + 1));

        }

        //Control de los movimientos cuando es ficha blanca
        if (color.equals("W")) {
            positions.add(new Position(position.getX() + 1, position.getY() - 1));

            positions.add(new Position(position.getX() - 1, position.getY() - 1));
        }
        return positions;

    }

    public List<Position> getPawnMovesAtack(List<Piece> pieceList) {
        List<Position> positions = new LinkedList<>();


        Piece rightEnemy = null;
        Piece leftEnemy = null;
        //Control de los movimientos cuando es ficha negra
        if (color.equals("B")) {
            for (Piece piece : pieceList) {

                if (piece.getPosition().equals(new Position(position.getX() + 1, position.getY() + 1)) && !piece.getColor().equals(color)) {
                    rightEnemy = piece;
                }
                if (piece.getPosition().equals(new Position(position.getX() - 1, position.getY() + 1)) && !piece.getColor().equals(color)) {
                    leftEnemy = piece;
                }

            }
            if (rightEnemy != null) {
                positions.add(new Position(position.getX() + 1, position.getY() + 1));

            }
            if (leftEnemy != null) {
                positions.add(new Position(position.getX() - 1, position.getY() + 1));
            }

        }
        //Control de los movimientos cuando es ficha blanca
        if (color.equals("W")) {
            for (Piece piece : pieceList) {
                if (piece.getPosition().equals(new Position(position.getX() + 1, position.getY() - 1)) && !piece.getColor().equals(color)) {
                    rightEnemy = piece;
                }
                if (piece.getPosition().equals(new Position(position.getX() - 1, position.getY() - 1)) && !piece.getColor().equals(color)) {
                    leftEnemy = piece;
                }
            }
            if (rightEnemy != null) {
                positions.add(new Position(position.getX() + 1, position.getY() - 1));
            }
            if (leftEnemy != null) {
                positions.add(new Position(position.getX() - 1, position.getY() - 1));

            }


        }

        return positions;
    }

    public List<Position> getPawnMoves(List<Piece> pieceList) {
        List<Position> positions = new LinkedList<>();

        Piece pieceInFront = null;
        Piece pieceIn2Front = null;

        //Control de los movimientos cuando es ficha negra
        if (color.equals("B")) {
            for (Piece piece : pieceList) {


                if (piece.getPosition().equals(new Position(position.getX(), position.getY() + 1))) {
                    pieceInFront = piece;
                }
                if (piece.getPosition().equals(new Position(position.getX(), position.getY() + 2))) {
                    pieceIn2Front = piece;
                }
            }

            if (fistMovement && pieceIn2Front == null && pieceInFront == null) {
                positions.add(new Position(position.getX(), position.getY() + 2));

            }

            if (pieceInFront == null) {
                positions.add(new Position(position.getX(), position.getY() + 1));
            }
        }
        //Control de los movimientos cuando es ficha blanca
        if (color.equals("W")) {
            for (Piece piece : pieceList) {

                if (piece.getPosition().equals(new Position(position.getX(), position.getY() - 1))) {
                    pieceInFront = piece;
                }
                if (piece.getPosition().equals(new Position(position.getX(), position.getY() - 2))) {
                    pieceIn2Front = piece;
                }
            }

            if (fistMovement && pieceIn2Front == null) {
                positions.add(new Position(position.getX(), position.getY() - 2));

            }
            if (pieceInFront == null) {
                positions.add(new Position(position.getX(), position.getY() - 1));
            }

        }

        return positions;
    }

    public List<Position> getPawnAll(List<Piece> pieceList) {

        return concatenarList(getPawnMoves(pieceList), getPawnMovesAtack(pieceList)
        );
    }

    public List<Position> getBishopMoves(List<Piece> pieceList) {
        List<Position> listPosition1 = new ArrayList<>();
        List<Position> listPosition2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Math.abs(i - position.getX()) ==
                        Math.abs(j - position.getY())) {

                    if (i - position.getX() + j - position.getY() == 0) {
                        if (new Position(i, j).equals(position)) {
                            listPosition1.add(new Position(i, j));
                            listPosition2.add(new Position(i, j));
                        } else {
                            //izq abajo  - arriba derecha
                            listPosition1.add(new Position(i, j));
                        }
                    } else {

                        //izq arriba - abajo derecha
                        listPosition2.add(new Position(i, j));

                    }

                }
            }
        }

        //obtenemos los aliados de la lista 1
        List<Piece> allies = pieceList.stream()
                .filter(piece -> piece.getColor().equals(color))
                .filter(piece -> !piece.getPosition().equals(position))
                .toList();

        List<Position> positionsAllies = getPositionsAllies(listPosition1, allies);
        List<Position> positionsAllies2 = getPositionsAllies(listPosition2, allies);

        List<Position> salida = concatenarList(positionsAllies, positionsAllies2);

        //actualizamos los datos posibles
        listPosition1 = getPositionsPosibles(listPosition1, salida);
        listPosition2 = getPositionsPosibles(listPosition2, salida);

        //Buscamos los enemigos del tablero
        List<Piece> enemies = pieceList.stream()
                .filter(piece -> !piece.getColor().equals(color))
                .toList();

        //obtenemos las posiciones posibles teniendo en cuenta los enemigos
        List<Position> positionsEnemies = getPositionsEnemies(listPosition1, enemies);
        List<Position> positionsEnemies2 = getPositionsEnemies(listPosition2, enemies);

        return concatenarList(positionsEnemies, positionsEnemies2);
    }

    private static List<Position> concatenarList(List<Position> positionsAllies, List<Position> positionsAllies2) {
        List<Position> concatenatedList = new ArrayList<>();
        for (Position item : positionsAllies) {
            concatenatedList.add(item);
        }
        for (Position item : positionsAllies2) {
            concatenatedList.add(item);
        }
        return concatenatedList;
    }

    private static List<Position> getPositionsPosibles(List<Position> listPosition1, List<Position> finalSalida) {
        listPosition1 = listPosition1.stream()
                .filter(position1 -> finalSalida.stream()
                        .anyMatch(position2 -> position2.equals(position1)))
                .toList();
        return listPosition1;
    }

    private List<Position> getPositionsEnemies(List<Position> listPosition1, List<Piece> enemies) {
        List<Position> salida = new LinkedList<>();

        boolean medio = false;
        int numEnemies = 0;

        for (Position position : listPosition1) {
            if (medio) {
                if (enemies.stream().map(Piece::getPosition).noneMatch(position1 -> position1.equals(position))) {
                    //si no hay enemigo
                    if (numEnemies == 0) {
                        salida.add(position);
                    }
                } else {
                    // si encuentra un enemigo. Terminamos de buscar y lo añade
                    if (numEnemies == 0) {
                        salida.add(position);
                        numEnemies++;
                    }
                    break;
                }
            } else {
                //izq hasta el centro
                if (enemies.stream().map(Piece::getPosition).noneMatch(position1 -> position1.equals(position))) {
                    //si no hay enemigo
                    salida.add(position);
                } else {
                    salida = new LinkedList<>();
                    salida.add(position);
                    numEnemies++;


                }
            }
            if (position.equals(this.position)) {
                medio = true;
                numEnemies = 0;
            }
        }
        return salida;
    }

    private List<Position> getPositionsAllies(List<Position> listPosition, List<Piece> allies) {
        boolean medio = false;
        boolean isAlie = false;
        List<Position> salida = new LinkedList<>();

        //buscamos las posiciones validas sin incluir los aliados
        for (Position position : listPosition) {
            if (medio) {
                if (allies.stream().map(Piece::getPosition).noneMatch(position1 -> position1.equals(position))) {
                    //si no hay aliados
                    if (!isAlie) {
                        salida.add(position);
                    }
                } else {
                    isAlie = true;
                    // si encuentra un aliado. Terminamos de buscar
                    break;
                }
            } else {
                //izq hasta el centro
                if (allies.stream().map(Piece::getPosition).noneMatch(position1 -> position1.equals(position))) {
                    //si no hay aliados
                    salida.add(position);
                } else {
                    //si hay aliado
                    salida = new LinkedList<>();

                }
            }
            if (position.equals(this.position)) {
                medio = true;
            }
        }
        return salida;
    }

    public List<Position> getQueenMoves(List<Piece> pieceList) {
        List<Position> towerMoves = getTowerMoves(pieceList);
        List<Position> bishopMoves = getBishopMoves(pieceList);
        return Stream.concat(towerMoves.stream(), bishopMoves.stream()).toList();
    }

    public List<Position> getTowerMoves(List<Piece> pieceList) {
        List<Position> positions = new LinkedList<>();
        //mirar x derecha
        int numAttackEnemies = 0;
        for (int x = position.getX() + 1; x < 8; x++) {

            boolean isAllie = false;
            for (Piece piece : pieceList) {
                if (!piece.getColor().equals(color) && piece.getPosition().equals(new Position(x, position.getY()))) {
                    numAttackEnemies++;
                } else if (piece.getColor().equals(color) && piece.getPosition().equals(new Position(x, position.getY()))) {
                    isAllie = true;
                    break;
                }

            }
            if (isAllie) {
                break;
            }
            if (numAttackEnemies >= 1) {
                positions.add(new Position(x, position.getY()));
                break;
            }
            positions.add(new Position(x, position.getY()));
        }
        numAttackEnemies = 0;
        //mirar x izquierda
        for (int x = position.getX() - 1; x >= 0; x--) {
            boolean isAllie = false;
            for (Piece piece : pieceList) {
                if (!piece.getColor().equals(color) && piece.getPosition().equals(new Position(x, position.getY()))) {
                    numAttackEnemies++;
                } else if (piece.getColor().equals(color) && piece.getPosition().equals(new Position(x, position.getY()))) {
                    isAllie = true;
                    break;
                }

            }
            if (isAllie) {
                break;
            }
            if (numAttackEnemies >= 1) {
                positions.add(new Position(x, position.getY()));
                break;
            }
            positions.add(new Position(x, position.getY()));
        }
        numAttackEnemies = 0;
        //mirar y arriba
        for (int y = position.getY() - 1; y >= 0; y--) {
            boolean isAllie = false;
            for (Piece piece : pieceList) {
                if (!piece.getColor().equals(color) && piece.getPosition().equals(new Position(position.getX(), y))) {
                    numAttackEnemies++;
                } else if (piece.getColor().equals(color) && piece.getPosition().equals(new Position(position.getX(), y))) {
                    isAllie = true;
                    break;
                }

            }
            if (isAllie) {
                break;
            }
            if (numAttackEnemies >= 1) {
                positions.add(new Position(position.getX(), y));
                break;
            }
            positions.add(new Position(position.getX(), y));
        }
        numAttackEnemies = 0;
        //mirar y debajo
        for (int y = position.getY() + 1; y < 8; y++) {
            boolean isAllie = false;
            for (Piece piece : pieceList) {
                if (!piece.getColor().equals(color) && piece.getPosition().equals(new Position(position.getX(), y))) {
                    numAttackEnemies++;
                } else if (piece.getColor().equals(color) && piece.getPosition().equals(new Position(position.getX(), y))) {
                    isAllie = true;
                    break;
                }

            }
            if (isAllie) {
                break;
            }
            if (numAttackEnemies >= 1) {
                positions.add(new Position(position.getX(), y));
                break;
            }
            positions.add(new Position(position.getX(), y));
        }
        return positions.stream().filter(Position::isValid).toList();

    }

    public List<Position> getHorseMoves(List<Piece> pieceList) {
        List<Position> positions = new LinkedList<>();
        positions.add(new Position(position.getX() + 1, position.getY() + 2));
        positions.add(new Position(position.getX() - 1, position.getY() + 2));
        positions.add(new Position(position.getX() + 2, position.getY() - 1));
        positions.add(new Position(position.getX() + 2, position.getY() + 1));
        positions.add(new Position(position.getX() + 1, position.getY() - 2));
        positions.add(new Position(position.getX() - 1, position.getY() - 2));
        positions.add(new Position(position.getX() - 2, position.getY() + 1));
        positions.add(new Position(position.getX() - 2, position.getY() - 1));
        List<Position> out = new ArrayList<>(positions.stream().toList());
        for (Position position1 : positions) {

            for (Piece piece : pieceList) {
                if (piece.getColor().equals(color) && piece.getPosition().equals(position1)) {

                    out.remove(position1);
                    break;
                }
            }

        }
        return out.stream().filter(Position::isValid)
                .toList();

    }

    @Override
    public String toString() {
        return "Piece{" +
                "id=" + id +
                ", position=" + position +
                ", tipo='" + tipo + '\'' +
                ", color='" + color + '\'' +
                ", isDead=" + isDead +
                '}';
    }
}
