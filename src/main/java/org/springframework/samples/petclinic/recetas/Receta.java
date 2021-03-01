package org.springframework.samples.petclinic.recetas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Column;
import org.springframework.samples.petclinic.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "recetas")
public class Receta extends BaseEntity {

    @Column(name = "medicamento")
    @NotEmpty
    private String medicamento;
    @Column(name = "dosis")
    @NotEmpty
    private String dosis;
    @Column(name = "frecuencia")
    @NotEmpty
    private String frecuencia;
    @Column(name = "fecha")
    @NotEmpty
    private String fecha;
    @Column(name = "numeroreceta")
    @NotEmpty
    private String numeroreceta;
    @Column(name = "cita")
    private int cita;

    public Receta() {
    }

    public Receta(String medicamento, String dosis, String frecuencia, String fecha, String numeroReceta, int cita) {
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.fecha = fecha;
        this.numeroreceta = numeroReceta;
        this.cita = cita;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumeroreceta() {
        return numeroreceta;
    }

    public void setNumeroreceta(String numeroReceta) {
        this.numeroreceta = numeroReceta;
    }

    public int getCita() {
        return cita;
    }

    public void setCita(int cita) {
        this.cita = cita;
    }
}
