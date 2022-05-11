/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Elvig
 */
public class Venta {

    private int numero;
    private ArrayList<Linea_venta> lineasVenta;
    private int iva;
    private Almacen miAlmacen;
    private LocalDate fecha;
    private Cliente cliente;
    private double total;

    public Venta(Almacen miAlmacen, LocalDate fecha, Cliente cliente) {

        this.iva = 21;
        this.lineasVenta = new ArrayList<>();
        this.miAlmacen = miAlmacen;
        this.cliente = cliente;
        this.fecha = fecha;

    }

    public Venta(int numero, Almacen miAlmacen, LocalDate fecha, Cliente cliente, double total) {
        this.numero = numero;
        this.iva = 21;
        this.lineasVenta = new ArrayList<>();
        this.miAlmacen = miAlmacen;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;


    }

    public void addLineaVenta(int num_linea,int cantidad, Producto p) {
        lineasVenta.add(new Linea_venta(num_linea, cantidad, p));
    }

    public void imprimirVenta() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nombre = cliente.getNombre();
        
        LocalDate fecha = this.fecha;
        System.out.println("******************");
        System.out.println("Venta " + this.numero + " Fecha " + fecha.format(dtf));
        System.out.println("Nombre " + nombre);
        System.out.println("DNI: "+cliente.getDni());
        
//        for (Linea_venta linea_venta : lineasVenta) {
//            linea_venta.imprimirLinea();
//        }
//        System.out.println("SubTotal: " + calcularSubtotal());
//        System.out.println("IVA: " + calcularIva());
        System.out.printf("Importe: %.2f\n",this.total);
        System.out.println("******************");
    }

    public double calcularTotal() {
        this.total = calcularSubtotal() + calcularIva();
        return total;
    }

    public double calcularIva() {
        double totalIva = 0;
        totalIva = (calcularSubtotal() * 21) / 100;
        return totalIva;
    }

    public double calcularSubtotal() {
        double subTotal = 0;
        for (Linea_venta linea_venta : lineasVenta) {
            subTotal = subTotal + linea_venta.getImporteLinea();
        }
        return subTotal;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Linea_venta> getLineasVenta() {
        return lineasVenta;
    }

    public void setLineasVenta(ArrayList<Linea_venta> lineasVenta) {
        this.lineasVenta = lineasVenta;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public Almacen getMiAlmacen() {
        return miAlmacen;
    }

    public void setMiAlmacen(Almacen miAlmacen) {
        this.miAlmacen = miAlmacen;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
