package net.kmn64.im.main.item.materialsystem.material;

public enum IMMaterials {  
    SODIUM_CHLORIDE(new IMMaterial("sodium_chloride")),
    MAGNESIUM_CHLORIDE(new IMMaterial("magnesium_chloride").setColor(0x716d6c)),
    LITHIUM_CHLORIDE(new IMMaterial("lithium_chloride").setColor(0x737062)),
    CALCIUM_CHLORIDE(new IMMaterial("calcium_chloride").setColor(0xc4c4c4)),
    AMMONIUM_CHLORIDE(new IMMaterial("ammonium_chloride").setColor(0x9b9b9b));

    private final IMMaterial material;

    private IMMaterials(IMMaterial material)
    {
        this.material = material;
    }

    public IMMaterial getMaterial()
    {
        return material;
    }
}
