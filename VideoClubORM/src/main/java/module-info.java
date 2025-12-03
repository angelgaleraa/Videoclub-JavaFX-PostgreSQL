module com.angelg.videocluborm {

    requires javafx.controls;
    requires javafx.fxml;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens com.angelg.videocluborm to javafx.fxml;
    opens com.angelg.videocluborm.controller to javafx.fxml;
    opens com.angelg.videocluborm.model.entity to org.hibernate.orm.core;

    exports com.angelg.videocluborm;
    exports com.angelg.videocluborm.controller;
}

