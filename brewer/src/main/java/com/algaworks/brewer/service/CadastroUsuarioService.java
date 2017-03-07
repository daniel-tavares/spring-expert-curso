package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.service.exception.UsuarioCadastradoException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private Usuarios usuarios; 
	
	@Transactional
	public void cadastrar(Usuario usuario) {
		Optional<Usuario> usuarioOptional = usuarios.findByEmail(usuario.getEmail());
		if (usuarioOptional.isPresent()) {
			throw new UsuarioCadastradoException("Usuário com email já cadastrado");
		}
		usuarios.save(usuario);
	}
	
}
