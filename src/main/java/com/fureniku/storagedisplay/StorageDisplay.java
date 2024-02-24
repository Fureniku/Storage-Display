package com.fureniku.storagedisplay;

import com.fureniku.metropolis.utils.Debug;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StorageDisplay.MODID)
public class StorageDisplay {

    public static final String MODID = "storagedisplay";

    public static StorageDisplay INSTANCE;
    public RegistrationDecorations registration;

    public StorageDisplay() {
        INSTANCE = this;
        Debug.registerMod("Storage Display");

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        registration = new RegistrationDecorations(MODID, modEventBus);
        registration.init(modEventBus);
    }
}
