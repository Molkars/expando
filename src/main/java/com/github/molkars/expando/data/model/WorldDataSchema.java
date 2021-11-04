package com.github.molkars.expando.data.model;

import java.util.HashMap;
import java.util.Map;

public class WorldDataSchema {
  private String name;
  private double size;
  private double damage;
  private double damageGracePeriod;
  private double centerX, centerY, centerZ;
  private long lastTime;
  private int mobCount, passiveCount;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public double getSize() {
    return size;
  }
  public void setSize(double size) {
    this.size = size;
  }

  public double getDamage() {
    return damage;
  }
  public void setDamage(double damage) {
    this.damage = damage;
  }

  public double getDamageGracePeriod() {
    return damageGracePeriod;
  }
  public void setDamageGracePeriod(double damageGracePeriod) {
    this.damageGracePeriod = damageGracePeriod;
  }

  public double getCenterX() {
    return centerX;
  }
  public void setCenterX(double centerX) {
    this.centerX = centerX;
  }

  public double getCenterY() {
    return centerY;
  }
  public void setCenterY(double centerY) {
    this.centerY = centerY;
  }

  public double getCenterZ() {
    return centerZ;
  }
  public void setCenterZ(double centerZ) {
    this.centerZ = centerZ;
  }

  public long getLastTime() {
    return lastTime;
  }
  public void setLastTime(long lastTime) {
    this.lastTime = lastTime;
  }

  public int getMobCount() {
    return mobCount;
  }
  public void setMobCount(int mobCount) {
    this.mobCount = mobCount;
  }

  public int getPassiveCount() {
    return passiveCount;
  }
  public void setPassiveCount(int passiveCount) {
    this.passiveCount = passiveCount;
  }

  public static WorldDataSchema fromMap(Map<String, Object> map) throws NullPointerException {
    WorldDataSchema world = new WorldDataSchema();
    world.name = (String) map.get("name");
    world.size = (double) map.get("size");
    world.damage = (double) map.get("damage");
    world.damageGracePeriod = (double) map.get("damageGracePeriod");
    world.centerX = (double) map.get("centerX");
    world.centerY = (double) map.get("centerY");
    world.centerZ = (double) map.get("centerZ");
    world.lastTime = (int) map.get("lastTime");
    world.mobCount = (int) map.get("mobCount");
    world.passiveCount = (int) map.get("passiveCount");
    return world;
  }

  public Map<String, Object> toMap() {
    HashMap<String, Object> map = new HashMap<>();
    map.put("name", name);
    map.put("size", size);
    map.put("damage", damage);
    map.put("damageGracePeriod", damageGracePeriod);
    map.put("centerX", centerX);
    map.put("centerY", centerY);
    map.put("centerZ", centerZ);
    map.put("lastTime", lastTime);
    map.put("mobCount", mobCount);
    map.put("passiveCount", passiveCount);
    return map;
  }
}