package net.kmn64.im.main.item.materialsystem.system;

import net.kmn64.im.main.item.materialsystem.material.IMMaterial;
import net.kmn64.im.main.item.materialsystem.material.IMMaterials;
import net.kmn64.im.main.item.materialsystem.part.IMParts;

public enum IMMaterialParts {
    SODIUM_CHLORIDE(IMMaterials.SODIUM_CHLORIDE, IMParts.SALT),
    MAGNESIUM_CHLORIDE(IMMaterials.MAGNESIUM_CHLORIDE, IMParts.SALT),
    LITHIUM_CHLORIDE(IMMaterials.LITHIUM_CHLORIDE, IMParts.SALT),
    CALCIUM_CHLORIDE(IMMaterials.CALCIUM_CHLORIDE, IMParts.SALT),
    AMMONIUM_CHLORIDE(IMMaterials.AMMONIUM_CHLORIDE, IMParts.SALT);

    private final IMMaterials material;
    private final IMParts[] parts;

    private IMMaterialParts(IMMaterials material, IMParts... parts)
    {
        this.material = material;
        this.parts = parts;
    }

    public IMMaterials getEnumMaterial()
    {
        return material;
    }

    public IMMaterial getMaterial()
    {
        return material.getMaterial();
    }

    public IMParts[] getParts()
    {
        return parts;
    }

    public IMMaterial findMaterial(String materialName)
    {
        for (IMMaterialParts material : IMMaterialParts.values())
        {
            if (material.getMaterial().getMaterialName().equals(materialName))
                return material.getMaterial();
        }
        return null;
    }

    public IMMaterial findMaterial(IMParts part)
    {
        for (IMMaterialParts material : IMMaterialParts.values())
        {
            for (IMParts part1 : material.getParts())
            {
                if (part1.getPart().getPartName().equals(part.getPart().getPartName()))
                    return material.getMaterial();
            }
        }
        return null;
    }
}
