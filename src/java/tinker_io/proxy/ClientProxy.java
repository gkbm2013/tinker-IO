package tinker_io.proxy;

import org.apache.logging.log4j.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tinker_io.TileEntity.StirlingEngineTileEntity;
import tinker_io.main.Main;
import tinker_io.registry.BlockRenderRegister;
import tinker_io.registry.FluidRenderRegister;
import tinker_io.registry.ItemColorRegister;
import tinker_io.registry.ItemRenderRegister;
import tinker_io.render.TileEntityStirlingEngineRender;

public class ClientProxy extends CommonProxy{
	public void registerRenderThings() {
		//System.out.println("[Tinker I/O] Render Started!");
		Main.logger.log(Level.INFO, "Render Started!");
		ClientRegistry.bindTileEntitySpecialRenderer(StirlingEngineTileEntity.class, new TileEntityStirlingEngineRender());
		FluidRenderRegister.fluidModelRegister();
	}
	
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
	 // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
	 // your packets will not work because you will be getting a client
	 // player even when you are on the server! Sounds absurd, but it's true.

	 // Solution is to double-check side before returning the player:
	 return (ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx));
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
	}
	@Override
	public void init(FMLInitializationEvent e) {
		this.registerRenderThings();
		//BlockRenderRegister.registerBlockRenderer();
		//ItemRenderRegister.registerItemRenderer();
		ItemColorRegister.regItemColor();
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
	}
}
