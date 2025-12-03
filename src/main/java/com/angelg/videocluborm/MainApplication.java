package com.angelg.videocluborm;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {

        // Guardamos la ventana principal (Stage) dentro del SceneManager.
        // Esto permite que cualquier parte de la aplicación pueda cambiar la vista actual.
        SceneManager.setStage(stage);

        // Cargamos la vista principal de la aplicación.
        // "main-view.fxml" es el archivo raíz donde está la tabla de películas y el menú.
        SceneManager.switchTo("main-view.fxml");
    }

    public static void main(String[] args) {
        // Método estándar para lanzar aplicaciones JavaFX.
        launch();
    }
}




