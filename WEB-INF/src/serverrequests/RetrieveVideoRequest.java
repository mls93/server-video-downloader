package serverrequests;

import videodownloader.HighestQVideoDownloader;
import videodownloader.VideoDownloader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by MLS on 25.08.2015.
 */
public class RetrieveVideoRequest extends HttpServlet {
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.println("Error");
        String title = request.getParameter("title");
        String artist = request.getParameter("artist");
        String extra = request.getParameter("extra");

        VideoDownloader vd1 = new HighestQVideoDownloader(title,artist,extra);
        vd1.downloadVideo();

        response.sendRedirect("main");
    }
}
