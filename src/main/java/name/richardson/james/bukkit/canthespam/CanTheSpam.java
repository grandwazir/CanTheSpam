package name.richardson.james.bukkit.canthespam;

import java.io.IOException;

import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;

public class CanTheSpam extends Plugin {

  private CanTheSpamConfiguration configuration;

  public void onDisable() {
    this.logger.info(String.format("%s is disabled!", this.getDescription().getName()));
  }

  public void onEnable() {
    
    try {
      this.logger.setPrefix("[CanTheSpam] ");
      this.loadConfiguration();
      this.setPermission();
      // load the worlds
      this.registerListeners();
    } catch (final IOException exception) {
      this.logger.severe("Unable to load configuration!");
      exception.printStackTrace();
    } finally {
      if (!this.getServer().getPluginManager().isPluginEnabled(this)) return;
    }
    this.logger.info(String.format("%s is enabled.", this.getDescription().getFullName()));
    
  }

  private void registerListeners() {
    // TODO Auto-generated method stub
    
  }

  private void getBanHammerHandler() {
    // TODO Auto-generated method stub
    
  }

  private void loadConfiguration() throws IOException {
    configuration = new CanTheSpamConfiguration(this);
    if (configuration.isDebugging()) {
      Logger.enableDebugging(this.getDescription().getName().toLowerCase());
    }
  }

  
  
}
