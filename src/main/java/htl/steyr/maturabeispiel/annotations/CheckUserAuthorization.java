package htl.steyr.maturabeispiel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckUserAuthorization {
    /**
     * This annotation is meant to be used on methods that require a user to be logged in.
     * Its corresponding aspect is CheckUserAuthorizationAspect.
     * For it to work, the method must have a String parameter that contains the token as the first parameter.
     */
}
