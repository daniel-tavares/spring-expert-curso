package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrosController {
	
	@GetMapping("/404")
	public String paginaNaoEncontrada() {
		return "404";
	}
	
	@GetMapping("/500")
	public String paginaErroNoServidor() {
		return "500";
	}
	
	@GetMapping("/403")
    public String acessoNegado() {
        return "403";
    }
}
