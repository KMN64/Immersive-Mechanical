package net.kmn64.im.main.item.materialsystem.system;

import net.kmn64.im.main.item.IMBaseItem;
import net.kmn64.im.main.item.materialsystem.material.IMMaterials;
import net.kmn64.im.main.item.materialsystem.part.IMParts;

public interface IMMaterialSystemItem {
    IMMaterials getMaterial();
    IMParts getPart();

    int getColor();
}
