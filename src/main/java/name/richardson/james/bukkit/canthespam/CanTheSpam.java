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
      if (!this.getServer().getPluginManager().isPluginEnabled(this)) return;
    }
    this.logger.info(String.format("%s is enabled.", this.getDescription().getFullName()));
    
  }

  private void getBanHammer() {
    BanHammer plugin = (BanHammer) this.getServer().getPluginManager().getPlugin("BanHammer");
    if (plugin != null) {
      logger.info("Using " + plugin.getDescription().getFullName() + ".");
      banHammerHandler = plugin.getHandler(CanTheSpam.class);
    }
    
  }

  private void populateTracker() {
    for (Player player : this.getServer().getOnlinePlayers()) {
      this.tracker.put(player.getName(), 0);
    }
  }

  private void startTimedTasks() {
    this.timer = new CooldownTimer(configuration.getThreshold(), this.tracker);
    this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, this.timer, 5 * 20, this.timer.getTicks());
  }

  private void registerListeners() {
    this.listener = new SpamListener(configuration.getHitCount(), tracker, this.banHammerHandler);
    this.getServer().getPluginManager().registerEvents(this.listener, this);
  }

  private void loadConfiguration() throws IOException {
    configuration = new CanTheSpamConfiguration(this);
    if (configuration.isDebugging()) {
      Logger.enableDebugging(this.getDescription().getName().toLowerCase());
    }
  }

  
  
}
