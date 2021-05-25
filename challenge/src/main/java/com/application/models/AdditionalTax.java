package com.application.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
public class AdditionalTax {
	
	private final double basic_tax = 0.10;
	private final double import_tax = 0.05;
	private final double music_tax = 0.15;
	private final double final_basic = 1.10;
	private final double final_import = 1.05;
	private final double final_music = 1.15;
	
	//Este metodo arredonda valores
	public double basicRound (double roundItem) {
		BigDecimal roundFactor = new BigDecimal(roundItem).setScale(2, RoundingMode.HALF_EVEN);		
		return roundFactor.doubleValue();
	}
	
	public double allTaxCalculator(double itemPrice, int amount, boolean isMusicProduct, boolean isBasicTaxFree, boolean isImport) {
		
		return 	!isBasicTaxFree	&&	 isImport	&&	 isMusicProduct	?	basicRound(basicTaxCalculator(itemPrice,amount)  + importTaxCalculator(itemPrice,amount) + musicTaxCalculator(itemPrice,amount))	:
				!isBasicTaxFree	&&	!isImport	&&	 isMusicProduct	?	basicRound(basicTaxCalculator(itemPrice,amount)  + musicTaxCalculator(itemPrice,amount))	:
				!isBasicTaxFree	&&	 isImport	&&	!isMusicProduct	?	basicRound(basicTaxCalculator(itemPrice,amount)  + importTaxCalculator(itemPrice,amount))	:
				 isBasicTaxFree	&&	 isImport	&&	 isMusicProduct	?	basicRound(importTaxCalculator(itemPrice,amount) + musicTaxCalculator(itemPrice,amount))	:
				 isBasicTaxFree	&&	!isImport	&&	 isMusicProduct	?	basicRound(musicTaxCalculator(itemPrice,amount)) :
				 isBasicTaxFree	&&	 isImport	&&	!isMusicProduct	?	basicRound(importTaxCalculator(itemPrice,amount)) :
				(0.00);							

	}
	
	public double finalAllTaxCalculator(double itemPrice, int amount, boolean isMusicProduct, boolean isBasicTaxFree, boolean isImport) {
		
	return 	!isBasicTaxFree	&&	 isImport	&&	 isMusicProduct	?	basicRound(basicTaxCalculator(itemPrice,amount)  + importTaxCalculator(itemPrice,amount) + finalMusicProductTaxCalculator(itemPrice,amount))	:
			!isBasicTaxFree	&&	!isImport	&&	 isMusicProduct	?	basicRound(basicTaxCalculator(itemPrice,amount) +  finalMusicProductTaxCalculator(itemPrice,amount))	:
			!isBasicTaxFree	&&	 isImport	&&	!isMusicProduct	?	basicRound(basicTaxCalculator(itemPrice,amount)  + finalImportTaxCalculator(itemPrice,amount))	:
			 isBasicTaxFree	&&	 isImport	&&	 isMusicProduct	?	basicRound(importTaxCalculator(itemPrice,amount) + finalMusicProductTaxCalculator(itemPrice,amount))	:
			 isBasicTaxFree	&&	!isImport	&&	 isMusicProduct	?	basicRound(finalMusicProductTaxCalculator(itemPrice,amount)) :
			 isBasicTaxFree	&&	 isImport	&&	!isMusicProduct	?	basicRound(finalImportTaxCalculator(itemPrice,amount)) :
			(basicRound(itemPrice) * amount);	
	}
	
	
	//Este metodo calcula a taxa basica de vendas
	public double basicTaxCalculator(double itemPrice, int amount) {
		return (basicRound(itemPrice * this.basic_tax)) * amount;
	}
	
	//Este metodo calcula a taxa para produtos importados
	public double importTaxCalculator(double itemPrice, int amount) {
		return (basicRound(itemPrice * this.import_tax)) * amount;
	}
	
	//Este metodo calcula a taxa para produtos importados
	public double musicTaxCalculator(double itemPrice, int amount) {
		return (basicRound(itemPrice * this.music_tax)) * amount;
	}
	
	//Este metodo calcula o preco do produto incluindo a taxa basica
	public double finalBasicTaxCalculator(double itemPrice, int amount) {
		return (basicRound(itemPrice * this.final_basic)) * amount;
	}
	
	//Este metodo calcula o preco do produto incluindo a taxa basica
	public double finalMusicProductTaxCalculator(double itemPrice, int amount) {
		return (basicRound(itemPrice * this.final_music)) * amount;
	}
	
	public double finalImportTaxCalculator(double itemPrice, int amount) {
		return (basicRound(itemPrice * this.final_import)) * amount;
	}
	
}
