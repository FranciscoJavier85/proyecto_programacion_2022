/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Elvig
 */
public class Almacen {

    private ArrayList<Producto> listaProductos;

    public Almacen() {
        listaProductos = new ArrayList<>();
    }

    public void nuevoProducto( String descripcion, double precioUnitario,
            int unidades) {
        listaProductos.add(new Producto(construirRef(listaProductos.size()), descripcion, precioUnitario, unidades));
    }

    public String construirRef(int numero) {
        String referencia = "REF10" + numero;
        return referencia;
    }

    private boolean comprobarRef(String ref) {
        for (Producto producto : listaProductos) {
            if (ref.equals(producto.getRef())) {
                return true;
            }
        }
        return false;
    }

    public boolean añadirStock(String ref, int unidades) {
        if(comprobarRef(ref)){
            for (Producto producto : listaProductos) {
                producto.setUnidades(producto.getUnidades() + unidades);
                return true;
            }
        } 
        return false;
    }



public void listarAlmacen() {
        for (Producto producto : listaProductos) {
            if (producto.getUnidades() < 10) {
                System.out.println("\033[31m" + producto + "\033[30m");
            } else {
                System.out.println(producto);
            }
        }
    }

    public Producto buscarProdcuto(String ref) {
        for (Producto listaProducto : listaProductos) {
            if (ref.equals(listaProducto.getRef())) {
                return listaProducto;
            }
        }
        return null;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

}
