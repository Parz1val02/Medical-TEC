package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Deliverymedicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DeliverymedicamentoRepository extends JpaRepository<Deliverymedicamento, Integer> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into deliverymedicamentos (latitudactual , longitudactual, latitudfinal, longitudfinal , estado) values (?1, ?2, ?3, ?4, '?5')")
    void crearDelivery(Double latActual, Double longitudActual, Double longitudFinal, Double latFinal, String estado);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "Update deliverymedicamentos a set a.latitudactual= ?1, a.longitudactual= ?2 where iddeliverymedicamentos=?3")
    void guardarUbicacion(Float latitudActual, Float longitudActual, Integer id);

}