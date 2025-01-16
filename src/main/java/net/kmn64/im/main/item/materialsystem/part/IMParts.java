package net.kmn64.im.main.item.materialsystem.part;

public enum IMParts {
    SALT(new IMPart("salt")),
    NONMETALDUST(new IMPart("nonmetaldust"));

    private final IMPart part;
    private IMParts(IMPart part)
    {
        this.part = part;
    }

    public IMPart getPart()
    {
        return part;
    }
}
