package name.richardson.james.bukkit.canthespam;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpamListener implements Listener {

  private int hitCount = 5;
  private final Map<String, Integer> tracker;
  
  public SpamListener(Integer hitCount, Map<String, Integer> tracker) {
    this.hitCount = hitCount;
    this.tracker = tracker;
  }
  
  public boolean isPlayerOverLimit(String playerName) {
    if (tracker.get(playerName) == this.hitCount) {
      return true;
    } else {
      return false;
    }
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent event) {
    final String playerName = event.getPlayer().getName();
    tracker.put(playerName, 0);
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {
    final String playerName = event.getPlayer().getName();
    tracker.remove(playerName);
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
    final String playerName = event.getPlayer().getName();  
    this.incrementPlayer(playerName);
    if (this.isPlayerOverLimit(playerName)) {
      event.getPlayer().setBanned(true);
      event.getPlayer().kickPlayer("Flooding the server");
      event.setCancelled(true);
    }
  }
  
  private void incrementPlayer(String playerName) {
    tracker.put(playerName, tracker.get(playerName) + 1);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerChatEvent(PlayerChatEvent event) {
    final String playerName = event.getPlayer().getName();  
    this.incrementPlayer(playerName);
    if (this.isPlayerOverLimit(playerName)) {
      event.getPlayer().setBanned(true);
      event.getPlayer().kickPlayer("Flooding the server");
      event.setCancelled(true);
    }
  }
  
}
