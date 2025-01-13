package net.kmn64.im.main;

import java.util.LinkedList;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

/**
 * The IMRegister class is responsible for registering and managing custom Fluids, Items, and Blocks
 * for the Immersive Mechanical mod. It provides methods to register these objects and retrieve them
 * by name.
 * 
 * <p>It maintains three separate lists for registered Fluids, Items, and Blocks, and provides methods
 * to add to these lists and retrieve objects from them.</p>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #fluidRegister(Fluid)} - Registers a Fluid object.</li>
 *   <li>{@link #itemRegister(Item)} - Registers an Item object.</li>
 *   <li>{@link #blockRegister(Block)} - Registers a Block object.</li>
 *   <li>{@link #getRegistedFluid()} - Returns the list of registered Fluids.</li>
 *   <li>{@link #getRegistedItem()} - Returns the list of registered Items.</li>
 *   <li>{@link #getRegistedBlock()} - Returns the list of registered Blocks.</li>
 *   <li>{@link #findFluid(String)} - Finds a Fluid by its registry name.</li>
 *   <li>{@link #findItem(String)} - Finds an Item by its registry name.</li>
 *   <li>{@link #findBlock(String)} - Finds a Block by its registry name.</li>
 * </ul>
 * 
 * <p>Note: The class uses LinkedList to store the registered objects.</p>
 */
public class IMRegister {
    private static final LinkedList<Fluid> registedIMFluids = new LinkedList<>();
    private static final LinkedList<Item> registedIMItems = new LinkedList<>();
    private static final LinkedList<Block> registedIMBlocks = new LinkedList<>();

    public static void fluidRegister(Fluid fluid)
    {
        if (fluid != null)
            registedIMFluids.add(fluid);
    }

    public static void itemRegister(Item item)
    {
        if (item != null)
            registedIMItems.add(item);
    }

    public static void blockRegister(Block block)
    {
        if (block != null)
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

    /**
     * Finds and returns a Fluid object by its name.
     *
     * @param name the name of the fluid to find
     * @return the Fluid object if found, otherwise null
     */
    @Nullable
    public static Fluid findFluid(String name)
    {
        Fluid[] fluids = registedIMFluids.stream().filter((fluid)->fluid.getRegistryName().getPath().equals(name)).toArray(Fluid[]::new);
        return fluids.length>0 ? fluids[0]: null;
    }

    /**
     * Finds an item by its registry name.
     *
     * @param name the registry name of the item to find
     * @return the item with the specified registry name, or null if no such item is found
     */
    @Nullable
    public static Item findItem(String name)
    {
        Item[] items = registedIMItems.stream().filter((item)->item.getRegistryName().getPath().equals(name)).toArray(Item[]::new);
        return items.length>0 ? items[0]: null;
    }

    /**
     * Finds a block by its registry name.
     *
     * @param name The name of the block to find.
     * @return The block with the specified name, or null if no such block is found.
     */
    @Nullable
    public static Block findBlock(String name)
    {
        Block[] blocks = registedIMBlocks.stream().filter((block)->block.getRegistryName().getPath().equals(name)).toArray(Block[]::new);
        return blocks.length>0 ? blocks[0]: null;
    }
}
