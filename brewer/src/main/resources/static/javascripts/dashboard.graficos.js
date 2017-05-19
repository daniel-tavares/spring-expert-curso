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

$(function() {
	var graficosVendaPorMes = new Brewer.GraficosVendaPorMes();
	graficosVendaPorMes.iniciar();
})