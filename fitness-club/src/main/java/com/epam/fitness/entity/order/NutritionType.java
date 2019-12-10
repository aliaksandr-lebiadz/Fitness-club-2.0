package com.epam.fitness.entity.order;

public enum NutritionType {

    LOW_CALORIE("low calorie"),
    MEDIUM_CALORIE("medium calorie"),
    HIGH_CALORIE("high calorie");

    private String value;

    NutritionType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NutritionType getNutritionType(String value){
        for(NutritionType type : values()){
            String typeValue = type.getValue();
            if(typeValue.equals(value)){
                return type;
            }
        }
        return null;
    }
}