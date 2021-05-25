package com.application.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Data;

@Data
public class AdditionalTax {
	
	
	private double itemPrice;
	
	//Este metodo arredonda valores
	public double basicRound (double roundItem) {
		BigDecimal roundFactor = new BigDecimal(roundItem).setScale(2, RoundingMode.HALF_EVEN);		
		return roundFactor.doubleValue();
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
	//Este metodo calcula a taxa para produtos importados
	public double musicTaxCalculator(double itemPrice, int amount) {
		double musicTax = 0.15;
		return (basicRound(itemPrice * musicTax)) * amount;
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
