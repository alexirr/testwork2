package com.example.test_work2.servlets;

import com.example.test_work2.config.FreemarkerConfig;
import com.example.test_work2.models.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("")
public class WhoAmIServlet  extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, Object> root = new HashMap<>();
        root.put("title", "Главная страница");
        root.put("context_path", request.getContextPath());
        User user = (User)request.getSession().getAttribute("user");
        if(user != null){
            root.put("username", user.getUserName());
        }else{
            root.put("username", "Anon");
        }
        response.setCharacterEncoding("UTF-8");
        Template tmpl = FreemarkerConfig.getConfig(this.getServletContext()).getTemplate("main.ftl");
        try{
            tmpl.process(root, response.getWriter());
        }catch (TemplateException e){
            throw new RuntimeException();
        }
    }
}
