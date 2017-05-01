package com.algaworks.brewer.session;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;

@Component
@SessionScope
public class TabelaItensSession {

    private Set<TabelaItensVenda> tabelas = new HashSet<>();

    public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
        TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
        tabela.adicionarItem(cerveja, quantidade);
        tabelas.add(tabela);
    }


    public void alterarQuantidade(String uuid, Cerveja cerveja, Integer quantidade) {
        TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
        tabela.alterarQuantidade(cerveja, quantidade);
    }

    public void removerItem(String uuid, Cerveja cerveja) {
        TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
        tabela.removerItem(cerveja);
    }

    public List<ItemVenda> getItens(String uuid) {
        TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
        return tabela.getItens();
    }
    
    public BigDecimal getValorTotal(String uuid) {
        return buscarTabelaPorUuid(uuid).getValorTotal();
    }
    
    private TabelaItensVenda buscarTabelaPorUuid(String uuid) {
        return tabelas.stream()
                .filter(t -> t.getUuid().equals(uuid))
                .findAny()
                .orElse(new TabelaItensVenda(uuid));
    }


}
