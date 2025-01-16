package net.kmn64.im.main.creativetab.subcreativetab.category;

import java.util.Arrays;

import javax.annotation.Nonnull;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.creativetab.IMCreativeTab;
import net.kmn64.im.main.creativetab.subcreativetab.SubCreativeTabMaterials;
import net.kmn64.im.main.item.materialsystem.material.IMMaterials;
import net.kmn64.im.main.item.materialsystem.part.IMParts;
import net.kmn64.im.main.item.materialsystem.system.IMMaterialSystem;
import net.kmn64.im.main.item.materialsystem.system.IMMaterialSystemRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * CTMaterial is a custom ItemGroup for organizing items related to materials in the mod.
 * It extends the ItemGroup class and provides a custom icon and search bar functionality.
 * 
 * <p>This class is a singleton, with the single instance accessible via the {@code INSTANCE} field.
 * The constructor sets the background suffix for the item group and initializes it with a name
 * based on the mod ID.</p>
 * 
 * <p>The {@code makeIcon} method specifies the icon for the item group, which is an item stack
 * of "Sodium Chloride" (Salt). The {@code hasSearchBar} method indicates that this item group
 * includes a search bar.</p>
 * 
 * @see ItemGroup
 */
public class CTMaterial extends IMCreativeTab {
    public static final CTMaterial INSTANCE = new CTMaterial();

    /**
     * The ResourceLocation for the menu image used in the creative tab.
     * This field is initialized to the default menu image for this creative tab.
     */
    private static final ResourceLocation CREATIVETAB_MENU_TEXTURES = new ResourceLocation(IMMain.MODID, "textures/gui/creativetab/im_menu.png");
    
    /**
     * The ResourceLocation for the tabs image used in the creative tab.
     * This field is initialized to the default tabs image for this creative tab.
     */
    private static final ResourceLocation CREATIVETAB_TABS_TEXTURES = new ResourceLocation(IMMain.MODID, "textures/gui/creativetab/im_tabs.png");
    
    /**
     * The selected material group for the creative tab.
     * This field is initialized to the default material group (SubCreativeTabMaterials.MATERIAL).
     */
    private static SubCreativeTabMaterials selectedCT = SubCreativeTabMaterials.MATERIAL;

    public CTMaterial()
    {
        super();
        setBackgroundImage(CREATIVETAB_MENU_TEXTURES);
    }

    public static void updateSubCT(SubCreativeTabMaterials group) {
        selectedCT = group;
    }

    public static SubCreativeTabMaterials getCurrentSubCT() {
        return selectedCT;
    }

    @Override
    public Item getItem() {
        return IMMaterialSystemRegister.find(IMMaterials.SODIUM_CHLORIDE,IMParts.SALT);
    }

    @SuppressWarnings("null")
    @Override
    public Tuple<Boolean, ResourceLocation> getSearchBar() {
        return new Tuple<>(false,null);
    }

    /**
     * Retrieves the ResourceLocation for the tabs image used in the creative tab.
     * This method overrides the default implementation to provide a custom tabs image.
     *
     * @return A ResourceLocation object pointing to the custom tabs image for this creative tab.
     *         In this case, it returns the CREATIVETAB_TABS_TEXTURES constant.
     */
    @Override
    public ResourceLocation getTabsImage() {
        return CREATIVETAB_TABS_TEXTURES;
    }


    /**
     * Retrieves the color for the label of this creative tab.
     * This method overrides the default implementation to provide a custom color.
     *
     * @return An integer representing the RGB color value for the label.
     *         In this case, it returns the color code for green.
     */
    @SuppressWarnings("null")
    @Override
    public int getLabelColor() {
        return TextFormatting.GREEN.getColor();
    }

    
    /**
     * Retrieves the display name for this creative tab.
     * This method is only called on the client side.
     *
     * @return An ITextComponent representing the formatted display name of the creative tab.
     *         The name is constructed using the mod ID and the category name of the currently selected material group.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getDisplayName() {
        return new StringTextComponent(String.format("itemGroup.%smaterial.%s",IMMain.MODID, getCurrentSubCT().getCreativeTabMaterial().getCategoryName()));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemList(@Nonnull NonNullList<ItemStack> items) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof IMMaterialSystem)
            {
                IMMaterialSystem materialSystem = (IMMaterialSystem)item;
                IMParts[] parts = getCurrentSubCT().getCreativeTabMaterial().getParts();

                if (Arrays.asList(parts).indexOf(materialSystem.getPart()) > -1)
                    items.add(new ItemStack(item));
            }
        }
    }
}
