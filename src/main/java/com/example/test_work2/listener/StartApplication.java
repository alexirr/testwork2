package com.example.test_work2.listener;

import com.example.test_work2.repository.DBConnection;
import com.example.test_work2.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartApplication implements ServletContextListener {

    private DBConnection dbConnection;

    public void contextInitialized(ServletContextEvent sce) {

        DBConnection dbConnection = DBConnection.getInstance();
        System.out.println("DBConnection initialized");

        sce.getServletContext().setAttribute("UserService", new UserService());

        System.out.println("Services initialized");
    }

    public void contextDestroyed(ServletContextEvent sce) {

        if (dbConnection != null) {
            dbConnection.destroyConnection();
            System.out.println("DBConnection destroyed");
        }
    }
}

