var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function() {
	
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfCnpj]');
		this.inputCpfCnpj = $('#cpfCnpj');
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionado = $(evento.currentTarget);
		var documento = tipoPessoaSelecionado.data('documento');
		this.labelCpfCnpj.text(documento);
		this.inputCpfCnpj.mask(tipoPessoaSelecionado.data('mascara'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
		
	}
	
	return MascaraCpfCnpj;
})();

$(function() {
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});

