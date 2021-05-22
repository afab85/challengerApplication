package com.application.models;

import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	//Este metodo extrai o preco da ordem de compra de acordo com o input
	public String price() {
		return this.textOrder.substring(this.textOrder.indexOf("at ") + 3, this.textOrder.length());
	}
	//Este metodo extrai o produto do input
	public String product() {
		return this.textOrder.substring(this.textOrder.indexOf(" ") + 1, this.textOrder.indexOf(" at"));
	}
	//Este metodo estrai do input a quantidade
	public int amount() {
		return Integer.parseInt(this.textOrder.substring(0, this.textOrder.indexOf(" ")));
	}
	
	//Neste metodo foi definido uma palavra chave para identificar se o produto eh importado, que varre todo fragmento de entrada.
	public boolean isImport() {
		return (Pattern.compile("import",  Pattern.MULTILINE).matcher(this.textOrder.toLowerCase()).find()) ?  true : false;

	}
	//Neste metodo foi definido uma palavra chave para identificar se o produto eh idento de taxas, que varre todo fragmento de entrada.
	public boolean isBasicTaxFree() {
		return (Pattern.compile("book|syrup|pill|choco|apple",  Pattern.MULTILINE).matcher(this.textOrder.toLowerCase()).find()) ?  true : false;
	}
	//Este metodo arredonda valores
	public double basicRound (double itemPrice) {
		double roundFactor = 1.1;
		itemPrice *= roundFactor;
		itemPrice = Math.rint(itemPrice);
		
		return itemPrice /= roundFactor;
	}
		
	//Este metodo calcula a taxa basica de vendas
	public double basicTaxCalculator(double itemPrice, int amount) {
		double basicTax = 0.10;
		return basicRound(itemPrice * basicTax) * amount;
	}
	//Este metodo calcula a taxa para produtos importados
	public double importTaxCalculator(double itemPrice, int amount) {
		double importTax = 0.05;
		return (basicRound(itemPrice * importTax)) * amount;
	}
	//Este metodo calcula a taxa para produtos importados e sem isencao
	public double bothTaxCalculator(double itemPrice, int amount) {
		double importTax = 0.05;
		double basicTax = 0.10;
		return (basicRound(itemPrice * (importTax + basicTax))) * amount;
	}
	//Este metodo calcula o preco do produto incluindo a taxa basica
	public double finalBasicTaxCalculator(double itemPrice, int amount) {
		double basicTax = 1.10;
		return (basicRound(itemPrice * basicTax)) * amount;
	}
	//Este metodo calcula o preco do produto incluindo a taxa basica
	public double finalImportTaxCalculator(double itemPrice, int amount) {
		double importTax = 1.05;
		return (basicRound(itemPrice * importTax)) * amount;
	}
	//Este metodo calcula o preco do produto incluindo a taxa basica e a taxa de produtos importados
	public double finalBothTaxCalculator(double itemPrice, int amount) {
		double importTax = 0.05;
		double basicTax = 1.10;
		return (basicRound(itemPrice * (importTax + basicTax))) * amount;
	}
}
