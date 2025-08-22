package xyz.geik.farmer.modules.sellcommand.commands;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.geik.farmer.Main;
import xyz.geik.farmer.api.handlers.FarmerItemSellEvent;
import xyz.geik.farmer.api.managers.FarmerManager;
import xyz.geik.farmer.helpers.ModuleHelper;
import xyz.geik.farmer.model.Farmer;
import xyz.geik.farmer.model.inventory.FarmerInv;
import xyz.geik.farmer.model.inventory.FarmerItem;
import xyz.geik.farmer.model.user.FarmerPerm;
import xyz.geik.farmer.modules.FarmerModule;
import xyz.geik.farmer.modules.sellcommand.SellCommand;
import xyz.geik.glib.chat.ChatUtils;
import xyz.geik.glib.shades.triumphteam.cmd.core.BaseCommand;
import xyz.geik.glib.shades.triumphteam.cmd.core.annotation.Command;
import xyz.geik.glib.shades.triumphteam.cmd.core.annotation.Default;
import xyz.geik.glib.shades.triumphteam.cmd.core.annotation.SubCommand;
import xyz.geik.glib.shades.xseries.XMaterial;

import java.util.Locale;

/**
 * SellCommand Commands
 *
 * @author geyik
 */
@RequiredArgsConstructor
@Command(value = "farmer", alias = {"farm", "çiftçi", "fm", "ciftci"})
public class MainCommand extends BaseCommand {

    /**
     * Base command
     * @param sender executor
     */
    @Default
    public void defaultCommand(CommandSender sender) {
        ChatUtils.sendMessage(sender, Main.getLangFile().getMessages().getUnknownCommand());
    }

    /**
     * Sell Basic Command
     * @param sender command sender
     * @param item item
     */
    @SubCommand(value = "sell", alias = {"sat"})
    public void sellCommand(CommandSender sender, String item) {
        FarmerModule module = ModuleHelper.getInstance().getModule("SellCommand");
        if (module == null) return;

        if (!module.isEnabled()) {
            ChatUtils.sendMessage(sender, SellCommand.getInstance().getLang().getString("geyserDisabled"));
            return;
        }
        // Checks if sender instanceof player
        if (sender instanceof ConsoleCommandSender)
            return;
        Player player = (Player) sender;
        // Checks item dropped in region of a player
        // And checks region owner has a farmer
        final String regionID;
        try {
            regionID = Main.getIntegration().getRegionID(((Player) sender).getLocation());
            if (regionID == null || !FarmerManager.getFarmers().containsKey(regionID))
                return;
            Farmer farmer = FarmerManager.getFarmers().get(regionID);
            // Sell All command
            if (SellCommand.getSellAllCommands().stream().anyMatch(cmd -> cmd.equalsIgnoreCase(item)))
                executeSellEvent(farmer, player, null);
                // Sell only one item command
            else
                executeSellEvent(farmer, player, item);
        }
        catch (NullPointerException ignored) {
        }
    }

    /**
     * Executes sell event for desired command
     * @param farmer region farmer
     * @param player target player
     * @param item to sell item @nullable
     */
    private static void executeSellEvent(Farmer farmer, @NotNull Player player, @Nullable String item) {
        // Checks farmer in collection state
        if (player.hasPermission("farmer.admin") ||
                farmer.getUsers().stream().anyMatch(user -> (
                        !user.getPerm().equals(FarmerPerm.COOP)
                                && user.getName().equalsIgnoreCase(player.getName())))) {
            // If replacer matches then replace the item
            // Sells only one item
            if (item != null) {
                if (player.hasPermission("farmer.sell." + item.toLowerCase(Locale.ROOT))) {
                    if (SellCommand.getNameReplacer().containsKey(item))
                        item = SellCommand.getNameReplacer().get(item);
                    String checkMaterial = item;
                    // If default items does not contain the material
                    if (FarmerInv.defaultItems.stream()
                            .noneMatch(defaultItem -> defaultItem.getMaterial().toString().equalsIgnoreCase(checkMaterial))) {
                        ChatUtils.sendMessage(player, SellCommand.getInstance().getLang().getString("cantFindTheItem"));
                        return;
                    }
                    FarmerItem toSell = farmer.getInv().getStockedItem(XMaterial.valueOf(item.toUpperCase(Locale.ENGLISH)));
                    sellItem(farmer, player, toSell);
                    return;
                }
            }
            else {
                if (player.hasPermission("farmer.sell.all")) {
                    // Loops all the items
                    farmer.getInv().getItems().forEach(farmerItem -> sellItem(farmer, player, farmerItem));
                    return;
                }
            }
        }
        // Execute when couldn't be returned
        ChatUtils.sendMessage(player, SellCommand.getInstance().getLang().getString("noPerm"));
    }

    /**
     * Triggers the sell event
     * @param farmer Farmer of region
     * @param player executor
     * @param item to sell item
     */
    private static void sellItem(Farmer farmer, Player player, FarmerItem item) {
        // Sell event execution
        FarmerItemSellEvent itemSellEvent = new FarmerItemSellEvent(farmer, item, player);
        itemSellEvent.setGeyser(true);
        Bukkit.getPluginManager().callEvent(itemSellEvent);
    }
}
