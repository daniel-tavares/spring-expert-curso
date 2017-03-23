package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vendas")
@Controller
public class VendasController {

	@GetMapping("/nova")
	public String nova() {
		return "venda/CadastroVenda";
	}
	
}
