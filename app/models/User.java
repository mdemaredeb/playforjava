package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints;



@Entity
public class User extends Model {
    @Id
    public Long id;
    @Constraints.Required
    public String email;
    @Constraints.Required
    public String password;

    public static Finder<Long, User> find =
            new Finder<>(Long.class, User.class);

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }
    public static User authenticate(String email,
                                    String password) {

        return find.where().eq("email",email).eq("password",password).findUnique();

        /*if ("mdemare@debmedia.com".equals(email)
                && "secret".equals(password))
            return new User("mdemare@debmedia.com", "secret");
        else
            return null;*/
    }
    public static Finder<Long, User> finder
            = new Finder<Long, User>(Long.class, User.class);
}