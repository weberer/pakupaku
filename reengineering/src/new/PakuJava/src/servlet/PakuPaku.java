package servlet;

import Controller.GameController;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

public class PakuPaku extends HttpServlet {

    private static int lastFrameId = -1;
    private GameController control;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int requestFrameId = Integer.parseInt(request.getParameter("frameId"));

        //if(requestFrameId != ++lastFrameId)
        //    response.sendError(HttpServletResponse.SC_CONFLICT); // Frame was missing, indicating that the game has entered an invalid state
        //else {
            System.out.println("Frame " + requestFrameId);
            if (request.getParameter("keycode") != null)
                System.out.println("Keycode: " + request.getParameter("keycode"));

            JSONObject dataToSend = null;
            if(control != null)
            {
                dataToSend = control.getDataToSend();
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            out.print("{\"responseData\": \"Success! This is the Response From a Servlet!!!\"}");
            if(dataToSend != null)
                out.print(dataToSend.toString());
            out.flush();
            out.close();

        //}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("ERROR: Received GET Request");
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "The Server does not support GET requests");
    }

    public void init() {
        System.out.println("Server is Starting...");
        control = new GameController();
        control.startGame();
    }
}
