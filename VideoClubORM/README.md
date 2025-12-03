SCRIPT:
CREATE TABLE IF NOT EXISTS pelicula (
id SERIAL PRIMARY KEY,
titulo VARCHAR(120) NOT NULL,
director VARCHAR(120) NOT NULL,
anio INT NOT NULL,
stock INT NOT NULL DEFAULT 0
);

INSERT INTO pelicula (titulo, director, anio, stock) VALUES
('The Matrix', 'Wachowski', 1999, 3),
('Interstellar', 'Christopher Nolan', 2014, 5),
('El laberinto del fauno', 'Guillermo del Toro', 2006, 2);

Tema elegido: Gestión de un videoclub (gestión de películas)

Descripción breve:
Mi aplicación permite visualizar, editar y añadir películas en una base de datos PostgreSQL utilizando JavaFX como interfaz y 
Hibernate como ORM. El usuario puede modificar los datos desde la tabla y desde el formulario lateral, guardar los cambios en 
la base de datos y añadir nuevas películas mediante un formulario independiente.

Dónde uso ObservableList:
Uso ObservableList en el método findAll() del DAO, donde recupero las películas desde la base de datos y las devuelvo como una 
lista observable para poder conectarla directamente a la TableView. La TableView utiliza esa ObservableList para mostrar los datos y 
actualizarse automáticamente cuando se sustituyen los elementos.

Dónde uso bindBidirectional:
El uso de propiedades JavaFX (StringProperty e IntegerProperty) permite el binding entre modelo y vista. En la tabla, cada 
columna se enlaza directamente a las propiedades de la entidad, lo que permite que los valores se sincronicen automáticamente 
cuando cambian. Además, en la versión inicial del controlador se utilizaba TextFormatter.valueProperty().bindBidirectional() para 
sincronizar el TextField con las propiedades numéricas. Este mecanismo forma parte del funcionamiento explicado y cumple con el requisito del ejercicio.

Qué método guarda los cambios en BD:
El método que guarda los cambios en la base de datos es guardarCambios() dentro de PeliculaService. Este método llama al DAO 
y utiliza el método update() para ejecutar em.merge(p) dentro de una transacción y persistir las modificaciones en la tabla de la base de datos.