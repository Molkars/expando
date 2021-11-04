package com.github.molkars.expando;

import com.github.molkars.expando.data.WorldConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.Objects;

public class ExpandoListener implements Listener {
  @EventHandler
  public void onAdvancement(PlayerAdvancementDoneEvent event) {
    double increase = event.getAdvancement().getKey().getKey().contains("recipes") ? .125 : 1;
    WorldBorder border = Objects.requireNonNull(Bukkit.getWorld(Expando.world.getWorldName())).getWorldBorder();
    border.setSize(border.getSize() + (2 * increase));
  }

  @EventHandler
  public void entityDeathEvent(EntityDeathEvent event) {
    if (Bukkit.getOnlinePlayers().size() == 0) return;

    WorldConfiguration schema = Expando.world;
    World world = Bukkit.getWorld(schema.getName());

    if (world == null) {
      Expando.getInstance().getLogger().severe("Unable to find world, default to first");
      world = Bukkit.getWorlds().get(0);
    }

    switch (event.getEntityType()) {
      case WITHER: {
        int count;
        if (event.getEntity().getKiller() == null) {
          count = Expando.config.getInt("scores.wither.non-player-kill", 100);
        } else {
          count = Expando.config.getInt("scores.wither.player-kill", 1000);
        }

        int expandBy = Expando.config.getInt("scores.wither.expand-by", 8);
        int limit = Expando.config.getInt("scores.wither.count", 1000);

        schema.setWitherCount(schema.getWitherCount() + count);

        if (schema.getWitherCount() >= limit) {
          schema.setWitherCount(schema.getWitherCount() - limit);
          schema.setSize(schema.getSize() + expandBy);
          world.getWorldBorder().setSize(schema.getSize());
        }

        return;
      }
      case ENDER_DRAGON: {
        int count;
        if (event.getEntity().getKiller() == null) {
          count = Expando.config.getInt("scores.ender-dragon.non-player-kill", 500);
        } else {
          count = Expando.config.getInt("scores.ender-dragon.player-kill", 1000);
        }

        int expandBy = Expando.config.getInt("scores.ender-dragon.expand-by", 16);
        int limit = Expando.config.getInt("scores.ender-dragon.count", 1000);

        schema.setEnderDragonCount(schema.getEnderDragonCount() + count);

        if (schema.getEnderDragonCount() >= limit) {
          schema.setEnderDragonCount(schema.getEnderDragonCount() - limit);
          schema.setSize(schema.getSize() + expandBy);
          world.getWorldBorder().setSize(schema.getSize());
        }

        return;
      }
    }

    if (event.getEntity() instanceof Monster) {
      int count;
      if (event.getEntity().getKiller() == null) {
        count = Expando.config.getInt("scores.monster.non-player-kill", 1);
      } else {
        count = Expando.config.getInt("scores.monster.player-kill", 5);
      }

      int objective = Expando.config.getInt("scores.monster.count", 750);
      int expandBy = Expando.config.getInt("scores.monster.expand-by", 2);
      schema.setMobCount(schema.getMobCount() + count);

      if (schema.getMobCount() >= objective) {
        schema.setMobCount(schema.getMobCount() - objective);
        schema.setSize(schema.getSize() + expandBy);
        world.getWorldBorder().setSize(schema.getSize());
      }

      return;
    }

    if (event.getEntity() instanceof Creature) {
      int count;
      if (event.getEntity().getKiller() == null) {
        count = Expando.config.getInt("scores.creature.non-player-kill", 1);
      } else {
        count = Expando.config.getInt("scores.creature.player-kill", 5);
      }

      int objective = Expando.config.getInt("scores.creature.count", 1250);
      int expandBy = Expando.config.getInt("scores.creature.expand-by", 2);
      schema.setPassiveCount(schema.getPassiveCount() + count);

      if (schema.getPassiveCount() >= objective) {
        schema.setPassiveCount(schema.getPassiveCount() - objective);
        schema.setSize(schema.getSize() + expandBy);
        world.getWorldBorder().setSize(schema.getSize());
      }
    }
  }
}

