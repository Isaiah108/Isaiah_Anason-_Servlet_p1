package com.revature.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BasicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //resp.setHeader("Content-Type","text/html");
        resp.setStatus(200);
        resp.getOutputStream().println("<b><u>Basic Servlet Example");
    }
}
