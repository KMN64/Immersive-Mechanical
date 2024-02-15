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
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;

public class XMLWriter {
	private static final String recipepath="config/ine/recipes";
	
	private static final String[] elements = new String[] {"Recipes","Recipe","Input","Output","InputTag"};
	private static final String[] attributes = new String[] {"time","energy"};
	private static final String[] elements_new = new String[] {"ItemStack","FluidStack"};
	
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
	        
	        ItemStack[] inputitems = recipedata.inputData.items;
	        FluidStack[] inputfluids = recipedata.inputData.fluids;
	        
	        Map<ITag.INamedTag<Item>,Integer> inputtagitems = recipedata.inputData.tagitems;
	        INEFluidTagInput[] inputtagfluids = recipedata.inputData.tagfluids;
	        
	        ItemStack[] outputitems = recipedata.inputData.items;
	        FluidStack[] outputfluids = recipedata.inputData.fluids;
	        
	        //Input
	        if (inputitems !=null)
	        	writerecipeitemsinput(doc,recipeElement,inputitems);
	        
	        if (inputfluids !=null)
	        	writerecipefluidsinput(doc,recipeElement,inputfluids);
	        
	        if (inputtagitems !=null)
	        	writerecipetagitemsinput(doc,recipeElement,inputtagitems);
	        
	        if (inputtagfluids !=null)
	        	writerecipetagfluidsinput(doc,recipeElement,inputtagfluids);
	        
	        // Output
	        if (outputitems !=null)
	        	writerecipeitemsoutput(doc,recipeElement,outputitems);
	        
	        if (outputfluids !=null)
	        	writerecipefluidsoutput(doc,recipeElement,outputfluids);
	        
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

	@SuppressWarnings("deprecation")
	private static void writerecipefluidsoutput(Document document, Element recipeElement, FluidStack[] fluids) {
		// TODO Auto-generated method stub
		for (FluidStack fluid : fluids)
        {
        	Element inputElement = document.createElement(elements[3]);
        	Element typeElement = document.createElement(elements_new[1]);
        	inputElement.setTextContent(Registry.FLUID.getKey(fluid.getFluid()).toString()+";"+fluid.getAmount());
        	inputElement.appendChild(typeElement);
            recipeElement.appendChild(inputElement);
        }
	}


	@SuppressWarnings("deprecation")
	private static void writerecipeitemsoutput(Document document, Element recipeElement, ItemStack[] items) {
		// TODO Auto-generated method stub
		for (ItemStack item : items)
        {
        	Element inputElement = document.createElement(elements[3]);
        	Element typeElement = document.createElement(elements_new[0]);
        	inputElement.setTextContent(Registry.ITEM.getKey(item.getItem()).toString()+";"+item.getCount());
        	inputElement.appendChild(typeElement);
        	recipeElement.appendChild(inputElement);
        }
	}


	private static void writerecipetagfluidsinput(Document document, Element recipeElement, INEFluidTagInput[] tagfluids) {
		// TODO Auto-generated method stub
		for (INEFluidTagInput tagfluid:tagfluids)
		{
			Element inputElement = document.createElement(elements[4]);
			Element typeElement = document.createElement(elements_new[1]);
        	inputElement.setTextContent(tagfluid.getNamedFluidTag()+";"+tagfluid.getAmount());
        	inputElement.appendChild(typeElement);
            recipeElement.appendChild(inputElement);
		}
	}


	private static void writerecipetagitemsinput(Document document, Element recipeElement, Map<ITag.INamedTag<Item>,Integer> tagitems) {
		// TODO Auto-generated method stub
		tagitems.forEach((tagitem,amount)->{
			Element inputElement = document.createElement(elements[4]);
			Element typeElement = document.createElement(elements_new[0]);
        	inputElement.setTextContent(tagitem.getName().toString()+";"+amount);
        	inputElement.appendChild(typeElement);
            recipeElement.appendChild(inputElement);
		});
	}

	@SuppressWarnings("deprecation")
	private static void writerecipefluidsinput(Document document, Element recipeElement, FluidStack[] fluids) {
		// TODO Auto-generated method stub
		for (FluidStack fluid : fluids)
        {
        	Element inputElement = document.createElement(elements[2]);
        	Element typeElement = document.createElement(elements_new[1]);
        	inputElement.setTextContent(Registry.FLUID.getKey(fluid.getFluid()).toString()+";"+fluid.getAmount());
        	inputElement.appendChild(typeElement);
            recipeElement.appendChild(inputElement);
        }
	}

	@SuppressWarnings("deprecation")
	private static void writerecipeitemsinput(Document document,Element recipeElement, ItemStack[] items) {
		// TODO Auto-generated method stub
		for (ItemStack item : items)
        {
        	Element inputElement = document.createElement(elements[2]);
        	Element typeElement = document.createElement(elements_new[0]);
        	inputElement.setTextContent(Registry.ITEM.getKey(item.getItem()).toString()+";"+item.getCount());
        	inputElement.appendChild(typeElement);
        	recipeElement.appendChild(inputElement);
        }
	}
	
	/** For test only **/
	public static boolean writingTest()
	{
		return writeXMLfromRecipe(RecipesData.TEST);
	}
}
