var Brewer = Brewer || {};

Brewer.GraficosVendaPorMes = (function() {
	
	function GraficosVendaPorMes() {
		this.ctx = $('#graficoVendasPorMes')[0].getContext('2d');
		
	}
	
	GraficosVendaPorMes.prototype.iniciar = function() {
		$.ajax({
			url: 'vendas/totalPorMes',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(vendaMes) {
		var meses = [];
		var valores = [];
		vendaMes.forEach(function(obj) {
			meses.unshift(obj.mes);
			valores.unshift(obj.total);
		});
		
		var graficoVendasPorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: meses,
		    	datasets: [{
		    		label: 'Vendas por mês',
		    		backgroundColor: "rgba(26,179,148,0.5)",
	                pointBorderColor: "rgba(26,179,148,1)",
	                pointBackgroundColor: "#fff",
	                data: valores
		    	}]
		    }
		});

	}
	
	return GraficosVendaPorMes;
	
})();

Brewer.GraficoVendaPorOrigem = (function() {
	
	function GraficoVendaPorOrigem () {
		this.ctx = $('#graficoVendasPorOrigem')[0].getContext('2d');
	}
	
	GraficoVendaPorOrigem.prototype.iniciar = function() {
		$.ajax({
			method: 'GET',
			url: 'vendas/totalOrigem',
			success: onDadosRecebidos.bind(this)
				
		});
	}
	
	function onDadosRecebidos(vendasMesOrigem) {
		var meses = [];
		var cervejasNacionais = [];
		var cervejasInternacionais = [];
		
		vendasMesOrigem.forEach(function(obj) {
			meses.unshift(obj.mes);
			cervejasNacionais.unshift(obj.totalNacional);
			cervejasInternacionais.unshift(obj.totalInternacional);
		});
		
		var graficoVendasPorOrigem = new Chart(this.ctx, {
			type: 'bar',
			data: {
				labels: meses,
				datasets: [{
					label: 'Nacional',
					backgroundColor: "rgba(220, 220, 220, 0.5)",
					data: cervejasNacionais
				},
				{
					label: 'Internacional',
					backgroundColor: "rgba(26, 179, 148, 0.5)",
					data: cervejasInternacionais
				}]
			}
		});
	}
	
	
	return GraficoVendaPorOrigem;
	
})();

$(function() {
	var graficosVendaPorMes = new Brewer.GraficosVendaPorMes();
	graficosVendaPorMes.iniciar();
	
	var graficoVendaPorOrigem = new Brewer.GraficoVendaPorOrigem();
	graficoVendaPorOrigem.iniciar(); 
})