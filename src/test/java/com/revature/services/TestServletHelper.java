package com.revature.services;

import com.revature.models.Dog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TestServletHelper {
    HttpServletRequest req;
    HttpServletResponse resp;

//    @Test
//    public void readingObjectsAllTest() throws IOException{
//        Class<Dog> classType = Dog.class;
//        List<Object> returnObjects = new ArrayList<>();
//        ServletHelper servletHelper = new ServletHelper();
//        returnObjects.add(new Dog(1,"Ari","Great Dane", 6));
//        returnObjects.add(new Dog(2,"Koa","King Charles",1));
//
//        Mockito.when(req.getParameter("Request-Type")).thenReturn("all");
//        Mockito.when(req.getParameter("Primary-Key")).thenReturn("2");
//        Mockito.when(ORM.readAll(classType)).thenReturn(returnObjects);
//
//    }
//
//    @Test
//    public void readingObjectSingleTest() throws IOException {
//        Class<Dog> classType = Dog.class;
//        ServletHelper servletHelper = new ServletHelper();
//        Object returnObject = new Dog(1,"Ari","Great Dane", 6);
//
//        Mockito.when(req.getParameter("Request-Type")).thenReturn("single");
//        Mockito.when(req.getParameter("Primary-Key")).thenReturn("2");
//        Mockito.when(ORM.readRecordByID(classType,"2")).thenReturn(returnObject);
//
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//
//        Mockito.when(resp.getWriter()).thenReturn(pw);
//
//        servletHelper.readingObjects(req,resp,classType);
//        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
//
//        Assertions.assertFalse(result.isEmpty());
//    }
}
