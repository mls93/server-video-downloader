package serverrequests; /**
 * Created by MLS on 23.08.2015.
 */

import parsing.MyMP4Parser;
import songwordsretrieving.FirstWordsRetriever;
import songwordsretrieving.WordsRetriever;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class RetrieveWords extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        WordsRetriever retriever = new FirstWordsRetriever(request.getParameter("title"));
        String result = retriever.retrieveWords();

        out.println(result);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}