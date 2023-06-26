package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.UxUi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UxUiRepository extends JpaRepository<UxUi, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update ux_ui set logo=?1, logonombre=?2, logocontenttype=?3 where id=5")
    void cambiarLogo(byte[] logo, String nombre, String content);
}