package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AlergiaRepository extends JpaRepository<Alergia,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into alergias (nombre, enabled) values (?1, 1)", nativeQuery = true)
    void guardarAlergias(String nombre);

    @Query(value = "SELECT LAST_INSERT_ID();", nativeQuery = true)
    Integer lastID();

    @Query(value = "SELECT * FROM telesystem.alergias where idalergias=?1 and enabled=1;", nativeQuery = true)
    Alergia obtenerAlergia(Integer id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update alergias set enabled=0 where idalergias=?1")
    void borrarAlergia(Integer id);



}
