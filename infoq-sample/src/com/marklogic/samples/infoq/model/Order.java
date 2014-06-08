package com.marklogic.samples.infoq.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order
{
	private long orderNum;
	private Customer customer;
	private LineItem[] lineItems;
	private double total;
	private String notes;

	public Order()
	{
	}

	public long getOrderNum()
	{
		return this.orderNum;
	}
	@XmlAttribute
	public Order setOrderNum(long _orderNum)
	{
		this.orderNum = _orderNum;
		return this;
	}

	public Customer getCustomer()
	{
		return this.customer;
	}
	@XmlElement
	public Order setCustomer(Customer _customer)
	{
		this.customer = _customer;
		return this;
	}

	public LineItem[] getLineItems()
	{
		return this.lineItems;
	}
	@XmlElementWrapper(name = "lineItems")
	@XmlElement(name="lineItem")
	public Order setLineItems(LineItem[] _lineItems)
	{
		this.lineItems = _lineItems;
		if (_lineItems != null)
		{
			double total=0;
			for (LineItem item: _lineItems)
			{
				total+=item.getTotal();
			}
			this.setTotal(total);
		}
		return this;
	}

	public double getTotal()
	{
		return this.total;
	}

	public void setTotal(double _total)
	{
		this.total = _total;
	}

	public String getNotes()
	{
		return this.notes;
	}
	@XmlElement
	public Order setNotes(String _notes)
	{
		this.notes = _notes;
		return this;
	}
	
}
