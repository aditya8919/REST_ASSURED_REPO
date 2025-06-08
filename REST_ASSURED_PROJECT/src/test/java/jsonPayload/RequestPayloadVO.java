package jsonPayload;

import java.util.HashMap;

public class RequestPayloadVO
{
	private String firstName;
	private String lastName;
	private int totalprice;
	private boolean depositpaid;
	private HashMap<String, String> bookingdates;
	
	
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public int getTotalprice()
	{
		return totalprice;
	}
	public void setTotalprice(int totalprice)
	{
		this.totalprice = totalprice;
	}
	public boolean isDepositpaid()
	{
		return depositpaid;
	}
	public void setDepositpaid(boolean depositpaid)
	{
		this.depositpaid = depositpaid;
	}
	public HashMap<String, String> getBookingdates()
	{
		return bookingdates;
	}
	public void setBookingdates(HashMap<String, String> bookingdates)
	{
		this.bookingdates = bookingdates;
	}

	
	

}
