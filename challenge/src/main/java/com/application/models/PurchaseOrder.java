package com.application.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

//Esta é a classe da requisição de compra, o input conforme desafio é feito atraves de uma linha de texto
//Os metodos abaixo extraem informacoes referentes a quantidade, preco, produto e se é um produto importado, 
//isento de taxa calculo das taxas e arredondamento dos valores processados
//Foi usado o plugin lombok para gerar os metodos getters, setters, equals, hash, contrutor etc
@Entity
@Data
public class PurchaseOrder {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String textOrder;
	
	//Este metodo fatia as ordens de compra que tem mais de um item
	public String[] sliceOrder() {
		StringBuilder order = new StringBuilder(this.textOrder);
		
		int totalOfDots = 0;
		
		char temp;
		
		for (int i = 0; i < order.length(); i++) {
			
			temp = order.charAt(i);
			if (temp == '.')
          	totalOfDots++;
		}
		
		for (int i = 0; i < totalOfDots; i++) {
			order.replace(order.indexOf(".")+3, order.indexOf(".")+3, "#@#");
			order.replace(order.indexOf("."), order.indexOf("."), ",");
			order.deleteCharAt(order.indexOf("."));			
		}

		this.textOrder = order.toString().replaceAll(",", ".");
		
		String[] orderArray = this.textOrder.split("#@#");
		
		for (int i = 0; i < orderArray.length; i++) {
			orderArray[i] = orderArray[i].trim();
			
		}
		
		return orderArray;
	}
	
	//Este metodo extrai o preco da ordem de compra de acordo com o input
	public String price() {
		return this.textOrder.substring(this.textOrder.indexOf("at ") + 3, this.textOrder.length());
	}
	//Este metodo extrai o produto do input
	public String product() {
		return this.textOrder.substring(this.textOrder.indexOf(" ") + 1, this.textOrder.indexOf(" at"));
	}
	//Este metodo extrai do input a quantidade
	public int amount() {
		return Integer.parseInt(this.textOrder.substring(0, this.textOrder.indexOf(" ")));
	}
	
	//Neste metodo foi definido uma palavra chave para identificar se o produto eh importado, que varre todo fragmento de entrada.
	public boolean isImport() {
		return (Pattern.compile("import",  Pattern.MULTILINE).matcher(this.textOrder.toLowerCase()).find()) ?  true : false;

	}
	//Neste metodo foi definido uma palavra chave para identificar se o produto eh isento de taxas, que varre todo fragmento de entrada.
	public boolean isBasicTaxFree() {
		return (Pattern.compile("book|syrup|pill|choco|apple",  Pattern.MULTILINE).matcher(this.textOrder.toLowerCase()).find()) ?  true : false;
	}
	//Neste metodo foi definido uma palavra chave para identificar se o produto eh do tipo music, o metodo varre todo fragmento de entrada.
	public boolean isMusicProduct() {
		return (Pattern.compile("music",  Pattern.MULTILINE).matcher(this.textOrder.toLowerCase()).find()) ?  true : false;
	}
	
	public double FinaltaxItemsCalculator(PurchaseOrder po) {		
		AdditionalTax ad = new AdditionalTax();
		
		return ad.finalAllTaxCalculator(Double.parseDouble(po.price()), po.amount(), po.isMusicProduct(), po.isBasicTaxFree(), po.isImport()); 
	}
	
	public double salesTaxCalculator (PurchaseOrder po) {		
		AdditionalTax ad = new AdditionalTax();
		
		return ad.allTaxCalculator(Double.parseDouble(po.price()), po.amount(), po.isMusicProduct(), po.isBasicTaxFree(), po.isImport());
	}


}
