package net.kmn64.im.main.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import net.kmn64.im.main.IMRegister;
import net.kmn64.im.main.block.IMBaseBlock;
import net.kmn64.im.main.creativetabs.CTMaterial;
import net.minecraft.item.Item;

public class IMMaterials {
    public static final String[][][] materials = new String[][][]
    {
        //{{"Item"},{"test"}}
        {{"Sodium Chloride"},{"Salt"}},
        {{"Magnesium Chloride"},{"Salt"}},
        {{"Lithium Chloride"},{"Salt"}},
        {{"Calcium Chloride"},{"Salt"}},
        {{"Ammonium Chloride"},{"Salt"}}
    };

    private static final Item[] materials_exception = new Item[]
    {

    };

    public static void register()
    {
        for (String[][] materials : IMMaterials.materials) {
            String material = materials[0][0]; // Index thingy
            for (String part : materials[1]) {
                String name = String.format("%s_%s", material.toLowerCase().replaceAll("\\s", "_"), part.toLowerCase().replaceAll("\\s", "_"));
                if (part=="block")
                {
                    IMRegister.blockRegister(new IMBaseBlock(name));
                }
                else
                {
                    boolean isExcept = Arrays.stream(IMMaterials.materials_exception).anyMatch((item)->item.getRegistryName().getPath()==name);
                    Item[] items = Arrays.stream(IMMaterials.materials_exception).filter((item)->item.getRegistryName().getPath()==name).toArray(Item[]::new);
                    if (isExcept) IMRegister.itemRegister(new IMBaseItem(name, new Item.Properties().tab(CTMaterial.INSTANCE))); else IMRegister.itemRegister(items.length>0 ? items[0] : null);
                }
            }
        }
    }

    public static Item find(String material, String part)
    {
        String name = String.format("%s_%s", material.toLowerCase().replaceAll("\\s", "_"), part.toLowerCase().replaceAll("\\s", "_"));
        return IMRegister.findItem(name);
    }
}