/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Elvig
 */
public class Producto {
    private String ref;
    private String descripcion;
    private double precioUnitario;
    private int unidades;
    
    public Producto(String ref, String descripcion, double precioUnitario, 
            int unidades){
        this.ref = ref;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.unidades = unidades;
    }

    
    @Override
    public String toString(){
        return String.format("%s - %s - %.2f - %d", this.ref, this.descripcion,
                this.precioUnitario, this.unidades);
    }
    
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
    
    
    
    
    
    
       
}
