package com.example.android.baker.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    public double quantity;
    public String measure;
    @SerializedName("ingredient")
    public String name;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(" • %.2f %s of %s", quantity, measure, name);
    }
}
