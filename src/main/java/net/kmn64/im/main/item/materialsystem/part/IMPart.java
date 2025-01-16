package net.kmn64.im.main.item.materialsystem.part;

import javax.annotation.Nonnull;

public class IMPart {
    private final String partName;
    private String tagName = "";

    public IMPart(String partName)
    {
        this.partName = partName;
    }

    public String getPartName()
    {
        return partName;
    }

    public IMPart setTagName(@Nonnull String tagName)
    {
        this.tagName = tagName;
        return this;
    }

    public String getTagName()
    {
        return tagName;
    }
}
