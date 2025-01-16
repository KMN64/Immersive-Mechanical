package net.kmn64.im.main.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class IMBaseBlockItem extends BlockItem {
    public IMBaseBlockItem(Block block) {
        this(block, new Item.Properties());
    }

    public IMBaseBlockItem(Block block, Item.Properties properties) {
      super(block, properties);
      this.setRegistryName(block.getRegistryName());
   }
}
