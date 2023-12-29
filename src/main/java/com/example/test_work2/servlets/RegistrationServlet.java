package com.example.test_work2.servlets;

import com.example.test_work2.config.FreemarkerConfig;
import com.example.test_work2.models.User;
import com.example.test_work2.service.UserService;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("UserService");
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, Object> root = new HashMap<>();
        root.put("title", " Регистрация");
        root.put("context_path", request.getContextPath());
        response.setCharacterEncoding("UTF-8");
        Template tmpl = FreemarkerConfig.getConfig(this.getServletContext()).getTemplate("regpage.ftl");
        try{
            tmpl.process(root, response.getWriter());
        }catch (TemplateException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("login");
        String password = request.getParameter("password");

        if (!validateUsername(username)) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "This login is already in use, or you entered an empty login");
            response.sendRedirect(request.getContextPath() + "/registration");
            return;
        }

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);

        userService.saveUser(user);

        HttpSession session = request.getSession();

        session.setAttribute("user", user);

        response.sendRedirect("./login");
    }

    private boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        Optional<User> existingUser = userService.findByUsername(username);
        return existingUser.isEmpty();
    }
}
