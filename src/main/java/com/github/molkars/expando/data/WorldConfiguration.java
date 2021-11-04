package com.github.molkars.expando.data;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class WorldConfiguration extends YamlConfiguration {

  public String getWorldName() {
    return getString("name");
  }
  public void setWorldName(String name) {
    set("name", name);
  }

  public double getSize() {
    return getDouble("size", getDefaultWorld().getWorldBorder().getSize());
  }
  public void setSize(double size) {
    set("size", size);
  }

  public double getDamage() {
    return getDouble("damage");
  }
  public void setDamage(double damage) {
    set("damage", damage);
  }

  public double getDamageGracePeriod() {
    return getDouble("damageGracePeriod");
  }
  public void setDamageGracePeriod(double damageGracePeriod) {
    set("damageGracePeriod", damageGracePeriod);
  }

  public double getCenterX() {
    return getDouble("centerX", getDefaultWorld().getSpawnLocation().getX());
  }
  public void setCenterX(double centerX) {
    set("centerX", centerX);
  }

  public double getCenterY() {
    return getDouble("centerY", getDefaultWorld().getSpawnLocation().getY());
  }
  public void setCenterY(double centerY) {
    set("centerY", centerY);
  }

  public double getCenterZ() {
    return getDouble("centerZ", getDefaultWorld().getSpawnLocation().getZ());
  }
  public void setCenterZ(double centerZ) {
    set("centerZ", centerZ);
  }

  public int getMobCount() {
    return getInt("mobCount", 100);
  }
  public void setMobCount(int mobCount) {
    set("mobCount", mobCount);
  }

  public int getPassiveCount() {
    return getInt("passiveCount", 250);
  }
  public void setPassiveCount(int passiveCount) {
    set("passiveCount", passiveCount);
  }

  public int getWitherCount() { return getInt("witherCount", 0); }
  public void setWitherCount(int witherCount) { set("witherCount", witherCount); }

  public int getEnderDragonCount() { return getInt("enderDragonCount", 0); }
  public void setEnderDragonCount(int enderDragonCount) { set("enderDragonCount", enderDragonCount); }

  private static World getDefaultWorld() {
    return Bukkit.getWorlds().get(0);
  }
}