import java.util.Hashtable;
import java.util.List;

/**
 * Se importan 2 clases. Específicamente en esta clase, Hashtable y List se importan para poder usarla si el usuario desea eliminar su cuenta basica. Se borra el objeto de ambos.
 */

public class CuentaBasica{
    private double saldo;
    
    /**
     * Este constructor inicializa en 0 el saldo debido a que se usa cuando se crea una cuenta nueva
     * @param void
     */
    public CuentaBasica(){
        this.saldo = 0;
    }

    public double getSaldo(){
        return saldo;
    }

    public void setSaldo(double saldo){
        this.saldo = saldo;
    }
    
    /**
     * En esta funcion es de donde se borra el objeto de la lista y de nuestra hashtable
     * @param cliente
     * @param list
     * @param map
     * @return void
     */
    public static void eliminar(Cliente cliente, List <Cliente> list,  Hashtable<Integer, Cliente> map){
        list.remove(list.indexOf(cliente));
        map.remove(cliente.getNumeroCuenta());
        System.out.println(" ");
        System.out.println("Cuenta eliminada correctamente");
    }
}