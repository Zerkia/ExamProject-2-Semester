package examProject2.web;

import examProject2.domain.ExamProjectException;
import examProject2.domain.models.Project;
import examProject2.domain.services.ProjectService;
import examProject2.repositories.ProjectRepositoryImplemented;
import examProject2.repositories.UserRepositoryImplemented;
import examProject2.domain.models.User;
import examProject2.domain.services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {
    private UserService userService = new UserService(new UserRepositoryImplemented());

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/createUser")
    public RedirectView createUser(WebRequest request) throws ExamProjectException {
        String username = request.getParameter("username");
        String password1 = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if(password1.equals(password2)){
            User user = userService.createUser(username, password1, 3);
            request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
            return new RedirectView("login");
        }
        else{
            throw new ExamProjectException("wrong");
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public RedirectView loginUser(WebRequest request) throws ExamProjectException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.login(username, password);

        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);

        return new RedirectView("mainPage");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/");
    }
}
