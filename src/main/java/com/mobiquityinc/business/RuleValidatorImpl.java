package com.mobiquityinc.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.mobiquityinc.DTO.Item;
import com.mobiquityinc.DTO.Packet;
import com.mobiquityinc.exception.BusinessException;

public class RuleValidatorImpl implements RuleValidator{
	
	private static final Predicate<Item> ITEM_WEIGHT_PRICE = (i)->(i.getPrice()>100 || i.getWeight()>100);

	@Override
	public void validateConstraints(Packet packet) throws BusinessException {
		if(packet.getWeight() > 100) {
			throw new BusinessException("The max weight package is greater than 100");
		}
		
		if(packet.getItems().size() > 15) {
			throw new BusinessException("The max items package is greater than 15");
		}
		
		int itemsFound = (int) packet.getItems().stream().filter(ITEM_WEIGHT_PRICE).count();
		
		if(itemsFound > 0) {
			throw new BusinessException("There are some items that their weight or price are greater than 100");
		}
	}
	
	@Override
	public String validatePacketGoal(Packet packet) {
		StringBuilder indexs = new StringBuilder();
		double weightCount = 0;
		
		packet.setItems(packet.getItems().stream().
				filter(i->i.getWeight()<packet.getWeight()).collect(Collectors.toList()));
		packet.getItems().sort(Comparator.comparing(Item::getPrice, Collections.reverseOrder()));
		packet.setItems(orderByWeight(packet.getItems()));
		
		for(Item item : packet.getItems()) {
			weightCount = weightCount + item.getWeight();
			
			if(weightCount <= packet.getWeight()) {
				indexs.append(item.getIndex()).append(",");
			}else {
				weightCount = weightCount - item.getWeight();
				if(weightCount == packet.getWeight()) {
					break;
				}
			}
		}
		
		String result = indexs.toString();
		
		if(result != null && !result.isEmpty()) {
			result = result.substring(0, result.length()-1);
		}else {
			result = "-";
		}
		
		return result;
		
	}
	
	private List<Item> orderByWeight(List<Item> items){
		for(int i = 0; i<items.size(); i++) {
			if((i+1<items.size()) && (items.get(i).getPrice() == items.get(i+1).getPrice())) {
				if(items.get(i).getWeight()>items.get(i+1).getWeight()) {
					Item aux = items.get(i);
					items.set(i, items.get(i+1));
					items.set(i+1, aux);
				}
			}
		}
		return items;
	}

}
