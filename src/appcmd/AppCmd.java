
package appcmd;

import java.util.Scanner;

/**
 *
 * @author chris
 */
public class AppCmd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        Consola c = new Consola();
        
        String entrada = " ";
        
        Scanner src = new Scanner(System.in);
        
        while (!"close".equals(c.getCmd())) {            
            
             System.out.println("Entra una linea de comando : ");
            
             
              entrada = src.nextLine();
        
              c.setLineaComando(entrada);
              
        //-------------------------------------------------------------
        String convert = c.getLineaComando().toLowerCase();
        
        String[] tab = convert.split(" ");
        
        if(c.getListaComando().contains(tab[0])){
         
            c.setCmd(tab[0]); 
            
            c.menu();
          }else{
           System.err.println("La comando "+ tab[0] +" no es reconocida por el sistema");
            
        }
        
        //-------------------------------------------------------------
             
              
             
        }
        src.close();
        System.out.println("Consola cerrada...");
       
        
        
        
       
    }
    
}
