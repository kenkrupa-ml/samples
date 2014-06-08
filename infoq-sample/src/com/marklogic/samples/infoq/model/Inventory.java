package com.marklogic.samples.infoq.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.marklogic.samples.infoq.exception.InventoryUnavailableException;

@XmlRootElement
public class Inventory
{
	private Map<String, InventoryEntry> items = new HashMap<String, InventoryEntry>();

	public Inventory()
	{
	}
	
	public Inventory loadSample()
	{
		InventoryEntry item = new InventoryEntry().setItemName("apple").setPrice(.50).setCount(1000);
		this.items.put(item.getItemName(), item);

		item = new InventoryEntry().setItemName("airplane").setPrice(749999.99).setCount(1);
		this.items.put(item.getItemName(), item);

		item = new InventoryEntry().setItemName("bongo").setPrice(25.00).setCount(50);
		this.items.put(item.getItemName(), item);

		item = new InventoryEntry().setItemName("bass drum").setPrice(75.00).setCount(500);
		this.items.put(item.getItemName(), item);

		item = new InventoryEntry().setItemName("chalkboard").setPrice(35.00).setCount(10);
		this.items.put(item.getItemName(), item);

		item = new InventoryEntry().setItemName("chariot").setPrice(1750.00).setCount(12);
		this.items.put(item.getItemName(), item);
		
		return this;
	}
	
	public void incrementItem(String _item, int _amount)
	{
		InventoryEntry item = this.items.get(_item);
		int count=item!=null?item.getCount():0;
		if (item!=null) item.setCount(count+_amount);
	}
	
	public void decrementItem(String _item, int _amount) throws InventoryUnavailableException
	{
		InventoryEntry item = this.items.get(_item);
		
		int count=item!=null?item.getCount():0;
		
		if (item==null || (count - _amount) < 0)
		{
			throw new InventoryUnavailableException("Not enough inventory. Requested " + _amount + " but only " + count + " available.");
		}
		
		item.setCount(count-_amount);
	}

	public Map<String, InventoryEntry> getItems()
	{
		return this.items;
	}
	@XmlElement(name="item")
	public void setItems(HashMap<String, InventoryEntry> _items)
	{
		this.items = _items;
	}
}
