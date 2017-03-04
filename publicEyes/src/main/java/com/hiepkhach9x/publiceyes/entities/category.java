package com.hiepkhach9x.publiceyes.entities;

/**
 * Created by hungh on 3/4/2017.
 */

public class Category {

    private int id;
    private int categoryIcon;
    private String categoryName;

    public Category() {
    }

    public Category(int id, int categoryIcon, String categoryName) {
        this.id = id;
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
