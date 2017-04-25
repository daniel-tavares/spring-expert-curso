package com.algaworks.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.session.TabelaItensSession;

@RequestMapping("/vendas")
@Controller
public class VendasController {

    @Autowired
	private Cervejas cervejas;

    @Autowired
    private TabelaItensSession tabelaItens;
    
    @GetMapping("/nova")
	public ModelAndView nova() {
        ModelAndView mv = new ModelAndView("venda/CadastroVenda");
        mv.addObject("uuid", UUID.randomUUID());
		return mv;
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
	    Cerveja cerveja = cervejas.findOne(codigoCerveja);
	    tabelaItens.adicionarItem(uuid, cerveja, 1);
	    return mvTabelaVendas(uuid);
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoCerveja, Integer quantidade, String uuid) {
	    Cerveja cerveja = cervejas.findOne(codigoCerveja);
	    tabelaItens.alterarQuantidade(uuid, cerveja, quantidade);
	    return mvTabelaVendas(uuid);
	}
	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable String uuid) {
	    tabelaItens.removerItem(uuid, cerveja);
	    return mvTabelaVendas(uuid);
	}

    private ModelAndView mvTabelaVendas(String uuid) {
        ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
        mv.addObject("itens", tabelaItens.getItens(uuid));
        return mv;
    }
	
}
