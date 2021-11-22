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
import java.util.stream.Collectors;

@WebServlet("/dog")
public class DogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getHeader("Content-Type");
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));

        Class<?> objectClassType = ServletHelper.getClass(req.getHeader("Object-Type"));
        ObjectMapper mapper = new ObjectMapper();
        boolean recordUpdated = false;
        Object obj = null;

        if (contentType.equals("application/json")) {
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

        if (contentType.equals("application/json")) {
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
        String primaryKeyToDelete = req.getParameter("Primary-Key");
    }
}
