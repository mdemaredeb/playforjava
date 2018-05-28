package models;

import play.data.validation.Constraints;
import play.db.ebean.Model.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.*;

@Entity
public class Tag {

    @Id
    public Long id;
    @Constraints.Required
    public String name;

    @ManyToMany(mappedBy = "tags")
    public List<Product> products;

    public static Finder<Long, Tag> find= new Finder<>(Long.class, Tag.class);

    public Tag() {
        // Left empty
    }

    public Tag(Long id, String name, Collection<Product> products) {
        this.id = id;
        this.name = name;
        this.products = new LinkedList<Product>(products);
        for (Product product : products) {
            product.tags.add(this);
        }
    }

    public static Tag findById(Long id) {
        return find.byId(id);
    }

}