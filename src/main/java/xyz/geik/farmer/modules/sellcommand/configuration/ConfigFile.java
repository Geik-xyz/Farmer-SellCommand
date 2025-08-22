package xyz.geik.farmer.modules.sellcommand.configuration;

import lombok.Getter;
import lombok.Setter;
import xyz.geik.glib.shades.okaeri.configs.OkaeriConfig;
import xyz.geik.glib.shades.okaeri.configs.annotation.Comment;
import xyz.geik.glib.shades.okaeri.configs.annotation.NameStrategy;
import xyz.geik.glib.shades.okaeri.configs.annotation.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Modules file
 *
 * @author geik
 * @since 2.0
 */
@Getter
@Setter
@Names(strategy = NameStrategy.IDENTITY)
public class ConfigFile extends OkaeriConfig {

    @Comment({"if you want to use sell command module",
            "set feature to true."})
    private boolean status = false;

    @Comment({"Sell-all Args"})
    private List<String> sellAllCommands = Arrays.asList("all", "whole", "hepsi");

    @Comment({"Layout for gui placement",
            "l -> Left click icon",
            "r -> Right click icon",
            "s -> Shift Right click icon"
    })
    private List<String> geyserLayout = Arrays.asList(
            "         ",
            " l  r  s ",
            "         "
    );

    private GeyserGui geyserGui = new GeyserGui();

    @Getter
    @Setter
    public static class GeyserGui extends OkaeriConfig {

        private String guiName = "&8Farmer Geyser Gui";

        private Items items = new Items();

        /**
         * Items of buy gui settings
         *
         * @author amownyy
         * @since 2.0
         */
        @Getter
        @Setter
        public static class Items extends OkaeriConfig {

            private LeftClick leftClick = new LeftClick();
            /**
             * GeyserLeftClick item settings
             * (Only for geyser player)
             *
             * @author amownyy
             * @since 2.0
             */
            @Getter
            @Setter
            public static class LeftClick extends OkaeriConfig {
                private String name = "&eLeft Click";
                private List<String> lore = Arrays.asList(
                        "",
                        "&aGet the selected item as a",
                        "&astack in your farmer inventory."
                );
                private String material = "DIAMOND";
                private int modelData = 0;
                boolean hasGlow = false;
            }

            private RightClick rightClick = new RightClick();
            /**
             * GeyserRightClick item settings
             * (Only for geyser player)
             *
             * @author amownyy
             * @since 2.0
             */
            @Getter
            @Setter
            public static class RightClick extends OkaeriConfig {
                private String name = "&eRight Click";
                private List<String> lore = Arrays.asList(
                        "",
                        "&aGet the selected item as a",
                        "&amax amount in your farmer inventory."
                );
                private String material = "DIAMOND";
                private int modelData = 0;
                boolean hasGlow = false;
            }

            private ShiftRightClick shiftRightClick = new ShiftRightClick();
            /**
             * GeyserShiftRightClick item settings
             * (Only for geyser player)
             *
             * @author amownyy
             * @since 2.0
             */
            @Getter
            @Setter
            public static class ShiftRightClick extends OkaeriConfig {
                private String name = "&eShift+Right Click";
                private List<String> lore = Arrays.asList(
                        "",
                        "&aSell all items in your farmer inventory."
                );
                private String material = "DIAMOND";
                private int modelData = 0;
                boolean hasGlow = false;
            }
        }
    }

}