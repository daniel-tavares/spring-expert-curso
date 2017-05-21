package com.algaworks.brewer.dto;

import java.math.BigDecimal;

public class ItensEstoqueValorDTO {
    
    private Long quantidade;
    private BigDecimal valorTotal;
    
    public ItensEstoqueValorDTO(Long quantidade, BigDecimal valorTotal) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }
    public Long getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
