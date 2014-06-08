package com.marklogic.samples.infoq.main;

import javax.xml.bind.JAXBContext;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.Transaction;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.samples.infoq.model.InventoryEntry;

public class LoadInventory
{

	public LoadInventory()
	{
	}

	public static void main(String[] args)
	{
		Transaction trans = null;
		try
		{
			DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8072, "admin", "admin", Authentication.DIGEST);
			XMLDocumentManager docMgr = client.newXMLDocumentManager();
			
			InventoryEntry entry = null;
			
			JAXBContext entryContext = JAXBContext.newInstance(com.marklogic.samples.infoq.model.InventoryEntry.class);
			JAXBHandle entryHandle = null;
			
			trans = client.openTransaction();
			
			entry = new InventoryEntry().setItemName("artichoke").setPrice(.50).setCount(1000);
			entryHandle = new JAXBHandle(entryContext);
			entryHandle.set(entry);
			docMgr.write("/infoq/inventory/"+entry.getItemName()+".xml", entryHandle);
			System.out.println("Inventory entry for " + entry.getItemName() + " was written to the DB");

			entry = new InventoryEntry().setItemName("airplane").setPrice(749999.99).setCount(1);
			entryHandle = new JAXBHandle(entryContext);
			entryHandle.set(entry);
			docMgr.write("/infoq/inventory/"+entry.getItemName()+".xml", entryHandle);
			System.out.println("Inventory entry for " + entry.getItemName() + " was written to the DB");

			entry = new InventoryEntry().setItemName("bongo").setPrice(25.00).setCount(50);
			entryHandle = new JAXBHandle(entryContext);
			entryHandle.set(entry);
			docMgr.write("/infoq/inventory/"+entry.getItemName()+".xml", entryHandle);
			System.out.println("Inventory entry for " + entry.getItemName() + " was written to the DB");
	
			entry = new InventoryEntry().setItemName("bass-drum").setPrice(75.00).setCount(500);
			entryHandle = new JAXBHandle(entryContext);
			entryHandle.set(entry);
			docMgr.write("/infoq/inventory/"+entry.getItemName()+".xml", entryHandle);
			System.out.println("Inventory entry for " + entry.getItemName() + " was written to the DB");

			entry = new InventoryEntry().setItemName("chalkboard").setPrice(35.00).setCount(10);
			entryHandle = new JAXBHandle(entryContext);
			entryHandle.set(entry);
			docMgr.write("/infoq/inventory/"+entry.getItemName()+".xml", entryHandle);
			System.out.println("Inventory entry for " + entry.getItemName() + " was written to the DB");

			entry = new InventoryEntry().setItemName("chariot").setPrice(1750.00).setCount(12);
			entryHandle = new JAXBHandle(entryContext);
			entryHandle.set(entry);
			docMgr.write("/infoq/inventory/"+entry.getItemName()+".xml", entryHandle);
			System.out.println("Inventory entry for " + entry.getItemName() + " was written to the DB");
			
			trans.commit();
			
		}
		catch (Exception e)
		{
			if (trans != null)
			{
				trans.rollback();
			}
			throw new RuntimeException("Things did not go as planned.", e);
		}
		finally
		{
		}
	}

}
