var Brewer = Brewer || {};

Brewer.ComboEstado = (function() {
	
	function ComboEstado() {
		this.combo = $('#estado');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboEstado.prototype.iniciar = function() {
		this.combo.on('change', onEstadoAlterado.bind(this));

	}
	
	function onEstadoAlterado() {
		this.emitter.trigger('alterado', this.combo.val());
	}
	
	return ComboEstado;
	
})();

Brewer.ComboCidade = (function() {
	
	function ComboCidade(comboEstado) {
		this.comboEstado = comboEstado;
		this.combo = $('#cidade');
		this.imgLoading = $('.js-img-loading');
		this.inputHiddenCidadeSelecionada = $('#codigoCidadeSelecionada');
	}
	
	ComboCidade.prototype.iniciar = function() {
		this.comboEstado.on('alterado', onEstadoAlterado.bind(this));
		reset.call(this);
		var codigoEstado = this.comboEstado.combo.val();
		inicializarCidades.call(this, codigoEstado);
	}
	
	function onEstadoAlterado(evento, codigoEstado) {
		this.inputHiddenCidadeSelecionada.val('');
		inicializarCidades.call(this, codigoEstado)
	}
	
	function inicializarCidades(codigoEstado) {
		if (codigoEstado){
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: {
					'estado': codigoEstado
				},
				beforeSend: inciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			
			resposta.done(onBuscarCidadesFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function inciarRequisicao() {
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
		
	}
	
	function reset() {
		this.combo.attr('disabled', 'disabled');
		this.combo.html('<option>Selecione a cidade</option>');
	}
	
	function onBuscarCidadesFinalizado(cidades) {
		var options = [];
		cidades.forEach(function (cidade) {
			var option = '<option value="' + cidade.codigo + '">' + cidade.nome + '</option>';
			options.push(option);
		});
		
		var codigoCidadeSelecionada = this.inputHiddenCidadeSelecionada.val();
		
		if (codigoCidadeSelecionada) {
			this.combo.val(codigoCidadeSelecionada);
		}
		
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
	}
	
	return ComboCidade;
	
})();

$(function() {
	
	var comboEstado = new Brewer.ComboEstado();
	comboEstado.iniciar();
	
	var comboCidade = new Brewer.ComboCidade(comboEstado);
	comboCidade.iniciar();
	
});