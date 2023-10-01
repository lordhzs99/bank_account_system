import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set; 
import java.util.LinkedHashSet; 
import java.text.DecimalFormat;
/**
 * La clase Cliente se inincializa cuando se instancia un nuevo objeto desde la clase principal BNACO
 * Esta clase es capaz de gestionar todo lo que involucra las funciones del cliente (excepto el cajero
 * , que se vera en la clase), esto es la cuenta basica o principal, la cuenta de inversion, los datos 
 * del usuario, consultas de su saldo, eliminacion de cuentas, etc.
 */
public class Cliente {
    /**
     * Se comienzan declarando los atributos de la clase, todos de tipo privado
     * Estos solo podran accederse mediante getters y setters
     */
    private String nombre, fecha, edo, curp, rfc, dir; 
    private int numcuenta;
    private long numcel;
    private CuentaBasica cBasica;
    private CuentaInversion cInversion;
    private Set<Cajero> kajero;
    private static DecimalFormat df;


    /**
     * El constructor define los atributos de la clase y los referencia con ayuda
     * de la palabra reservada this. En el caso de cBasica, cInversion y kajero
     * se crea un nuevo objeto en el constructor, dado que al registrarse un 
     * cliente es necesario acceder a las clases cuenta de inversion, cuenta basica
     * y cajero
     */
    public Cliente(String nombre, String fecha, String edo, String curp, String rfc, String dir, long numcel, int numcuenta){
        this.nombre = nombre;
        this.fecha = fecha;
        this.edo = edo;
        this.curp = curp.toUpperCase();
        this.rfc = rfc.toUpperCase();
        this.dir = dir;
        this.numcel = numcel;
        this.numcuenta = numcuenta;
        this.cBasica = new CuentaBasica();
        this.cInversion = null;
        this.kajero = new LinkedHashSet<>();
        df = new DecimalFormat("#.00");
    }

    /**
     * La funcion menuCliente desplega un menu hacia el usuario, en este es posible seleccionar
     * y navegar entre las opciones. 
     * @param Cliente instancia
     * @param lista donde se almacenan los clientes
     * @param hashtable general donde se almacenan los numeros de cuenta de los usuarios registrados 
     * @return void 
     */
    public static void menuCliente(Cliente cliente, List <Cliente> list,  Hashtable<Integer, Cliente> map){
        Scanner sc = new Scanner(System.in);
        int option; 
		do{
        System.out.println(" ");
        System.out.println("Bienvenido " + cliente.getNombre() + ", Que deseas hacer hoy?");
        System.out.println(" ");
        System.out.println("[1] Consultar saldo"); 
        System.out.println("[2] Consultar mis datos personales");
        System.out.println("[3] Modificar cuenta basica"); 
        System.out.println("[4] Cuenta de inversion"); 
        System.out.println("[5] Eliminar cuenta DE INVERSION"); 
        System.out.println("[6] Eliminar cuenta BASICA"); 
        System.out.println("[7] Cerrar sesion");
        System.out.print("Opcion: ");
        option = sc.nextInt();
        switch(option){
            case 1:
                System.out.println(" ");
                System.out.print("Tu saldo es: $"+ df.format(cliente.getCuentaBasica().getSaldo()) +" MXN");
                System.out.println(" ");
            break;
            
            case 2:
                System.out.println(" ");
                System.out.println("Nombre: " + cliente.getNombre()); 
                System.out.println("fecha de nacimiento: " + cliente.getFecha()); 
                System.out.println("Estado de residencia: " + cliente.getEdo()); 
                System.out.println("CURP: " + cliente.getCurp()); 
                System.out.println("RFC: " + cliente.getRfc()); 
                System.out.println("Domicilio: " + cliente.getDir()); 
                System.out.println("Numero de celular: " + cliente.getNumcel()); 
            break;
            case 3:
                cliente.modificar(); 
            break;
            case 4: 
                if(cliente.getCuentaInversion() == null){
                    System.out.println(" ");
                    System.out.println("No tienes una cuenta de inversion, ¿Quieres crearla?");
                    System.out.println("[1] Si");
                    System.out.println("[2] No");
                    System.out.print("Opcion: ");
                    option = sc.nextInt();
                    if(option == 1){
                        cliente.setCuentaInversion(new CuentaInversion());
                        System.out.println(" ");
                        System.out.println("Se ha creado su cuenta de inversion");
                    }else{
                        System.out.println(" ");
                        System.out.println("No sea ha creado ninguna cuenta de inversion ");
                        break; 
                    }
                }
                cliente.getCuentaInversion().menu(cliente); 
            break; 
            case 5: 
                cliente.eliminarInversion(); 
            break; 
            case 6:
                int op;
                System.out.println(" ");
                System.out.println("¡¡¡ ADVERTENCIA !!!");
                System.out.println("Al eliminar su CUENTA BASICA, se elimina");
                System.out.println("automaticamente su cuenta de INVERSION");
                System.out.println(" ");
                System.out.println("¿Esta seguro de eliminar su cuenta?");
                System.out.println("[1] Sí");
                System.out.println("[2] No");
                System.out.print("Opcion: ");
                op = sc.nextInt();
                
                if(op == 1){
                    CuentaBasica.eliminar(cliente, list, map);
                    return; 
                }
            break;
            
            case 7:
                System.out.println(" ");
                System.out.println("Tu sesion ha sido cerrada, gracias por tu visita " + cliente.getNombre()); 
                return; 
            default:
                System.out.println("Opcion invalida..."); 	

        }
        }while(true); 
    }
    
    /**
     * El metodo modificar brinda al usuario la posibilidad de modificar sus datos introducidos
     * en el registro previo
     * @param ninguno 
     */
    public void modificar(){
        Scanner sc = new Scanner(System.in); 
        System.out.println(" ");
        System.out.println("Introduzca los nuevos datos");
        System.out.println("Si no desea modificar alguno, no escriba nada");
        System.out.println(" ");
        System.out.print("Nombre de usuario: ");
        String nombre = sc.nextLine();  
        System.out.print("Fecha de nacimiento (dd/mm/aaaa): ");
        String fecha = sc.nextLine();  
        System.out.print("Estado de residencia: "); 
        String edo = sc.nextLine();  
        System.out.print("CURP: "); 
        String curp = sc.nextLine();
        System.out.print("RFC: "); 
        String rfc = sc.nextLine();  
        System.out.print("Domicilio: "); 
        String dir = sc.nextLine();  
        System.out.print("Numero de celular: ");
        long numcel = sc.nextInt();
        if(nombre != ""){
            this.nombre = nombre;
        }
        if(fecha != ""){
            this.fecha = fecha;
        }
        if(edo != ""){
            this.edo = edo;
        }
        if(curp != ""){
            this.curp = curp.toUpperCase();
        }
        if(rfc != ""){
            this.rfc = rfc.toUpperCase(); 
        }
        if(dir != ""){
            this.dir = dir;
        }
        if(numcel != 0){
            this.numcel = numcel;
        }
    }

    /**
     * Este metodo da la posibilidad de eliminar la cuenta de inversion del usuario, siempre 
     * y cuando: 
     * 1. Exista
     * 2. No haya ninguna inversion en ese momento
     * @param ninguno
     */
    public void eliminarInversion(){
        if(!this.cInversion.invirtiendo()){
            cInversion.eliminar(this);
            cInversion = null;
        }else{
            System.out.println("No puedes eliminar tu cuenta mientra haya una inversiona activa");
        }
    }

    /**
     * Getters y setters
     * @param atributos 
     */
    public String getNombre(){
        return nombre;
    }
    public String getFecha(){
        return fecha; 
    }
     public String getEdo(){
        return edo; 
    } 
    public String getCurp(){
        return curp; 
    } 
    public String getRfc(){
        return rfc; 
    } 
    public String getDir(){
        return dir; 
    }
    public long getNumcel(){
        return numcel; 
    }  
    public int getNumeroCuenta(){
        return numcuenta; 
    } 
    public CuentaBasica getCuentaBasica(){
        return cBasica;
    }
    public CuentaInversion getCuentaInversion(){
        return cInversion;
    }

    public Set<Cajero> getMiSet() {
        return kajero;
    }
  
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public void setNombre(String nombre){
        this.nombre = nombre; 
    }
    public void setEdo(String edo){
        this.edo = edo; 
    }
    public void setCurp(String curp){
        this.curp = curp; 
    }
    public void setRfc(String rfc){
        this.rfc = rfc; 
    }
    public void setDir(String dir){
        this.dir = dir; 
    }
    public void setNumcel(int numcel){
        this.numcel = numcel; 
    }
    public void setNumCuenta(int numcuenta){
        this.numcuenta = numcuenta; 
    }
    public void setCuentaInversion(CuentaInversion cInversion){
        this.cInversion = cInversion; 
    }

    public void setKajero(Cajero elemento) {
        kajero.add(elemento);
    }

}
