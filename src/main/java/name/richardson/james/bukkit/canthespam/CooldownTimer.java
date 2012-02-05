package name.richardson.james.bukkit.canthespam;

import java.util.Map;
import java.util.Map.Entry;

import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Time;

public class CooldownTimer implements Runnable {

  private final long ticks;
  private final Map<String, Integer> tracker;
  private final Logger logger = new Logger(CooldownTimer.class);

  public CooldownTimer(Long milliseconds, Map<String, Integer> tracker) {
    final long seconds = milliseconds / 1000;
    this.ticks = seconds * 20;
    this.tracker = tracker;
    logger.debug(String.format("Configuring threshold to %s (%d ticks)", Time.millisToLongDHMS(milliseconds), ticks));
  }
  
  public long getTicks() {
    return this.ticks;
  }
  
  public void run() { 
    logger.debug(tracker.toString());
    for (Entry<String, Integer> player : tracker.entrySet()) {
      int value = player.getValue(); 
      if (value != 0) player.setValue(value - 1);
    }
    logger.debug(tracker.toString());
  }
  
}
