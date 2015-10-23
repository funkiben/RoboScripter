package script.instruction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GUISetting {
	public double min() default Integer.MIN_VALUE;
	public double max() default Integer.MAX_VALUE;
	public boolean canEdit() default true;
}
