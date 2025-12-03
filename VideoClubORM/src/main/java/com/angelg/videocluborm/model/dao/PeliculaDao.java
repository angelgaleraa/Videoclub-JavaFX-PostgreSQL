package com.angelg.videocluborm.model.dao;

import com.angelg.videocluborm.model.entity.Pelicula;
import com.angelg.videocluborm.model.util.JpaUtil;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PeliculaDao {

    // READ: obtiene todas las películas de la base de datos ordenadas por ID.
    // Devuelve una ObservableList para integrarse directamente con JavaFX.
    public ObservableList<Pelicula> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        List<Pelicula> list = em.createQuery(
                        "SELECT p FROM Pelicula p ORDER BY p.id", Pelicula.class)
                .getResultList();
        em.close();
        return FXCollections.observableArrayList(list);
    }

    // READ: obtiene una sola película por su clave primaria (id)
    public Pelicula find(int id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        Pelicula p = em.find(Pelicula.class, id);
        em.close();
        return p;
    }

    // CREATE: inserta una nueva película en la base de datos usando persist()
    public boolean insert(Pelicula p) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            em.getTransaction().begin(); // Inicia la transacción
            em.persist(p);               // Inserta la entidad
            em.getTransaction().commit(); // Confirma los cambios
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback(); // Revierte si hay error
            return false;
        } finally {
            em.close(); // Cierra siempre el EntityManager
        }
    }

    // UPDATE: actualiza una película existente utilizando merge()
    public boolean update(Pelicula p) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(p);                 // Merge sincroniza el estado nuevo con BD
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    // DELETE: elimina una película
    // Se usa merge() primero por si la entidad está detached
    public boolean delete(Pelicula p) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            Pelicula adjunta = em.merge(p);  // Se asegura de que la entidad esté gestionada
            em.remove(adjunta);              // La elimina de la BD
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    // Consulta JPQL: buscar películas por director usando LIKE
    // Retorna ObservableList para uso directo con TableView
    public ObservableList<Pelicula> buscarPorDirector(String director) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        List<Pelicula> list = em.createQuery(
                        "SELECT p FROM Pelicula p WHERE p.director LIKE :dir ORDER BY p.titulo",
                        Pelicula.class)
                .setParameter("dir", "%" + director + "%")  // Parámetro con comodines
                .getResultList();
        em.close();
        return FXCollections.observableArrayList(list);
    }

    // Consulta JPQL: buscar películas por título usando LIKE
    public ObservableList<Pelicula> buscarPorTitulo(String titulo) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        List<Pelicula> list = em.createQuery(
                        "SELECT p FROM Pelicula p WHERE p.titulo LIKE :tit ORDER BY p.titulo",
                        Pelicula.class)
                .setParameter("tit", "%" + titulo + "%")
                .getResultList();
        em.close();
        return FXCollections.observableArrayList(list);
    }
}



