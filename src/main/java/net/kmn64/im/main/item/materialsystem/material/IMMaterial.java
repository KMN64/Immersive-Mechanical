package net.kmn64.im.main.item.materialsystem.material;

import javax.annotation.Nonnull;

public class IMMaterial {
    private int color = 0xffffff;
    private final String materialName;

    protected IMMaterial(String materialName)
    {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public IMMaterial setColor(@Nonnull Integer color)
    {
        this.color = color;
        return this;
    }

    public int getColor()
    {
        return color;
    }
}
