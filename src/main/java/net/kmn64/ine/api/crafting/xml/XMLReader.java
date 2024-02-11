package net.kmn64.ine.api.crafting.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	
	public static List<RecipeData> loadRecipefromXML(String recipetype)
	{
		List<RecipeData> recipelist = new ArrayList<RecipeData>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Tuple<String,Boolean> _tuple = isFileExist(new ResourceLocation(ImmersiveNuclearEngineering.MODID,recipetype));
			if (!_tuple.getB())
				throw new IOException();
		} catch (Exception e) {
			ImmersiveNuclearEngineering.LOGGER.fatal(e.toString());
		}
		
		return recipelist;
	}
}
