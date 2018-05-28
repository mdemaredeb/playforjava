package models;

import com.avaje.ebean.Page;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.F;
import play.libs.F.Option;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;
import utils.DateFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Product extends Model implements PathBindable<Product>, QueryStringBindable<Product> {

    @Id
    public Long id;
    @Constraints.Required(groups = Step1.class)
    public String name;
    //@Constraints.Required(groups = Step2.class)
    @Constraints.ValidateWith(value = EanValidator.class, message = "must be 13 numbers", groups = Step2.class)
    public String ean;
    public String description;
    @DateFormat("yyyy-MM-dd")
    public Date date;
    public byte[] picture;

    @ManyToMany
    public List<Tag> tags /*= new LinkedList<Tag>()*/;

    @OneToMany(mappedBy = "product")
    public List<StockItem> stockItems;

    public static Finder<Long, Product> find =
            new Finder<>(Long.class, Product.class);

    public Product() { }

    public Product(String ean, String name, String description) {
        this.ean = ean;
        this.name = name;
        this.description = description;
    }
    public boolean hasDate(){
        if(date != null)return true;
        return false;
    }

    //public static List<Product> findAll() {return new ArrayList<Product>(products);}

    public static Product findByEan(String ean) {
        return find.where().eq("ean", ean).findUnique();
    }

    public static Page<Product> find(int page) {
        return find.where()
                .orderBy("id asc")
                .findPagingList(8)
                .setFetchAhead(false)
                .getPage(page);
    }

    /*public static List<Product> findByName(String term) {
        final List<Product> results = new ArrayList<Product>();
        for (Product candidate : products) {
            if (candidate.name.toLowerCase().contains(term.toLowerCase())) {
                results.add(candidate);
            }
        }
        return results;
    }*/


    //Para que sea path bindable
    @Override
    public Product bind(String key, String value) {
        return findByEan(value);
    }

    @Override
    public String unbind(String key) {
        return this.ean;
    }

    @Override
    public String javascriptUnbind() {
        return this.ean;
    }

    //
    @Override
    public Option<Product> bind(String key, Map<String, String[]> data) {
        return Option.Some(findByEan(data.get("ean")[0]));
    }

    public String toString() {
        return String.format("%s - %s", ean, name);
    }

    /*
    public void save() {
        products.remove(findByEan(this.ean));
        products.add(this);
    }*/

    public interface Step1 {
    }

    public interface Step2 {
    }

    public static class EanValidator extends Constraints.Validator<String> {
        @Override
        public boolean isValid(String value) {
            String pattern = "^[0-9]{5,}$";
            return value != null && value.matches(pattern);
        }

        @Override
        public F.Tuple<String, Object[]> getErrorMessageKey() {
            return new F.Tuple<String, Object[]>("error.invalid.ean",
                    new Object[]{});
        }
    }
}
