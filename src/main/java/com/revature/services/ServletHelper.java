package com.revature.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ServletHelper {
    public static void readingObjects(HttpServletRequest req, HttpServletResponse resp, Class<?> classType) throws IOException {
        String requestType = req.getParameter("Request-Type");
        ObjectMapper mapper = new ObjectMapper();
        if(requestType==null)return;

        if (requestType.equals("single")) {
            Object obj = ORM.readRecordByID(classType, req.getParameter("Primary-Key"));
            resp.getWriter().println(mapper.writeValueAsString(obj));
            resp.setStatus(200);
        } else if (requestType.equals("all")) {
            List<Object> objects = ORM.readAll(classType);
            for (Object obj : objects) {
                resp.getWriter().println(mapper.writeValueAsString(obj));
            }
            resp.setStatus(200);
        } else {
            resp.getWriter().println("Request-type:" + requestType);
            resp.getWriter().println(" Primary-Key:" + req.getParameter("Primary-Key"));
            resp.getWriter().println("<h2>Failed to find Objects</h2>");
            resp.setStatus(403);
        }


    }

    public static void updatingObjects(HttpServletRequest req, HttpServletResponse resp, Class<?> classType) throws IOException {
        String contentType = req.getHeader("Content-Type");
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));
        ObjectMapper mapper = new ObjectMapper();
        boolean recordUpdated = false;
        Object obj = null;

        if (contentType.equals("application/json")) {
            try {
                obj = mapper.readValue(collectorBody, classType);
                if (ORM.updateRecord(obj))
                    recordUpdated = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (recordUpdated) {
            resp.setStatus(200);
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().println(mapper.writeValueAsString(obj));
        } else {
            resp.setStatus(403);
            resp.getOutputStream().println("<h2>Invalid Object to update</h2>");
        }
    }

    public static void creatingObjects(HttpServletRequest req, HttpServletResponse resp, Class<?> classType) throws IOException {
        String contentType = req.getHeader("Content-Type");
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));

        ObjectMapper mapper = new ObjectMapper();
        boolean recordAdded = false;
        Object obj = null;

        if (contentType.equals("application/json")) {
            try {
                obj = mapper.readValue(collectorBody, classType);
                if (ORM.addRecord(obj)) {
                    recordAdded = true;
                    resp.getWriter().println("Added Record");
                }
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

    public static void deletingObjects(HttpServletRequest req, HttpServletResponse resp, Class<?> classType) throws IOException {
        String primaryKeyToDelete = req.getParameter("Primary-Key");
        if (primaryKeyToDelete != null) {
            if (ORM.deleteRecordPrimaryKey(classType, primaryKeyToDelete)) {
                resp.getOutputStream().println("Deleted Successfully");
                resp.setStatus(200);
            } else {
                resp.getOutputStream().println("Failed to delete");
                resp.setStatus(400);
            }
        } else {
            resp.getOutputStream().println("Didn't provide Primary-Key");
            resp.setStatus(400);
        }
    }
}
