package com.github.molkars.expando;

import com.github.molkars.expando.commands.ExpandoCommand;
import com.github.molkars.expando.data.WorldConfiguration;
import com.github.molkars.expando.schedule.TimedExpand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Expando extends JavaPlugin  {
  public static Expando instance;

  public Expando() {
    instance = this;
    worldFile = new File(getDataFolder() + "world.yaml");
    configFile = new File(getDataFolder() + "config.yaml");
    config = (YamlConfiguration) getConfig();
  }

  public static final WorldConfiguration world = new WorldConfiguration();
  public static File worldFile;
  public static YamlConfiguration config;
  public static File configFile;

  @Override
  public void onEnable() {
    saveDefaultConfig();
    setupCommands();
    readWorldData();
    setupSchedules();
    setupListeners();
  }
  @Override
  public void onDisable() {
    saveWorldData();
    saveConfig();
  }

  void setupCommands() {
    PluginCommand command = getCommand("expando");
    ExpandoCommand cmd = new ExpandoCommand();
    assert command != null;
    command.setExecutor(cmd);
    command.setTabCompleter(cmd);
  }
  void setupSchedules() {
    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TimedExpand(), 0, getConfig().getInt("time.expansion-interval"));
    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::saveWorldData, 0, 500);
  }
  void setupListeners() {
    getServer().getPluginManager().registerEvents(new ExpandoListener(), this);
  }
  void saveWorldData() {
    try {
      world.save(worldFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  void readWorldData() {
    try {
      world.load(worldFile);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }
  }

  public static Expando getInstance() {
    return instance;
  }

  public static void updateDifferences(World real) {
    WorldBorder border = real.getWorldBorder();
    if (border.getSize() != world.getSize()) world.setSize(border.getSize());
    if (border.getDamageAmount() != world.getDamage()) world.setDamage(border.getDamageAmount());
    if (border.getDamageBuffer() != world.getDamageGracePeriod()) world.setDamageGracePeriod(border.getDamageBuffer());
    if (border.getCenter().getX() != world.getCenterX()) world.setCenterX(border.getCenter().getX());
    if (border.getCenter().getY() != world.getCenterY()) world.setCenterY(border.getCenter().getY());
    if (border.getCenter().getZ() != world.getCenterZ()) world.setCenterZ(border.getCenter().getZ());
  }
}
