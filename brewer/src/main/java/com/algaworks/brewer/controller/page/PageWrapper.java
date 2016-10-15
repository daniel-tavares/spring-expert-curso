package com.algaworks.brewer.controller.page;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {
	
	public Page<T> page;
	private UriComponentsBuilder uriComponentsBuilder;
	
	public PageWrapper(Page<T> page, UriComponentsBuilder uriComponentsBuilder) {
		this.page = page;
		this.uriComponentsBuilder = uriComponentsBuilder;
	}
	
	public List<T> getConteudo() {
		return page.getContent();
	}
	
	public boolean isVazia() {
		return page.getContent().isEmpty();
	}
	
	public int getAtual() {
		return page.getNumber();
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public int getTotal() {
		return page.getTotalPages();
	}
	
	public String urlParaPagina(int pagina) {
		return uriComponentsBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
}
