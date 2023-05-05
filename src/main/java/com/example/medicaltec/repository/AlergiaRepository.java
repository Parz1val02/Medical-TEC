package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlergiaRepository extends JpaRepository<Alergia, Integer> {
}