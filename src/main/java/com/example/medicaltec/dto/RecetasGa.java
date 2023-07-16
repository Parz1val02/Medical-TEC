package com.example.medicaltec.dto;

import com.example.medicaltec.Entity.Medicamento;
import com.example.medicaltec.Entity.Receta;
import com.example.medicaltec.Entity.RecetaHasMedicamento;

import java.util.List;

public class RecetasGa {
    private Boolean recetaPagada;
    private Receta receta;
    private Double precioTotal;
    private List<RecetaHasMedicamento> medicamentos;

    public List<RecetaHasMedicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<RecetaHasMedicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Boolean getRecetaPagada() {
        return recetaPagada;
    }

    public void setRecetaPagada(Boolean recetaPagada) {
        this.recetaPagada = recetaPagada;
    }
}
