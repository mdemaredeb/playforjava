package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Warehouse {
    @Id
    public Long id;
    public String name;
    @OneToOne
    public Address address;

    public static Model.Finder<Long, Warehouse> find = new Model.Finder<>(Long.class, Warehouse.class);

    @OneToMany(mappedBy = "warehouse")
    public List<StockItem> stock = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    public static Warehouse findById(Long id) {
        return find.byId(id);
    }
}
