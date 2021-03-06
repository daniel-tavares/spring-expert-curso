var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	
	function UploadFoto() {
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.inputUrlFoto = $('input[name=urlFoto]');
		
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);

		this.containerFotoCerveja = $('.js-foto-cerveja');

		this.uploadDrop = $('#upload-drop');
	}
	
	UploadFoto.prototype.iniciar = function() {
		var settings = {
			type:'json',
			filelimit: 1,
			allow: '*.(jpg|jpeg|png)',
			action: this.containerFotoCerveja.data('url-fotos'),
			complete: onUploadComplete.bind(this),
			beforeSend: adicionarCsrfToken
		}
		
		UIkit.uploadSelect($('#upload-select') ,settings);
		UIkit.uploadDrop(this.uploadDrop, settings);
		
		if (this.inputNomeFoto.val()) {
			renderizarFoto.call(this, { nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val(), url: this.inputUrlFoto.val() });
		}
	}
	
	function onUploadComplete(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		this.inputUrlFoto.val(resposta.url);

		renderizarFoto.call(this, resposta);
	}
	
	function renderizarFoto(resposta) {
		this.uploadDrop.hide();
		
		var htmlFotoCerveja = this.template({url: resposta.url})
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	function onRemoverFoto() {
		$('.js-container-foto-cerveja').remove();
		this.uploadDrop.show();
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}
	
	function adicionarCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}

	return UploadFoto;
	
})();

$(function() {
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});