package com.algaworks.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.helper.cidade.CidadeQueries;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadeQueries {
	
	public List<Cidade> findByEstadoCodigo(Long codigo);

	public Optional<Cidade> findByNome(String nome);
}
