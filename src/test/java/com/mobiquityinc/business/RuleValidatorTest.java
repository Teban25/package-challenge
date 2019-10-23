package com.mobiquityinc.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mobiquityinc.DTO.Item;
import com.mobiquityinc.DTO.Packet;
import com.mobiquityinc.exception.BusinessException;

public class RuleValidatorTest {

	private RuleValidator ruleValidator;
	private Packet packet;
	
	@Before
	public void setUp() {
		ruleValidator = new RuleValidatorImpl();
	}
	
	private List<Item> setUpItemsCorrect() {
		List<Item> items = new ArrayList<>();
		
		items.add(new Item(1,53.38,45));
		items.add(new Item(2,88.62,98));
		items.add(new Item(3,78.48,3));
		items.add(new Item(4,72.30,76));
		items.add(new Item(5,30.18,9));
		items.add(new Item(6,46.34,48));
		
		return items;
	}
	
	private List<Item> setUpItemsMaxIndex() {
		List<Item> items = new ArrayList<>();
		
		items.add(new Item(1,80,99));
		items.add(new Item(2,50,66));
		items.add(new Item(3,25,3));
		items.add(new Item(4,30,26));
		items.add(new Item(5,25,30));
		items.add(new Item(6,15,46));
		items.add(new Item(7,90,51));
		items.add(new Item(8,20,21));
		items.add(new Item(9,9,17));
		items.add(new Item(10,6,63));
		items.add(new Item(11,70,21));
		items.add(new Item(12,55,39));
		items.add(new Item(13,35,15));
		items.add(new Item(14,63,68));
		items.add(new Item(15,69,91));
		items.add(new Item(16,85,3));
		
		return items;
	}
	
	private List<Item> setUpItemsMaxWeightAndPrice() {
		List<Item> items = new ArrayList<>();
		
		items.add(new Item(1,106,99));
		items.add(new Item(2,50,66));
		items.add(new Item(3,121,3));
		items.add(new Item(4,30,132));
		items.add(new Item(5,25,30));
		items.add(new Item(6,15,46));
		
		return items;
	}
	
	/**
	 * Test Case that throws a BusinessException
	 * when the max weight package is greater than 100
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testThat_throwsBusinessException_when_weightPackageIsGreaterThanOneHundred()
			throws BusinessException{
		//GIVEN
		packet = new Packet(120, new ArrayList<>());
		//WHEN
		ruleValidator.validateConstraints(packet);
		//THEN
	}
	
	/**
	 * Test Case that throws a BusinessException
	 * when the items package is greater than 15
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testThat_throwsBusinessException_when_itemsWeightOrPriceNumberPackageIsGreaterThanFifteen()
			throws BusinessException{
		//GIVEN
		packet = new Packet(80, setUpItemsMaxIndex());
		//WHEN
		ruleValidator.validateConstraints(packet);
		//THEN
	}
	
	/**
	 * Test Case that throws a BusinessException
	 * when the items weight or price are greater than 100
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testThat_throwsBusinessException_when_itemsPackageAreGreaterThanOneHundred()
			throws BusinessException{
		//GIVEN
		packet = new Packet(80, setUpItemsMaxWeightAndPrice());
		//WHEN
		ruleValidator.validateConstraints(packet);
		//THEN
	}
	
	/**
	 * Test Case that not throw any exception when all items
	 * are correct
	 * @throws BusinessException
	 */
	@Test
	public void testThat_notThrowAnyException_when_allItemsAreCorrect()
			throws BusinessException{
		//GIVEN
		packet = new Packet(80, setUpItemsCorrect());
		//WHEN
		ruleValidator.validateConstraints(packet);
		//THEN
	}
	
	/**
	 * Test Case that get the index result correctly
	 * @throws BusinessException
	 */
	@Test
	public void testThat_getAnIndexResultCorrectly()
			throws BusinessException{
		//GIVEN
		packet = new Packet(81, setUpItemsCorrect());
		//WHEN
		String result = ruleValidator.validatePacketGoal(packet);
		//THEN
		assertEquals("4", result);
	}
}
