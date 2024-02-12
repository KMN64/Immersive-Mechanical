package net.kmn64.ine.api.crafting.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.utils.INEFluidTagInput;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;

public class XMLWriter {
	private static final String recipepath="config/ine/recipes";
	
	private static final String[] elements = new String[] {"Recipes","Recipe","Input","Output","Inputtag"};
	private static final String[] attributes = new String[] {"time","energy","type"};
	
	public static boolean writeXMLfromRecipe(WriterRecipeData recipedata)
	{
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.newDocument();
	        Element recipesElement = doc.createElement(elements[0]);
	        doc.appendChild(recipesElement);
	        Element recipeElement = doc.createElement(elements[1]);
	        
	        Attr timeattribute = doc.createAttribute(attributes[0]);
	        timeattribute.setValue(""+recipedata.time);
	        Attr energyattribute = doc.createAttribute(attributes[1]);
	        energyattribute.setValue(""+recipedata.energy);
	        recipeElement.setAttributeNode(timeattribute);
	        recipeElement.setAttributeNode(energyattribute);
	        recipesElement.appendChild(recipeElement);
	        
	        ItemStack[] items = recipedata.items;
	        FluidStack[] fluids = recipedata.fluids;
	        
	        Map<ITag.INamedTag<Item>,Integer> tagitems = recipedata.tagitems;
	        INEFluidTagInput[] tagfluids = recipedata.tagfluids;
	        
	        if (items !=null)
	        	writerecipeitemsinput(doc,recipeElement,items);
	        
	        if (fluids !=null)
	        	writerecipefluidsinput(doc,recipeElement,fluids);
	        
	        if (tagitems !=null)
	        	writerecipetagitemsinput(doc,recipeElement,tagitems);
	        
	        if (tagfluids !=null)
	        	writerecipetagfluidsinput(doc,recipeElement,tagfluids);
	        
	        // XML transformer
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(doc);
	        File filepath = new File(recipepath + "/"+recipedata.recipename+".xml");
	        if (!filepath.exists())
	        	new File(filepath.getAbsolutePath().replaceAll(filepath.getName(), "")).mkdir();
	        
	        StreamResult result = new StreamResult(new File(filepath.getAbsolutePath()));
	        transformer.transform(source, result);
        } catch(Exception e) {
        	try {
				PrintWriter pw = new PrintWriter(new File("inexml.log"));
				e.printStackTrace(pw);
			} catch (Exception ex) {}
        	return false;
        }
        
        return true;
	}

	
	private static void writerecipetagfluidsinput(Document document, Element recipeElement, INEFluidTagInput[] tagfluids) {
		// TODO Auto-generated method stub
		for (INEFluidTagInput tagfluid:tagfluids)
		{
			Element inputElement = document.createElement(elements[2]);
        	Attr typeattribute = document.createAttribute(attributes[2]);
        	typeattribute.setValue("fluid");
        	inputElement.setAttributeNode(typeattribute);
        	inputElement.setTextContent(tagfluid.getNamedFluidTag()+";"+tagfluid.getAmount());
            recipeElement.appendChild(inputElement);
		}
	}


	private static void writerecipetagitemsinput(Document document, Element recipeElement, Map<ITag.INamedTag<Item>,Integer> tagitems) {
		// TODO Auto-generated method stub
		tagitems.forEach((tagitem,amount)->{
			Element inputElement = document.createElement(elements[2]);
        	Attr typeattribute = document.createAttribute(attributes[2]);
        	typeattribute.setValue("fluid");
        	inputElement.setAttributeNode(typeattribute);
        	inputElement.setTextContent(tagitem.getName().toString()+";"+amount);
            recipeElement.appendChild(inputElement);
		});
	}

	@SuppressWarnings("deprecation")
	private static void writerecipefluidsinput(Document document, Element recipeElement, FluidStack[] fluids) {
		// TODO Auto-generated method stub
		for (FluidStack fluid : fluids)
        {
        	Element inputElement = document.createElement(elements[2]);
        	Attr typeattribute = document.createAttribute(attributes[2]);
        	typeattribute.setValue("fluid");
        	inputElement.setAttributeNode(typeattribute);
        	inputElement.setTextContent(Registry.FLUID.getKey(fluid.getFluid()).toString()+";"+fluid.getAmount());
            recipeElement.appendChild(inputElement);
        }
	}

	@SuppressWarnings("deprecation")
	private static void writerecipeitemsinput(Document document,Element recipeElement, ItemStack[] items) {
		// TODO Auto-generated method stub
		for (ItemStack item : items)
        {
        	Element inputElement = document.createElement(elements[2]);
        	Attr typeattribute = document.createAttribute(attributes[2]);
        	typeattribute.setValue("item");
        	inputElement.setAttributeNode(typeattribute);
        	inputElement.setTextContent(Registry.ITEM.getKey(item.getItem()).toString()+";"+item.getCount());
            recipeElement.appendChild(inputElement);
        }
	}
	
	/** For test only **/
	public static boolean writingTest()
	{
		return writeXMLfromRecipe(RecipesData.TEST);
	}
}
