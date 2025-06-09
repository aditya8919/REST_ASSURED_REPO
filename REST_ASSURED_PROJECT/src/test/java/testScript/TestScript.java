package testScript;

import api_calls.API_Calls;
import utilities.AllObjects;
import utilities.ExtentReportsHelper;
import utilities.XMLHelper;

public class TestScript
{

	public boolean verifyCreateBooking()
	{
		boolean flag = false;
		try
		{
			if (AllObjects.api_Calls().createBooking())
			{
				ExtentReportsHelper.LogPass("Booking created successfully");
				flag = true;
			} 
			else
			{
				ExtentReportsHelper.LogFail("Failed to create booking.");
			}
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : verifyCreateBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}

		return flag;
	}

	public boolean getBookingDetailsById()
	{
		boolean flag = false;

		try
		{
			if (AllObjects.api_Calls().getBookingDetailsById())
			{
				ExtentReportsHelper.LogPass("Booking details retrieved.");
				flag = true;
			} 
			else
			{
				ExtentReportsHelper.LogFail("Failed to retrieve booking details.");
			}
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : getBookingDetailsById : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return flag;
	}
	
	public boolean verifyGetAllBookings()
	{
		boolean flag = false;

		try
		{
			if (AllObjects.api_Calls().getAllBookingIds())
			{
				ExtentReportsHelper.LogPass("All booking details retrieved.");
				flag = true;
			} 
			else
			{
				ExtentReportsHelper.LogFail("Failed to retrieve all booking details.");
			}
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : verifyGetAllBookings : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return flag;
	}
	
	
	public boolean updateBooking()
	{
		boolean flag = false;

		try
		{
			if (AllObjects.api_Calls().updateBooking())
			{
				ExtentReportsHelper.LogPass("Booking details updated successfully.");
				flag = true;
			} 
			else
			{
				ExtentReportsHelper.LogFail("Failed to update booking details.");
			}
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : updateBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	public boolean partiallyUpdateBooking()
	{
		boolean flag = false;

		try
		{
			if (AllObjects.api_Calls().partialUpdateBooking())
			{
				ExtentReportsHelper.LogPass("Booking details updated partially.");
				flag = true;
			} 
			else
			{
				ExtentReportsHelper.LogFail("Failed to do partial update of booking.");
			}
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : partiallyUpdateBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return flag;
	}

	public boolean verifyDeleteBooking()
	{
		boolean flag = false;

		try
		{
			if (AllObjects.api_Calls().deleteBooking())
			{
				ExtentReportsHelper.LogPass("Booking deleted successfully.");
				flag = true;
			} 
			else
			{
				ExtentReportsHelper.LogFail("Failed to delete booking.");
			}
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : verifyDeleteBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
}
