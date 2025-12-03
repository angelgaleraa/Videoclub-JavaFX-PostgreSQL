package com.angelg.videocluborm.model.entity;

import jakarta.persistence.*;
import javafx.beans.property.*;

@Entity                         // Marca la clase como entidad JPA (tabla en BD)
@Table(name = "pelicula")       // Nombre real de la tabla
public class Pelicula {

    // ================================================================
    //  CAMPOS REALES → Estos son los que Hibernate guarda directamente
    //  en la base de datos. Son los "campos persistentes".
    // ================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Autoincremental
    private int id;

    // Estos campos sí se guardan en la tabla "pelicula"
    private String titulo;
    private String director;
    private int anio;
    private int stock;

    // ================================================================
    //  PROPERTIES para JavaFX (NO se guardan en BD)
    //  Sirven únicamente para que TableView y bindBidirectional funcionen.
    //  Están marcadas con @Transient para que JPA las ignore.
    // ================================================================
    @Transient
    private final StringProperty tituloProperty = new SimpleStringProperty();

    @Transient
    private final StringProperty directorProperty = new SimpleStringProperty();

    @Transient
    private final IntegerProperty anioProperty = new SimpleIntegerProperty();

    @Transient
    private final IntegerProperty stockProperty = new SimpleIntegerProperty();

    // ================================================================
    //  CONSTRUCTORES
    // ================================================================
    public Pelicula() {
        // Constructor vacío obligatorio para Hibernate
    }

    public Pelicula(String titulo, String director, int anio, int stock) {
        // Constructor usado al crear nuevas películas
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.stock = stock;

        // Sincronizamos los valores iniciales con las Properties de JavaFX
        this.tituloProperty.set(titulo);
        this.directorProperty.set(director);
        this.anioProperty.set(anio);
        this.stockProperty.set(stock);
    }

    // ================================================================
    //  GETTERS NORMALES
    //  Hibernate utiliza estos para leer valores de la entidad.
    // ================================================================
    public int getId() {
        return id;  // Se devuelve tal cual desde BD
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public int getAnio() {
        return anio;
    }

    public int getStock() {
        return stock;
    }

    // ================================================================
    //  SETTERS NORMALES
    //  Actualizan el campo REAL y también la Property de JavaFX,
    //  para mantener sincronización en ambas direcciones.
    // ================================================================
    public void setTitulo(String titulo) {
        this.titulo = titulo;               // Se guarda en BD al hacer merge()
        this.tituloProperty.set(titulo);    // Se actualiza la Property (UI)
    }

    public void setDirector(String director) {
        this.director = director;
        this.directorProperty.set(director);
    }

    public void setAnio(int anio) {
        this.anio = anio;
        this.anioProperty.set(anio);
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.stockProperty.set(stock);
    }

    // ================================================================
    //  PROPERTIES PARA TABLAS Y FORMULARIOS JAVAFX
    //  Estas las usa la TableView y bindBidirectional.
    // ================================================================
    public IntegerProperty idProperty() {
        // Id no se puede bindear, así que devolvemos una Property nueva.
        return new SimpleIntegerProperty(id);
    }

    public StringProperty tituloProperty() {
        return tituloProperty;
    }

    public StringProperty directorProperty() {
        return directorProperty;
    }

    public IntegerProperty anioProperty() {
        return anioProperty;
    }

    public IntegerProperty stockProperty() {
        return stockProperty;
    }

    // ================================================================
    //  MÉTODO @PostLoad
    //  Se ejecuta automáticamente cada vez que Hibernate carga la entidad
    //  desde la BD. Sirve para sincronizar los campos reales con las
    //  Properties de JavaFX.
    //  Sin esto, al recargar datos las Properties quedarían desactualizadas.
    // ================================================================
    @PostLoad
    private void syncProperties() {
        tituloProperty.set(titulo);
        directorProperty.set(director);
        anioProperty.set(anio);
        stockProperty.set(stock);
    }
}




