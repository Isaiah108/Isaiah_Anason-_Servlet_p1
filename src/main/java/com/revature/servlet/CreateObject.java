package com.revature.servlet;


import java.io.IOException;
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
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));
        System.out.println(collectorBody);

        String objectType = req.getHeader("Object_Type");

        ObjectMapper mapper = new ObjectMapper();

        switch (objectType) {
            case "Dog":
                try {
                    Dog dog = mapper.readValue(collectorBody, Dog.class);
                    ORM.makeTable(Dog.class);
                    ORM.addRecord(dog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resp.setStatus(200);
                break;
            case "User":
                try{
                    User user = mapper.readValue(collectorBody,User.class);
                    ORM.addRecord(user);
                }catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }
}
