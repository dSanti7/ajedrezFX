package org.dvd.ajedrez.view;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoxView {

    private Rectangle boxSelected;
    private PieceView pieceView;
    private int x;
    private int y;
    private boolean isSelected;
   // private Color paint = new Color(0.13, 1.0, 0.246, 0.18);


    public PieceView getPieceView() {
        return pieceView;
    }

    public void setPieceView(PieceView pieceView) {
        this.pieceView = pieceView;
    }

    public Rectangle getBoxSelected() {
        return boxSelected;
    }

    public void setBoxSelected(Rectangle boxSelected) {
        this.boxSelected = boxSelected;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
