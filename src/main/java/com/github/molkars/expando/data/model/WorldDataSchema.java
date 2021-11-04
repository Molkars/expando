package com.github.molkars.expando.util.yaml;

public class WorldDataSchema {
  private String name;
  private double size;
  private double damage;
  private double damageGracePeriod;
  private double centerX, centerY, centerZ;
  private long lastTime;

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
}