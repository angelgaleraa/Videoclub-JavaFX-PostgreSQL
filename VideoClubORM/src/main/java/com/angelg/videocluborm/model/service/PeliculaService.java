package com.angelg.videocluborm.model.service;

import com.angelg.videocluborm.model.dao.PeliculaDao;
import com.angelg.videocluborm.model.entity.Pelicula;
import javafx.collections.ObservableList;

public class PeliculaService {

    // Acceso al DAO (capa de datos). El Service actúa como intermediario entre UI y BD.
    private final PeliculaDao dao = new PeliculaDao();

    // Almacena el último error ocurrido en validaciones o en operaciones DAO.
    private String ultimoError;

    public String getUltimoError() {
        return ultimoError;
    }

    /**
     * Método privado que valida los datos de una Pelicula antes de insertarla o actualizarla.
     * Aquí se cumplen los requisitos del ejercicio: validaciones en el Service.
     */
    private boolean validar(Pelicula p) {
        if (p.getTitulo() == null || p.getTitulo().isBlank()) {
            ultimoError = "El título no puede estar vacío";
            return false;
        }
        if (p.getDirector() == null || p.getDirector().isBlank()) {
            ultimoError = "El director no puede estar vacío";
            return false;
        }
        if (p.getAnio() < 1895 || p.getAnio() > 2100) {
            // 1895 = nacimiento del cine
            ultimoError = "El año debe estar entre 1895 y 2100";
            return false;
        }
        if (p.getStock() < 0) {
            ultimoError = "El stock no puede ser negativo";
            return false;
        }

        // Si todo es correcto, limpiamos errores
        ultimoError = null;
        return true;
    }

    // ----------- READ (SELECT) ------------------

    /**
     * Devuelve todas las películas. Llama directamente al DAO,
     * pero el Service es quien decide qué exponer al exterior.
     */
    public ObservableList<Pelicula> cargarPeliculas() {
        return dao.findAll();
    }

    // ----------- CREATE (INSERT) ------------------

    /**
     * Inserta una nueva película en BD.
     * Primero valida (requisito del ejercicio) y después llama al DAO.
     */
    public boolean insertarNueva(Pelicula p) {
        if (!validar(p)) return false;
        return dao.insert(p);
    }

    // ----------- UPDATE ------------------

    /**
     * Guarda los cambios de una película existente.
     * Igual que insertar: primero validamos, luego mandamos al DAO.
     */
    public boolean guardarCambios(Pelicula p) {
        if (!validar(p)) return false;
        return dao.update(p);
    }

    // ----------- DELETE ------------------

    /**
     * Elimina una película usando el DAO.
     * No necesita validación porque es una operación directa.
     */
    public boolean eliminar(Pelicula p) {
        return dao.delete(p);
    }

    // ----------- BÚSQUEDAS ------------------

    /**
     * Busca películas por director.
     * Si el usuario no escribe nada, se devuelven todas.
     */
    public ObservableList<Pelicula> buscarPorDirector(String dir) {
        if (dir == null || dir.isBlank()) {
            return cargarPeliculas();
        }
        return dao.buscarPorDirector(dir);
    }

    /**
     * Busca películas por título.
     * Igual que buscarPorDirector.
     */
    public ObservableList<Pelicula> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return cargarPeliculas();
        }
        return dao.buscarPorTitulo(titulo);
    }
}




