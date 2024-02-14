package net.kmn64.ine.api.crafting.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

public class XMLReader {
	private static final String[] elements = new String[] {"Recipes","Recipe","Input","Output","Inputtag"};
	public static Tuple<String,Boolean> isFileExist(ResourceLocation resourceLocation)
	{
		String filepath = "config/"+resourceLocation.getPath()+"/recipes/"+resourceLocation.getNamespace()+".xml"; // I don't sure about this
		File filetest = new File(filepath);
		return new Tuple<String, Boolean>(filepath,filetest.exists());
	}
	
	public static List<ReaderRecipeData> loadRecipefromXML(String recipetype)
	{
		List<ReaderRecipeData> recipelist = new ArrayList<ReaderRecipeData>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Tuple<String,Boolean> _tuple = isFileExist(new ResourceLocation(ImmersiveNuclearEngineering.MODID,recipetype));
			
			if (!_tuple.getB())
				throw new IOException();
			
			Document doc = db.parse(_tuple.getA());
			doc.getDocumentElement().normalize();
			NodeList recipe = doc.getElementsByTagName(elements[0]);
			
			NodeList input = null;
			NodeList output = null;
			NodeList inputtag = null;
			int time,energy;
			for (int i = 0; i<recipe.getLength();i++)
			{
				Node node = recipe.item(i);
				
				if (node.getNodeType()==Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					input = element.getElementsByTagName(elements[0]);
					output = element.getElementsByTagName(elements[1]);
					inputtag = element.getElementsByTagName(elements[2]);
				}
				
				if (input!=null&&output!=null&&inputtag!=null)
				{
					if (input!=null)
					time = Integer.valueOf(attribute.getAttributes().item(0).getTextContent());
					energy = Integer.valueOf(attribute.getAttributes().item(1).getTextContent());
				}
			}
			
			readrecipefrominput(input);
			
		} catch (Exception e) {
			ImmersiveNuclearEngineering.LOGGER.fatal(e.toString());
		}
		
		return recipelist;
	}

	private static Map<String,String> readrecipefrominput(NodeList input) {
		if (input == null) return Map.of();
		
		HashMap<String,String> hashmap =new HashMap<>();
		
		for(int i=0;i<input.getLength();i++)
		{
			Node node = input.item(i);
			
			if (node.getNodeType()==Node.ELEMENT_NODE)
			{
				Element inputElement = (Element)node;
				hashmap.put(inputElement.getAttribute("type"),inputElement.getTextContent());
			}
		}
		
		return hashmap;
	}
}
