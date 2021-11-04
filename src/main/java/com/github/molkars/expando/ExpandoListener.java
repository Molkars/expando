package com.github.molkars.expando;

import com.github.molkars.expando.data.model.WorldDataSchema;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Monster;
import org.bukkit.entity.WaterMob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.Objects;

public class ExpandoListener implements Listener {
  @EventHandler
  public void onAdvancement(PlayerAdvancementDoneEvent event) {
    double increase = event.getAdvancement().getKey().getKey().contains("recipes") ? .125 : 1;
    WorldBorder border = Objects.requireNonNull(Bukkit.getWorld(Expando.getInstance().world.getName())).getWorldBorder();
    border.setSize(border.getSize() + (2 * increase));
  }

  @EventHandler
  public void entityDeathEvent(EntityDeathEvent entityDeathEvent) {
    WorldDataSchema schema = Expando.instance.world;
    World world = Bukkit.getWorld(schema.getName());

    if (world == null) {
      Expando.getInstance().getLogger().severe("Unable to find world, default to first");
      world = Bukkit.getWorlds().get(0);
    }

    if (entityDeathEvent.getEntity() instanceof Monster) {
      int count = entityDeathEvent.getEntity().getKiller() == null ? 1 : 5;
      schema.setMobCount(schema.getMobCount() + count);

      if (schema.getMobCount() >= 750) {
        schema.setMobCount(schema.getMobCount() - 750);
        schema.setSize(schema.getSize() + 2);
        world.getWorldBorder().setSize(schema.getSize());
      }

      return;
    }

    if (entityDeathEvent.getEntity() instanceof Animals || entityDeathEvent.getEntity() instanceof WaterMob) {
      int count = entityDeathEvent.getEntity().getKiller() == null ? 1 : 5;
      schema.setPassiveCount(schema.getPassiveCount() + count);

      if (schema.getPassiveCount() >= 1250) {
        schema.setPassiveCount(schema.getPassiveCount() - 1250);
        schema.setSize(schema.getSize() + 2);
        world.getWorldBorder().setSize(schema.getSize());
      }
    }
  }
}

