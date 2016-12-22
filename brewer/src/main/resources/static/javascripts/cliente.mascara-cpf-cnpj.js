var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function() {
	
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfCnpj]');
		this.inputCpfCnpj = $('#cpfCnpj');
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
		var tipoPessoaSelecionada = this.radioTipoPessoa.filter(':checked')[0];
		if (tipoPessoaSelecionada) {
			aplicarMascara.call(this, $(tipoPessoaSelecionada));
		}
		
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionado = $(evento.currentTarget);
		aplicarMascara.call(this, tipoPessoaSelecionado);
		this.inputCpfCnpj.val('');
	}
	
	function aplicarMascara(tipoPessoa) {
		var documento = tipoPessoa.data('documento');
		this.labelCpfCnpj.text(documento);

		this.inputCpfCnpj.mask(tipoPessoa.data('mascara'));
		this.inputCpfCnpj.removeAttr('disabled');
		
	}
	
	return MascaraCpfCnpj;
})();

$(function() {
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});

