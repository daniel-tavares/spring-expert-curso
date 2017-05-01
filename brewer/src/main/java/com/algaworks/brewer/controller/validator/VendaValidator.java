package com.algaworks.brewer.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.algaworks.brewer.model.Venda;

@Component
public class VendaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Venda.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Selecione um cliente na pesquisa rápida");
        
        Venda venda = (Venda) target;
        
        if (venda.getHoraEntrega() != null && venda.getDataEntrega() == null) {
            errors.rejectValue("dataEntrega", "", "Informe uma data da entrega para um horário");
        }
    }

    
}
