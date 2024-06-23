package org.dvd.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.awt.*;


public class ViewBoardController {
    @FXML
    Rectangle backgroundTablero;
    @FXML
    Rectangle tablero;
    @FXML
    Rectangle casilla;
    @FXML
    Group groupTablero;

    public void changeSizeTablero() {
        int size = (int) tablero.getHeight();
        //Definimos como cuadrado
        tablero.setWidth(size);
    }
}
