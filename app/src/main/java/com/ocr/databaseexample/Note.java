package com.ocr.databaseexample;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Florence LE BOURNOT on 12/03/2020
 */
@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public int priority;

    //Dans constructor nom paramètre = nom donnée ne pas rajouter de p pour paramètre
    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int pId) { id = pId; }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public int getPriority() { return priority; }

    public void setTitle(String pTitle) { title = pTitle; }

    public void setDescription(String pDescription) { description = pDescription; }

    public void setPriority(int pPriority) { priority = pPriority; }
}
