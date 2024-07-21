package org.dvd.ajedrez.model;

public class Output {
    private boolean isCorrect; // variable que indica que es correcto el movimeinto
    private int idPiece; // Se devuelve la ficha movida
    private String color; // Color de la ficha que movió
    private int idPieceAux; //Se indica cuando hay más de una ficha movida
    private int idPieceDeleted; // Se envía cuando se borrar alguna ficha
    private String winnerColor;// Si se gana alguien

    private boolean checkKing;// Si hubiera jaque se indicaría
    private String error; // indica si hay algún problema con el movimiento

    private boolean draw; // indicador si hay tablas
    private String information; // información de salida

    public void restart(int idPiece, String color) {
        this.idPiece = idPiece;
        this.color = color;
        idPieceAux = -1;
        idPieceDeleted = -1;
        winnerColor = null;
        checkKing = false;
        error = null;
        isCorrect = true;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isCheckKing() {
        return checkKing;
    }

    public void setCheckKing(boolean checkKing) {
        this.checkKing = checkKing;
    }

    public int getIdPiece() {
        return idPiece;
    }

    public void setIdPiece(int idPiece) {
        this.idPiece = idPiece;
    }

    public int getIdPieceAux() {
        return idPieceAux;
    }

    public void setIdPieceAux(int idPieceAux) {
        this.idPieceAux = idPieceAux;
    }

    public int getIdPieceDeleted() {
        return idPieceDeleted;
    }

    public void setIdPieceDeleted(int idPieceDeleted) {
        this.idPieceDeleted = idPieceDeleted;
    }

    public String getWinnerColor() {
        return winnerColor;
    }

    public void setWinnerColor(String winnerColor) {
        this.winnerColor = winnerColor;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Output{" +
                "idPiece=" + idPiece +
                ", color='" + color + '\'' +
                ", idPieceAux=" + idPieceAux +
                ", idPieceDeleted=" + idPieceDeleted +
                ", winnerColor='" + winnerColor + '\'' +
                ", checkKing=" + checkKing +
                ", error='" + error + '\'' +
                ", draw=" + draw + '\'' +
                ", information=" + information +
                '}';
    }
}
