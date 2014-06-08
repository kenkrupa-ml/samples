package com.marklogic.samples.infoq.main;

import javax.xml.bind.JAXBContext;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.samples.infoq.model.Customer;


public class SmokeTest
{

	public SmokeTest()
	{
	}

	public static void main(String[] args)
	{
		try
		{
			// Open a connection on localhost:8072 with username/password credentials of admin/admin using DIGEST authentication
			DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8072, "admin", "admin", Authentication.DIGEST);
			// Get a document manager from the client
			XMLDocumentManager docMgr = client.newXMLDocumentManager();
			
			// Establish a context object for the Customer class
			JAXBContext context = JAXBContext.newInstance(com.marklogic.samples.infoq.model.Customer.class, com.marklogic.samples.infoq.model.Order.class);
			
			// Get a new customer object and populate it
			Customer customer = new Customer();
			customer.setId(1L);
			customer.setFirstName("Frodo").setLastName("Baggins").setEmail("frodo.baggins@middleearth.org").setStreet("Bagshot Row, Bag End").setCity("Hobbiton").setStateOrProvince("The Shire");
			
			// Get a handle on the object to be persisted
			JAXBHandle customerHandle = new JAXBHandle(context);
			customerHandle.set(customer);
			
			// Write the object to the DB
			docMgr.write("/infoq/customers/customer-"+customer.getId()+".xml", customerHandle);
			System.out.println("Customer " + customer.getId() + " was written to the DB");
			
			Customer customer2 = new Customer();
			customer2.setId(2L);
			customer2.setFirstName("Rex").setLastName("Shepard").setEmail("rex@dogsrus.com").setStreet("1 Milk Bone Lane").setCity("Anytown").setPostalCode("12345");
			customerHandle.set(customer2);

			// Write the object to the DB
			docMgr.write("/infoq/customers/customer-"+customer2.getId()+".xml", customerHandle);
			System.out.println("Customer " + customer2.getId() + " was written to the DB");
			
			/*Order order = new Order().setOrderNum(1).setCustomer(customer);
			LineItem[] items = new LineItem[2];
			items[0] = new LineItem().setItem("thing 1").setUnitPrice(1.50).setQuantity(3).setTotal(4.50);
			items[1] = new LineItem().setItem("thing 2").setUnitPrice(5.00).setQuantity(2).setTotal(10.00);
			order.setLineItems(items);
			
			JAXBHandle orderHandle = new JAXBHandle(context);
			orderHandle.set(order);
			
			docMgr.write("/infoq/orders/order-"+order.getOrderNum()+".xml", orderHandle);
			
			System.out.println("Order " + order.getOrderNum() + " was written to the DB");*/
			
			QueryManager queryMgr = client.newQueryManager();
			StringQueryDefinition qd = queryMgr.newStringDefinition();
			qd.setCriteria("dog");
			SearchHandle results = queryMgr.search(qd, new SearchHandle());
			MatchDocumentSummary[] docs = results.getMatchResults();
			System.out.println("Found " + docs.length + " document match(es)");
			for (MatchDocumentSummary docSummary: docs)
			{
				System.out.println("Found match for " + docSummary.getUri() + " with snippet of: " + docSummary.getFirstSnippetText());
			}

		}
		catch (Exception e)
		{
			throw new RuntimeException("Things did not go as planned.", e);
		}
	}

}
