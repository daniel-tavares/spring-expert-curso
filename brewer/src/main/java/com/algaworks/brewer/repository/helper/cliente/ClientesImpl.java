package com.algaworks.brewer.repository.helper.cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class ClientesImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		criteria = paginacaoUtil.preparar(pageable, criteria);
		criteria = adicionarFiltro(criteria, filter);
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}


	private Criteria adicionarFiltro(Criteria criteria, ClienteFilter filter) {
		if(filter != null) {
			if(!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(),  MatchMode.ANYWHERE));
			}
			if(!StringUtils.isEmpty(filter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpfOuCnpj", filter.getCpfOuCnpj()));
			}
		}
		return criteria;
	}

	private Long total(ClienteFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
}
