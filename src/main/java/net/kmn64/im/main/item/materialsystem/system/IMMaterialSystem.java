package net.kmn64.im.main.item.materialsystem.system;

import net.kmn64.im.main.creativetab.subcreativetab.category.CTMaterial;
import net.kmn64.im.main.item.IMBaseItem;
import net.kmn64.im.main.item.materialsystem.material.IMMaterials;
import net.kmn64.im.main.item.materialsystem.part.IMParts;
import net.minecraft.item.Item;

public class IMMaterialSystem extends IMBaseItem implements IMMaterialSystemItem {
    private final IMMaterials material;
    private final IMParts part;
    public IMMaterialSystem(IMMaterials material, IMParts part) {
        super(String.format("%s_%s", material.getMaterial().getMaterialName(), part.getPart().getPartName()), new Item.Properties().tab(CTMaterial.INSTANCE));

        this.material=material;
        this.part=part;
    }

    public IMMaterials getMaterial()
    {
        return material;
    }

    public IMParts getPart()
    {
        return part;
    }

    @Override
    public int getColor() {
        return material.getMaterial().getColor();
    }
}
