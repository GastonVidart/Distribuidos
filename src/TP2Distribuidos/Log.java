
package TP2Distribuidos;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    
    private static Logger logger;
    
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
    
    public static void logInfo(String system, String message){
        logger.log(Level.INFO, "->{0}: {1}", new Object[]{system, message});
    }
    
    public static void logError(String system, String message){
        logger.log(Level.INFO, "XX{0}: {1}", new Object[]{system, message});
    }
}
