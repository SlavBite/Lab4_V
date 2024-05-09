import model.UserEntity;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Register", urlPatterns = "/Register")
public class Register extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем данные из формы
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        String email = request.getParameter("mail");

        // Создаем экземпляр UserDao для взаимодействия с базой данных
        UserDao userDao = new UserDao();

        // Создаем нового пользователя
        UserEntity newUser = new UserEntity();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setEmail(email);

        // Сохраняем пользователя в базе данных
        userDao.saveUser(newUser);
        // Перенаправляем пользователя на страницу приветствия или другую страницу
        response.sendRedirect("welcome.jsp");
    }
}
