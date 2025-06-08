package testSuite;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import basePack.BaseClass;
import testScript.TestScript;

@Listeners(utilities.ListenersClass.class)
public class API_TestSuite extends BaseClass
{
	protected API_TestSuite() throws IOException
	{
		super();
	}

	@Test(priority = 1)
	public void verifyPostCall()
	{
		 Assert.assertTrue(new TestScript().verifyCreateBooking());
	}
	
	@Test(priority = 2)
	public void verifyGetCall()
	{
		Assert.assertTrue(new TestScript().getBookingDetailsById());
	}
	
	@Test(priority = 3)
	public void verifyGetAllBookingIds()
	{
		Assert.assertTrue(new TestScript().verifyGetAllBookings());
	}
	
	@Test(priority = 4)
	public void verifyPutCall()
	{
		Assert.assertTrue(new TestScript().updateBooking());
	}
	
	@Test(priority = 5)
	public void verifyPatchCall()
	{
		Assert.assertTrue(new TestScript().partiallyUpdateBooking());
	}
	
	@Test(priority = 6)
	public void verifyDeleteCall()
	{
		Assert.assertTrue(new TestScript().verifyDeleteBooking());
	}

}
