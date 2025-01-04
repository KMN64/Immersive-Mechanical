package net.kmn64.im.main.block;

import net.kmn64.im.main.creativetabs.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class IMBaseBlockItem extends BlockItem {
    public IMBaseBlockItem(Block block) {
        this(block, new Item.Properties().tab(CreativeTab.INSTANCE));
    }

    public IMBaseBlockItem(Block block, Item.Properties properties) {
      super(block, properties);
      this.setRegistryName(block.getRegistryName());
   }
}
