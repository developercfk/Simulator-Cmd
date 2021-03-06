
package appcmd;

import java.io.File;

/**
 *
 * @author chris
 */
public class DiskFileExplorer {
    
     private String initialpath = "";
    private Boolean recursivePath = false;
    public int filecount = 0;
    public int dircount = 0;
 
/**
 * Constructeur
 * @param path chemin du répertoire
 * @param subFolder analyse des sous dossiers
 */
    public DiskFileExplorer(String path, Boolean subFolder) {
        super();
        this.initialpath = path;
        this.recursivePath = subFolder;
    }
 
    public void list() {
        this.listDirectory(this.initialpath);
    }
 
    private void listDirectory(String dir) {
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() == true) {
                    System.out.println("Directorio: " + files[i].getAbsolutePath());
                    this.dircount++;
                } else {
                    System.out.println("  Fichero: " + files[i].getName());
                    this.filecount++;
                }
                if (files[i].isDirectory() == true && this.recursivePath == true) {
                    this.listDirectory(files[i].getAbsolutePath());
                }
            }
        }
    }    
}
