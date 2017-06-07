package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Profile("local")
@Component
public class FotoStorageLocal implements FotoStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
//	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath(System.getenv("HOME"), ".brewerfotos"));
	}
	
	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}
	
	@Override
	public String salvar(MultipartFile[] files) {
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
			    arquivo.transferTo(new File(this.local.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
				gerarThumbnail(novoNome);
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando a foto", e);
			}
		}

		return novoNome;
	}
	
//	@Override
//	public byte[] recuperarFotoTemporaria(String nome) {
//		try {
//			return Files.readAllBytes(localTemporario.resolve(nome));
//		} catch (IOException e) {
//			throw new RuntimeException("Erro lendo a foto temporária: " + nome, e);
//		}
//	}
	
//	public void salvar(String foto) {
//		moverLocalTempParaLocalPermanente(foto);
//		gerarThumbnail(foto);
//	}
	
	@Override
	public byte[] recuperar(String foto) {
		try {
			return Files.readAllBytes(local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto: " + foto, e);
		}
	}
	
	@Override
	public byte[] recuperarThumbnail(String foto) {
	    return recuperar(THUMBANAIL_PREFIX + foto);
	}

//	private void moverLocalTempParaLocalPermanente(String foto) {
//		try {
//			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
//		} catch (IOException e) {
//			throw new RuntimeException("Erro movendo a foto para destino final", e);
//		}
//		
//	}

	private void gerarThumbnail(String foto) {
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail", e);
		}
	}
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
//			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
//			Files.createDirectories(this.localTemporario);
			if (logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para salvar fotos");
				logger.debug("Pasta default:" + local.toAbsolutePath());
//				logger.debug("Pasta temporária:" + localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}
	
    @Override
    public void excluirFoto(String foto) {
        try {
            Files.deleteIfExists(this.local.resolve(foto));
            Files.deleteIfExists(this.local.resolve(THUMBANAIL_PREFIX + foto));
        } catch (IOException e) {
            logger.warn(String.format("Erro apagando foto '%s'. Mensagem: %s", foto, e.getMessage()));
        }
    }

    @Override
    public String getUrl(String foto) {
        return "http://localhost:8080/brewer/fotos/" + foto;
    }

}
