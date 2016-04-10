package tinker_io.registry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.IFluidBlock;
import tinker_io.api.MeshDefinitionFix;
import tinker_io.main.Main;

public class FluidRenderRegister {
	private static final String FLUID_MODEL_PATH = Main.MODID + ":" + "fluid";
	
	public static void fluidModelRegister(){
		registerFluid(FluidRegister.blockPureMetal);
		//registerFluidModel(FluidRegister.blockPureMetal);
		System.out.println("[Tinker I/O] Fluid Model loaded!");
	}
	
	public static void registerFluid(BlockFluidClassic blockFluid){
		Item item = Item.getItemFromBlock(blockFluid);
		String name = blockFluid.getFluid().getName();
		
		ModelResourceLocation modleLoc = new ModelResourceLocation(Main.MODID+ ":" +"fluid"/*, name*/);
		//System.out.println("MRSL:" + modleLoc.toString());
		
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition(){

			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return modleLoc;
			}
			
		});
		ModelLoader.setCustomStateMapper(blockFluid, new StateMapperBase(){

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modleLoc;
			}
			
		});
		System.out.println("TIO/TIO");
	}
	
	public static void registerFluidModel(IFluidBlock fluidBlock) {
		Item item = Item.getItemFromBlock((Block) fluidBlock);

		//ModelBakery.addVariantName(item);
		ModelBakery.registerItemVariants(item, new ResourceLocation(FLUID_MODEL_PATH ,fluidBlock.getFluid().getName()));
		System.out.println("[TIO] " + fluidBlock.getFluid().getName());

		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid().getName());

		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}
	
	
}
