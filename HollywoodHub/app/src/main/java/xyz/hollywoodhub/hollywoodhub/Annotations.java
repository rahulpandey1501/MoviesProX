package xyz.hollywoodhub.hollywoodhub;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by rpandey.ppe on 30/07/17.
 */

public class Annotations {
    @Documented
    @Retention(CLASS)
    @Target({METHOD,CONSTRUCTOR,TYPE})
    public @interface GoMovies {
    }

    @Documented
    @Retention(CLASS)
    @Target({METHOD,CONSTRUCTOR,TYPE})
    public @interface CMoviesHD {
    }
}
