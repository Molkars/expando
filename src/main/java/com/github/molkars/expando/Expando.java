package com.github.molkars.expando;

import com.github.molkars.expando.util.yaml.WorldDataSchema;
import com.github.molkars.expando.util.yaml.YamlParser;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Expando extends JavaPlugin implements Runnable {
  public static Expando instance;

  public Expando() {
    instance = this;
  }

  private File worldFile;
  private DataStore store;

  @Override
  public void onEnable() {
    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this, 0, 10);

    PluginCommand command = getCommand("expando");
    if (command == null) {
      getLogger().severe("Could not get command");
    } else {
      ExpandoCommand cmd = new ExpandoCommand();
      command.setExecutor(cmd);
      command.setTabCompleter(cmd);
    }

    store = new DataStore();

    getServer().getPluginManager().registerEvents(new ExpandoListener(), this);
  }

  void read() {
    store.worlds = YamlParser.parseWorldFile();
  }

  void write() {

  }

  @Override
  public void onDisable() {
  }

  @Override
  public void run() {
    int i = 0;
    while (i < store.worlds.getWorlds().size()) {
      final WorldDataSchema schema = store.worlds.getWorlds().get(i);
      World world = getServer().getWorld(schema.getName());
      if (world == null) continue;

      WorldBorder border = world.getWorldBorder();

      if (border.getSize() != schema.getSize()) { // Abandon this world
        store.worlds.getWorlds().remove(schema);
        continue;
      }

      long time = world.getFullTime();
      if (time < schema.getLastTime() && schema.getSize() < 128) {
        double size = schema.getSize() + 2;
        schema.setSize(size);
        border.setSize(size);
      }

      schema.setLastTime(time);
    }
  }

  public static Expando getInstance() {
    return instance;
  }

  public File getWorldFile() {
    return worldFile;
  }
  public void setWorldFile(File worldFile) {
    this.worldFile = worldFile;
  }

  public DataStore getStore() {
    return store;
  }
  public void setStore(DataStore store) {
    this.store = store;
  }
}
