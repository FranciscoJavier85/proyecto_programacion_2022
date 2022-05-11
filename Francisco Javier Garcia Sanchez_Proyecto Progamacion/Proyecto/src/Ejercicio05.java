/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Elvig
 */
public class Ejercicio05 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s = new Scanner(System.in);

        if (Bd_almacen.conectar() == null) {
            System.out.println("\033[31mERROR");
        } else {
            System.out.println("\033[32mOK\033[30m");
        }
        Almacen miAlmacen = new Almacen();
        miAlmacen.setListaProductos(Bd_almacen.getProductoSql());
        ArrayList<Venta> misVentas = new ArrayList<>();

        int id_empleado = 0;
        int id_cliente = 0;
        String password = "";
        int opcion = 0;
        Cliente clienteAux = null;

        do {
            try {
                System.out.println("Ferretería Águilas - TPV");
                System.out.println("**************************");
                System.out.println("1. Listar los productos");
                System.out.println("2. Registrarte como nuevo Cliente");
                System.out.println("3. Ingresa como Cliente");
                System.out.println("4. Ingresa como Empleado");
                System.out.println("5. Salir");
                System.out.println("Elige opción> ");
                opcion = Integer.parseInt(s.nextLine());
            } catch (Exception e) {
                System.out.println("Error: Solo acepta números");
            }
            switch (opcion) {
                case 1:
                    System.out.println("\033[33m-------PRODUCTOS-------");
                    System.out.println("***********************\033[30m");
                    miAlmacen.listarAlmacen();
                    System.out.println("");
                    break;

                case 2:
                    menuNuevoUsuario();
                    break;

                case 3:
                    try {

                    System.out.println("Introduce tu id: ");
                    id_cliente = Integer.parseInt(s.nextLine());

                    System.out.println("Introduce tu contraseña:");
                    password = s.nextLine();

                } catch (Exception e) {
                    System.out.println("Error: Datos introducidos erroneos");
                }
                boolean comprobar = Bd_almacen.loginClienteSql(id_cliente, password);

                if (comprobar) {
                    clienteAux = Bd_almacen.getClienteSql(id_cliente);
                    menuCliente(miAlmacen, clienteAux, misVentas);
                } else {
                    System.out.println("Los datos no son correctos");
                    System.out.println("");
                }
                break;

                case 4:
                    try {

                    System.out.println("Introduce tu id: ");
                    id_empleado = Integer.parseInt(s.nextLine());

                    System.out.println("Introduce tu contraseña:");
                    password = s.nextLine();

                } catch (Exception e) {
                    System.out.println("Error: Datos introducidos erroneos");
                }
                comprobar = Bd_almacen.loginEmpleadoSql(id_empleado, password);

                if (comprobar) {
                    menuEmpleado(miAlmacen, misVentas);
                } else {
                    System.out.println("Los datos no son correctos");
                }

                break;
                case 5:
                    System.out.println("Cerrando");
                    break;
                default:
                    System.out.println("Error en la opción");
            }

        } while (opcion != 5);
    }

    public static boolean comprobarDNI(String dni) {
        if (dni.length() != 9) {
            return false;
        }
        int prueba = 0;
        String[] letra = dni.split("");
        try {
            for (int i = 0; i < dni.length() -1; i++) {
                     prueba = Integer.parseInt(letra[i]);       
            }
           
        } catch (Exception e) {
            System.out.println("Error: Datos dni erroneo");
            return false;
        }
        try{
            prueba = Integer.parseInt(letra[9]);
        } catch(Exception e){
          return true;
        }
        return false;

    }

    public static boolean comprobarPassword(String password) {
        if (password.length() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public static LocalDate crearFecha() {
        Scanner s = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.now();
        String fecha3 = "";

        do {
            System.out.println("Dime la fecha de venta (dd/mm/aaaa)[intro para la fecha actual]:");
            fecha3 = s.nextLine();
            if (fecha3.equals("")) {
                return fecha;
            } else {
                try {
                    fecha = LocalDate.parse(fecha3, dtf);
                    break;
                } catch (Exception e) {
                    System.out.println("Error de fecha");
                }
            }
        } while (true);
        return fecha;
    }

    public static void menuNuevoUsuario() {
        Scanner s = new Scanner(System.in);
        Cliente clienteNuevo = null;
        System.out.println("");
        System.out.println("Buenos días rellene un pequeño formulario para ser inscribirse como cliente");
        System.out.println("Introduzca su nombre: ");
        String nombre = s.nextLine();
        System.out.println("Introduzca su ciudad: ");
        String ciudad = s.nextLine();
        do {
            System.out.println("Introduzca su dni[8 números 1 letra]: ");
            String dni = s.nextLine();
            if (comprobarDNI(dni)) {
                do {
                    System.out.println("Introduzca su contraseña[Tiene que tener 4 caracteres]: ");
                    String contraseña = s.nextLine();
                    if (comprobarPassword(contraseña)) {
                        clienteNuevo = new Cliente(nombre, ciudad, dni, contraseña);
                        Bd_almacen.añadirClienteSql(clienteNuevo);
                        break;
                    } else {
                        System.out.println("Contraseña erronea. Tiene que tener 4 caracteres");
                    }
                } while (true);
                break;
            } else {
                System.out.println("Error al introducir el dni. RECUERDA: 8 números 1 letra");
            }
        } while (true);
    }

    public static void menuCliente(Almacen miAlmacen, Cliente cliente, ArrayList<Venta> misVentas) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner s = new Scanner(System.in);
        int opcion = 0;
        int contador = 0;
        String ref = "";
        Venta ventaAux = null;
        ArrayList<Linea_venta> lineas = new ArrayList<>();

        LocalDate fecha = crearFecha();
        do {
            try {

            System.out.println("");
            System.out.println("\033[34mBuenos días \033[30m" + cliente.getNombre() + "\033[34m Fecha: " + fecha.format(dtf));
            System.out.println("\033[34m*******MENÚ*******");
            System.out.println("\033[34m******************");
            System.out.println("\033[34m1. Listar productos");
            System.out.println("\033[34m2. Comprar");
            System.out.println("\033[34m3. Consultar mis compras");
            System.out.println("\033[34m4. Cerrando sesión");
            System.out.println("\033[34mElige opción: \033[30m");
            opcion = Integer.parseInt(s.nextLine());
            System.out.println("");

            switch (opcion) {
                case 1:
                    miAlmacen.listarAlmacen();
                    break;
                case 2:
                    int num_linea = 1;
                    ventaAux = new Venta(miAlmacen, fecha, cliente);
                    do {
                        miAlmacen.listarAlmacen();
                        System.out.println("Introduce Referencia(-1 para salir): ");
                        ref = s.nextLine();
                        if (ref.equals("-1")) {
                            break;
                        }
                        Producto producto = miAlmacen.buscarProdcuto(ref);
                        System.out.println("Introduce la cantidad:");
                        int cantidad = Integer.parseInt(s.nextLine());
                        if(producto == null){
                            System.out.println("Referencia no existe");
                        }
                        if (producto.getUnidades() < cantidad) {
                            System.out.println("No quedan tantos artículos");
                        } else {
                            producto.setUnidades(producto.getUnidades() - cantidad);
                            ventaAux.addLineaVenta(num_linea, cantidad, producto);
                            num_linea++;
                        }
                    } while (true);
                    misVentas.add(ventaAux);
                    Bd_almacen.añadirVentaSql(ventaAux);
                    for (Venta venta : misVentas) {
                        if ((contador - 1) == venta.getNumero()) {
                            venta.imprimirVenta();
                            lineas = venta.getLineasVenta();

                        }
                    }

                    for (Linea_venta linea : lineas) {
                        Bd_almacen.descontarVentasSql(linea.getProducto().getRef(), linea.getCantidad());
                    }
                    break;

                case 3:
                    misVentas = Bd_almacen.getVentasSql(cliente.getCodigo_cliente());
                    for (Venta venta : misVentas) {
                        venta.imprimirVenta();
                    }
                    break;

                default:
                    System.out.println("Opción incorrecta");
                    break;
            }
            } catch (Exception e) {
                System.out.println("Error: Datos introducidos erroneos");
            }
        } while (opcion != 4);
    }

    public static void menuEmpleado(Almacen miAlmacen, ArrayList<Venta> misVentas) {
        Scanner s = new Scanner(System.in);
        int opcion = 0;
        String ref = "";
        int cantidad = 0;

        do {
            try {
                System.out.println("");
                System.out.println("Gestión del almacén");
                System.out.println("1. Añadir stock");
                System.out.println("2. Listar almacén");
                System.out.println("3. Buscar producto");
                System.out.println("4. Listar Ventas");
                System.out.println("5. Añadir producto");
                System.out.println("6. Borrar producto");
                System.out.println("7. Salir");
                System.out.println("******************");
                System.out.println("Escoge opción");
                opcion = Integer.parseInt(s.nextLine());

                switch (opcion) {
                    case 1:
                        do {
                            miAlmacen.listarAlmacen();
                            System.out.println("Dime la referencia del producto a modificar[-1 salir]");
                            ref = s.nextLine();
                            System.out.println("Introduce la cantidad del producto a sumar");
                            cantidad = Integer.parseInt(s.nextLine());

                            if (miAlmacen.añadirStock(ref, cantidad)) {
                                Bd_almacen.actualizarStockSql(ref, cantidad);
                                miAlmacen.setListaProductos(Bd_almacen.getProductoSql());
                                System.out.println("Cantidad modificada");
                            } else {
                                System.out.println("Error: La referencia no existe");
                            }
                        } while (ref.equals(-1));
                        break;

                    case 2:
                        miAlmacen.listarAlmacen();
                        break;

                    case 3:
                        System.out.println("¿Que referencia quieres buscar?");
                        ref = s.nextLine();
                        if (miAlmacen.buscarProdcuto(ref) != null) {
                            System.out.println(miAlmacen.buscarProdcuto(ref));
                        } else {
                            System.out.println("No existe esa referencia");
                        }
                        break;

                    case 4:
                        misVentas = Bd_almacen.getVentasEmpleadoSql();
                        for (Venta venta : misVentas) {
                            venta.imprimirVenta();
                        }
                        break;

                    case 5:
                        System.out.println("Introduce la descripcion");
                        ref = s.nextLine();
                        System.out.println("Introduce su precio:");
                        double precioU = Double.parseDouble(s.nextLine());
                        System.out.println("Introduce la cantidad inicial:");
                        cantidad = Integer.parseInt(s.nextLine());
                        miAlmacen.nuevoProducto(ref, precioU, cantidad);
                        for (int i = 1; i <= miAlmacen.getListaProductos().size() - 1; i++) {
                            System.out.println("Tamñao del array" + miAlmacen.getListaProductos().size());
                            System.out.println("Valor de I" + i);
                            if (i == miAlmacen.getListaProductos().size() - 1) {
                                Producto productoAux = miAlmacen.getListaProductos().get(i);
                                System.out.println("");
                                System.out.println("");
                                Bd_almacen.añadirProductoSql(productoAux);
                            }
                        }
                        break;

                    case 6:
                        System.out.println("¿Quieres borrar algún producto?");
                        String respuesta = s.nextLine();
                        if (respuesta.toLowerCase().equals("si")) {
                            miAlmacen.listarAlmacen();
                            System.out.println("Introduce la referencia del producto:");
                            ref = s.nextLine();
                            System.out.println("Esta acción eliminara el producto para siempre, ¿quiere continuar?");
                            respuesta = s.nextLine();
                            if (respuesta.toLowerCase().equals("si")) {
                                for (Producto producto : miAlmacen.getListaProductos()) {
                                    if (producto.getRef().equals(ref)) {
                                        Bd_almacen.borrarProductoSqul(producto);
                                        miAlmacen.setListaProductos(Bd_almacen.getProductoSql());
                                    }
                                }
                            }
                        }
                        break;

                    case 7:
                        System.out.println("Cerrando");
                        break;

                    default:
                        System.out.println("Opción incorrecta");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: Datos introducidos erroneos");
            }
        } while (opcion != 7);
    }

}
