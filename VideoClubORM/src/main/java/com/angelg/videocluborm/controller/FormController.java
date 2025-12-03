package com.angelg.videocluborm.controller;

import com.angelg.videocluborm.SceneManager;
import com.angelg.videocluborm.model.entity.Pelicula;
import com.angelg.videocluborm.model.service.PeliculaService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormController {

    // Campos de texto del formulario para introducir los datos de la nueva película
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private TextField txtAnio;
    @FXML private TextField txtStock;

    // Etiqueta para mostrar mensajes de estado (errores o confirmación)
    @FXML private Label lblEstado;

    // Servicio que gestiona la lógica de validación y acceso a BD
    private final PeliculaService service = new PeliculaService();

    @FXML
    public void onGuardar() {
        try {
            // Se crea un objeto Pelicula vacío que rellenaremos con los datos del formulario
            Pelicula p = new Pelicula();
            p.setTitulo(txtTitulo.getText());
            p.setDirector(txtDirector.getText());

            // Se convierten los valores numéricos desde los TextField
            p.setAnio(Integer.parseInt(txtAnio.getText()));
            p.setStock(Integer.parseInt(txtStock.getText()));

            // Se intenta validar e insertar la película llamando al service
            // Si insertarNueva devuelve false, significa que no supera las validaciones
            if (!service.insertarNueva(p)) {
                lblEstado.setText("❌ Datos no válidos");
                return;
            }

            // Si todo va bien, volvemos a la vista principal
            SceneManager.switchTo("main-view.fxml");

        } catch (Exception e) {
            // Cualquier excepción (formato incorrecto, números no válidos, etc.)
            lblEstado.setText("❌ Error de formato");
        }
    }

    @FXML
    public void onCancelar() {
        // Si se pulsa cancelar, simplemente volvemos a la pantalla principal
        SceneManager.switchTo("main-view.fxml");
    }
}


