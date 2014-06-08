package com.marklogic.samples.infoq.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"id", "firstName", "lastName", "email", "street", "city", "stateOrProvince", "postalCode", "countryCode"})

public class Customer
{
	private long id;
	private String email;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String stateOrProvince;
	private String postalCode;
	private String countryCode;

	public Customer()
	{
	}

	
	public long getId()
	{
		return this.id;
	}
	@XmlAttribute
	public void setId(long _id) // Regular setter - seems JAXB has issues with attributes and fancy setter
	{
		this.id = _id;
	}

	public String getEmail()
	{
		return this.email;
	}
	@XmlElement
	public Customer setEmail(String _email)
	{
		this.email = _email;
		return this;
	}

	public String getFirstName()
	{
		return this.firstName;
	}
	@XmlElement
	public Customer setFirstName(String _firstName)
	{
		this.firstName = _firstName;
		return this;
	}

	public String getLastName()
	{
		return this.lastName;
	}
	@XmlElement
	public Customer setLastName(String _lastName)
	{
		this.lastName = _lastName;
		return this;
	}

	public String getStreet()
	{
		return this.street;
	}
	@XmlElement
	public Customer setStreet(String _street)
	{
		this.street = _street;
		return this;
	}

	public String getCity()
	{
		return this.city;
	}
	@XmlElement
	public Customer setCity(String _city)
	{
		this.city = _city;
		return this;
	}

	public String getStateOrProvince()
	{
		return this.stateOrProvince;
	}
	@XmlElement
	public Customer setStateOrProvince(String _stateOrProvince)
	{
		this.stateOrProvince = _stateOrProvince;
		return this;
	}

	public String getPostalCode()
	{
		return this.postalCode;
	}
	@XmlElement
	public Customer setPostalCode(String _postalCode)
	{
		this.postalCode = _postalCode;
		return this;
	}

	public String getCountryCode()
	{
		return this.countryCode;
	}
	@XmlElement
	public Customer setCountryCode(String _countryCode)
	{
		this.countryCode = _countryCode;
		return this;
	}

}
