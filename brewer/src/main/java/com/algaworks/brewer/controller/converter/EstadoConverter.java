package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Estado;

public class EstadoConverter implements Converter<String, Estado> {

	@Override
	public Estado convert(String id) {
		Estado estado = null;
		if(!StringUtils.isEmpty(id)) {
			estado = new Estado();
			estado.setCodigo(Long.valueOf(id));
		}
		return estado;
	}

}
