package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Dog;
import com.revature.models.User;
import com.revature.services.ORM;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateObject extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String objectType = req.getHeader("Object_Type");
        String contentType = req.getHeader("Content-Type");
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));

        List<Class<?>> classTypes = new ArrayList<>();
        classTypes.add(Dog.class);
        classTypes.add(User.class);
        Class<?> objectClassType = null;
        ObjectMapper mapper = new ObjectMapper();
        boolean recordUpdated = false;
        Object obj = null;

        for (Class<?> clazz : classTypes) {
            if (req.getHeader("Object_Type").equals(clazz.getSimpleName()))
                objectClassType = clazz;
        }

        if (contentType.equals("application/json")) {
            try {
                obj = mapper.readValue(collectorBody, objectClassType);
                if (ORM.updateRecord(obj))
                    recordUpdated = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
