package com.algaworks.brewer.storage.s3;

import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

public class FotoStorageS3 implements FotoStorage {

    @Override
    public String salvar(MultipartFile[] files) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] recuperar(String foto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] recuperarThumbnail(String foto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void excluirFoto(String foto) {
        // TODO Auto-generated method stub
        
    }

}
