package com.marklogic.samples.infoq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.marklogic.samples.infoq.exception.InventoryUnavailableException;

@XmlRootElement
public class InventoryEntry
{
	String itemName;
	double price;
	int count;

	public InventoryEntry()
	{
	}

	public String getItemName()
	{
		return this.itemName;
	}
	@XmlElement
	public InventoryEntry setItemName(String _item)
	{
		this.itemName = _item;
		return this;
	}

	public double getPrice()
	{
		return this.price;
	}
	@XmlElement
	public InventoryEntry setPrice(double _price)
	{
		this.price = _price;
		return this;
	}

	public int getCount()
	{
		return this.count;
	}
	@XmlElement
	public InventoryEntry setCount(int _count)
	{
		this.count = _count;
		return this;
	}
	
	public void incrementItem(int _amount)
	{
		this.setCount(count+_amount);
	}
	
	public void decrementItem(int _amount) throws InventoryUnavailableException
	{
		if (this.count - _amount < 0)
		{
			throw new InventoryUnavailableException("Not enough inventory. Requested " + _amount + " but only " + count + " available.");
		}
		
		this.setCount(count-_amount);
	}

}
