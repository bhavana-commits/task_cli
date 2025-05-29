package com.tasktracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String description;
    private Status status;
    private String createdAt;
    private String updatedAt;


    public Task(String description)
    {
        this.id=getUniqueNo();
        this.description=description;
        this.status=Status.TODO;
        this.createdAt=LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.updatedAt=LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private static int getUniqueNo()
    {
        int id=1;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("uniqueid.txt"))){
            String line=bufferedReader.readLine();
            if(line!=null && !line.isEmpty())
            {
                id=Integer.parseInt(line.trim());
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());

        }

        try(FileWriter fileWriter =new FileWriter("uniqueid.txt",false))
        {
         fileWriter.write(String.valueOf(id+1));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task ID: " + id + ", Description: " + description + ", Status: " + status;
    }
}
