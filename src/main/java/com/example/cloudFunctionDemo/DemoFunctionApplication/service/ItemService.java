package com.example.cloudFunctionDemo.DemoFunctionApplication.service;


import java.util.List;
import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Item;
import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Order;
//not being used since code added in function
public interface ItemService {
	
	public List<Item> getAllItems();
	public void addItem(Item item); 
	public Item getItemById(String id);
	public List<Order> getAllOrders();
	public Order addOrder(Order order);
}
