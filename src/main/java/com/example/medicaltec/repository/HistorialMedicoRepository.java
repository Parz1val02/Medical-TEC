package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Historialmedico;
//import com.example.medicaltec.dto.InsertSelectLastIdDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HistorialMedicoRepository extends JpaRepository<Historialmedico, Integer> {
    @Query(nativeQuery = true, value = "SELECT idhistorialmedico FROM telesystem_2.historialmedico where idhistorialmedico=?1")
    String verificaridHistorialMedico(String id);

    /* FASE DE PRUEBAS ADMIN PARA HISTORIAL POR DEFECTO NO TOCAR NI DESCOMENTAR
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO telesystem_2.historialmedico (validahistorial,seguros_id_seguro) VALUES (1,7)")
    void crearHistorialMedicoPorDefecto();

    @Query(nativeQuery = true, value = "SELECT LAST_INSERT_ID() as 'id'")
    InsertSelectLastIdDto crearHistorialMedicoDevolverID();  //USO OBLIGATORIO LUEGO DE HACER EL*/

}