package net.kmn64.im.main.creativetab.subcreativetab;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import blusunrize.immersiveengineering.client.ClientUtils;
import net.kmn64.im.IMMain;
import net.kmn64.im.main.creativetab.subcreativetab.category.CTMaterial;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;

@Mod.EventBusSubscriber(modid = IMMain.MODID, bus = Bus.MOD)
public class CreativeTabHandler {
    private static boolean isPressed = false;
    private static final ResourceLocation CTEXPANSION_GUI_TEXTURES = new ResourceLocation(IMMain.MODID, "textures/gui/creativetab/creativetab_expansion.png");
    private static final List<CreativeTabButton> subCTButtons = new LinkedList<CreativeTabButton>();

    @SubscribeEvent
    public void drawScreen(GuiScreenEvent.BackgroundDrawnEvent event) {
        Screen screen = event.getGui();
        if (screen instanceof CreativeScreen)
        {
            CreativeScreen creativeScreen = (CreativeScreen)screen;
            if (creativeScreen.getSelectedTab()==CTMaterial.INSTANCE.getId() && !subCTButtons.isEmpty())
                subCTButtons.forEach((button) -> {
                    button.active = true;
                    button.visible = true;
                });
            else
                subCTButtons.forEach((button) -> {
                    button.active = false;
                    button.visible = false;
                });
        }
    }

    @SubscribeEvent
    public void initializeGuiEvent(GuiScreenEvent.InitGuiEvent event) {
        Screen screen = event.getGui();
        if (screen instanceof CreativeScreen)
        {
            CreativeScreen creativeScreen = (CreativeScreen)screen;
            int i = (int) (creativeScreen.getGuiLeft() - Math.floor(136*1.425));
            int j = (creativeScreen.height - 195) / 2;

            for(int iteration = 0; iteration < SubCreativeTabMaterials.values().length; iteration++) {
                SubCreativeTabMaterials currentCT = SubCreativeTabMaterials.values()[iteration];

                CreativeTabButton button = new CreativeTabButton(i + 166 + 7, j + 46 + iteration,button1 -> {
                    CTMaterial.updateSubCT(currentCT);
                    creativeScreen.resize(creativeScreen.getMinecraft(), creativeScreen.width, creativeScreen.height);
                    isPressed = true;
                });

                subCTButtons.add(button);
                event.addWidget(button);
            }
        }
    }

    public static class CreativeTabButton extends Button {
        public CreativeTabButton(int x, int y, Button.IPressable onPress) {
            super(x, y, 27, 23, StringTextComponent.EMPTY, onPress);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float ticks) {
            if(!visible) return;
            
            ClientUtils.bindTexture(CTEXPANSION_GUI_TEXTURES);
            GlStateManager._color4f(1f,1f,1f,1f);

            boolean isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            matrixStack.pushPose();

            if (!isPressed)
                AbstractGui.blit(matrixStack, x, y, isHovered ? 28 : 0, 0, width + ((isHovered) ? 28 : 0), height, 256, 256);
            else
                AbstractGui.blit(matrixStack, x, y, 56, 0, 27, height, 256, 256);

            matrixStack.popPose();

            matrixStack.pushPose();

            AbstractGui.blit(matrixStack, x, y, 84, 0, 19, height, 256, 256);

            matrixStack.popPose();

            isPressed = false;
        }
    }
}
