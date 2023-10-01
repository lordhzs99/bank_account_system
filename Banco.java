import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Hashtable; 

/**
 * Clase en la cual se encuentra el metodo principal, representa el banco en el cual se encuentran registrados los clientes.
 * @author Carlos Manuel Alvarez Lopez
 * @author Hector Zambrano Serrano
 * @author Juan Otoniel Gomez Lopez
 * @version Septiembre - 2023
 */

public class Banco {
    /**
     * Registra a los nuevos clientes en el banco, tanto en la lista como en el mapa y les genera su numero de cuenta unico.
     * @param list Es una lista de instancias de cliente en la cual se lleva un registro de los clientes
     * @param map Mapa implementado con una hashtable con el cual se accede a los clientes registrados
     */
    public static void registro(List <Cliente> list, Hashtable<Integer, Cliente> map){
        Random random = new Random(); 
        String nombre, fecha, edo, curp, rfc, dir; 
        long numcel;
        Scanner sc = new Scanner(System.in); 
        System.out.println(" ");
        System.out.print("Nombre completo: "); 
        nombre = sc.nextLine();
        System.out.print("Fecha de nacimiento (dd/mm/aaaa): "); 
        fecha = sc.nextLine();
        System.out.print("Estado de residencia: "); 
        edo = sc.nextLine();
        System.out.print("CURP: "); 
        curp = sc.nextLine();
        System.out.print("RFC: "); 
        rfc = sc.nextLine();
        System.out.print("Domicilio: "); 
        dir = sc.nextLine();
        System.out.print("Numero de celular: "); 
        numcel = sc.nextLong();
        int nodecuenta;

        do{
            nodecuenta = random.nextInt(100000, 200000); 
        }while(map.containsKey(nodecuenta));
        System.out.println(" ");
        System.out.println("Registro Exitoso");
        System.out.println("Tu numero de cuenta es " + nodecuenta);
        Cliente c = new Cliente(nombre, fecha, edo, curp, rfc, dir, numcel, nodecuenta);
        map.put(nodecuenta, c); 
        list.add(c); 
        System.out.println();
    }

    /**
     * Metodo principal en el que se declara la lista y el mapa de clientes, ademas de desplegar el menu para manejar el programa
     */
    public static void main(String[] args){
        List <Cliente> list = new ArrayList<Cliente>(); 
        Hashtable<Integer, Cliente> map = new Hashtable<Integer, Cliente>( );
        Scanner sc = new Scanner(System.in);

        int option;  
		do{
        System.out.println(" ");
		System.out.println("Bienvenido usuario, ¿que desea hacer hoy?"); 
        System.out.println(" ");
		System.out.println("[1] Nuevo usuario"); 
		System.out.println("[2] Ingresar como usuario");
        System.out.println("[3] Acceder al Cajero");
        System.out.println("[4] Salir");
        System.out.print("Opcion: ");
		option = sc.nextInt(); 
		switch(option){
				case 1:
                    System.out.println(" ");
				    System.out.println("Gracias por su preferencia");
                    System.out.println("Por favor siga las siguientes instrucciones");
                    registro(list, map);
				break;
                
                case 2:    // Entrar al submenu de usuario y trabajar sobre el objeto de la clase
                    if(list.isEmpty()){
                        System.out.println("No hay usuarios registrados"); 
                    }else{
                        System.out.println(" ");
                        System.out.print("Numero de cuenta: "); 
                        int seleccion = sc.nextInt(); 
                        if(map.containsKey(seleccion)){
                            Cliente.menuCliente(map.get(seleccion), list, map); 
                        }else{
                            System.out.println("El numero de cuenta ingresado no pertenece a ninguna cuenta.");
                        }
                    }
                break;

                case 3:
                System.out.println(" ");
                System.out.print("Numero de cuenta: ");
                int cuenta = sc.nextInt();
                if(map.containsKey(cuenta)){
                    Cajero.menu(map.get(cuenta));
                } else{
                    System.out.println("ERROR. Cuenta no encontrada");
                }
                break;

				case 4:
					System.out.println("Hasta luego!"); 
					System.exit(0);
                default:
					System.out.println("Opcion invalida..."); 	

			}
		}while(true); 
    }
}