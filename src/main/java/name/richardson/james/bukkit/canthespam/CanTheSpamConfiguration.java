package name.richardson.james.bukkit.canthespam;

import java.io.IOException;

import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.Time;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;

public class CanTheSpamConfiguration extends AbstractConfiguration {
  
  public final static String FILE_NAME = "config.yml";

  public CanTheSpamConfiguration(Plugin plugin) throws IOException {
    super(plugin, FILE_NAME);
  }

  public boolean isDebugging() {
    return this.configuration.getBoolean("debugging");
  }
  
  public int getHitCount() {
    return this.configuration.getInt("hit-count");
  }
  
  public Long getThreshold() {
    return Time.parseTime(this.configuration.getString("threshold"));
  }
  
}
