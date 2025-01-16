package net.kmn64.im.main.creativetab.subcreativetab;

import net.kmn64.im.main.item.materialsystem.part.IMParts;

public enum SubCreativeTabMaterials {
    MATERIAL(0,new SubCreativeTabMaterial("material",IMParts.SALT));

    private final int index;
    private final SubCreativeTabMaterial material;
    private SubCreativeTabMaterials(int index, SubCreativeTabMaterial material)
    {
        this.index=index;
        this.material=material;
    }

    public int getIndex()
    {
        return index;
    }

    public SubCreativeTabMaterial getCreativeTabMaterial()
    {
        return material;
    }
}
