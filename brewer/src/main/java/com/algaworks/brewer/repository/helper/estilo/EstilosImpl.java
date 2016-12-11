package com.algaworks.brewer.repository.helper.estilo;

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

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class EstilosImpl implements EstilosQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable) {
		Criteria criteria = getCriteria();
		criteria = paginacaoUtil.preparar(pageable, criteria);
		criteria = adicionarFiltro(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
		
	}

	private Criteria adicionarFiltro(EstiloFilter filter, Criteria criteria) {
		if(filter != null) {
			if(!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	private long total(EstiloFilter filter) {
		Criteria criteria = getCriteria();
		criteria = adicionarFiltro(filter, criteria);
		return (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	private Criteria getCriteria() {
		return manager.unwrap(Session.class).createCriteria(Estilo.class);
	}
}
