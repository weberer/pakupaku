package servlet;

import Controller.GameController;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

public class PakuPaku extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // setup response writer
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {

            GameController controller;
            HttpSession session = request.getSession();
            if (session.isNew()) {
                controller = new GameController(); // create a new controller for new sessions
                session.setAttribute("controller", controller); // I believe the object pointer is stored, not a serialized version of the controller, so it shouldn't need to be re-added to the session
                System.out.println("New Session!");
            } else
               controller = (GameController) session.getAttribute("controller");


            String requestType = request.getParameter("type");
            System.out.println("Received request of type: " + requestType);
            JSONObject obj;

            switch (requestType) {
                case "menu":
                    obj = controller.getHighScoreList();
                    break;
                case "game_ready":
                    obj = controller.getHighScore();
                    break;
                case "start_game":
                    controller.startGame();
                    obj = new JSONObject(); // no data to return here.
                    obj.put("empty", "empty"); // there needs to be something in it, otherwise the parser throws a fit.
                    break;
                default:
                    obj = new JSONObject().put("default", "response2");
            }

            System.out.println("New Game Status:" + controller.getGameStatus());
            out.print("{\"data\":" + obj.toString() + "}");
        }
        catch(Exception e) {
            System.out.println("Exception in Servlet PakuPaku.");
            e.printStackTrace();
        }
        finally
        {
            out.flush();
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("ERROR: Received GET Request");
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "The Server does not support GET requests");
    }

    public void init() {
        System.out.println("PAKU PAKU Server has Started Succesfully");
    }
}
