package controllers;

import play.*;
import play.libs.Comet;
import play.mvc.*;
import play.mvc.Results.*;
import play.mvc.Results.Chunks;
import play.data.Form;

import views.html.*;

import models.*;

import javax.validation.constraints.NotNull;
import java.util.*;



public class Application extends Controller {

    public static Result index() {
        return ok(index.render("live stream"));
    }

    public static Result liveUpdate() {
        // Prepare a chunked text stream
       /* Chunks<String> chunks = new StringChunks() {

            // Called when the stream is ready
            public void onReady(Chunks.Out<String> out) {
                ExpeditedOrders.registerChunkOut(out);
            }

        };
        response().setContentType("text/html;charset=UTF-8");
        return ok(chunks);*/
        Comet comet = new Comet("parent.cometMessage") {
            public void onConnected() {
                ExpeditedOrders.registerChunkOut(this);
            }
        };
        return ok(comet);
    }

    public static class Login {
        public String email;
        public String password;

    }
    public static Result login() {
        return ok(
                login.render(Form.form(Login.class))
        );
    }
    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        String email = loginForm.get().email;
        String password = loginForm.get().password;
        if (User.authenticate(email, password) == null){
            flash("error", "Invalid password or email.");
            ///Login newLogin = new Login();newLogin.email = email;
            //return forbidden(login.render(loginForm));
            return redirect(routes.Application.login());
        }
        session().clear();
        session("email", email);
        System.out.println(session("email"));
        return redirect(routes.Products.index());
    }


}