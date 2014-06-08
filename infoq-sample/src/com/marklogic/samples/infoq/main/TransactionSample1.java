package com.marklogic.samples.infoq.main;

import javax.xml.bind.JAXBContext;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.FailedRequestException;
import com.marklogic.client.ForbiddenUserException;
import com.marklogic.client.Transaction;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.samples.infoq.exception.InventoryUnavailableException;
import com.marklogic.samples.infoq.model.Customer;
import com.marklogic.samples.infoq.model.InventoryEntry;
import com.marklogic.samples.infoq.model.LineItem;
import com.marklogic.samples.infoq.model.Order;

public class TransactionSample1
{

	public TransactionSample1()
	{
	}

	public static void main(String[] args)
	{
		DatabaseClient client = null;
		try
		{
			
			client = DatabaseClientFactory.newClient("localhost", 8072, "admin", "admin", Authentication.DIGEST);
			XMLDocumentManager docMgr = client.newXMLDocumentManager();
			
			Class[] classes = {
					com.marklogic.samples.infoq.model.Customer.class,
					com.marklogic.samples.infoq.model.InventoryEntry.class,
					com.marklogic.samples.infoq.model.Order.class
			};
			JAXBContext context = JAXBContext.newInstance(classes);
			JAXBHandle jaxbHandle = new JAXBHandle(context);
			
			Transaction transaction = client.openTransaction();
			try
			{
				// get the artichoke inventory
				String artichokeUri="/infoq/inventory/artichoke.xml";
				docMgr.read(artichokeUri, jaxbHandle);
				InventoryEntry artichokeInventory = jaxbHandle.get(InventoryEntry.class);
				System.out.println("Got the entry for " + artichokeInventory.getItemName());
				
				// get the bongo inventory
				String bongoUri="/infoq/inventory/bongo.xml";
				docMgr.read(bongoUri, jaxbHandle);
				InventoryEntry bongoInventory = jaxbHandle.get(InventoryEntry.class);
				System.out.println("Got the entry for " + bongoInventory.getItemName());
				
				// get the airplane inventory
				String airplaneUri="/infoq/inventory/airplane.xml";
				docMgr.read(airplaneUri, jaxbHandle);
				InventoryEntry airplaneInventory = jaxbHandle.get(InventoryEntry.class);
				System.out.println("Got the entry for " + airplaneInventory.getItemName());
				
				// get the customer
				docMgr.read("/infoq/customers/customer-2.xml", jaxbHandle);
				Customer customer = jaxbHandle.get(Customer.class);
				System.out.println("Got the customer " + customer.getFirstName());

				// Prep the order
				String itemName=null;
				double itemPrice=0;
				int quantity=0;
				
				Order order = new Order().setOrderNum(1).setCustomer(customer);
				LineItem[] items = new LineItem[3];
				// Add 3 artichokes
				itemName=artichokeInventory.getItemName();
				itemPrice=artichokeInventory.getPrice();
				quantity=3;
				items[0] = new LineItem().setItem(itemName).setUnitPrice(itemPrice).setQuantity(quantity).setTotal(itemPrice*quantity);
				System.out.println("Added artichoke line item.");
				// Decrement artichoke inventory
				artichokeInventory.decrementItem(quantity);
				System.out.println("Decremented " + quantity + " artichoke(s) from inventory.");
				
				// Add a bongo
				itemName=bongoInventory.getItemName();
				itemPrice=bongoInventory.getPrice();
				quantity=1;
				items[1] = new LineItem().setItem(itemName).setUnitPrice(itemPrice).setQuantity(quantity).setTotal(itemPrice*quantity);
				System.out.println("Added bongo line item.");
				// Decrement bongo inventory
				bongoInventory.decrementItem(quantity);
				System.out.println("Decremented " + quantity + " bongo(s) from inventory.");

				// Add an airplane
				itemName=airplaneInventory.getItemName();
				itemPrice=airplaneInventory.getPrice();
				quantity=1;
				items[2] = new LineItem().setItem(itemName).setUnitPrice(itemPrice).setQuantity(quantity).setTotal(itemPrice*quantity);
				System.out.println("Added airplane line item.");
				// Decrement airplane inventory
				airplaneInventory.decrementItem(quantity);
				System.out.println("Decremented " + quantity + " airplane(s) from inventory.");

				// Add all line items to the order
				order.setLineItems(items);
				// Add some notes to the order
				order.setNotes("Customer may either have a dog or is possibly a talking dog.");
				jaxbHandle.set(order);
				// Write the order to the DB
				docMgr.write("/infoq/orders/order-"+order.getOrderNum()+".xml", jaxbHandle);
				System.out.println("Order was written to the DB");
				
				jaxbHandle.set(artichokeInventory);
				docMgr.write(artichokeUri, jaxbHandle);
				System.out.println("Artichoke inventory was written to the DB");
				
				jaxbHandle.set(bongoInventory);
				docMgr.write(bongoUri, jaxbHandle);
				System.out.println("Bongo inventory was written to the DB");

				jaxbHandle.set(airplaneInventory);
				docMgr.write(airplaneUri, jaxbHandle);
				System.out.println("Airplane inventory was written to the DB");
				
				// Commit the whole thing
				transaction.commit();
			}
			catch (FailedRequestException fre)
			{
				transaction.rollback();
				throw new RuntimeException("Things did not go as planned.", fre);
			}
			catch (ForbiddenUserException fue)
			{
				transaction.rollback();
				throw new RuntimeException("You don't have permission to do such things.", fue);
			}
			catch (InventoryUnavailableException iue)
			{
				transaction.rollback();
				throw new RuntimeException("It appears there's not enough inventory for something. You may want to do something about it...", iue);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Things did not go as planned.", e);
		}
	}

}
