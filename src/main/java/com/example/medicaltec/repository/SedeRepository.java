package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SedeRepository extends JpaRepository<Sede, Integer> {

    @Query(value="SELECT * FROM telesystem.sedes s WHERE NOT s.idsedes=3", nativeQuery = true)
    List<Sede> sedesMenosActual();


}