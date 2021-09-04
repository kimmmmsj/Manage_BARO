package com.baro.utils.parsing.Category;

import java.util.ArrayList;

public class CategoryList {
    public boolean result;
    public String message;
    public ArrayList<Category> category;

    public CategoryList(boolean result, String message, ArrayList<Category> category) {
        this.result = result;
        this.message = message;
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryList{" +
                "resutl=" + result +
                ", message='" + message + '\'' +
                ", category=" + category +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }
}
