package utilities;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLHelper
{

	public static String readData(String nodeName)
	{
		String nodeValue = null;
		try
		{
			File xmlFile = new File(ProjectPaths.runtimeXMlfilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName(nodeName);
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					nodeValue = element.getTextContent();
//					System.out.println("Value: " + nodeValue);
					break; // get first node's value
				}
			}

			return nodeValue;
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception while reading XML file : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static void writeNode(String tagName, String newValue)
	{
		try
		{
			File xmlFile = new File(ProjectPaths.runtimeXMlfilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			removeEmptyTextNodes(doc.getDocumentElement());
			
			NodeList nodeList = doc.getElementsByTagName(tagName);
			if (nodeList.getLength() > 0)
			{
				Node node = nodeList.item(0);
				node.setTextContent(newValue);
				ExtentReportsHelper.LogInfo("Updated node <" + tagName + "> to value: " + newValue);
			} 
			else
			{
				ExtentReportsHelper.LogFail("Tag <" + tagName + "> not found in the XML.");
				return;
			}

			// Write changes back to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);

		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception while writing XML file : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void removeEmptyTextNodes(Node node)
	{
		NodeList list = node.getChildNodes();
		for (int i = list.getLength() - 1; i >= 0; i--)
		{
			Node child = list.item(i);
			if (child.getNodeType() == Node.TEXT_NODE)
			{
				if (child.getTextContent().trim().isEmpty())
				{
					node.removeChild(child);
				}
			} else if (child.getNodeType() == Node.ELEMENT_NODE)
			{
				removeEmptyTextNodes(child); // Recurse
			}
		}
	}
}
