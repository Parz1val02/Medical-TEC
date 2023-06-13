package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Boleta;
import com.example.medicaltec.Entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoletaRepository extends JpaRepository<Boleta,Integer> {

    @Query(value = "select * from boletas where cita_idcita=?1", nativeQuery = true)
    Boleta obtenerCitaxBoleta(Integer id);


    //paciente
    @Modifying
    @Query(value = "insert into boletas (conceptopago, monto, seguro_idseguro, receta_idreceta, cita_idcita) values (?1,?2,?3,?4,?5 )", nativeQuery = true)
    void crearBoletaCita(String concepto, Integer monto, int idseguro, Integer idreceta, int idcita);
}
