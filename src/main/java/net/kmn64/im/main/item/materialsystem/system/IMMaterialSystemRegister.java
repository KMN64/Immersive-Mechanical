package net.kmn64.im.main.item.materialsystem.system;

import javax.annotation.Nullable;

import net.kmn64.im.main.IMRegister;
import net.kmn64.im.main.item.materialsystem.material.IMMaterials;
import net.kmn64.im.main.item.materialsystem.part.IMParts;
import net.minecraft.item.Item;

/**
 * The IMMaterials class is responsible for managing and registering various materials and their parts.
 * It contains a predefined list of materials and provides methods to register and find items based on these materials.
 */
public class IMMaterialSystemRegister {
    public static void register()
    {
        IMMaterialParts[] parts = IMMaterialParts.values();
        for (IMMaterialParts part : parts)
        {
            IMMaterials material = part.getEnumMaterial();
            for (IMParts _part : part.getParts())
                IMRegister.itemRegister(new IMMaterialSystem(material, _part));
        }
    }

    @Nullable
    public static IMMaterialSystem find(IMMaterials material,IMParts part)
    {
        String materialName = material.getMaterial().getMaterialName();
        String partName = part.getPart().getPartName();

        return (IMMaterialSystem)IMRegister.findItem(String.format("%s_%s", materialName, partName));
    }
}