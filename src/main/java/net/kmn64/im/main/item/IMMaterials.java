package net.kmn64.im.main.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import net.kmn64.im.main.IMRegister;
import net.minecraft.item.Item;

public class IMMaterials {
    public static String[][][] materials = new String[][][]
    {
        {{"Item"},{"test"}}
    };

    private static Item[] materials_exception = new Item[]
    {

    };

    public static void register()
    {
        for (String[][] materials : IMMaterials.materials) {
            String material = materials[0][0]; // Index thingy
            for (String part : materials[1]) {
                String name = String.format("%s_%s", material.toLowerCase(), part);
                Stream<Item> items = Arrays.stream(IMMaterials.materials_exception);
                boolean isExcept = items.anyMatch((item)->item.getRegistryName().getPath()==name);
                if (isExcept) IMRegister.itemRegister(new IMBaseItem(name)); else IMRegister.itemRegister(items.filter((item)->item.getRegistryName().getPath()==name).toArray(Item[]::new)[0]);
            }
        }
    }
}