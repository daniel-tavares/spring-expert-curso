package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cerveja;

@Controller
public class CervejasController {
	
	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	@RequestMapping("/cervejas/novo")
	public String novo(Cerveja cerveja) {
		
		logger.error("Logger de erro");
		logger.info("Logger de info");
		
		return "cerveja/CadastroCerveja";
	}
	
	@RequestMapping(value="/cervejas/novo", method=RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			model.addAttribute("mensagem", "Erro no formulário!");
			return novo(cerveja);
		}
		System.out.println(cerveja.getSku());
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso");
		return "redirect:/cervejas/novo";
	}
}
