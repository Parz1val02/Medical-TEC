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
    @Query(nativeQuery = true, value = "SELECT idsedes as `Id`, nombre as `Nombre` FROM sedes where idsedes=?1")
    SedeDto getSedeId(String id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update usuario set  sedes_idsedes=?1 where dni=?2" )
    void cambiarSede(String idSede, String dni);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update sedes set zona_horaria=?1" )
    void cambiarZonaHoraria(String zonaHoraria);
    @Query(value="SELECT * FROM telesystem_2.sedes s WHERE NOT s.idsedes=?1", nativeQuery = true)
    List<Sede> sedesMenosActual(int sede_actual);
    @Query(nativeQuery = true, value = "SELECT idsedes,nombre,latitud,longitud FROM telesystem_2.sedes")
    List<Sede1Dto> sedeMapa();
    @Query(nativeQuery = true, value = "SELECT idsedes as `Idsedes`,nombre as `Nombre`,latitud as `Latitud`,longitud as `Longitud` FROM telesystem_2.sedes where idsedes=?1")
    Sede1Dto sedeA(Integer idsede);
}
