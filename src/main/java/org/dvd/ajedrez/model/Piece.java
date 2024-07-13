package org.dvd.ajedrez.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    public boolean canPieceMove(Position newPosition) {
        if (newPosition.equals(position)) {
            return false;
        }
        switch (tipo) {
            case "T"://Torre
                Optional<Position> towerPosition = getTowerMoves().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (towerPosition.isPresent()) {
                    return true;
                }

                break;
            case "H"://Caballo
                Optional<Position> horsePosition = getHorseMoves().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                LOGGER.info("Posición {}", position);
                LOGGER.info("movimientos de caballo {}", getHorseMoves().toString());
                if (horsePosition.isPresent()) {
                    return true;
                }
                break;
            case "B"://Alfil
                Optional<Position> bishopMove = getBishopMoves().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (bishopMove.isPresent()) {
                    return true;
                }

                break;
            case "Q"://Reina
                Optional<Position> queenPosition = getQueenMoves().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (queenPosition.isPresent()) {
                    return true;
                }

                break;
            case "K"://Rey
                Optional<Position> kingPosition = getKingMoves().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (kingPosition.isPresent()) {
                    return true;
                }
                break;
            case "P"://Peon
                Optional<Position> pawnPosition = getPawnMoves().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (pawnPosition.isPresent()) {
                    return true;
                }
                break;
        }

        return false;
    }

    public List<Position> getKingMoves() {
        List<Position> positions = new LinkedList<>();

        positions.add(new Position(position.getX() + 1, position.getY()));
        positions.add(new Position(position.getX() - 1, position.getY()));
        positions.add(new Position(position.getX() + 1, position.getY() + 1));
        positions.add(new Position(position.getX(), position.getY() + 1));
        positions.add(new Position(position.getX() - 1, position.getY() + 1));
        positions.add(new Position(position.getX() + 1, position.getY() - 1));
        positions.add(new Position(position.getX() - 1, position.getY() - 1));
        positions.add(new Position(position.getX(), position.getY() - 1));


        return positions.stream().filter(Position::isValid).toList();
    }

    public List<Position> getPawnMoves() {
        List<Position> positions = new LinkedList<>();

        //Black
        if (color.equals("B")) {
            positions.add(new Position(position.getX(), position.getY() + 1));
            if (attack) {
                positions.add(new Position(position.getX() + 1, position.getY() + 1));
                positions.add(new Position(position.getX() - 1, position.getY() + 1));
            }
            if (fistMovement) {
                positions.add(new Position(position.getX(), position.getY() + 2));
            }
        }
        //White
        if (color.equals("W")) {
            positions.add(new Position(position.getX(), position.getY() - 1));
            if (attack) {
                positions.add(new Position(position.getX() + 1, position.getY() - 1));
                positions.add(new Position(position.getX() - 1, position.getY() - 1));
            }
            if (fistMovement) {
                positions.add(new Position(position.getX(), position.getY() - 2));

            }

        }

        return positions.stream().filter(Position::isValid).toList();
    }

    public List<Position> getBishopMoves() {
        List<Position> positions = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Math.abs(i - position.getX()) ==
                        Math.abs(j - position.getY())) {
                    positions.add(new Position(i, j));
                }
            }
        }
        return positions.stream().filter(Position::isValid).toList();
    }

    public List<Position> getQueenMoves() {
        List<Position> positions = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Math.abs(i - position.getX()) ==
                        Math.abs(j - position.getY())
                ) {
                    positions.add(new Position(i, j));
                } else {
                    if (i == position.getX()
                            || j == position.getY()) {
                        positions.add(new Position(i, j));
                    }
                }
            }
        }
        return positions.stream().filter(Position::isValid).toList();
    }

    public List<Position> getTowerMoves() {
        List<Position> positions = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == position.getX()
                        || j == position.getY()) {
                    positions.add(new Position(i, j));
                }
            }
        }

        return positions.stream().filter(Position::isValid).toList();

    }

    public List<Position> getHorseMoves() {
        List<Position> positions = new LinkedList<>();
        positions.add(new Position(position.getX() + 1, position.getY() + 2));
        positions.add(new Position(position.getX() - 1, position.getY() + 2));
        positions.add(new Position(position.getX() + 2, position.getY() - 1));
        positions.add(new Position(position.getX() + 2, position.getY() + 1));
        positions.add(new Position(position.getX() + 1, position.getY() - 2));
        positions.add(new Position(position.getX() - 1, position.getY() - 2));
        positions.add(new Position(position.getX() - 2, position.getY() + 1));
        positions.add(new Position(position.getX() - 2, position.getY() - 1));

        return positions.stream().filter(Position::isValid).toList();

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
