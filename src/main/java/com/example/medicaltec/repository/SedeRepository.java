package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Sede;
import com.example.medicaltec.dto.Sede1Dto;
import com.example.medicaltec.dto.SedeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface SedeRepository extends JpaRepository<Sede,Integer> {
    @Query(nativeQuery = true, value = "SELECT idsedes FROM telesystem_2.sedes where idsedes=?1")
    String verificaridSede(String id);
    @Query(nativeQuery = true, value = "SELECT S.idsedes as `Id`, S.nombre as `Nombre` FROM usuario U\n" +
            "inner join sedes S on U.sedes_idsedes=S.idsedes where U.dni=?1")
    SedeDto getSede(String dni);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update usuario set  sedes_idsedes=?1 where dni=?2" )
    void cambiarSede(String idSede, String dni);
    @Query(value="SELECT * FROM telesystem_2.sedes s WHERE NOT s.idsedes=?1", nativeQuery = true)
    List<Sede> sedesMenosActual(int sede_actual);
    @Query(nativeQuery = true, value = "SELECT idsedes,nombre,latitud,longitud FROM telesystem_2.sedes")
    List<Sede1Dto> sedeMapa();
}
