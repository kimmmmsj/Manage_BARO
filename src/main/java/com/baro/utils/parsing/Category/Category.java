package com.baro.utils.parsing.Category;

public class Category {
    public String category_name;
    public int category_id;

    @Override
    public String toString() {
        return "Category{" +
                "category_name='" + category_name + '\'' +
                ", category_id=" + category_id +
                '}';
    }

    public Category(String category_name, int category_id) {
        this.category_name = category_name;
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
