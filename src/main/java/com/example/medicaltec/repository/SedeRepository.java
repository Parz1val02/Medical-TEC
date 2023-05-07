package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SedeRepository extends JpaRepository<Sede,Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update usuario set sedes_idsedes=?1 where dni=\"22647853\";" )
    void cambiarSede(String idSede);
}
