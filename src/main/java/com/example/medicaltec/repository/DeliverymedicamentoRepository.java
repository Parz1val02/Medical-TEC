package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Deliverymedicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DeliverymedicamentoRepository extends JpaRepository<Deliverymedicamento, Integer> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into deliverymedicamentos (latitudinicial , longitudinicial, latitudfinal, longitudfinal , estado) values (?1, ?2, ?3, ?4, '?5')")
    void crearDelivery(Double latInicial, Double longitudInicial, Double longitudFinal, Double latFinal, String estado);

}