package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.FormAutoregistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormAutoregistroRepository extends JpaRepository<FormAutoregistro,Integer> {

    @Query(nativeQuery = true, value = "select dni from form_autoregistro")
    List<String> ListaDnis ();

}
