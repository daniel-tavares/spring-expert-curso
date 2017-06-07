package com.algaworks.brewer.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.storage.FotoStorage;

public class CervejaEntityListener {

    @Autowired
    private FotoStorage fotoStorage;
    
    @PostLoad
    public void postLoad(final Cerveja cerveja) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        cerveja.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBANAIL_PREFIX + cerveja.getFotoOuMock()));
        cerveja.setUrlFoto(fotoStorage.getUrl(cerveja.getFoto()));
    }
}
