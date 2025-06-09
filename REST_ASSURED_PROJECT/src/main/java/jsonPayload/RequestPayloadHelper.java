package jsonPayload;

public class RequestPayloadHelper
{
	
	public String getRequestBody(RequestPayloadVO requestPayloadVO)
	{
		String body = "{\r\n" + "    \"firstname\" : \""+requestPayloadVO.getFirstName()+"\",\r\n" + "    \"lastname\" : \""+requestPayloadVO.getLastName()+"\",\r\n"
				+ "    \"totalprice\" : "+requestPayloadVO.getTotalprice()+",\r\n" + "    \"depositpaid\" : "+requestPayloadVO.isDepositpaid()+",\r\n" + "    \"bookingdates\" : {\r\n"
				+ "        \"checkin\" : \""+requestPayloadVO.getBookingdates().get("checkin")+"\",\r\n" + "        \"checkout\" : \""+requestPayloadVO.getBookingdates().get("checkout")+"\"\r\n" + "    }\r\n"
				+ "}";
		
		return body;
	}
	
	
	
	
	
	
	
	
}
