package com.algaworks.brewer.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {
	
	public Criteria preparar(Pageable pageable, Criteria criteria) {
		int totalPorPagina = pageable.getPageSize();
		int pagina = pageable.getPageNumber();
		int primeiroElemento = totalPorPagina * pagina;
		
		criteria.setFirstResult(primeiroElemento);
		criteria.setMaxResults(totalPorPagina);
		
		Sort sort = pageable.getSort();
		if(sort != null) {
			String propriedade = sort.iterator().next().getProperty();
			Sort.Order order = sort.getOrderFor(propriedade);
			criteria.addOrder(order.isAscending() ? Order.asc(propriedade) : Order.desc(propriedade));
		}
		
		return criteria;
	}

}
