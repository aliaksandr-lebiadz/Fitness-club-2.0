package com.epam.fitness.tag.localizer;

import com.epam.fitness.entity.order.NutritionType;
import javax.servlet.jsp.JspException;

public class NutritionTypeLocalizerTag extends AbstractLocalizer {

    private static final String LOW_CALORIE_NUTRITION = "low_calorie_nutrition";
    private static final String MEDIUM_CALORIE_NUTRITION = "medium_calorie_nutrition";
    private static final String HIGH_CALORIE_NUTRITION = "high_calorie_nutrition";

    @Override
    protected String getAttributeName(String nutritionTypeValue) throws JspException {
        NutritionType nutritionType = NutritionType.valueOf(nutritionTypeValue);
        String attributeName;
        switch (nutritionType) {
            case LOW_CALORIE:
                attributeName = LOW_CALORIE_NUTRITION;
                break;
            case MEDIUM_CALORIE:
                attributeName = MEDIUM_CALORIE_NUTRITION;
                break;
            case HIGH_CALORIE:
                attributeName = HIGH_CALORIE_NUTRITION;
                break;
            default:
                throw new JspException("Invalid nutrition type value: " + nutritionTypeValue);
        }
        return attributeName;
    }
}
