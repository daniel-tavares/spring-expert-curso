package com.algaworks.brewer.session;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.session.TabelaItensVenda;

public class TabelaItensVendaTest {
    
    private TabelaItensVenda tabelaItensVenda;
    
    @Before
    public void setUp() {
        this.tabelaItensVenda = new TabelaItensVenda("1");
    }
    
    @Test
    public void deveCalcularValorTotalSemItens() throws Exception {
        assertEquals(BigDecimal.ZERO, this.tabelaItensVenda.getValorTotal());
    }
    
    @Test
    public void deveCalcularValorTotalComUmItem() throws Exception {
        Cerveja cerveja = new Cerveja();
        cerveja.setValor(BigDecimal.TEN);
        
        tabelaItensVenda.adicionarItem(cerveja, 1);
        
        assertEquals(BigDecimal.TEN, tabelaItensVenda.getValorTotal());
    }
    
    @Test
    public void deveCalcularValorTotalComVariosItens() throws Exception {
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        c1.setValor(BigDecimal.TEN);
        
        Cerveja c2 = new Cerveja();
        c2.setCodigo(2L);
        c2.setValor(new BigDecimal("8.9"));
        
        tabelaItensVenda.adicionarItem(c1, 1);
        tabelaItensVenda.adicionarItem(c2, 2);
        
        assertEquals(new BigDecimal("27.8"), tabelaItensVenda.getValorTotal());
    }
    
    @Test
    public void deveManterTamanhoDaListaParaMesmasCervejas() throws Exception {
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        c1.setValor(new BigDecimal("4.50"));
        
        tabelaItensVenda.adicionarItem(c1, 1);
        tabelaItensVenda.adicionarItem(c1, 1);
        
        assertEquals(1, tabelaItensVenda.total());
        assertEquals(new BigDecimal("9.00"), tabelaItensVenda.getValorTotal());
    }
    
    @Test
    public void deveAlterarQuantidadeDoItem() throws Exception {
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        c1.setValor(new BigDecimal("4.50"));
        
        tabelaItensVenda.adicionarItem(c1, 1);
        tabelaItensVenda.alterarQuantidade(c1, 3);
        
        assertEquals(1, tabelaItensVenda.total());
        assertEquals(3, tabelaItensVenda.quantidade());
        assertEquals(new BigDecimal("13.50"), tabelaItensVenda.getValorTotal());
    }
    
    @Test
    public void deveExcluirItem() throws Exception {
        Cerveja c1 = new Cerveja();
        c1.setCodigo(1L);
        c1.setValor(new BigDecimal("4.50"));
        Cerveja c2 = new Cerveja();
        c2.setCodigo(2L);
        c2.setValor(new BigDecimal("5.50"));
        
        tabelaItensVenda.adicionarItem(c1, 1);
        tabelaItensVenda.adicionarItem(c2, 2);
        tabelaItensVenda.removerItem(c1);
        
        assertEquals(1, tabelaItensVenda.total());
        assertEquals(2, tabelaItensVenda.quantidade());
        assertEquals(new BigDecimal("11.00"), tabelaItensVenda.getValorTotal());
    }
    
}
