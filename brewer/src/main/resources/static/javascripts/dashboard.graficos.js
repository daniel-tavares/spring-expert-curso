var Brewer = Brewer || {};

Brewer.GraficosVendaPorMes = (function() {
	
	function GraficosVendaPorMes() {
		this.ctx = $('#graficoVendasPorMes')[0].getContext('2d');
		
	}
	
	GraficosVendaPorMes.prototype.iniciar = function() {
		var myLineChart = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'],
		    	datasets: [{
		    		label: 'Vendas por mês',
		    		backgroundColor: "rgba(26,179,148,0.5)",
	                pointBorderColor: "rgba(26,179,148,1)",
	                pointBackgroundColor: "#fff",
	                data: [10, 5, 7, 2, 9]
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