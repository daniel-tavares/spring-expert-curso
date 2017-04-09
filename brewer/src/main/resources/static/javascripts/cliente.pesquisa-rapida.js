var Brewer = Brewer || {};

Brewer.PesquisaRapidaCliente = (function() {

	function PesquisaRapidaCliente() {
		this.pesquisaRapidaModal = $('#pesquisaRapidaClientes');
		this.nomeInput = $('#nomeClienteModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-clientes-btn');
		this.containerTabelaPesquisa = $('#containerPesquisaRapidaClientes');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-cliente').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}

	PesquisaRapidaCliente.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaModal.on('shown.bs.modal', onModalShow.bind(this));
	}
	
	function onModalShow() {
		this.nomeInput.focus();
	}

	function onPesquisaRapidaClicado(e) {
		e.preventDefault();
		$.ajax({
			url : this.pesquisaRapidaModal.find('form').attr('action'),
			method : 'GET',
			contentType : 'application/json',
			data : {
				nome : this.nomeInput.val()
			},
			success : onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});

	}

	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden')
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		
		var tabelaClientePesquisaRapida = new Brewer.TabelaClientePesquisaRapida(this.pesquisaRapidaModal);
		tabelaClientePesquisaRapida.iniciar();
	}
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden')
	}

	return PesquisaRapidaCliente;

})();

Brewer.TabelaClientePesquisaRapida = (function() {
	
	function TabelaClientePesquisaRapida(modal) {
		this.modalCliente = modal;
		this.cliente = $('.js-cliente-pesquisa-rapida');
	}
	
	TabelaClientePesquisaRapida.prototype.iniciar = function() {
		this.cliente.on('click', onClienteSelecionado.bind(this));
	}
	
	function onClienteSelecionado(evento) {
		this.modalCliente.modal('hide');
		
		var clienteSelecionado = $(evento.currentTarget);
		$('#nomeCliente').val(clienteSelecionado.data('nome'));
		$('#codigoCliente').val(clienteSelecionado.data('codigo'));
	}
	
	return TabelaClientePesquisaRapida;
	
})();

$(function() {

	var pesquisaRapidaCliente = new Brewer.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();

});