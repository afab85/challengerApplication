package com.application.challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.application.models.PurchaseOrder;



class ChallengeApplicationTests {

	@Test
	public void mustCalculateTax_WhenProductIsMusicType() {
		PurchaseOrder po = new PurchaseOrder();
		
		po.setTextOrder("1 music CD at 14.99");
		
		assertEquals(3.75, po.salesTaxCalculator(po));
		assertEquals(true, po.isMusicProduct());
		assertEquals(false, po.isImport());
		assertEquals(false, po.isBasicTaxFree());
		
	}
	
	@Test
	public void doNotCalculateTax_WhenProductIsNotMusicType() {
		PurchaseOrder po = new PurchaseOrder();
		
		po.setTextOrder("1 apple at 00.50");
		
		assertEquals(0.00, po.salesTaxCalculator(po));
		assertEquals(false, po.isMusicProduct());
		assertEquals(false, po.isImport());
		assertEquals(true, po.isBasicTaxFree());
	}
	
	@Test
	public void mustCalculateTax_WhenProductIsImportMusicType() {
		PurchaseOrder po = new PurchaseOrder();
		
		po.setTextOrder("2 imported music CD at 10.00");
		
		assertEquals(6.00, po.salesTaxCalculator(po));
		assertEquals(true, po.isMusicProduct());
		assertEquals(true, po.isImport());
		assertEquals(false, po.isBasicTaxFree());
	}
	
	@Test
	public void mustCalculateTax_WhenProductIsTaxFree() {
		PurchaseOrder po = new PurchaseOrder();
		
		po.setTextOrder("1 music book at 10.00");
		
		assertEquals(1.50, po.salesTaxCalculator(po));
		assertEquals(true, po.isMusicProduct());
		assertEquals(false, po.isImport());
		assertEquals(true, po.isBasicTaxFree());
	}

}
