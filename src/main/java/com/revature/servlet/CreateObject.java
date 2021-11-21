package com.revature.servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.models.*;
import com.revature.services.ORM;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet(urlPatterns = "/create")
public class CreateObject extends HttpServlet {
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
        boolean recordAdded = false;
        Object obj = null;

        for (Class<?> clazz : classTypes) {
            if (req.getHeader("Object_Type").equals(clazz.getSimpleName()))
                objectClassType = clazz;
        }
        if (contentType.equals("application/json")) {
            try {
                obj = mapper.readValue(collectorBody, objectClassType);
                if (ORM.addRecord(obj))
                    recordAdded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        if (recordAdded) {
            resp.setStatus(201);
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().println(mapper.writeValueAsString(obj));
        } else {
            resp.setStatus(200);
            resp.getOutputStream().println("<h2>Invalid Object to add</h2>");
        }

    }
}
