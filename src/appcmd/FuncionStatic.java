/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appcmd;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author chris
 */
public class FuncionStatic {
    
    
     public static void delete(File f) throws IOException{
 
      if(f.isDirectory()){
        //si le dossier est vide, supprimez-le
        if(f.list().length == 0){
          f.delete();
          System.out.println("Dossier est supprimé: "+ f.getAbsolutePath());
        }else{
          //lister le contenu du répertoire
          String files[] = f.list();
     
          for (String tmp : files) {
             File file = new File(f, tmp);
             //suppression récursive
             delete(file);
          }
          //vérifiez à nouveau le dossier, s'il est vide, supprimez-le
          if(f.list().length == 0){
          f.delete();
          System.out.println("Dossier est supprimé: "+ f.getAbsolutePath());
          }
          }
      }else{
          //si il est un fichier, supprimez-le
          f.delete();
          System.out.println("Fichier est supprimé: " + f.getAbsolutePath());
      }
    }
    
}
