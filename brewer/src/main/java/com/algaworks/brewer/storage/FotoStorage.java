package com.algaworks.brewer.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
    
    String THUMBANAIL_PREFIX = "thumbnail.";
    
	public String salvar(MultipartFile[] files);

	public byte[] recuperar(String foto);
	
	public byte[] recuperarThumbnail(String foto);

    public void excluirFoto(String foto);

    public String getUrl(String foto);
    
    default String renomearArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal.replaceAll("\\s", "");
    }
}
