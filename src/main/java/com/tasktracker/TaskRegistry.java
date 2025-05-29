package com.tasktracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRegistry {

    private  Map<Integer,Task> registry;
    private FileHandler fileHandler;

    public  TaskRegistry()
    {
        fileHandler=new FileHandler();
        this.registry=fileHandler.loadTask();

    }


    public void addTask(Task t)
    {
        registry.put(t.getId(),t);
        fileHandler.storeTasks(registry);

    }

    public Task getTask(Integer id)
    {
        return  registry.get(id);

    }

    public  void removeTask(Integer id)
    {
        registry.remove(id);
        fileHandler.storeTasks(registry);
    }

    public void updateTask(Integer id,String task)
    {
        Task t=registry.get(id);
        t.setDescription(task);
    }

    public void displayAllTasks()
    {
        for(Task task:registry.values())
        {
           System.out.println(task);
        }

    }

    public  void displayAllTasks(String s)
    {
        for(Task task:registry.values())
        {
            if(task.getStatus().toString().equalsIgnoreCase(s))
            {
                System.out.println(task);
            }

        }
    }

    public void markInProgress(Integer id)
    {
        Task t=registry.get(id);
        t.setStatus(Status.IN_PROGRESS);
    }

    public void markDone(Integer id)
    {
        Task t=registry.get(id);
        t.setStatus(Status.DONE);
    }






}
