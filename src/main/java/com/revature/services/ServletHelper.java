package com.revature.services;

import com.revature.models.*;

import java.util.ArrayList;
import java.util.List;

public class ServletHelper {
    public static Class<?> getClass(String classString){
        List<Class<?>> classTypes = new ArrayList<>();
        classTypes.add(Dog.class);
        classTypes.add(User.class);
        classTypes.add(Person.class);

        for (Class<?> clazz : classTypes) {
            if (classString.equals(clazz.getSimpleName()))
                return clazz;
        }
        return null;
    }
}
