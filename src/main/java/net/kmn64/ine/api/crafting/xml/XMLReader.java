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
import net.kmn64.ine.common.utils.INEFluidTagInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;

public class XMLReader {
	private static final String[] elements = new String[] {"Recipes","Recipe","Input","Output","Inputtag"};
	public static Tuple<String,Boolean> isFileExist(ResourceLocation resourceLocation)
	{
		String filepath = "config/"+resourceLocation.getPath()+"/recipes/"+resourceLocation.getNamespace()+".xml"; // I don't sure about this
		File filetest = new File(filepath);
		return new Tuple<String, Boolean>(filepath,filetest.exists());
	}
	
	/**
	 * @param recipetype
	 * @return A list of {@code ReaderRecipeData}
	 */
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
			NodeList recipe = doc.getElementsByTagName(elements[1]);
			
			int time,energy;
			for (int i = 0; i<recipe.getLength();i++)
			{
				Node node = recipe.item(i);
				
				if (node.getNodeType()==Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					Attr timeAttribute = element.getAttributeNode("time");
					Attr energyAttribute = element.getAttributeNode("energy");
					time = Integer.valueOf(timeAttribute.getAttributes().item(0).getTextContent());
					energy = Integer.valueOf(energyAttribute.getAttributes().item(1).getTextContent());
					
					NodeList input = element.getElementsByTagName(elements[2]);
					NodeList output = element.getElementsByTagName(elements[3]);
					NodeList inputtag = element.getElementsByTagName(elements[4]);
					
					Map<String,String> inputMap = readrecipe(input);
					inputMap.putAll(readrecipetag(inputtag));
					Map<String,String> outputMap = readrecipe(output);
					
					// Input
					List<ItemStack> inputItemStack = new ArrayList<>();
					List<FluidStack> inputFluidStack = new ArrayList<>();
					Map<ITag.INamedTag<Item>,Integer> inputItemTag = new HashMap<>();
					List<INEFluidTagInput> inputFluidTag = new ArrayList<>();
					
					// Output
					List<ItemStack> outputItemStack = new ArrayList<>();
					List<FluidStack> outputFluidStack = new ArrayList<>();
					
					inputMap.forEach((a,b)->{
						Map<String,Integer> stack = splitToStack(b);
						String id = stack.keySet().toArray(new String[0])[0];
						int amount = stack.get(id);
						
						if (a=="ItemStack")
							inputItemStack.add(new ItemStack(Registry.ITEM.get(new ResourceLocation(id)),amount));
						if (a=="ItemStackTag")
							inputItemTag.put(ItemTags.bind(id),amount);
						
						if (a=="FluidStack")
							inputFluidStack.add(new FluidStack(Registry.FLUID.get(new ResourceLocation(id)),amount));
						if (a=="FluidStackTag")
							inputFluidTag.add(new INEFluidTagInput(FluidTags.bind(id),amount));
					});
					
					outputMap.forEach((a,b)->{
						Map<String,Integer> stack = splitToStack(b);
						String id = stack.keySet().toArray(new String[0])[0];
						int amount = stack.get(id);
						
						if (a=="ItemStack")
							outputItemStack.add(new ItemStack(Registry.ITEM.get(new ResourceLocation(id)),amount));
						if (a=="FluidStack")
							outputFluidStack.add(new FluidStack(Registry.FLUID.get(new ResourceLocation(id)),amount));
					});
					
					RecipeData INPUT = new RecipeData(inputItemStack.toArray(new ItemStack[0]), inputFluidStack.toArray(new FluidStack[0]), inputItemTag, inputFluidTag.toArray(new INEFluidTagInput[0]));
					RecipeData OUTPUT = new RecipeData(outputItemStack.toArray(new ItemStack[0]), outputFluidStack.toArray(new FluidStack[0]), null,null);
					recipelist.add(new ReaderRecipeData(INPUT,OUTPUT,time,energy));
				}
			}
		} catch (Exception e) {
			ImmersiveNuclearEngineering.LOGGER.fatal(e.toString());
		}
		
		return recipelist;
	}

	private static Map<String, Integer> splitToStack(String str) {
		// TODO Auto-generated method stub
		Map<String,Integer> hashmap = new HashMap<>();
		String[] splited = str.split(";");
		
		hashmap.put(splited[0],Integer.valueOf(splited[1]));
		
		return hashmap;
	}

	private static Map<String,String> readrecipe(NodeList nodelist) {
		if (nodelist == null) return Map.of();
		
		HashMap<String,String> hashmap =new HashMap<>();
		
		Node node = nodelist.item(0);
		if (node.getNodeType()==Node.ELEMENT_NODE)
		{
			Element recipeElement = (Element)node;
			NodeList itemStackNodes = recipeElement.getElementsByTagName("ItemStack");
			NodeList fluidStackNodes = recipeElement.getElementsByTagName("FluidStack");
			
			for (int j = 0; j < itemStackNodes.getLength(); j++) {
				Node _node = itemStackNodes.item(j);
				
				if (_node.getNodeType()==Node.ELEMENT_NODE)
					hashmap.put("ItemStack",((Element)_node).getTextContent());
			}
					
			for (int k = 0; k < fluidStackNodes.getLength(); k++) {
				Node _node = fluidStackNodes.item(k);
				
				if (_node.getNodeType()==Node.ELEMENT_NODE)
					hashmap.put("FluidStack",((Element)_node).getTextContent());
			}
		}
		
		return hashmap;
	}
	
	private static Map<String,String> readrecipetag(NodeList nodelist) {
		if (nodelist == null) return Map.of();
		
		HashMap<String,String> hashmap =new HashMap<>();
		
		Node node = nodelist.item(0);
		if (node.getNodeType()==Node.ELEMENT_NODE)
		{
			Element recipeElement = (Element)node;
			NodeList itemStackNodes = recipeElement.getElementsByTagName("ItemStack");
			NodeList fluidStackNodes = recipeElement.getElementsByTagName("FluidStack");
			
			for (int j = 0; j < itemStackNodes.getLength(); j++) {
				Node _node = itemStackNodes.item(j);
				
				if (_node.getNodeType()==Node.ELEMENT_NODE)
					hashmap.put("ItemStackTag",((Element)_node).getTextContent());
			}
					
			for (int k = 0; k < fluidStackNodes.getLength(); k++) {
				Node _node = fluidStackNodes.item(k);
				
				if (_node.getNodeType()==Node.ELEMENT_NODE)
					hashmap.put("FluidStackTag",((Element)_node).getTextContent());
			}
		}
		
		return hashmap;
	}
}
