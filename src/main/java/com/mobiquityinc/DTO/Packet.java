package com.mobiquityinc.DTO;

import java.util.List;

public class Packet {

	private double weight;
	private List<Item> items;
	
	public Packet(double weight, List<Item> items) {
		this.weight = weight;
		this.items = items;
	}

	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
