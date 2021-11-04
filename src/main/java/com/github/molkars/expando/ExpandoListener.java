package com.github.molkars.expando;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class ExpandoListener implements Listener {
  @EventHandler
  public void onAdvancement(PlayerAdvancementDoneEvent event) {
    if (event.getAdvancement().getKey().getKey().contains("recipes")) return;
    WorldBorder border = Expando.getInstance().getStore().getWorld(event.getPlayer().getWorld().getName()).getWorldBorder();
    border.setSize(border.getSize() + .5);
  }

  @EventHandler
  public void mobKillEvent(EntityDeathEvent entityDeathEvent) {
    if (entityDeathEvent.getEntity() instanceof Player) return;
    if (entityDeathEvent.getEntity().getKiller() == null) return;

    WorldBorder border = Expando.getInstance().world.getWorldBorder();
    if (entityDeathEvent.getEntity() instanceof Monster) {
      Expando.getInstance().mobKillCount++;
      if (Expando.getInstance().mobKillCount < 100) {
        return;
      }

      Expando.getInstance().mobKillCount -= 100;
      border.setSize(border.getSize() + .5);
      for (final Player p : Bukkit.getOnlinePlayers())
        p.sendMessage("The world has expanded by one block");
      return;
    }

    if (!(entityDeathEvent.getEntity() instanceof Animals))
      return;

    Expando.getInstance().passiveKillCount++;
    if (Expando.getInstance().passiveKillCount < 250)
      return;

    Expando.getInstance().passiveKillCount -= 250;
    border.setSize(border.getSize() + .5);
    for (final Player p : Bukkit.getOnlinePlayers())
      p.sendMessage("The world has expanded by one block");
  }
}

