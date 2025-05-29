package com.tasktracker;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileHandler {
    String FILE="tasks.json";

    public FileHandler()
    {

    }

    private  void  createNewFile()
    {
        File jsonFile=new File(FILE);
        if(!jsonFile.exists())
            try(FileWriter fw=new FileWriter(FILE))
            {
                fw.write("{}");
                AppLogger.getInstance().logInfo("Created new tasks.json file");
            }
            catch (IOException e)
            {
                System.out.println("error");
                AppLogger.getInstance().logError("Failed to create tasks.json",e);
            }
    }

    private void checkCorruptedFile(boolean isCritical)
    {
        File jsonFile=new File(FILE);
        if(!jsonFile.exists()) return;

        try(FileReader reader=new FileReader(jsonFile))
        {
            new Gson().fromJson(reader, new TypeToken<Map<Integer,Task>>(){}.getType());

        }catch (JsonSyntaxException | IOException e)
        {

        AppLogger.getInstance().logError("Corrupted JSON details in tasks.json",e);

        if(isCritical)
            throw new RuntimeException("Critical data corruption detected in tasks.json!");

        String backupFile="backup/tasks_corrupted_"+generateTimeStamp()+".json";
        File backupjson=new File(backupFile);
        if(jsonFile.renameTo(backupjson))
        {
            AppLogger.getInstance().logWarning("Renamed corrupted tasks.json to " +backupjson);
        }
        else{AppLogger.getInstance().logError("Failed to rename corrupted tasks.json", e);
        }
        }
        createNewFile();
    }
    private String generateTimeStamp()
    {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp=formatter.format(new Date());
        return timeStamp;
    }

    public void verifyFileIntegrity(boolean isCritical)
    {
        createNewFile();
        checkCorruptedFile(isCritical);
    }

    public  Map<Integer,Task> loadTask()
    {
        File jsonfile=new File(FILE);
        Map<Integer,Task> data=new ConcurrentHashMap<>();
        if(!jsonfile.exists())
        {
            AppLogger.getInstance().logWarning("tasks.json not found. Returning an empty task list.");
            return  data;
        }

        try(FileReader reader=new FileReader(jsonfile)) {
            data = new Gson().fromJson(reader,new TypeToken<Map<Integer,Task>>(){}.getType());
            if(data==null)
            {
                data=new HashMap<>();
                AppLogger.getInstance().logWarning("tasks.json was empty or contained invalid data.");
            }

        }catch (JsonSyntaxException e)
        {
            AppLogger.getInstance().logError("Corrupted JSON detected in tasks.json", e);
            checkCorruptedFile(false);
            return new ConcurrentHashMap<>();
        }
        catch (IOException e)
        {
            AppLogger.getInstance().logError("Error accessing tasks.json", e);

        }
        return  data;
    }

    public void storeTasks(Map<Integer,Task> data) {
        File jsonfile = new File(FILE);
        if (!jsonfile.exists()) {
            AppLogger.getInstance().logWarning("tasks.json not found.Creating a new file.");
            createNewFile();

        }
        try (Writer writer = new FileWriter(jsonfile)) {
            new Gson().toJson(data,writer);
            AppLogger.getInstance().logInfo("Successfully stored tasks.json.");
        }
        catch (IOException e)
        {
            AppLogger.getInstance().logError("Error accessing tasks.json",e);
        }
    }


}
