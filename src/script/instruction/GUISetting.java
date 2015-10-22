package script.instruction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GUISetting {
	public int min() default Integer.MIN_VALUE;
	public int max() default Integer.MAX_VALUE;
}
