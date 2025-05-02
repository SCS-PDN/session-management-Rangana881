import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // TODO: Implement logout logic
        // 1. Invalidate session
        HttpSession session = request.getSession(false); // false = don't create if doesn't exist
        if (session != null) {
            session.invalidate();
        }
        // 2. Remove username cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    cookie.setMaxAge(0); // delete cookie
                    cookie.setPath("/"); // ensure it matches original path
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        // 3. Redirect to login.html
        response.sendRedirect("login.html");
    }
}