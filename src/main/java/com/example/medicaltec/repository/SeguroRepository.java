package com.example.medicaltec.repository;
import com.example.medicaltec.Entity.Seguro;
import com.example.medicaltec.dto.Seguro1Dto;
import com.example.medicaltec.dto.SeguroDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeguroRepository extends JpaRepository<Seguro,Integer> {

    @Query(nativeQuery = true,value="select * from seguros where nombre_seguro = ?1")
    Seguro obtenerSegurobyName (String seguroName);

    @Query(nativeQuery = true, value ="SELECT id_seguro FROM telesystem_2.seguros where id_seguro=?1")
    String verificaridSeguro(String id);

    @Query(nativeQuery = true, value ="SELECT S.id_seguro as `Id`, S.nombre_seguro as `Nombre` FROM usuario U\n" +
            "inner join seguros S on U.seguros_id_seguro=S.id_seguro where U.dni=?1")
    SeguroDto getSeguro(String id);

    @Query(nativeQuery = true, value="SELECT id_seguro as `Id`, porc_seguro as `Porcentaje` FROM telesystem_2.seguros")
    List<Seguro1Dto> lista();
}
