package tinker_io.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.util.IStringSerializable;
import slimeknights.mantle.block.EnumBlock;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.Locale;

public class SmelteryStructureRegister {

    private static ImmutableSet.Builder<Block> builder = ImmutableSet.builder();

    public static void init() {
        for (Block block : TinkerSmeltery.validSmelteryBlocks) {
            builder.add(block);
        }

        //Register blocks here
        builder.add(BlockRegistry.fuelInputMachine);

        TinkerSmeltery.validSmelteryBlocks = builder.build();
    }

    public enum AdditionalComponentType implements IStringSerializable, EnumBlock.IEnumMeta {

        FUEL_INPU_MACHINE;

        public final int meta;

        AdditionalComponentType() {
            meta = ordinal();
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta() {
            return meta;
        }
    }
}
