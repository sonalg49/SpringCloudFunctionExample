package com.example.cloudFunctionDemo.DemoFunctionApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Item;
import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Order;
import com.example.cloudFunctionDemo.DemoFunctionApplication.repository.ItemRepository;
import com.example.cloudFunctionDemo.DemoFunctionApplication.repository.OrderRepository;
import com.example.cloudFunctionDemo.DemoFunctionApplication.service.ItemService;

@Component
public class FunctionClass {
	
	//@Autowired
	//ItemService itemservice;
	
	private final ItemRepository itemRespository;
	private final OrderRepository orderRepository;
	
	public FunctionClass(ItemRepository itemRespository,OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
		this.itemRespository = itemRespository;
	}
	
	@Bean
	public Supplier<List<Item>> getitems(){
		return () -> {
			List<Item> itemList = itemRespository.findAll();
			return itemList;
			//return itemservice.getAllItems();
		};
	}
	
	@Bean
	public Function<Item,String> additem(){
			return item -> {
				//itemservice.addItem(item);
				Optional<Item> itemPresent = itemRespository.findById(item.getId());
				String result = null;
				if(itemPresent.isPresent()) {
					Item oldItem = itemPresent.get();
					if(oldItem.getName().equals(item.getName())) {
						itemRespository.save(new Item(item.getId(), item.getName(), item.getPrice(), oldItem.getCount()+ item.getCount()));
						result = "item already present, count increased";
				}
					else {
						result = "add item with new different id starting from: "+ itemRespository.count();
					}

				}
				
				else {
					itemRespository.save(item);
					result ="new item added";
				}
				
				return result;
			};
		}
	
	@Bean
	public Function<String, Item> getItemUsingId(){
		return (id) -> {
			Item item = itemRespository.findById(id).get();
			return item;
			//return itemservice.getItemById(id);
		};
	}
	
	@Bean
	public Function<String,String> deleteItem(){
		return (id)	->{
			Item item = itemRespository.findById(id).get();
			if(item != null) {
				itemRespository.delete(item);
				return "item deleted";
			}
			else
				return "item not present";
			
		};
	}
	
	@Bean
	public Supplier<List<Order>> getorders(){
		return () -> {
			List<Order> orderList = orderRepository.findAll();
			return orderList;
		};
	}
	
	@Bean
	public Function<String,Order> getOrderById(){
		return (id) -> {
			Order order = orderRepository.findById(id).get();
			return order;
		};
	}
	
	@Bean
	public Function<Order, String> addorder(){
			return order -> {
				//Order addedOrder = itemservice.addOrder(order);
					List<Item> itemList = order.getItemsList();
					List<Item> itemListNew = new ArrayList<Item>();
					for (Item item : itemList) {
						Item itemAddItem = itemRespository.findById(item.getId()).get();
						int countOfItem = itemAddItem.getCount();
						if(countOfItem>=item.getCount()) {
							Item itemAddOrder = new Item(item.getId(), item.getName(), item.getPrice(), item.getCount());
							itemListNew.add(itemAddOrder);
							itemAddItem.setCount(countOfItem - item.getCount());
							itemRespository.save(itemAddItem);
						}
						else
							break;
					}
					order.setItemsList(itemListNew);
					orderRepository.save(order);
					Order addedOrder = order;
					
				
				if(addedOrder.getItemsList() != null)
					return "new order added";
				else
					return "item quantity less, so order not added";
			};
		}
	
	
	

}
