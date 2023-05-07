package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AlergiaRepository extends JpaRepository<Alergia,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into alergias (nombre) values (?1)", nativeQuery = true)
    void guardarAlergias(String nombre);




}
