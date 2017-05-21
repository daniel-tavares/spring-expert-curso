package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.brewer.dto.ItensEstoqueValorDTO;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Vendas;

@Controller
public class DashboardController {
    
    @Autowired
    private Vendas vendas;
    
    @Autowired
    private Clientes clientes;

    @Autowired
    private Cervejas cervejas;

    @RequestMapping("/")
    public ModelAndView dashboard() {
        ModelAndView mv = new ModelAndView("Dashboard");
        ItensEstoqueValorDTO itensEstoqueValor = cervejas.valorItensEstoque();
        
        mv.addObject("vendasNoAno", vendas.valorTotalNoAno());
        mv.addObject("vendasNoMes", vendas.valorTotalNoMes());
        mv.addObject("ticketMedioNoAno", vendas.valorTicketMedioNoAno());
        mv.addObject("totalClientes", clientes.count());
        mv.addObject("valorEstoque", itensEstoqueValor.getValorTotal());
        mv.addObject("totalEstoque", itensEstoqueValor.getQuantidade());
        
        return mv;
    }
}
