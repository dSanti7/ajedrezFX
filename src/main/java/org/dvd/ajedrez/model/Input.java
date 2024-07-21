package org.dvd.ajedrez.model;


public class Input {
private int idPiece;
private Position actualPosition;

private Position newPosition;

    public int getIdPiece() {
        return idPiece;
    }

    public void setIdPiece(int idPiece) {
        this.idPiece = idPiece;
    }

    public Position getActualPosition() {
        return actualPosition;
    }

    public void setActualPosition(Position actualPosition) {
        this.actualPosition = actualPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    @Override
    public String toString() {
        return "Input{" +
                "idPiece=" + idPiece +
                ", actualPosition=" + actualPosition +
                ", newPosition=" + newPosition +
                '}';
    }
}
