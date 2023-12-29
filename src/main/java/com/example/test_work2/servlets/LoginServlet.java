package com.example.test_work2.servlets;

import com.example.test_work2.config.FreemarkerConfig;
import com.example.test_work2.models.User;
import com.example.test_work2.repository.UserRepository;
import com.example.test_work2.repository.UserRepositoryImpl;
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
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> root = new HashMap<>();
        root.put("title", " Вход");
        root.put("context_path", request.getContextPath());
        response.setCharacterEncoding("UTF-8");
        Template tmpl = FreemarkerConfig.getConfig(this.getServletContext()).getTemplate("login.ftl");
        try{
            tmpl.process(root, response.getWriter());
        }catch (TemplateException e){
            throw new RuntimeException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        userRepository = new UserRepositoryImpl();

        String username = request.getParameter("login");
        String password = request.getParameter("password");


        Optional<User> userOptional = userRepository.getUserByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();


            if (user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                response.sendRedirect(request.getContextPath() + "/login?error=invalid_credentials");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login?error=user_not_found");
        }
    }
}