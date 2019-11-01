package servlet;

import Controller.GameController;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

public class PakuPaku extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static int lastFrameId = -1;
    private GameController control;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       /* int requestFrameId = Integer.parseInt(request.getParameter("frameId"));
        control.receivedFrame(requestFrameId);
        System.out.println("Frame " + requestFrameId);
        if (request.getParameter("keycode") != null)
            System.out.println("Keycode: " + request.getParameter("keycode"));

        control.receivedUserInput(request.getParameter("keycode"));
        JSONObject dataToSend = null;
        control.update();
        if(control != null)
        {
            dataToSend = control.getDataToSend();
        }*/

       // Prepare Response writer
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();


        GameController controller;
        HttpSession session = request.getSession();
        if(session.isNew())
        {
            controller = new GameController(); // create a new controller for new sessions
            controller.init();
        }
        else
            controller = (GameController)session.getAttribute("controller");


        String requestType = request.getParameter("type");
        System.out.println("Received request of type " + requestType);


        System.out.println("Session ID: " + request.getSession().getId());
        out.print("{\"data\": \"Success! This is the Response From a Servlet!!!\"}");
        out.flush();
        out.close();

        //}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("ERROR: Received GET Request");
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "The Server does not support GET requests");
    }

    public void init() {
        System.out.println("PAKU PAKU Server has Started Succesfully");
    }
}
