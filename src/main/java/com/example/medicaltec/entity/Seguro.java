package com.example.medicaltec.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seguros")
public class Seguro {

        @Id
        @Column(name = "id_seguro", nullable = false)
        private Integer id;

        @Column(name = "nombre_seguro", nullable = false, length = 45)
        private String nombreSeguro;

        @Column(name = "porc_seguro", nullable = false, length = 45)
        private String porcSeguro;

        @Column(name = "porc_doctor", nullable = false, length = 45)
        private String porcDoctor;


}
