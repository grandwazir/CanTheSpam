/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * CooldownTimer.java is part of CanTheSpam.
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
import java.util.Map.Entry;

import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Time;

public class CooldownTimer implements Runnable {

  private final long ticks;
  private final Map<String, Integer> tracker;
  private final Logger logger = new Logger(CooldownTimer.class);

  public CooldownTimer(final Long milliseconds, final Map<String, Integer> tracker) {
    final long seconds = milliseconds / 1000;
    this.ticks = seconds * 20;
    this.tracker = tracker;
    this.logger.debug(String.format("Configuring threshold to %s (%d ticks)", Time.millisToLongDHMS(milliseconds), this.ticks));
  }

  public long getTicks() {
    return this.ticks;
  }

  public void run() {
    this.logger.debug(this.tracker.toString());
    for (final Entry<String, Integer> player : this.tracker.entrySet()) {
      final int value = player.getValue();
      if (value != 0) {
        player.setValue(value - 1);
      }
    }
    this.logger.debug(this.tracker.toString());
  }

}
