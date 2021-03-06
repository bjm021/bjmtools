package de.bjm.mc.bjmtools.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BJMCommandHandler {
    boolean requiresPlayer();
    String name() default "";
    String description() default "";
    String usage() default "";
}
