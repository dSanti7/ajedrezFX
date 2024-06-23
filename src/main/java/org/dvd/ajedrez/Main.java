package org.dvd.ajedrez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.dvd.ajedrez.controller.ViewBoardController;

import java.io.IOException;

import static org.dvd.ajedrez.utilitis.Paths.VIEW_BOARD;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_BOARD));
        Parent parent = loader.load();

        ViewBoardController controller = loader.getController();
        // Modificar el color del Rectangle
        controller.changeSizeTablero();
        controller.putBoxes();

        Scene scene = new Scene(new StackPane(parent), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}