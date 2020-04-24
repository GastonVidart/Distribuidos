
package TP2Distribuidos;

// @author guido

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    
    public static Logger logger;
    
    private static FileHandler fh;
    
    public static void startLog(String fileName) throws SecurityException, IOException{
        
        File f = new File(fileName);
        if(!f.exists()){
            f.createNewFile();
        }
        
        fh = new FileHandler(fileName, true);
        logger = Logger.getLogger("test");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);        
    }

}
