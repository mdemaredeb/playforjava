package controllers;

import com.avaje.ebean.Page;
import com.google.common.io.Files;
import models.Product;
import models.Tag;
import play.data.Form;
import play.mvc.*;
import securesocial.core.java.SecureSocial;
import views.html.details;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@With(CatchAction.class)
public class Products extends Controller {

    private static final Form<Product> productForm = Form.form(Product.class);

    /*@BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
        RequestBody body = request().body();
        return ok("We expected to get json: " + body.asJson());
    }*/
    public static Result index() {
        return redirect(routes.Products.list(0));
    }

    public static Result listAll() {
       /* if (request().accepts("text/plain")) {
            return ok(StringUtils.join(Product.findAll(), "\n"));
        }*/
        response().setContentType("text/html; charset=utf-8");
        response().setCookie("theme", "blue");
        response().discardCookie("theme");

        return ok(/*list.render(Product.findAll())*/);
    }

    public static Integer getCantProd() {
        return Product.find.findRowCount();
    }

    public static Result list(Integer page) {
        Page<Product> products = Product.find(page);

        return ok(views.html.catalog.render(products));
    }
    @Security.Authenticated(Secured.class)
    //@SecureSocial.SecuredAction
    public static Result newProduct() {
        return ok(details.render(productForm));
    }
    @Security.Authenticated(Secured.class)
    public static Result details(Product product) {
        Form<Product> filledForm = productForm.fill(product);
        return ok(details.render(filledForm));
    }/*
    public static Result details(String product) {
        Form<Product> filledForm = productForm.fill(Product.findByEan(product));
        return ok(details.render(filledForm));
    }*/
    @Security.Authenticated(Secured.class)
    public static Result save() {
        Http.MultipartFormData body = request().body().asMultipartFormData();

        //                                                 Hace la validacion por pasos
        Form<Product> boundForm = Form.form(Product.class, Product.Step2.class).bindFromRequest();
        Form<Product> step1 = Form.form(Product.class, Product.Step1.class).bindFromRequest();

        if (boundForm.hasErrors() || step1.hasErrors()) {
            flash("error", "Please correct the form below.");
            System.out.println(boundForm.errors());
            return badRequest(details.render(boundForm));
        }

        Product product = boundForm.get();
        Http.MultipartFormData.FilePart part = body.getFile("picture");
        if (part != null) {
            File picture = part.getFile();
            try {
                product.picture = Files.toByteArray(picture);
            } catch (IOException e) {
                return internalServerError("Error reading file upload");
            }
        }

        List<Tag> tags = new ArrayList<Tag>();
        for (Tag tag : product.tags) {
            if (tag.id != null) {
                tags.add(Tag.findById(tag.id));
            }
        }
        product.tags = tags;

        if (product.id == null) {

            product.save();     // or Ebean.save(product);
        } else {
            product.update();
        }

        //StockItem item = new StockItem();
        //item.quantity = 0L;
        //item.product = product;
        //item.save();
        flash("success",
                String.format("Successfully added product %s", product));
        return redirect(routes.Products.list(0));
    }
    @Security.Authenticated(Secured.class)
    public static Result picture(String ean) {
        final Product product = Product.findByEan(ean);
        if (product == null) return notFound();
        System.out.println(product.picture);
        return ok(product.picture);
    }
    @Security.Authenticated(Secured.class)
    public static Result delete(String ean) {
        final Product product = Product.findByEan(ean);
        if (product == null) {
            return notFound(String.format("404 - Product %s does not exist.", ean));
        }
        product.delete();
        return redirect(routes.Products.list(0));
    }


}
