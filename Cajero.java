import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Se importan 3 clases. Scanner para la entrada del usuario como la cantidad a depositar/retirar.
 * Date y SimpleDateFormat para la fecha de las transacciones
 */

public class Cajero {

    private double cantidad;
    private String fecha;

        /**
         * Menu del cajero
         * Es en esta funcion contiene 3 opciones: depositar, retirar y ver historial de  movimientos. En necesario pedirle la cuenta del usuario para poder modificar correctamente sus datos.
         * @param cliente
         * @return void
         */
    public static void menu(Cliente cliente){

        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Scanner sc = new Scanner(System.in);
        int op;
        double cant;

        do{
            System.out.println(" ");
            System.out.println("[1] Depositar");
            System.out.println("[2] Retirar");
            System.out.println("[3] Ver historial de transacciones");
            System.out.println("[4] Regresar al Menu principal");
            System.out.print("Opcion: ");
            op = sc.nextInt();
            
            if(op == 1 || op == 2){
                String fecha = formato.format(fechaActual);
                System.out.println(" ");
                System.out.print("Cantidad: ");
                cant = sc.nextDouble();

                if(op == 2){
                    
                    if(cant > cliente.getCuentaBasica().getSaldo()){
                        System.out.println(" ");
                        System.out.println("ERROR. Saldo insuficiente");
                    } else{
                        cant *= -1;
                        cliente.getCuentaBasica().setSaldo(cliente.getCuentaBasica().getSaldo()+cant);
                        Cajero k = new Cajero(cant, fecha);
                        cliente.setKajero(k);
                        System.out.println(" ");
                        System.out.println("Transacción realizada exitosamente !");
                    }
                } else{
                    cliente.getCuentaBasica().setSaldo(cliente.getCuentaBasica().getSaldo()+cant);
                    Cajero k = new Cajero(cant, fecha);
                    cliente.setKajero(k);
                    System.out.println(" ");
                    System.out.println("Transacción realizada exitosamente !");
                }
                
            } else{
                mostrar(cliente);
            }
        }while(op != 4);
    }

    /**
     * Contructor del cajero
     * El historial de transacciones será un set, el cual guardara objetos que contenga una fecha y la cantidad depositada/retirada. Este constructor ayuda a crear esos objetos.
     * @param cantidad
     * @param fecha
     */
    public Cajero(double cantidad, String fecha){
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public String getFecha(){
        return fecha;
    }

    public double getCantidad(){
        return cantidad;
    }
    
    /**
     * Esta funcion es la que permite ver el historial de transacciones del cliente utilizando el set
     * @param cliente
     * @return void
     */
    public static void mostrar(Cliente cliente){


        double dinero = 0;
        if(cliente.getMiSet().isEmpty()){
            System.out.println(" ");
            System.out.println("Aun no tienes movimientos");
        } else{
            for (Cajero c : cliente.getMiSet()) {
                System.out.println(" ");
                
                if(c.getCantidad() <= 0){
                    System.out.println("MOTIVO: Retiro");
                    dinero = c.getCantidad() * -1;
                } else{
                    System.out.println("MOTIVO: Deposito");
                    dinero = c.getCantidad();
                }
                System.out.println("Cantidad: $"+ dinero +" MXN");
                System.out.println("Fecha y hora: "+ c.getFecha());
        }
        }
    }
}