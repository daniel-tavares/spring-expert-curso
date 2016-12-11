package com.algaworks.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {
	
	public Page<T> page;
	private UriComponentsBuilder uriComponentsBuilder;
	
	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		String url = request.getRequestURL().append(
				request.getQueryString() != null ? "?" + request.getQueryString() : "").toString().replaceAll("\\+", "%20");
		this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
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
	
	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = uriComponentsBuilder.cloneBuilder();
		String ordem = propriedade + "," + inverterDirecao(propriedade);
		return uriBuilderOrder.replaceQueryParam("sort", ordem).build(true).encode().toUriString();
	}

	public String urlParaPagina(int pagina) {
		return uriComponentsBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}
	
	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		return order != null;
		
	}
	
	private String inverterDirecao(String propriedade) {
		String ascOrdem = "asc";
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		if(order != null) {
			return order.isAscending() ? "desc" : ascOrdem;
		}
		return ascOrdem;
	}
	
}
