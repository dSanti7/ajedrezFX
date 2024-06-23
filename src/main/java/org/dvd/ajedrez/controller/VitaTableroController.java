package org.dvd.ajedrez.controller;

import javafx.fxml.FXML;

import java.awt.*;

public class VitaTableroController {
    @FXML
    Rectangle backgroundTablero;
    @FXML
    Rectangle tablero;
    @FXML
    Rectangle casilla;

    public void cambioTamañoTablero(){
        int size = (int)tablero.getSize().getHeight();
        //Definimos como cuadrado
        tablero.setSize(new Dimension(size,size));
    }
}
