package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.service.CadastroUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView cadastrar(@Validated Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}
		cadastroUsuarioService.cadastrar(usuario);
		attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
		return new ModelAndView("usuario/CadastroUsuario");
	}
}
