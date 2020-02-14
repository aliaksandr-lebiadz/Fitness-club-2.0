package com.epam.fitness.utils;

import java.util.Objects;

public class ServiceUtils {

    public static boolean hasAtLeastOneNonNullField(Object... fields){
        for(Object field : fields){
            if(Objects.nonNull(field)){
                return true;
            }
        }
        return false;
    }

}
