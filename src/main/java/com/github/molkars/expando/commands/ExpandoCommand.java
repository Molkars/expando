package com.github.molkars.expando.commands;

import com.github.molkars.expando.Expando;
import com.github.molkars.expando.data.WorldConfiguration;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExpandoCommand implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
    _Command cmd = getCommand(args);
    switch (cmd) {
      case START:
        return executeStartCommand(sender, command, label, args);
      case STATUS:
        return executeStatusCommand(sender, command, label, args);
      default:
      case INVALID:
        sender.sendMessage("Invalid command"); // Continue to return false
      case EXPANDO:
        return false;
    }
  }

  private boolean executeStatusCommand(CommandSender sender, Command command, String label, String[] args) {
    WorldConfiguration world = Expando.world;
    sender.sendMessage(Component.text(ChatColor.GOLD + "Mob Kills: " + world.getMobCount()));
    sender.sendMessage(Component.text(ChatColor.GOLD + "Passive Kills: " + world.getPassiveCount()));
    sender.sendMessage(Component.text(ChatColor.GOLD + "Size: " + world.getSize()));
    return true;
  }

  private boolean executeStartCommand(CommandSender sender, Command command, String label, String[] args) {
    Expando.world.setSize(8);
    String name;
    Location location;
    if (sender instanceof Player) {
      location = ((Player) sender).getLocation().toCenterLocation();
      name = ((Player) sender).getWorld().getName();
    } else {
      World first = Bukkit.getWorlds().get(0);
      location = first.getSpawnLocation().toCenterLocation();
      name = first.getName();
    }

    Expando.world.setCenterX(location.getX());
    Expando.world.setCenterY(location.getY());
    Expando.world.setCenterZ(location.getZ());
    Expando.world.setDamage(10);
    Expando.world.setDamageGracePeriod(10);
    Expando.world.setWorldName(name);

    sender.sendMessage("Updated world");
    return true;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    ArrayList<String> list = new ArrayList<>();
    if (args.length < 2) {
      String query = args.length > 0 ? args[0] : "";
      if ("status".contains(query.toLowerCase())) list.add("status");
    }
    return list;
  }

  _Command getCommand(String[] args) {
    if (args.length == 0) return _Command.EXPANDO;

    switch (args[0].toLowerCase()) {
      case "start":
        return _Command.START;
      case "status":
        return _Command.STATUS;
      default:
        return _Command.INVALID;
    }
  }

  private enum _Command {
    INVALID,
    EXPANDO,
    STATUS,
    START,
  }
}
