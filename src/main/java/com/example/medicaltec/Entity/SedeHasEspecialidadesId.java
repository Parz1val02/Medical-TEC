package com.example.medicaltec.Entity;

import jakarta.persistence.Column;

import java.io.Serializable;

public class SedeHasEspecialidadesId implements Serializable {

    @Column(name = "sedes_idsedes", nullable = false)
    private Integer sedesIdSedes;

    @Column(name = "especialidades_id_especialidad", nullable = false)
    private Integer especialidadesIdEspecialidades;


    public Integer getSedesIdSedes() {
        return sedesIdSedes;
    }

    public void setSedesIdSedes(Integer sedesIdSedes) {
        this.sedesIdSedes = sedesIdSedes;
    }

    public Integer getEspecialidadesIdEspecialidades() {
        return especialidadesIdEspecialidades;
    }

    public void setEspecialidadesIdEspecialidades(Integer especialidadesIdEspecialidades) {
        this.especialidadesIdEspecialidades = especialidadesIdEspecialidades;
    }
}
