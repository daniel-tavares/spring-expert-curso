package com.algaworks.brewer.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.validator.VendaValidator;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.service.CadastroVendaService;
import com.algaworks.brewer.session.TabelaItensSession;

@RequestMapping("/vendas")
@Controller
public class VendasController {

    @Autowired
	private Cervejas cervejas;

    @Autowired
    private TabelaItensSession tabelaItens;
    
    @Autowired
    private CadastroVendaService cadastroVendaService;

    @Autowired
    private VendaValidator vendaValidator;
    
    @InitBinder
    public void inicializarValidador(WebDataBinder binder) {
        binder.addValidators(vendaValidator);
    }
    
    @GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
        ModelAndView mv = new ModelAndView("venda/CadastroVenda");
        
        if (StringUtils.isEmpty(venda.getUuid())) {
            venda.setUuid(UUID.randomUUID().toString());
        }
		return mv;
	}
    
    @PostMapping("/nova")
    public ModelAndView cadastrar(@Valid Venda venda, BindingResult result, RedirectAttributes attributes, 
            @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
        if (result.hasErrors()) {
            return nova(venda);
        }
        
        venda.setUsuario(usuarioSistema.getUsuario());
        venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
        
        cadastroVendaService.salvar(venda);
        attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
        return new ModelAndView("redirect:/vendas/nova");
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
        mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
        return mv;
    }
	
}
