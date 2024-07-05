package org.dvd.ajedrez.model;

import java.util.Objects;

public class Position {
    private int x;
    private int y;


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }

    public boolean isValid() {
        return x >= 0 && x <= 15 && y >= 0 && y <= 15;
    }

    public boolean isValid(int plusX, int plusY) {
        //Si sale fuera del tablero
        int newX = x + plusX;
        int newY = y + plusY;
        return newX >= 0 && newX <= 15 && newY >= 0 && newY <= 15;
    }

    public boolean compareToAndValid(Position newPosition, int plusX, int plusY) {
        if (isValid(plusX, plusY)) {
            return newPosition.equals(this);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}