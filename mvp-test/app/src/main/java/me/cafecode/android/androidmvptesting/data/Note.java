package me.cafecode.android.androidmvptesting.data;

/**
 * Created by Natthawut Hemathulin on 4/15/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class Note {

    private int id;
    private String title;
    private String description;

    public Note() {
    }

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
