import com.avaje.ebean.Ebean;
import models.Tag;
import controllers.Application;
import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.data.format.Formatters;
import play.libs.Yaml;
import utils.AnnotationDateFormatter;
import play.*;
import play.filters.csrf.CSRFFilter;
import utils.BasicAuthenticationFilter;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Global extends GlobalSettings {
    public void onStart(Application app) {
        Formatters.register(Date.class, new AnnotationDateFormatter());
        InitialData.insert(app);
    }

    static class InitialData {
        public static void insert(Application app) {
            if (Ebean.find(Tag.class).findRowCount() == 0) {
                Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");
                Ebean.save(all.get("tags"));
            }
        }
    }

    @Override
    /*public <T extends EssentialFilter> Class<T>[] filters() {
        Class[] filters = {CSRFFilter.class};
        return filters;
    }*/
    public <T extends EssentialFilter> Class<T>[] filters() {
        Class[] filters={CSRFFilter.class/*,BasicAuthenticationFilter.class*/};
        filters = new Class[]{};
        return filters;
    }
}