package net.kmn64.im.main;

import java.util.LinkedHashMap;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.fluid.IMBaseFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = IMMain.MODID, bus = Bus.MOD)
public class IMContent {
    public static LinkedHashMap<String,IMBaseFluid> IMFluids = new LinkedHashMap<>();
}
