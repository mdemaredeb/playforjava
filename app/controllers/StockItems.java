package controllers;

import com.avaje.ebean.Page;
import models.Product;
import models.StockItem;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collections;
import java.util.List;

public class StockItems extends Controller {

    public static Result index() {
        List<StockItem> items = StockItem.find.where().ge("quantity",300).orderBy("quantity desc").setMaxRows(10).findList();
        //ge = greater or equal
        String str = new String("");
        for (StockItem i : items) {
            str+=i + "\n";
        }
        return ok(str);
    }

}