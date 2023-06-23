package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.SedeHasEspecialidades;
import com.example.medicaltec.Entity.SedeHasEspecialidadesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SedeHasEspecialidadeRepository extends JpaRepository<SedeHasEspecialidades, SedeHasEspecialidadesId> {

    @Query(nativeQuery = true, value = "SELECT especialidades_id_especialidad FROM sedes_has_especialidades where sedes_idsedes=?1")
    List<Integer> listarEspecialidadesPorId(Integer id);
}
