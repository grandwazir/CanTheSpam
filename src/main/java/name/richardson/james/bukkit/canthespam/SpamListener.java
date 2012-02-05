/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SpamListener.java is part of CanTheSpam.
 * 
 * CanTheSpam is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * CanTheSpam is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * CanTheSpam. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.canthespam;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import name.richardson.james.bukkit.banhammer.BanHandler;

public class SpamListener implements Listener {

  private int hitCount = 5;
  private final Map<String, Integer> tracker;
  private final BanHandler handler;

  public SpamListener(final Integer hitCount, final Map<String, Integer> tracker, final BanHandler handler) {
    this.hitCount = hitCount;
    this.tracker = tracker;
    this.handler = handler;
  }

  private void banPlayer(final Player player) {
    if (this.handler == null) {
      player.setBanned(true);
      player.kickPlayer("Flooding the server");
    } else {
      this.handler.banPlayer(player.getName(), "CanTheSpam", "Flooding the server", (long) 0, true);
    }
  }

  private void incrementPlayer(final String playerName) {
    this.tracker.put(playerName, this.tracker.get(playerName) + 1);
  }

  public boolean isPlayerOverLimit(final String playerName) {
    if (this.tracker.get(playerName) == this.hitCount) {
      return true;
    } else {
      return false;
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onCommandPreProcess(final PlayerCommandPreprocessEvent event) {
    final String playerName = event.getPlayer().getName();
    this.incrementPlayer(playerName);
    if (this.isPlayerOverLimit(playerName)) {
      this.banPlayer(event.getPlayer());
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerChatEvent(final PlayerChatEvent event) {
    final String playerName = event.getPlayer().getName();
    this.incrementPlayer(playerName);
    if (this.isPlayerOverLimit(playerName)) {
      this.banPlayer(event.getPlayer());
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(final PlayerJoinEvent event) {
    final String playerName = event.getPlayer().getName();
    this.tracker.put(playerName, 0);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(final PlayerQuitEvent event) {
    final String playerName = event.getPlayer().getName();
    this.tracker.remove(playerName);
  }

}
