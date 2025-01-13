package net.kmn64.im.main.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.kmn64.im.main.IMRegister;
import net.kmn64.im.main.block.IMBaseBlock;
import net.kmn64.im.main.creativetabs.CTMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Tuple;

/**
 * The IMMaterials class is responsible for managing and registering various materials and their parts.
 * It contains a predefined list of materials and provides methods to register and find items based on these materials.
 */
public class IMMaterials {

    /**
     * A three-dimensional array representing various materials and their common names.
     * Each material is represented as a two-element array where the first element is the 
     * chemical name and the second element is the common name.
     * 
     * Example:
     * {{"Item"},{"test"}}
     */
    public static final String[][][] materials = new String[][][]
    {
        //{{"Item"},{"test"}}
        {{"Sodium Chloride"},{"salt"}},
        {{"Magnesium Chloride"},{"salt"}},
        {{"Lithium Chloride"},{"salt"}},
        {{"Calcium Chloride"},{"salt"}},
        {{"Ammonium Chloride"},{"salt"}}
    };

    /**
     * The materials_exception array contains a list of items that are exceptions to the standard naming convention.
     * These items are registered with a different name than the default material_part format.
     */
    private static final Item[] materials_exception = new Item[]
    {

    };

    /**
     * Registers materials and their corresponding parts as blocks or items.
     * 
     * This method iterates over the materials defined in the `IMMaterials.materials` array.
     * For each material, it constructs a name by combining the material and part names,
     * converting them to lowercase and replacing spaces with underscores.
     * 
     * If the part name contains "block", it registers the part as a block using `IMRegister.blockRegister`.
     * Otherwise, it checks if the part is an exception by looking it up in the `IMMaterials.materials_exception` array.
     * If the part is not an exception, it registers the part as an item using `IMRegister.itemRegister`.
     * If the part is an exception, it registers the corresponding item from the `IMMaterials.materials_exception` array.
     */
    public static void register()
    {
        for (String[][] materials : IMMaterials.materials) {
            String material = materials[0][0]; // Index 0 is the material name
            for (String part : materials[1]) {
                String name = String.format("%s_%s", material.toLowerCase().replaceAll("\\s", "_"), part);
                if (part.toLowerCase().replaceAll("\\s", "_").equals("block"))
                {
                    IMRegister.blockRegister(new IMBaseBlock(name));
                }
                else
                {
                    boolean isExcept = Arrays.stream(IMMaterials.materials_exception).anyMatch((item)->item.getRegistryName().getPath()==name);
                    Item[] items = Arrays.stream(IMMaterials.materials_exception).filter((item)->item.getRegistryName().getPath()==name).toArray(Item[]::new);
                    if (!isExcept) IMRegister.itemRegister(new IMBaseItem(name, new Item.Properties().tab(CTMaterial.INSTANCE))); else IMRegister.itemRegister(items.length>0 ? items[0] : null);
                }
            }
        }
    }

    /**
     * Finds an item based on the given material and part.
     *
     * @param material the material of the item, which will be converted to lowercase and spaces replaced with underscores
     * @param part the part of the item, which will also be converted to lowercase and spaces replaced with underscores
     * @return the item found, or null if no item matches the given material and part
     */
    @Nullable
    public static Item find(String material, String part)
    {
        String name = String.format("%s_%s", material.toLowerCase().replaceAll("\\s", "_"), part);
        return IMRegister.findItem(name);
    }

    /**
     * Finds an item based on the given material and part.
     *
     * @param material the material of the item, which will be converted to lowercase and spaces replaced with underscores
     * @param part the part of the item, which will also be converted to lowercase and spaces replaced with underscores
     * @return the item found, or null if no item matches the given material and part
     */
    @Nullable
    public static String[] getMaterials(String part)
    {
        List<String> _materials = new LinkedList<String>();
        for (String[][] materials : IMMaterials.materials) {
            for (String _part : materials[1]) {
                if (_part.equals(part))
                    _materials.add(materials[0][0]);
            }
        }

        return _materials.toArray(new String[0]);
    }
}