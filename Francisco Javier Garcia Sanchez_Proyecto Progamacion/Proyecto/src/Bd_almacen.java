/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elvig
 */
public class Bd_almacen {

    private static final String URL = "jdbc:mariadb://localhost:3306/almacen";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection conexion;

    public Bd_almacen() {
    }

    public static Connection conectar() {

        //***********************************************************
        //    CARGAMOS EL DRIVER
        //***********************************************************
        try {

            Class.forName("org.mariadb.jdbc.Driver").newInstance();
            //System.out.println("Driver <org.mariadb.jdbc.Driver> cargado.");

        } catch (Exception ex) {
            System.out.println("Error, no se ha podido cargar MariaDB JDBC Driver");
        }

        try {
            //***********************************************************
            //    NOS CONECTAMOS A LA BASE DE DATOS
            //***********************************************************

            conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return conexion;

        } catch (SQLException ex) {
            //System.out.println(ex.getMessage());
            return null;
        }

    }

    public static void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    public static ArrayList<Venta> getVentasSql(int id) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            ArrayList<Venta> listaVentas = new ArrayList();

            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ventas WHERE codigo_cliente = "+id);

            while (rs.next()) {
                int id_venta = rs.getInt("id_venta");
                Cliente cliente = getClienteSql(id);
                Almacen miAlmacen = new Almacen ();
                
                String fechaAux = rs.getString("fecha_venta");
                LocalDate fecha = LocalDate.parse(fechaAux,dtf);
                double total = rs.getDouble("total");
 
                listaVentas.add(new Venta(id_venta, miAlmacen, fecha,cliente,total));
            }

            rs.close();
            statement.close();
            return listaVentas;

        } catch (SQLException ex) {
            System.out.println("ERROR: sql excepcion");
            System.out.println(ex);
            return null;
        }

    }
    
    
        public static ArrayList<Venta> getVentasEmpleadoSql() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            ArrayList<Venta> listaVentas = new ArrayList();

            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ventas ");

            while (rs.next()) {
                int id_venta = rs.getInt("id_venta");
                int codigo_cliente = rs.getInt("codigo_cliente");
                
                String fechaAux = rs.getString("fecha_venta");
                LocalDate fecha = LocalDate.parse(fechaAux,dtf);
                double total = rs.getDouble("total");
                
                System.out.printf("Nº venta: %d - %s\n",id_venta,fecha.format(dtf1));
                System.out.printf("codigo cliente: %d ",codigo_cliente);
                System.out.printf("Total: %.2f€"+total);
             
            }

            rs.close();
            statement.close();
            return listaVentas;

        } catch (SQLException ex) {
            System.out.println("ERROR: sql excepcion");
            System.out.println(ex);
            return null;
        }

    }
    
    
    public static ArrayList<Producto> getProductoSql() {
        try {
            ArrayList<Producto> listaProductos = new ArrayList();

            Statement statement = conexion.createStatement();

            ResultSet rs = statement.executeQuery("SELECT referencia, nombre, precio, cantidad  FROM producto");

            while (rs.next()) {
                String ref = rs.getString("referencia");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");

                listaProductos.add(new Producto(ref, nombre, precio, cantidad));
            }

            rs.close();
            statement.close();
            return listaProductos;

        } catch (SQLException ex) {
            System.out.println("ERROR: sql excepcion");
            System.out.println(ex);
            return null;
        }

    }

    public static Cliente getClienteSql(int codigo_cliente) {
        try {
            Statement statement = conexion.createStatement();
            Cliente cliente = null;

            ResultSet rs = statement.executeQuery("SELECT codigo_cliente, nombre, ciudad, dni FROM cliente WHERE codigo_cliente =  " + codigo_cliente);
            while (rs.next()) {
                codigo_cliente = rs.getInt("codigo_cliente");
                String nombre = rs.getString("nombre");
                String ciudad = rs.getString("ciudad");
                String dni = rs.getString("dni");

                cliente = new Cliente(codigo_cliente, nombre, ciudad, dni);
            }

            rs.close();
            statement.close();
            return cliente;

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void actualizarStockSql(String referencia_producto, int cantidad) {

        try {

            Statement statement = conexion.createStatement();
            String contacto = String.format("UPDATE producto SET cantidad = cantidad + %d WHERE referencia = \"%s\"", cantidad, referencia_producto);
            statement.executeUpdate(contacto);
            statement.close();
            //conexion.close();    

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void descontarVentasSql(String referencia_producto, int cantidad) {

        try {

            Statement statement = conexion.createStatement();
            String contacto = String.format("UPDATE producto SET cantidad = cantidad - %d WHERE referencia = \"%s\"", cantidad, referencia_producto);
            statement.executeUpdate(contacto);
            statement.close();
            //conexion.close();    

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean loginEmpleadoSql(int codigo_empleado, String contraseña_a_comprobar) {
        String password = "";
        try {
            Statement statement = conexion.createStatement();

            ResultSet rs = statement.executeQuery("SELECT contraseña FROM empleado WHERE codigo_empleado =  " + codigo_empleado);
            while (rs.next()) {
                password = rs.getString("contraseña");
            }
            if (!password.equals(contraseña_a_comprobar)) {
                return false;
            }
            rs.close();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static boolean loginClienteSql(int codigo_cliente, String contraseña_a_comprobar) {
        String password = "";
        try {
            Statement statement = conexion.createStatement();

            ResultSet rs = statement.executeQuery("SELECT contraseña FROM cliente WHERE codigo_cliente =  " + codigo_cliente);
            while (rs.next()) {
                password = rs.getString("contraseña");
            }
            if (!password.equals(contraseña_a_comprobar)) {
                return false;
            }
            rs.close();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static void añadirProductoSql(Producto p) {
        try {

            Statement statement = conexion.createStatement();
            String ref = p.getRef();
            String desc = p.getDescripcion();
            double precioUnitario = p.getPrecioUnitario();
            int unidades = p.getUnidades();

            String producto = String.format("INSERT INTO producto VALUES(");
            producto += "\"" + ref + "\"" + ", ";
            producto += "\"" + desc + "\"" + ", ";
            producto += precioUnitario + ", ";
            producto += unidades + " )";
            statement.executeUpdate(producto);
            statement.close();
            //conexion.close();    

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void añadirVentaSql(Venta v) {
        try {

            Statement statement = conexion.createStatement();
    
            int codigo_cliente = v.getCliente().getCodigo_cliente();
            LocalDate fecha = v.getFecha();
            double total = v.calcularTotal();
            String venta = String.format("INSERT INTO ventas (codigo_cliente, fecha_venta, total) VALUES ("); 
            venta +=  codigo_cliente  + ", ";
            venta += "\"" + fecha + "\", ";
            venta += total +  " )";
            statement.executeUpdate(venta);
            statement.close();
            //conexion.close();    

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    


    public static void borrarProductoSqul(Producto p) {
        try {
            String ref = p.getRef();
            Statement statement = conexion.createStatement();
            String producto = String.format("DELETE FROM producto WHERE referencia =  \""+ref+"\"");
            System.out.println(ref);
            statement.executeUpdate(producto);
            statement.close();
            //conexion.close();    

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void añadirClienteSql(Cliente c) {
        try {

            Statement statement = conexion.createStatement();

            String producto = String.format("INSERT INTO cliente ( nombre, ciudad, dni, contraseña)  VALUES(\"%s\", \"%s\",\"%s\", \"%s\")", c.getNombre(), c.getCiudad(), c.getDni(), c.getContraseña());
            statement.executeUpdate(producto);
            statement.close();
            //conexion.close();    

        } catch (SQLException ex) {
            Logger.getLogger(Ejercicio05.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
