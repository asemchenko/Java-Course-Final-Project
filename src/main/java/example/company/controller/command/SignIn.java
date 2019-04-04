package example.company.controller.command;

import example.company.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class SignIn implements Command {
    private UserService userService;

    public SignIn(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        // TODO проверка логина и пароля
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (userService.signIn(email, password)) {
            // TODO положить инфу о пользователе в сессию
            System.out.println("Successfully login");
            return "/index.jsp";
        } else {
            // TODO передай ошибку где-то тут
            System.out.println("Error: invalid login/password");
            return "/signIn.jsp";
        }
    }
}
