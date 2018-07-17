package tinker_io.handler;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {

    public static enum ItemUpgradeTypes implements IStringSerializable {

        //TODO Make Subitem Name Recognizable
        // https://www.youtube.com/watch?v=VBeE0-ryL4E
        BASE_UPG("0", 0), //upg_0
        SLOT_UPG_1("1", 1), //upg_1
        SLOT_UPG_2("2", 2), //upg_2
        SLOT_UPG_3("3", 3), //upg_3
        SLOT_UPG_4("4", 4), //upg_4
        REDETONE_UPG("5", 5), //upg_5
        SLOT_INFINITY_UPG("6", 6), //upg_6
        BASIN_UPGRADE("7", 7); //upg_7

        private int ID;
        private String name;

        private ItemUpgradeTypes(String name, int ID) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getID() {
            return this.ID;
        }

    }

}
