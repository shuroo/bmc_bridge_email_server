package controllers;

import play.mvc.*;

/**
 * This is the default play controller, display default view in endpoint "localhost:9000/"
 * Customised according to the exercise.
 *
 *  @author Shiri Rave
 *  @date 13/05/2025
 */

public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

}
