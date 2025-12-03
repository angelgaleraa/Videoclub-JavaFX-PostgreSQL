package com.angelg.videocluborm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Clase encargada de gestionar el cambio de escenas (ventanas) en la aplicación.
 * Centraliza la carga de archivos FXML y facilita la navegación entre vistas.
 */
public class SceneManager {

    // Referencia estática al Stage principal de la aplicación.
    // Se asigna desde MainApplication al iniciar.
    private static Stage primaryStage;

    /**
     * Guarda el Stage principal para poder cambiar su escena más adelante.
     * @param stage Stage inicial que entrega MainApplication
     */
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Cambia la vista actual cargando un archivo FXML ubicado dentro de:
     * /com/angelg/videocluborm/
     *
     * Esta función permite navegar entre pantallas simplemente llamando:
     * SceneManager.switchTo("main-view.fxml");
     *
     * @param fxmlFile Nombre del archivo FXML a cargar
     */
    public static void switchTo(String fxmlFile) {
        try {
            // Busca el recurso FXML dentro del paquete del proyecto
            URL resource = SceneManager.class.getResource(fxmlFile);

            // Si no se encuentra, muestra error y no continúa
            if (resource == null) {
                System.out.println("❌ No se encontró el FXML: " + fxmlFile);
                return;
            }

            // Carga el archivo FXML
            Parent root = FXMLLoader.load(resource);

            // Reemplaza la escena actual por la nueva
            primaryStage.setScene(new Scene(root));

            // Muestra la ventana actualizada
            primaryStage.show();

        } catch (IOException e) {
            // Muestra el error si el FXML no pudo cargarse
            e.printStackTrace();
        }
    }
}




