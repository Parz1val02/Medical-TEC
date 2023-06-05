package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Mensaje;
import com.example.medicaltec.Entity.Notificacione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificacioneRepository extends JpaRepository<Notificacione,Integer> {

    @Query(value = "SELECT * FROM telesystem_2.notificaciones WHERE usuario_dni=?1 order by fecha DESC, hora DESC;",
            nativeQuery = true)
    List<Notificacione> listarNotisActualesSegunUsuario(String usuario_dni);
}
