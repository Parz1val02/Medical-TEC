package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface SedeRepository extends JpaRepository<Sede,Integer> {
    @Query(nativeQuery = true, value = "SELECT idsedes FROM telesystem.sedes where idsedes=?1")
    String verificaridSede(String id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update usuario set  sedes_idsedes=?1 where dni=?2;" )
    void cambiarSede(String idSede, String dni);
    @Query(value="SELECT * FROM telesystem.sedes s WHERE NOT s.idsedes=?1", nativeQuery = true)
    List<Sede> sedesMenosActual(int sede_actual);
}
