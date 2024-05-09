import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.UserEntity;

@WebServlet(name = "/Login", urlPatterns = "/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //System.out.println(request.getParameter("name"));
        //System.out.println(request.getParameter("pass"));
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

//        UserDao userDao = new UserDao();
//        if (userDao.checkLogin(name, pass)) {
//            response.sendRedirect("welcome.jsp");
//        } else {
//            response.sendRedirect("index.jsp");
//        }
        String redirectUrl = null;

        UserDao userDao = new UserDao();

        if (userDao.checkAdminLogin(name, pass)) {
            redirectUrl = "admin.jsp"; // Здесь указываете URL для перенаправления на страницу администратора
        } else if (userDao.checkLogin(name, pass)) {
            String role = userDao.getUserRole(name, pass);
            redirectUrl = getRedirectUrl(role);
        } else {
            redirectUrl = "index.jsp";
        }


        response.sendRedirect(request.getContextPath() + redirectUrl); // Перенаправляем пользователя на соответствующий URL
    }


    protected String getRedirectUrl(String role) {
        // Определяем URL в зависимости от роли
        if (role == null) {
            // Обработка случая, когда значение role равно null
            return "newbie.jsp"; // Или другой URL по вашему выбору
        } else {
            switch (role) {
                case "junior":
                    return "roles/junior.jsp";
                case "middle":
                    return "roles/middle.jsp";
                case "senior":
                    return "roles/senior.jsp";
                default:
                    return "roles/newbie.jsp";
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

