package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlergiaRepository extends JpaRepository<Alergia, Integer> {

    @Query(value = "SELECT * FROM telesystem.alergias where idalergias=?1 and enabled=1;", nativeQuery = true)
    Alergia obtenerAlergia(Integer id);
}