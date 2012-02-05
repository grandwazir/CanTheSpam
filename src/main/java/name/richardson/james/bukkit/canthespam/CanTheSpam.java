/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * CanTheSpam.java is part of CanTheSpam.
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import name.richardson.james.bukkit.banhammer.BanHammer;
import name.richardson.james.bukkit.banhammer.BanHandler;
import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;

public class CanTheSpam extends Plugin {

  private CanTheSpamConfiguration configuration;
  private SpamListener listener;
  private CooldownTimer timer;
  private final Map<String, Integer> tracker = new HashMap<String, Integer>();
  private BanHandler banHammerHandler;

  private void getBanHammer() {
    final BanHammer plugin = (BanHammer) this.getServer().getPluginManager().getPlugin("BanHammer");
    if (plugin != null) {
      this.logger.info("Using " + plugin.getDescription().getFullName() + ".");
      this.banHammerHandler = plugin.getHandler(CanTheSpam.class);
    }

  }

  private void loadConfiguration() throws IOException {
    this.configuration = new CanTheSpamConfiguration(this);
    if (this.configuration.isDebugging()) {
      Logger.enableDebugging(this.getDescription().getName().toLowerCase());
    }
  }

  public void onDisable() {
    this.getServer().getScheduler().cancelTasks(this);
    this.logger.info(String.format("%s is disabled!", this.getDescription().getName()));
  }

  public void onEnable() {

    try {
      this.logger.setPrefix("[CanTheSpam] ");
      this.loadConfiguration();
      // load the worlds
      this.populateTracker();
      this.getBanHammer();
      this.registerListeners();
      this.startTimedTasks();
    } catch (final IOException exception) {
      this.logger.severe("Unable to load configuration!");
      exception.printStackTrace();
    } finally {
      if (!this.getServer().getPluginManager().isPluginEnabled(this)) {
        return;
      }
    }
    this.logger.info(String.format("%s is enabled.", this.getDescription().getFullName()));

  }

  private void populateTracker() {
    for (final Player player : this.getServer().getOnlinePlayers()) {
      this.tracker.put(player.getName(), 0);
    }
  }

  private void registerListeners() {
    this.listener = new SpamListener(this.configuration.getHitCount(), this.tracker, this.banHammerHandler);
    this.getServer().getPluginManager().registerEvents(this.listener, this);
  }

  private void startTimedTasks() {
    this.timer = new CooldownTimer(this.configuration.getThreshold(), this.tracker);
    this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, this.timer, 5 * 20, this.timer.getTicks());
  }

}
