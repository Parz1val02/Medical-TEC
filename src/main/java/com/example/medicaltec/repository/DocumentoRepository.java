package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
}