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

}