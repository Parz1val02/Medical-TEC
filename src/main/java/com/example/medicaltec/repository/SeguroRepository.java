package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Seguro;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepository extends JpaRepository<Seguro,Integer> {
}
