package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
    
    String PREFIX_THUMBANAIL = "thumbnail.";
    
	public String salvar(MultipartFile[] files);

	public byte[] recuperar(String foto);
	
	public byte[] recuperarThumbnail(String foto);

    public void excluirFoto(String foto);
}
