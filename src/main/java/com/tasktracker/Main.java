package com.tasktracker;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner s=new Scanner(System.in);
        TaskRegistry taskRegistry=new TaskRegistry();
        CommandManager commandManager=new CommandManager(taskRegistry);
        while(true)
        {
            System.out.println("Enter command:");
            String command=s.nextLine();

            if(command.equalsIgnoreCase("exit"))
                break;

            commandManager.processCommand(command);

        }
    }
}