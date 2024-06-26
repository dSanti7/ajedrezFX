package org.dvd.ajedrez.model;

public class Piece {
    private double x;
    private double y;
    private String tipo;

    public Piece(double x, double y, String tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
