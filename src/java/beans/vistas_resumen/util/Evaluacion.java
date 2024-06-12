
package beans.vistas_resumen.util;

public class Evaluacion {

    public String nombre;
    public String nombre_completo;
    public int cantidad;
    public double porcentaje;
    public String porcentaje_str;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getPorcentaje_str() {
        return porcentaje_str;
    }

    public void setPorcentaje_str(String porcentaje_str) {
        this.porcentaje_str = porcentaje_str;
    }

}
