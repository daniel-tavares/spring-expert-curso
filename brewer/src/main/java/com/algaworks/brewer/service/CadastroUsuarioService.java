package com.algaworks.brewer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.StatusUsuario;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import com.algaworks.brewer.service.exception.UsuarioCadastradoException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void cadastrar(Usuario usuario) {
		Optional<Usuario> usuarioOptional = usuarios.findByEmail(usuario.getEmail());
		if (usuarioOptional.isPresent() && !usuarioOptional.get().equals(usuario)) {
			throw new UsuarioCadastradoException("Usuário com email já cadastrado");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		} else if (StringUtils.isEmpty(usuario.getSenha())) {
		    usuario.setSenha(usuarioOptional.get().getSenha());
		}
		
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null) {
		    usuario.setAtivo(usuarioOptional.get().getAtivo());
		}

		usuarios.save(usuario);
	}

	@Transactional
	public void alterarStatus(List<Long> codigos, StatusUsuario status) {
		status.executar(codigos, usuarios);
	}
	
}
