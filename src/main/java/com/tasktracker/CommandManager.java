package com.tasktracker;

import java.util.Arrays;

public class CommandManager {
    private  TaskRegistry taskRegistry;

    public CommandManager(TaskRegistry taskRegistry)
    {
        this.taskRegistry=taskRegistry;
    }
    public void processCommand(String s)
    {
        String[] task=s.split(" ");
        String description;

        switch (task[0].toLowerCase())
        {
            case "add":
                description=String.join(" ", Arrays.copyOfRange(task,1,task.length));
                taskRegistry.addTask(new Task(description));
                break;
            case "delete":
                taskRegistry.removeTask(Integer.parseInt(task[1]));
                break;
            case "update":
                description=String.join(" ", Arrays.copyOfRange(task,2,task.length));
                taskRegistry.updateTask(Integer.parseInt(task[1]),description)  ;
                break;
            case "mark-in-progress":
                taskRegistry.markInProgress(Integer.parseInt(task[1]));
                break;
            case "mark-done":
                taskRegistry.markDone(Integer.parseInt(task[1]));
                break;
            case "list":
                if(task.length==1)
                    taskRegistry.displayAllTasks();
                else
                    taskRegistry.displayAllTasks(task[1]);
                break;
            default:
                System.out.println(" add \"description\"" +
                        " \n update \"task id\" \"description\"" +
                        " \n mark-in-progress \"task id\" " +
                        "\n mark-done \"task id\" " +
                        "\n list " +
                        "\n list done " +
                        "\n list todo " +
                        "\n list in-progress");


        }
    }
}
