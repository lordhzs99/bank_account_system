import java.time.LocalTime;
import java.util.Scanner;
import java.text.DecimalFormat;
/**
 * Representa la cuenta con la cual se pueden realizar inversiones, teniendo como atributos el saldo de la inversion, los ciclos, la fecha a la que se acabara la inversion, 
 * la tasa y un booleano indicando el estado de la inversion
 */
public class CuentaInversion{

    private LocalTime inversionDate; 
    private double saldo;
    private float tasa;
    private boolean activa;
    private int ciclos;
    private static DecimalFormat df;
/**
 * Constuctor con el cual se define la tasa de inversion y el estado como inactivo(falso)
 */
    public CuentaInversion(){
        saldo = 0;
        tasa = 0.08f;
        activa = false;
        ciclos = 0;
        inversionDate = LocalTime.now();
        df = new DecimalFormat("#.00");
    }

/**
 * Metodo que despliega en pantalla las opciones disponibles del usuario para realizar con su cuenta de inversion, comprobando tambien cada que se ejecuta si la
 * inverion ya se concluyo
 * @param cliente El cliente del cual se va a manipular la cuenta de inversion, tambien necesario para depositar y tomar dinero desde su cuenta basica
 */
    public void menu(Cliente cliente){
        Scanner sc = new Scanner(System.in); 
        int op;
        double retiro; 
        do{
            LocalTime aux = LocalTime.now();
            if(activa && inversionDate.isBefore(aux)){
                terminarInversion(cliente);
            }
            System.out.println(" ");
            System.out.println("Bienvenido a su cuenta de inversion, que desea hacer hoy?"); 
            System.out.println("[1] Invertir"); 
            System.out.println("[2] Retirar"); 
            System.out.println("[3] Consultar saldo"); 
            System.out.println("[4] Salir");
            System.out.print("Opcion: ");
            op = sc.nextInt();
            switch(op){
                case 1: 
                    invertir(cliente); 
                    break; 
                case 2: 
                    System.out.print("Cuanto desea retirar? (Pesos mexicanos): "); 
                    retiro = sc.nextDouble(); 
                    retirar(cliente, retiro); 
                case 3: 
                    consultar(); 
                    break;
                case 4: 
                    return; 
                default: 
                    System.out.println("Opcion invalida"); 
            }
        }while(true);   
    }
    /**
     * Metodo con el cual se imprime en pantalla toda la informacion de la cuenta, incluyendo en caso de haber inversion a cuantos plazos se invirtio y el rendimiento
     */
    public void consultar(){
        System.out.println(" ");
        System.out.println("Saldo: " + this.saldo);
        System.out.println("Tasa de inversion: " + tasa);
        System.out.println("Ciclos: " + ciclos);
        if(activa){
            System.out.println("La inversion activa a un plazo de " + ciclos + " minuto/s");
            System.out.println("A su termino habra generado:" + df.format((saldo*(1+tasa)-saldo)));
            System.out.println("Resultando en un saldo de: " + df.format(saldo*(1+tasa)));
        }else{
            System.out.println(" ");
            System.out.println("No hay inversiones activas");
        }
    }
    /**
     * Metodo con el cual se toma dinero de la cuenta principal y se activa la inversion de la cuenta hasta que se cumplan los ciclos de inversion
     * @param cliente Instancia de cliente del cual se tomara el saldo necesario para le inversion
     */
    public void invertir(Cliente cliente){
        if(activa){
            System.out.println("Ya tienes una inversion activa, puedes volver a invertir hasta: " + inversionDate);
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println(" ");
        System.out.print("Listo, ya puede ingresar la cantidad deseada: "); 
        double cantidad = sc.nextDouble(); 
        saldo = saldo + cantidad;
        System.out.print("Veces que desea invertir el saldo de la cuenta: ");
        ciclos = sc.nextInt();
        if(cliente.getCuentaBasica().getSaldo() >= cantidad){
            cliente.getCuentaBasica().setSaldo(cliente.getCuentaBasica().getSaldo() - cantidad);
            saldo = cantidad;
        }else{
            System.out.println("No se cuenta con los suficientes fondos en el cuenta basica");
            return;
        }
        System.out.println(" ");
        LocalTime aux = LocalTime.now(); 
        inversionDate = aux.plusMinutes(ciclos); 
        System.out.println("La cantidad invertida generara ganancias variables dentro de "+ ciclos +" minuto/s");
        activa = true;
    }
    /**
     * Metodo encargado de regresar el saldo de la cuenta de inversion a la cuenta principal del cliente indicado
     * @param cliente Cliente al cual se le dara el dinero retirado 
     * @param retiro Cantidad que se tomara de la cuenta de inversion
     */
    public void retirar(Cliente cliente, double retiro){
        if(retiro <= saldo){
            saldo -= retiro;
            cliente.getCuentaBasica().setSaldo(cliente.getCuentaBasica().getSaldo()+retiro);
        }else{
            System.out.println(" ");
            System.out.println("No se tiene el saldo suficiente");
        }
    }
    /**
     * Metodo para eliminar la cuenta de inversion del usuario indicado
     * @param cliente Cliente del cual se eliminara la cuenta
     */
    public void eliminar(Cliente cliente){
        cliente.getCuentaBasica().setSaldo(cliente.getCuentaBasica().getSaldo()+saldo);
        saldo = 0;
    }

    /**
     * Metodo encargado de añadir el saldo y establecer el estado pertinente al finalizar una inversion
     * @param cliente Cliente al cual pertenece la cuenta de inversion
     */
    public void terminarInversion(Cliente cliente){
        activa = false;
        for (int i = 0; i < ciclos; i++) {
            saldo = saldo*1.08f;
        }
        retirar(cliente, saldo);
    }
    /**
     * Indica el estado de la inversion
     * @return Un booleano
     */
    public boolean invirtiendo(){
        return activa;
    }
}
