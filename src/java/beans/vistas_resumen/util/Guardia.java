/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.vistas_resumen.util;

import java.util.Date;

/**
 *
 * @author Rene
 */
public class Guardia {

    public Date fecha;
    public int cantidad = 0;
    public int cantidad_cumplieron = 0;
    public int cantidad_incumplieron = 0;
    public double porcentaje_cumplieron;
    public double porcentaje_incumplieron;
    public String porcentaje_cumplieron_str;
    public String porcentaje_incumplieron_str;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad_cumplieron() {
        return cantidad_cumplieron;
    }

    public void setCantidad_cumplieron(int cantidad_cumplieron) {
        this.cantidad_cumplieron = cantidad_cumplieron;
    }

    public int getCantidad_incumplieron() {
        return cantidad_incumplieron;
    }

    public void setCantidad_incumplieron(int cantidad_incumplieron) {
        this.cantidad_incumplieron = cantidad_incumplieron;
    }

    public double getPorcentaje_cumplieron() {
        return porcentaje_cumplieron;
    }

    public void setPorcentaje_cumplieron(double porcentaje_cumplieron) {
        this.porcentaje_cumplieron = porcentaje_cumplieron;
    }

    public double getPorcentaje_incumplieron() {
        return porcentaje_incumplieron;
    }

    public void setPorcentaje_incumplieron(double porcentaje_incumplieron) {
        this.porcentaje_incumplieron = porcentaje_incumplieron;
    }

    public String getPorcentaje_cumplieron_str() {
        return porcentaje_cumplieron_str;
    }

    public void setPorcentaje_cumplieron_str(String porcentaje_cumplieron_str) {
        this.porcentaje_cumplieron_str = porcentaje_cumplieron_str;
    }

    public String getPorcentaje_incumplieron_str() {
        return porcentaje_incumplieron_str;
    }

    public void setPorcentaje_incumplieron_str(String porcentaje_incumplieron_str) {
        this.porcentaje_incumplieron_str = porcentaje_incumplieron_str;
    }

}
