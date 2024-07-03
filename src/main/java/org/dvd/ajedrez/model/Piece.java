package org.dvd.ajedrez.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Piece {
    private int id;

    private Position position;
    private String tipo;
    private String color;
    private boolean isDead;

    public Piece(int id, int x, int y, String tipo, String color) {
        this.id = id;
        position = new Position(x, y);
        this.tipo = tipo;
        this.color = color;
        this.isDead = false;
    }

    public Piece() {

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
                if (newPosition.getX() == position.getX()
                        || newPosition.getY() == position.getY()) {
                    return true;
                }

                break;
            case "H"://Caballo

                if ((newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() + 3)
                        || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() + 3)
                        || (newPosition.getX() == position.getX() + 3 && newPosition.getY() == position.getY() - 1)
                        || (newPosition.getX() == position.getX() + 3 && newPosition.getY() == position.getY() + 1)
                        || (newPosition.getX() == position.getX() + 1 && newPosition.getY() == position.getY() - 3)
                        || (newPosition.getX() == position.getX() - 1 && newPosition.getY() == position.getY() - 3)
                        || (newPosition.getX() == position.getX() - 3 && newPosition.getY() == position.getY() + 1)
                        || (newPosition.getX() == position.getX() - 3 && newPosition.getY() == position.getY() - 1)
                ) {

                    return true;
                }
            case "B"://Alfil
                if (Math.abs(newPosition.getX() - position.getX()) ==
                        Math.abs(newPosition.getY() - position.getY())) {
                    return true;
                }

                break;
            case "Q"://Reina
                if (Math.abs(newPosition.getX() - position.getX()) ==
                        Math.abs(newPosition.getY() - position.getY())) {
                    return true;
                } else {
                    if (newPosition.getX() == position.getX()
                            || newPosition.getY() == position.getY()) {
                        return true;
                    }
                }
                break;
            case "K"://Rey
                Optional<Position> kingPosition = getPositionKing().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (kingPosition.isPresent()) {
                    return true;
                }
                break;
            case "P"://Peon
                Optional<Position> pawnPosition = getPositionPawn().stream()
                        .filter(position1 -> position1.equals(newPosition)).findAny();
                if (pawnPosition.isPresent()) {
                    return true;
                }
                break;
        }

        return false;
    }

    public List<Position> getPositionKing() {
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

    public List<Position> getPositionPawn() {
        List<Position> positions = new LinkedList<>();

        //Black
        if (color.equals("B")) {
            positions.add(new Position(position.getX(), position.getY() + 1));
            positions.add(new Position(position.getX() + 1, position.getY() + 1));
            positions.add(new Position(position.getX() - 1, position.getY() + 1));
        }
        //White
        if (color.equals("W")) {
            positions.add(new Position(position.getX(), position.getY() - 1));
            positions.add(new Position(position.getX() + 1, position.getY() - 1));
            positions.add(new Position(position.getX() - 1, position.getY() - 1));

        }

        return positions.stream().filter(Position::isValid).toList();
    }
}
