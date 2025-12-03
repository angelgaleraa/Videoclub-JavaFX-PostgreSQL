package com.angelg.videocluborm.controller;

import com.angelg.videocluborm.SceneManager;
import com.angelg.videocluborm.model.entity.Pelicula;
import com.angelg.videocluborm.model.service.PeliculaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TextFormatter;

public class PeliculaController {

    // Tabla principal donde se muestran las películas
    @FXML private TableView<Pelicula> tabla;

    // Columnas de la tabla (cada una enlazada a una propiedad del modelo)
    @FXML private TableColumn<Pelicula, Integer> colId;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, Integer> colAnio;
    @FXML private TableColumn<Pelicula, Integer> colStock;

    // Campos del formulario de edición
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private TextField txtAnio;
    @FXML private TextField txtStock;

    // Campo para realizar búsquedas por director
    @FXML private TextField txtBuscarDirector;

    // Etiqueta para mostrar mensajes de estado
    @FXML private Label lblEstado;

    // Servicio que gestiona la lógica de BD y las validaciones
    private final PeliculaService service = new PeliculaService();

    // Conversor necesario para permitir edición de celdas numéricas
    private final IntegerStringConverter conv = new IntegerStringConverter();

    // TextFormatters para validar numéricos en los campos anio y stock
    private TextFormatter<Integer> fmtAnio, fmtStock;

    // Referencia a la película actualmente seleccionada
    private Pelicula seleccionada;

    @FXML
    public void initialize() {

        // Enlazar columnas con propiedades del modelo (lectura)
        colId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colTitulo.setCellValueFactory(d -> d.getValue().tituloProperty());
        colDirector.setCellValueFactory(d -> d.getValue().directorProperty());
        colAnio.setCellValueFactory(d -> d.getValue().anioProperty().asObject());
        colStock.setCellValueFactory(d -> d.getValue().stockProperty().asObject());

        // Permitir edición directa en tabla (edición)
        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirector.setCellFactory(TextFieldTableCell.forTableColumn());
        colAnio.setCellFactory(TextFieldTableCell.forTableColumn(conv));
        colStock.setCellFactory(TextFieldTableCell.forTableColumn(conv));

        // Hacer la tabla editable
        tabla.setEditable(true);

        // Cargar datos desde BD
        tabla.setItems(service.cargarPeliculas());

        // Actualizar modelo cuando se edita una celda
        colTitulo.setOnEditCommit(e -> e.getRowValue().setTitulo(e.getNewValue()));
        colDirector.setOnEditCommit(e -> e.getRowValue().setDirector(e.getNewValue()));
        colAnio.setOnEditCommit(e -> e.getRowValue().setAnio(e.getNewValue()));
        colStock.setOnEditCommit(e -> e.getRowValue().setStock(e.getNewValue()));

        // Formateadores para validar números en los campos del formulario
        fmtAnio = new TextFormatter<>(conv);
        fmtStock = new TextFormatter<>(conv);
        txtAnio.setTextFormatter(fmtAnio);
        txtStock.setTextFormatter(fmtStock);

        // Listener: cualquier cambio en los TextField se aplica a la película seleccionada
        txtTitulo.textProperty().addListener((o, oldVal, newVal) -> {
            if (seleccionada != null) seleccionada.setTitulo(newVal);
        });

        txtDirector.textProperty().addListener((o, oldVal, newVal) -> {
            if (seleccionada != null) seleccionada.setDirector(newVal);
        });

        fmtAnio.valueProperty().addListener((o, oldVal, newVal) -> {
            if (seleccionada != null && newVal != null) seleccionada.setAnio(newVal);
        });

        fmtStock.valueProperty().addListener((o, oldVal, newVal) -> {
            if (seleccionada != null && newVal != null) seleccionada.setStock(newVal);
        });

        // Listener: al seleccionar una fila, rellenamos el formulario
        tabla.getSelectionModel().selectedItemProperty().addListener((o, oldSel, sel) -> {
            seleccionada = sel;

            if (sel == null) {
                // Si no hay selección, limpiar formulario
                txtTitulo.clear();
                txtDirector.clear();
                fmtAnio.setValue(null);
                fmtStock.setValue(null);
                return;
            }

            // Mostrar datos de la película seleccionada en el formulario
            txtTitulo.setText(sel.getTitulo());
            txtDirector.setText(sel.getDirector());
            fmtAnio.setValue(sel.getAnio());
            fmtStock.setValue(sel.getStock());
        });
    }

    // CREATE — crear una nueva película rápida desde la tabla
    @FXML
    public void onNuevo() {
        Pelicula p = new Pelicula("Nueva película", "Director", 2000, 0);

        if (service.insertarNueva(p)) {
            tabla.setItems(service.cargarPeliculas());
            lblEstado.setText("Película creada");
        } else {
            lblEstado.setText(service.getUltimoError());
        }
    }

    // UPDATE — guardar cambios de la película seleccionada
    @FXML
    public void onGuardar() {
        if (seleccionada == null) {
            lblEstado.setText("Selecciona una película");
            return;
        }
        if (service.guardarCambios(seleccionada)) {
            lblEstado.setText("Cambios guardados");
        } else {
            lblEstado.setText(service.getUltimoError());
        }
    }

    // DELETE — eliminar la película seleccionada
    @FXML
    public void onEliminar() {
        if (seleccionada == null) {
            lblEstado.setText("Selecciona una película");
            return;
        }
        if (service.eliminar(seleccionada)) {
            tabla.setItems(service.cargarPeliculas());
            lblEstado.setText("Película eliminada");
        } else {
            lblEstado.setText("Error al eliminar");
        }
    }

    // READ — recargar todos los datos desde BD
    @FXML
    public void onRecargar() {
        tabla.setItems(service.cargarPeliculas());
        lblEstado.setText("Datos recargados");
    }

    // BÚSQUEDA — filtrar películas por director
    @FXML
    public void onBuscarDirector() {
        String dir = txtBuscarDirector.getText();
        tabla.setItems(service.buscarPorDirector(dir));
        lblEstado.setText("Búsqueda aplicada");
    }

    // Navegar a la ventana del formulario de creación
    @FXML
    public void onNueva() {
        SceneManager.switchTo("form-view.fxml");
    }
}




