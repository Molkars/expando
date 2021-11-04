package com.github.molkars.expando.schedule;

import com.github.molkars.expando.Expando;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class TimedExpand implements Runnable {
  @Override
  public void run() {
    World realWorld = Bukkit.getWorld(Expando.world.getWorldName());
    if (realWorld == null) {
      Expando.instance.getLogger().severe("Unable to get world, defaulting to first in list");
      realWorld = Bukkit.getWorlds().get(0);
    }

    Expando.updateDifferences(realWorld);

    int maxExp = Expando.config.getInt("time.max-expansion", 128);
    if (Expando.world.getSize() < maxExp) {
      Expando.world.setSize(Expando.world.getSize() + 2);
      if (Expando.world.getSize() > maxExp)
        Expando.world.setSize(maxExp);
    }

    for (final World world : Bukkit.getWorlds()) {
      WorldBorder border = world.getWorldBorder();
      border.setSize(Expando.world.getSize());
    }
  }
}
