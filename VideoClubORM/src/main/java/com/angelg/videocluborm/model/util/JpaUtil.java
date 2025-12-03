package com.angelg.videocluborm.model.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    // EntityManagerFactory es el objeto principal que gestiona la conexión
    // con la base de datos en JPA/Hibernate.
    // Aquí se crea UNA ÚNICA instancia estática (patrón Singleton).
    // "videoclubPU" es el nombre de la unidad de persistencia definida
    // en el archivo META-INF/persistence.xml.
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("videoclubPU");

    // Método público para que el resto de la aplicación obtenga el EMF.
    // Con este método, los DAOs pueden pedir EntityManager cuando lo necesiten.
    // No se crea más de un EMF: se reutiliza el mismo durante toda la aplicación.
    public static EntityManagerFactory getEmf() {
        return emf;
    }
}


