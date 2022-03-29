
package appcmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris
 */
public class Consola {
    
    
    private ArrayList<String> listaComando;
    private String lineaComando;
    private String cmd;
    private boolean reconocido;
    private boolean cmdActivado;
    
    private Path pathActual = Paths.get(System.getProperty("user.dir"));
    private File fileActual;
    private String pathSauvegarde;
    
    
    
    

    public Consola() {
        cargarComando();
       
    }
    
    
    public void scanner(){
        
        String convert = getLineaComando().toLowerCase();
        
        String[] tab = convert.split(" ");
        
        if(getListaComando().contains(tab[0])){
         
            setCmd(tab[0]);  
        }else{
           System.err.println("La comando "+ tab[0] +" no es reconocida por el sistema");
            
        }
        
        
        
        
    }
    
    public void cargarComando(){
        setListaComando(new ArrayList<String>());
		
		getListaComando().add("help");
		getListaComando().add("cd");
		getListaComando().add("mkdir");
		getListaComando().add("info");
		getListaComando().add("cat");
		getListaComando().add("top");
		getListaComando().add("mkfile");
		getListaComando().add("write");
		getListaComando().add("dir");
		getListaComando().add("delete");
		getListaComando().add("close");
		getListaComando().add("clear");
    }
    
    public void menu(){
        
        
       
        switch(getCmd()){
            
             case "help" : help(); break;
			  
            case "cd" : cd(); break;

            case "mkdir" : mkdir(); break;


            case "info" : info();  break;


            case "cat" : cat(); break;

            case "top" : top() ; break;

            case "mkfile": mkfile(); break;

            case "write" : write(); break;

            case "dir" : dir(); break;

            case "delete" : delete(); break;

            case "close" :   break;

            case "clear" : clear(); break;
        }
    }
    
    public void help(){
	       
		     String list[]  = new String[12];
		     
		     list[0] = "help -> Lista los comandos con una breve definición de lo que hacen.\n";
		     list[1] = "cd-> Muestra el directorio actual.\n" +
		               " [..] -> Accede al directorio padre.\n" +
		               " [<nombreDirectorio>] -> Accede a un directorio dentro del directorio actual.\n" +
		               " [<rutaAbsoluta] -> Accede a la ruta absoluta del sistema.\n";
		     list[2] = "mkdir <nombre_directorio> -> Crea un directorio en la ruta actual.\n";
		     list[3] = "info <nombre> -> Muestra la información del elemento. Indicando FileSystem,\n"
		             + "Parent, Root, Nº of elements, FreeSpace, TotalSpace y UsableSpace.\n";
		     list[4] = "cat <nombreFichero> -> Muestra el contenido de un fichero.\n";
		     list[5] = "top <numeroLineas> <nombreFichero> -> Muestra las líneas especificadas de un fichero.\n";
		     list[6] = "mkfile <nombreFichero> <texto> -> Crea un fichero con ese nombre y el contenido de texto.\n";
		     list[7] = "write <nombreFichero> <texto> -> Añade 'texto' al final del fichero especificado.\n";
		     list[8] = "dir -> Lista los archivos o directorios de la ruta actual.\n" +
		               " [<nombreDirectorio>] -> Lista los archivos o directorios dentro de ese directorio.\n" +
		               " [<rutaAbsoluta] -> Lista los archivos o directorios dentro de esa ruta.\n";
		     list[9] = "delete <nombre> -> Borra el fichero, si es un directorio borra todo su contenido y a si mismo.\n";
		     list[10] = "close -> Cierra el programa.\n";
		     list[11] = "Clear -> vacía la vista.\n";
		     
		       for (String string : list) {
		           
		           System.out.println(string);
		           
		       }
		              
		               
		       
		       
		   }
    
    public void info() {
		  
		  File file = getFileActual();
		  
		  long totalSpace = file.getTotalSpace();
		  long usableSpace = file.getUsableSpace();
		  long freeSpace = file.getFreeSpace();
		  String parent = file.getParent();
		 
		  Path root = Paths.get("c:");
		  
		  System.out.println(" === Partition Detail ===");

	        System.out.println(" === bytes ===");
	        System.out.println("Total size : " + totalSpace + " bytes");
	        System.out.println("Space free : " + usableSpace + " bytes");
	        System.out.println("Space free : " + freeSpace + " bytes");

	        System.out.println(" === mega bytes ===");
	        System.out.println("Total size : " + totalSpace/1024/1024 + " mb");
	        System.out.println("Space free : " + usableSpace/1024/1024 + " mb");
	        System.out.println("Space free : " + freeSpace/1024/1024 + " mb");
	        
	        System.out.println( "=== System info ===");
	        System.out.println("Root info : "+ root);
	        System.out.println("Parent info : "+ parent);
	  }

    public void mkdir(){
        
        String str[] = lineaComando.split(" ");
        
        for (int i = 1; i < str.length; i++) {
            
            String patchAct = pathActual.toString(); //acabo de añadir esta linea
            
            File file = new File(patchAct + "\\" + str[i]); //....++
            
            if(file.mkdir()){
                
                System.out.println("Directorio creada : "+ file.getAbsolutePath());
            }else if(file.exists()){
                System.err.println("Directorio ya existe: " + file.getAbsolutePath());
            }else{
               System.err.println("No puede crear este directorio : " + file.getAbsolutePath()); 
            }
        }
        
    }
    
    public void mkfile(){
         
        String str[] = lineaComando.split(" ");
        
        for (int i = 1; i < str.length; i++) {
            String patchAct = pathActual.toString(); //acabo de añadir esta linea
            File f = new File(patchAct + "\\" + str[i]);
            
         try {
             // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile())
                System.out.println("Fchero creada : "+f.getAbsolutePath());
            else
                System.out.println("Fiche ya exists : "+f.getAbsolutePath());
        }
        catch (Exception e) {
            System.err.println(e);
        }
           
        }
    }
    
    public void dir(){
        
        String[] str = lineaComando.split(" ");
        
        String pathToExplore = " ";
        
        
        
        if(str.length == 1){
        
        pathToExplore = ".";
            
        DiskFileExplorer diskFileExplorer = new DiskFileExplorer(pathToExplore, true);
        Long start = System.currentTimeMillis();
        diskFileExplorer.list();
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Analysis de " + pathToExplore + " en " + (System.currentTimeMillis() - start) + " mses");
        System.out.println(diskFileExplorer.dircount + " Directorios");
        System.out.println(diskFileExplorer.filecount + " Ficheros"); 
       
        }else if(str.length == 2){
           
        Path p = Paths.get(str[1]);
            
        pathToExplore = p.getName(p.getNameCount() - 1).toString();
            
        DiskFileExplorer diskFileExplorer = new DiskFileExplorer(pathToExplore, true);
        Long start = System.currentTimeMillis();
        diskFileExplorer.list();
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Analysis de " + pathToExplore + " en " + (System.currentTimeMillis() - start) + " mses");
        System.out.println(diskFileExplorer.dircount + " Directorios");
        System.out.println(diskFileExplorer.filecount + " Ficheros");
        }else{
            System.err.println("------> Linea de comando no es reconocido por el sistema ");
        }
      
            
    }
    
    public void cat(){
        
        String str[] = lineaComando.split(" ");
        
        if(str.length == 2){
             
            
            
            try{
            InputStream flux=new FileInputStream(str[1]); 
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String ligne;
            while ((ligne=buff.readLine())!=null){
                    System.out.println(ligne);
            }
            buff.close(); 
            }		
           catch (Exception e){
            System.out.println(e.toString());
            
            
        }
        }else{
            System.err.println("Comando incorecto");
        }

        
        
       
        
        
    }
    
    public void top(){
     
        String str[] = lineaComando.split(" ");
        
        
        if(str.length == 3){
            
             String patchAct = pathActual.toString(); //acabo de añadir esta linea
            File file = new File(patchAct + "\\" + str[2]);
            
            if(file.exists()){
                 
                if(isInteger(str[1])){
            
                Path path = Paths.get(str[2]);
                int num = Integer.parseInt(str[1]);

                 try {
                     List<String> lignes = Files.readAllLines(path);

                     for (int i = 0; i < lignes.size(); i++) {

                         if(i == num){

                             System.out.println(lignes.get(i));
                         }
                     }
                 } catch (IOException ex) {
                     Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
                 }  
                }else{
                System.err.println("Comando incorecto :  eg . top [NUMERO DE LA LINEA] [NOMBRE FICHERO]");
                  }
            }else{
                System.err.println("El fichero "+ file.getName() + " no existe");
            }
            
            
        }else{
            System.err.println("Comando incorecto :  eg . top [NUMERO DE LA LINEA] [NOMBRE FICHERO]");
        }
        
       
        
        

       
    }
    
    public void write(){
        
        String str[] = lineaComando.split(" ");
        
        String texte = "\n";
        
        for (int i = 2; i < str.length; i++) {
            
            if(texte == null){
                texte = str[i];
            }else{
               texte = texte +" "+ str[i] ;
            }
        }
        
        
        try {
            Path path = Paths.get(str[1]);
           
            
            Files.write(path, texte.getBytes(), StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.APPEND);
        } catch (IOException ex) {
            Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void clear(){
        
        for (int i = 0; i < 500; i++) {
            System.out.println("");
        }
        
        
    }
    
    public void delete(){
        
      String str[] = lineaComando.split(" ");
        
        for (int i = 1; i < str.length; i++) {
                try {
                    String patchAct = pathActual.toString(); //acabo de añadir esta linea  
                 File f = new File(patchAct + "\\" + str[i]); 
                 
                 FuncionStatic.delete(f);
                 
                 } catch (IOException ex) {
                     Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
                 } 
         }
        
        
    }
    
    public void cd(){
        
        
        String str[] = lineaComando.split(" ");
        String n;
        if("cd".equals(str[0]) && str.length == 1){
            try {
                n = ".";
                pathActual = Paths.get(n);
                System.out.println(": "+ pathActual.toRealPath(LinkOption.NOFOLLOW_LINKS));
            } catch (IOException ex) {
                Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else if("cd".equals(str[0]) && str.length == 2 && "..".equals(str[1])){
            
            try {
                
                if(!isCmdActivado()){
                  
                  pathActual = pathActual.toRealPath(LinkOption.NOFOLLOW_LINKS);
                  pathSauvegarde = pathActual.toString();
                   setCmdActivado(true);
                  System.out.println(": " + pathActual.getParent());  
                }else{
                   pathActual = Paths.get(pathSauvegarde);
                   System.out.println(": " + pathActual.subpath(0, pathActual.getNameCount()-2)); 
                   pathSauvegarde = pathActual.toString();
                }
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("cd".equals(str[0]) && str.length == 2){
           
            try {
                n = str[1];
                pathActual = Paths.get(n);
                System.out.println(": " + pathActual.toRealPath(LinkOption.NOFOLLOW_LINKS) );
            } catch (IOException ex) {
                Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.err.println("Comando incorecto");
        }
    }
    //---------------------GETTER Y SETTER---------------------------------------------------------
    /**
     * @return the fileActual
     */
    public File getFileActual() {
        return fileActual;
    }

    /**
     * @param fileActual the fileActual to set
     */
    public void setFileActual(File fileActual) {
        this.fileActual = fileActual;
    }

    /**
     * @return the lineaComando
     */
    public String getLineaComando() {
        return lineaComando;
    }

    /**
     * @param lineaComando the lineaComando to set
     */
    public void setLineaComando(String lineaComando) {
        this.lineaComando = lineaComando;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the reconocido
     */
    public boolean isReconocido() {
        return reconocido;
    }

    /**
     * @param reconocido the reconocido to set
     */
    public void setReconocido(boolean reconocido) {
        this.reconocido = reconocido;
    }

    /**
     * @return the listaComando
     */
    public ArrayList<String> getListaComando() {
        return listaComando;
    }

    /**
     * @param listaComando the listaComando to set
     */
    public void setListaComando(ArrayList<String> listaComando) {
        this.listaComando = listaComando;
    }
    
    
    
    //-------------------------------
    
    	public boolean isInteger(String cadena) {
		try {
			Integer.parseInt(cadena);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}

    /**
     * @return the cmdActivado
     */
    public boolean isCmdActivado() {
        return cmdActivado;
    }

    /**
     * @param cmdActivado the cmdActivado to set
     */
    public void setCmdActivado(boolean cmdActivado) {
        this.cmdActivado = cmdActivado;
    }
	
}
