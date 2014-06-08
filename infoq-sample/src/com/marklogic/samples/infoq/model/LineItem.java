package com.marklogic.samples.infoq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"item", "quantity", "unitPrice", "total"})
public class LineItem
{
	private int quantity;
	private String item;
	private double unitPrice;
	private double total;

	public LineItem()
	{
	}

	public int getQuantity()
	{
		return this.quantity;
	}
	@XmlElement
	public LineItem setQuantity(int _quantity)
	{
		this.quantity = _quantity;
		return this;
	}

	public String getItem()
	{
		return this.item;
	}
	@XmlElement
	public LineItem setItem(String _item)
	{
		this.item = _item;
		return this;
	}

	public double getUnitPrice()
	{
		return this.unitPrice;
	}
	@XmlElement
	public LineItem setUnitPrice(double _unitPrice)
	{
		this.unitPrice = _unitPrice;
		return this;
	}

	public double getTotal()
	{
		return this.total;
	}
	@XmlElement
	public LineItem setTotal(double _total)
	{
		this.total = _total;
		return this;
	}

}
