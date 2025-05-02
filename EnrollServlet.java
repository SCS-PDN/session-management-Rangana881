import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.Course;

@WebServlet("/EnrollServlet")
public class EnrollServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get courseId from URL parameter
        String courseId = request.getParameter("courseId");

        if (courseId == null || courseId.trim().isEmpty()) {
            response.sendRedirect("DashboardServlet");
            return;
        }
        // 2. Get current user's session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }
        // 3. Add course to enrolled list in session
        List<Course> availableCourses = getAvailableCourses();
        Course selectedCourse = null;
        for (Course course : availableCourses) {
            if (course.getId().equals(courseId)) {
                selectedCourse = course;
                break;
            }
        }
        if (selectedCourse != null) {
            List<Course> enrolledCourses = (List<Course>) session.getAttribute("enrolledCourses");
            if (enrolledCourses == null) {
                enrolledCourses = new ArrayList<>();
            }

            boolean alreadyEnrolled = enrolledCourses.stream()
                    .anyMatch(c -> c.getId().equals(courseId));

            if (!alreadyEnrolled) {
                enrolledCourses.add(selectedCourse);
                session.setAttribute("enrolledCourses", enrolledCourses);
            }
        }
        // 4. Redirect back to DashboardServlet
        response.sendRedirect("DashboardServlet");
    }
}