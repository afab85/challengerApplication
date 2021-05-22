package com.application.models;

import lombok.Data;

@Data
public class AdditionalTax {
	
	
	private double itemPrice;
	
	//Este metodo arredonda valores
	public double basicRound (double itemPrice) {
//		double roundFactor = 1.1;
//		itemPrice *= roundFactor;
//		itemPrice = Math.rint(itemPrice);
//		
//		return itemPrice /= roundFactor;
		
		return Math.nextDown(itemPrice);
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
