package net.kmn64.im.main;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.kmn64.im.main.fluid.IMBaseFluid;
import net.kmn64.im.main.item.IMBaseItem;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class IMRegister {
    private static LinkedList<Fluid> registedIMFluids = new LinkedList<>();
    private static LinkedList<Item> registedIMItems = new LinkedList<>();
    private static LinkedList<Block> registedIMBlocks = new LinkedList<>();

    public static void fluidRegister(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes)
    {
        registedIMFluids.add(new IMBaseFluid(fluidName, stillTex, flowingTex, buildAttributes));
    }

    

    public static void itemRegister(Item item)
    {
        registedIMItems.add(item);
    }

    public static void blockRegister(Block block)
    {
        registedIMBlocks.add(block);
    }

    public static LinkedList<Fluid> getRegistedFluid()
    {
        return registedIMFluids;
    }

    public static LinkedList<Item> getRegistedItem()
    {
        return registedIMItems;
    }

    public static LinkedList<Block> getRegistedBlock()
    {
        return registedIMBlocks;
    }

    public static Fluid findFluid(String name)
    {
        Fluid[] fluids = registedIMFluids.stream().filter((fluid)->fluid.getRegistryName().getPath()==name).toArray(Fluid[]::new);
        return fluids.length>0 ? fluids[0]: null;
    }

    public static Item findItem(String name)
    {
        Item[] items = registedIMItems.stream().filter((item)->item.getRegistryName().getPath()==name).toArray(Item[]::new);
        return items.length>0 ? items[0]: null;
    }

    public static Block findBlock(String name)
    {
        Block[] blocks = registedIMBlocks.stream().filter((block)->block.getRegistryName().getPath()==name).toArray(Block[]::new);
        return blocks.length>0 ? blocks[0]: null;
    }
}
