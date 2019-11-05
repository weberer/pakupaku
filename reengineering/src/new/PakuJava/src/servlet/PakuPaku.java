package servlet;

import Controller.GameController;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

public class PakuPaku extends HttpServlet {

    private static JSONObject getEmptyJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("empty", "empty");
        return obj;
    }

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
            JSONObject obj = getEmptyJSON();

            switch (requestType) {
                case "menu":
                    obj = controller.getHighScoreList();
                    controller.resetGame();
                    break;
                case "game_ready":
                    obj = controller.getHighScore();
                    break;
                case "start_game":
                    controller.startGame();
                    break;
                case "input":
                    String inputData = request.getParameter("input");
                    controller.receivedUserInput(inputData);
                    break;
                case "send_frame":
                    controller.update();
                    obj = controller.getDataToSend();
                    break;
                default:
                    obj = new JSONObject().put("default", "response");
            }

            // attach and send data
            out.print("{\"data\":" + obj.toString() + "}");
        }
        catch(Exception e) {
            System.out.println("Exception in Servlet PakuPaku.");
            e.printStackTrace();
        }
        finally
        {
            // properly close output stream
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
