package htl.steyr.maturabeispiel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResetValidityTime {
    /**
     * This annotation is used when the validity time of a token should be reset after an action.
     * Its corresponding aspect is ResetValidityTimeAspect.
     * For it to work, the method must have a String parameter that contains the token as the first parameter.
     */
}
