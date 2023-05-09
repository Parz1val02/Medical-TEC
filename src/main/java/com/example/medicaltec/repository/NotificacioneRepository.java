package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Mensaje;
import com.example.medicaltec.Entity.Notificacione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificacioneRepository extends JpaRepository<Notificacione,Integer> {

    @Query(value = "SELECT * FROM telesystem.notificaciones order by idnotificaciones ASC;",
            nativeQuery = true)
    List<Notificacione> listarNotificacionesMasActuales();
}
