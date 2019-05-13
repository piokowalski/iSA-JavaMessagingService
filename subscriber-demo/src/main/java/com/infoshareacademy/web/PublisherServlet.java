package com.infoshareacademy.web;

import java.io.IOException;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/publisher")
public class PublisherServlet extends HttpServlet {

    // add annotations

    private JMSContext jmsContext;

    private Topic topic;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        final String message = req.getParameter("msg");

        // send message

        resp.getWriter().println("Message sent.");
    }
}
