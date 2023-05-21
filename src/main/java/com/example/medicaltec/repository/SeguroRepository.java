package com.example.medicaltec.repository;
import com.example.medicaltec.Entity.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeguroRepository extends JpaRepository<Seguro,Integer> {
    @Query(nativeQuery = true, value ="SELECT id_seguro FROM telesystem.seguros where id_seguro=?1")
    String verificaridSeguro(String id);

}
