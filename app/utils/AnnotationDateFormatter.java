package utils;

import play.*;
import play.data.format.Formatters;
import play.data.format.Formatters.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Date;
import utils.DateFormat;

public class AnnotationDateFormatter extends Formatters.AnnotationFormatter<DateFormat,Date> {
    public Date parse(DateFormat annotation, String text,
                      Locale locale)
            throws java.text.ParseException {
        if(text == null || text.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new
                SimpleDateFormat(annotation.value(), locale);
        sdf.setLenient(false);
        return sdf.parse(text);
    }
    public String print(DateFormat annotation, Date value,
                        Locale locale) {
        if(value == null) {
            return "";
        }
        return new SimpleDateFormat (annotation.value(), locale)
                .format(value);
    }
}