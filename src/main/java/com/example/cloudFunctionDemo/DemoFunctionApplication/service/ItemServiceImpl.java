package com.example.cloudFunctionDemo.DemoFunctionApplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Item;
import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Order;
import com.example.cloudFunctionDemo.DemoFunctionApplication.repository.ItemRepository;
import com.example.cloudFunctionDemo.DemoFunctionApplication.repository.OrderRepository;
//not being used since code added in function only
@Service
public class ItemServiceImpl implements ItemService{

	private final ItemRepository itemRespository;
	private final OrderRepository orderRepository;
	
	public ItemServiceImpl(ItemRepository itemRespository,OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
		this.itemRespository = itemRespository;
	}
	
	public List<Item> getAllItems(){
		List<Item> itemList = itemRespository.findAll();
		return itemList;
	}
	
	public void addItem(Item item) {
		itemRespository.save(item);
		}

	public List<Order> getAllOrders() {
		List<Order> orderList = orderRepository.findAll();
		return orderList;
	}

	public Order addOrder(Order order) {
		
		List<Item> itemList = order.getItemsList();
		List<Item> itemListNew = new ArrayList<Item>();
		for (Item item : itemList) {
			Item itemAddItem = itemRespository.findById(item.getId()).get();
			int countOfItem = itemRespository.findById(item.getId()).get().getCount();
			if(countOfItem>=item.getCount()) {
				Item itemAddOrder = new Item(item.getId(), item.getName(), item.getPrice(), item.getCount());
				itemListNew.add(itemAddOrder);
				itemAddItem.setCount(countOfItem - item.getCount());
				itemRespository.save(itemAddItem);
			}
			else
				return null;
		}
		order.setItemsList(itemListNew);
		orderRepository.save(order);
		return order;
		
	}

	public Item getItemById(String id) {
		Item item = itemRespository.findById(id).get();
		return item;
	}
}
