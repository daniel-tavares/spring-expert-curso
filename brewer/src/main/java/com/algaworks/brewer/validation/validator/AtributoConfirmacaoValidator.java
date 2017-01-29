package com.algaworks.brewer.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import com.algaworks.brewer.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object>{
	
	private String atributo;
	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributoConfirmacao annotation) {
		this.atributo = annotation.atributo();
		this.atributoConfirmacao = annotation.atributoConfirmacao();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean valido = false;
		try {
			Object valorAtributo = BeanUtils.getProperty(object, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(object, this.atributoConfirmacao);
			
			valido = ambosIsNull(valorAtributo, valorAtributoConfirmacao) || ambosIsIguais(valorAtributo, valorAtributoConfirmacao);
			
		} catch (Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos", e);
		}
		
		if(!valido) {
			context.disableDefaultConstraintViolation();
			String mensagem = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(mensagem);
			builder.addPropertyNode(this.atributoConfirmacao).addConstraintViolation();
			
		}
		
		return valido;
	}
	
	private boolean ambosIsIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo != null && valorAtributo.equals(valorAtributoConfirmacao);
	}

	private boolean ambosIsNull(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo == null && valorAtributoConfirmacao == null;
	}

}
