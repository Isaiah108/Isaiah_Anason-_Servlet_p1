package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.ORM;
import com.revature.services.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Class<?> objectClassType = ServletHelper.getClass(req.getHeader("Object-Type"));
        String requestType = req.getHeader("Request-Type");
        ObjectMapper mapper = new ObjectMapper();

        if(objectClassType.getSimpleName().equals("User")){
            if(requestType.equals("single record")){
                Object obj = ORM.readRecordByID(objectClassType, req.getHeader("Primary-Key"));
                resp.getWriter().println(mapper.writeValueAsString(obj));
                resp.setStatus(200);
            }
            else if (requestType.equals("all")){
                List<Object> objects = ORM.readAll(objectClassType);
                for(Object obj: objects){
                    resp.getWriter().println(mapper.writeValueAsString(obj));
                }
                resp.setStatus(200);
            }
            else{
                resp.getOutputStream().println("<h2>Failed to find Objects</h2>");
                resp.setStatus(403);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));

        Class<?> objectClassType = ServletHelper.getClass(req.getHeader("Object-Type"));
        ObjectMapper mapper = new ObjectMapper();
        boolean recordUpdated = false;
        Object obj = null;

        if (contentType.equals("application/json")&&objectClassType.getSimpleName().equals("User")) {
            try {
                obj = mapper.readValue(collectorBody, objectClassType);
                if (ORM.updateRecord(obj))
                    recordUpdated = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (recordUpdated) {
            resp.setStatus(201);
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().println(mapper.writeValueAsString(obj));
        } else {
            resp.setStatus(200);
            resp.getOutputStream().println("<h2>Invalid Object to update</h2>");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));

        Class<?> objectClassType = ServletHelper.getClass(req.getHeader("Object-Type"));
        ObjectMapper mapper = new ObjectMapper();
        boolean recordAdded = false;
        Object obj = null;

        if (contentType.equals("application/json")&&objectClassType.getSimpleName().equals("User")) {
            try {
                obj = mapper.readValue(collectorBody, objectClassType);
                if (ORM.addRecord(obj))
                    recordAdded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (recordAdded) {
            resp.setStatus(201);
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().println(mapper.writeValueAsString(obj));
        } else {
            resp.setStatus(200);
            resp.getOutputStream().println("<h2>Invalid Object to add</h2>");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String primaryKeyToDelete = req.getHeader("Primary-Key");
        Class<?> objectClassType = ServletHelper.getClass(req.getHeader("Object-Type"));
        if(req.getHeader("Object-Type").equals("User")){
            if(ORM.deleteRecordPrimaryKey(objectClassType, primaryKeyToDelete)){
                resp.getOutputStream().println("Deleted Successfully");
                resp.setStatus(200);
            }
            else{
                resp.getOutputStream().println("Failed to delete");
                resp.setStatus(400);
            }
        }
        else{
            resp.getOutputStream().println("Failed to delete");
            resp.setStatus(400);
        }
    }
}
