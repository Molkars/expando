package com.github.molkars.expando;

import com.github.molkars.expando.commands.ExpandoCommand;
import com.github.molkars.expando.data.model.WorldDataSchema;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public final class Expando extends JavaPlugin implements Runnable {
  public static Expando instance;
  static final Yaml worldDataFileYaml = new Yaml();

  public Expando() {
    instance = this;
  }

  private File worldFile;
  public WorldDataSchema world;

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Override
  public void onEnable() {

    PluginCommand command = getCommand("expando");
    if (command == null) {
      getLogger().severe("Could not get command");
    } else {
      ExpandoCommand cmd = new ExpandoCommand();
      command.setExecutor(cmd);
      command.setTabCompleter(cmd);
    }

    File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
    File config = Path.of(jarFile.getParent(), "../config/expando").toFile();
    if (!config.exists()) config.mkdirs();

    File worlds = Path.of(config.getAbsolutePath(), "./worlds.yaml").toFile();
    try {
      worlds.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    worldFile = Path.of(jarFile.getParent(), "../config/expando/worlds.yaml").toAbsolutePath().toFile();

    readWorldFile();

    if (world == null) {
      world = new WorldDataSchema();
      world.setSize(8);
      world.setLastTime(0);
      World first = Bukkit.getWorlds().get(0);
      Location location = first.getSpawnLocation().toCenterLocation();
      String name = first.getName();


      world.setCenterX(location.getX());
      world.setCenterY(location.getY());
      world.setCenterZ(location.getZ());
      world.setDamage(10);
      world.setDamageGracePeriod(10);
      world.setName(name);
    }

    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this, 0, 10);

    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::writeWorldFile, 0, 500);

    getServer().getPluginManager().registerEvents(new ExpandoListener(), this);
  }

  public void readWorldFile() {
    try {
      Map<String, Object> value = worldDataFileYaml.load(new FileInputStream(worldFile));
      world = WorldDataSchema.fromMap(value);
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
      getLogger().severe("Unable to read world data");
    }
  }

  public void writeWorldFile() {
    try {
      worldDataFileYaml.dump(world.toMap(), new FileWriter(worldFile));
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
      getLogger().severe("Unable to read world data");
    }
  }

  @Override
  public void onDisable() {
    writeWorldFile();
  }

  @Override
  public void run() {
    World realWorld = Bukkit.getWorld(world.getName());
    if (realWorld == null) {
      getLogger().severe("Unable to get world, defautling to first in list");
      realWorld = Bukkit.getWorlds().get(0);
    }

    updateDifferences(realWorld);

    long time = realWorld.getFullTime();
    if (time < world.getLastTime() && world.getSize() < 128)
      world.setSize(world.getSize() + 2);
    world.setLastTime(time);

    for (final World world : Bukkit.getWorlds()) {
      WorldBorder border = world.getWorldBorder();
      border.setSize(this.world.getSize());
    }
  }

  public static Expando getInstance() {
    return instance;
  }

  void updateDifferences(World real) {
    WorldBorder border = real.getWorldBorder();
    if (border.getSize() != world.getSize()) world.setSize(border.getSize());
    if (border.getDamageAmount() != world.getDamage()) world.setDamage(border.getDamageAmount());
    if (border.getDamageBuffer() != world.getDamageGracePeriod()) world.setDamageGracePeriod(border.getDamageBuffer());
    if (border.getCenter().getX() != world.getCenterX()) world.setCenterX(border.getCenter().getX());
    if (border.getCenter().getY() != world.getCenterY()) world.setCenterY(border.getCenter().getY());
    if (border.getCenter().getZ() != world.getCenterZ()) world.setCenterZ(border.getCenter().getZ());
  }
}
