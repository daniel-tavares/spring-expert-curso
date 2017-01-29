package com.algaworks.brewer.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.algaworks.brewer.validation.validator.AtributoConfirmacaoValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AtributoConfirmacaoValidator.class })
public @interface AtributoConfirmacao {
	
	String message() default "Atributos não conferem";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	String atributo();
	
	String atributoConfirmacao();
	
}
