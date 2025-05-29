package com.tasktracker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLogger {
    private static AppLogger instance=null;
    private Logger logger;
    private AppLogger()
    {
        logger=Logger.getLogger(AppLogger.class.getName());
        setupLogger();
    }

    public  static AppLogger getInstance()
    {
        if(instance==null)
        {
            instance=new AppLogger();
        }
        return  instance;
    }

    public  void  setupLogger()
    {
        File logDir=new File("logs");
        if(!logDir.exists())
        {
            logDir.mkdir();
        }

        try {
            String logFileName = generateLogFileName();
            FileHandler fileHandler = new FileHandler(logFileName, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
            logger.info("Logger initialized. Writing logs to: " + logFileName);
        }catch (IOException e) {
            System.out.println("[ERROR] Failed to set up logging: " + e.getMessage());
        }
    }

    public void logInfo(String message)
    {
        logger.info(message);
    }

    public  void logWarning(String message)
    {
        logger.warning(message);
    }

    public  void  logError(String message,Exception e)
    {
        logger.severe(message+" |Exception: "+e.getMessage());
    }

    private String generateLogFileName()
    {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp=formatter.format(new Date());
        return "logs/logs_"+timeStamp+".txt";
    }
}
