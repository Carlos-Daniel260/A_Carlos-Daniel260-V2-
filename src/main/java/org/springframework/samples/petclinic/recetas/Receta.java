package org.springframework.samples.petclinic.recetas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import javax.persistence.Column;
import org.springframework.samples.petclinic.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Positive;
import org.springframework.samples.petclinic.citas.Citas;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "recetas")
public class Receta extends BaseEntity {

    @Column(name = "medicamento")
    private String medicamento;
    @Column(name = "dosis")
    private String dosis;
    @Column(name = "frecuencia")
    private int frecuencia;
    @Column(name = "fecha")
    private String fecha;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    private Citas cita;

    public Receta() {
    }

    public Receta(String medicamento, String dosis, int frecuencia, String fecha, Citas cita) {
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.fecha = fecha;
        this.cita = cita;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Citas getCita() {
        return cita;
    }

    public void setCita(Citas cita) {
        this.cita = cita;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

}
