/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Elvig
 */
public class Linea_venta {
    private int num_linea;
    private Producto producto;
    private int cantidad;
    private double importeLinea;
    
    
    public Linea_venta(int num_linea,int cantidad, Producto p){
        this.num_linea = num_linea;
        this.producto = p;
        this.cantidad = cantidad;
        this.importeLinea = cantidad*p.getPrecioUnitario();
    }
    
    
    public void imprimirLinea(){
        System.out.printf("%-10s  %d  %.2f\n",producto.getDescripcion(),cantidad,
               this.importeLinea);
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporteLinea() {
        return importeLinea;
    }

    public void setImporteLinea(double importeLinea) {
        this.importeLinea = importeLinea;
    }

    public int getNum_linea() {
        return num_linea;
    }

    public void setNum_linea(int num_linea) {
        this.num_linea = num_linea;
    }
    
    
}
