
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elvig
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
            if (Bd_almacen.conectar() == null) {
            System.out.println("\033[31mERROR");
        } else {
            System.out.println("\033[32mOK\033[30m");
        }
        
     
        Almacen miAlmacen = new Almacen();
        LocalDate fecha = LocalDate.now();
        Cliente cliente = new Cliente (1,"manolo","aguilas","111A" );
//        Venta v = new Venta(miAlmacen,fecha, cliente);
//
//        Producto p = new Producto("REF100","pipas",0.5,100);
//    
//        v.addLineaVenta(3, p);
//        
////        v.imprimirVenta();
//        System.out.printf("Total: %.2f\n",v.getTotal());
////        venta1.addLineaVenta(4, p);
//                venta1.imprimirVenta();
//        System.out.printf("Total: %.2f\n",venta1.getTotal());
//        
        ArrayList<Venta>misVentas = new ArrayList<>();
        misVentas = Bd_almacen.getVentasSql(1);
        
        for (Venta venta : misVentas) {
            venta.imprimirVenta();
        }
        
        
    }
    
}
