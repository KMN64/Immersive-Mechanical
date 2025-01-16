package net.kmn64.im.main.creativetab.subcreativetab;

import net.kmn64.im.main.item.materialsystem.part.IMParts;

public class SubCreativeTabMaterial {
    private final String categoryName;
    private final IMParts[] parts;
    protected SubCreativeTabMaterial(String categoryName, IMParts... parts)
    {
        this.categoryName = categoryName;
        this.parts = parts;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public IMParts[] getParts()
    {
        return parts;
    }
}
