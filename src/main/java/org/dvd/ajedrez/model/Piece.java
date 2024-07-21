package org.dvd.ajedrez.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
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
                    getPawnMoves(pieceList);
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
                Optional<Position> pawnPosition = getPawnMoves(pieceList).stream()
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

    public List<Position> getPawnMoves(List<Piece> pieceList) {
        List<Position> positions = new LinkedList<>();


        //Control de los movimientos cuando es ficha negra
        if (color.equals("B")) {

            Optional<Piece> rightEnemy = pieceList.stream().filter(piece -> piece.getPosition().equals(new Position(position.getX() + 1, position.getY() + 1)) && !piece.getColor().equals(color)).findAny();
            Optional<Piece> leftEnemy = pieceList.stream().filter(piece -> piece.getPosition().equals(new Position(position.getX() - 1, position.getY() + 1)) && !piece.getColor().equals(color)).findAny();
            if (rightEnemy.isPresent()) {
                positions.add(new Position(position.getX() + 1, position.getY() + 1));

            }
            if (leftEnemy.isPresent()) {
                positions.add(new Position(position.getX() - 1, position.getY() + 1));
            }
            if (fistMovement) {
                positions.add(new Position(position.getX(), position.getY() + 2));

            }
            Optional<Piece> pieceInFront = pieceList.stream().filter(piece -> piece.getPosition().equals(new Position(position.getX(), position.getY() + 1))).findAny();

            if (pieceInFront.isEmpty()) {
                positions.add(new Position(position.getX(), position.getY() + 1));
            }
        }
        //Control de los movimientos cuando es ficha blanca
        if (color.equals("W")) {
             Optional<Piece> rightEnemy = pieceList.stream().filter(piece -> piece.getPosition().equals(new Position(position.getX() + 1, position.getY() - 1)) && !piece.getColor().equals(color)).findAny();
            Optional<Piece> leftEnemy = pieceList.stream().filter(piece -> piece.getPosition().equals(new Position(position.getX() - 1, position.getY() - 1)) && !piece.getColor().equals(color)).findAny();

            if (rightEnemy.isPresent()) {
                positions.add(new Position(position.getX() + 1, position.getY() - 1));
            }
            if (leftEnemy.isPresent()) {
                positions.add(new Position(position.getX() - 1, position.getY() - 1));

            }
            if (fistMovement) {
                positions.add(new Position(position.getX(), position.getY() - 2));

            }
            Optional<Piece> pieceInFront = pieceList.stream().filter(piece -> piece.getPosition().equals(new Position(position.getX(), position.getY() - 1))).findAny();

            if (pieceInFront.isEmpty()) {
                positions.add(new Position(position.getX(), position.getY() - 1));
            }

        }

        return positions;
    }

    public List<Position> getBishopMoves(List<Piece> pieceList) {
        List<Position> listPosition1 = new ArrayList<>();
        List<Position> listPosition2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Math.abs(i - position.getX()) ==
                        Math.abs(j - position.getY())
                        && i != position.getX() && j != position.getY()) {

                    if (i - position.getX() + j - position.getY() == 0) {
                        //izq abajo  - arriba derecha
                        listPosition1.add(new Position(i, j));
                    } else {
                        //izq arriba - abajo derecha
                        listPosition2.add(new Position(i, j));
                    }
                }
            }
        }


        List<Position> alliesPosition1 = listPosition1.stream()
                .filter(position1 -> pieceList.stream().filter(piece -> piece.getColor().equals(color))
                        .filter(piece -> piece.getPosition().getX() != position.getX() && piece.getPosition().getY() != position.getY())
                        .map(Piece::getPosition).anyMatch(position2 -> position2.equals(position1)))
                .sorted(Comparator.comparingInt(position1 -> position1.getX() + position1.getY()))
                .toList();


        List<Position> alliesPosition2 = listPosition2.stream()
                .filter(position1 -> pieceList.stream().filter(piece -> piece.getColor().equals(color))
                        .map(Piece::getPosition).anyMatch(position2 -> position2.equals(position1)))
                .sorted(Comparator.comparingInt(position1 -> position1.getX() + position1.getY()))
                .toList();

        List<Position> salida = new LinkedList<>();
        for (Position postAllies : alliesPosition1) {

            int xAllie = postAllies.getX() - position.getX();
            if (xAllie < 0) {
                salida = listPosition1.stream().filter(position1 -> xAllie < position1.getX() - position.getX()).toList();
            } else {
                salida = listPosition1.stream().filter(position1 -> xAllie > position1.getX() - position.getX()).toList();
            }

        }
        List<Position> salida2 = new LinkedList<>();
        for (Position postAllies : alliesPosition2) {

            int positionAllie = postAllies.getX() - position.getX() + postAllies.getY() - position.getY();
            if (positionAllie < 0) {
                salida2 = alliesPosition2.stream().filter(position1 -> positionAllie < position1.getX() - position.getX() + position1.getY() - position.getY()).toList();
            } else {
                salida2 = alliesPosition2.stream().filter(position1 -> positionAllie > position1.getX() - position.getX() + position1.getY() - position.getY()).toList();
            }

        }


        //Todo filtro de casillas cuando hay enemigos.


        return Stream.concat(salida.stream(), salida2.stream()).filter(Position::isValid).toList();
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
            if (isAllie || numAttackEnemies > 1) {
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
            if (isAllie || numAttackEnemies > 1) {
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
            if (isAllie || numAttackEnemies > 1) {
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
            if (isAllie || numAttackEnemies > 1) {
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

        for (Position position1 : positions) {
            boolean isAllie = false;
            for (Piece piece : pieceList) {
                if (piece.getColor().equals(color) && piece.getPosition().equals(position1)) {
                    isAllie = true;
                    positions.remove(position1);
                    break;
                }
            }
            if (isAllie) {
                break;
            }
        }
        return positions.stream().filter(Position::isValid)
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
