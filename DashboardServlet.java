import java.io.IOException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/DashboardServlet")
public class DashboardServlet<HttpServletRequest, HttpServletResponse> extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Check if user is logged in (session)
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        if (username == null) {
            // Not logged in, redirect to login page
            response.sendRedirect("login.html");
            return;
        }
        // 2. Create a list of courses (hardcoded)
        List<Course> courses = Arrays.asList(
                new Course("MAT101", "Mathematics", "Dr. Smith"),
                new Course("PHY102", "Physics", "Dr. Johnson"),
                new Course("CHE103", "Chemistry", "Dr. Brown"),
                new Course("CSC104", "Computer Science", "Dr. Allen")
                );
        // 3. Store courses in request attribute
        request.setAttribute("username", username);
        request.setAttribute("courses", courses);
        // 4. Forward to dashboard.jsp
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}