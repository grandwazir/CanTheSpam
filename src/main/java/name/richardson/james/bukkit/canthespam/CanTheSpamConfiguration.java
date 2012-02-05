/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * CanTheSpamConfiguration.java is part of CanTheSpam.
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

import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.Time;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;

public class CanTheSpamConfiguration extends AbstractConfiguration {

  public final static String FILE_NAME = "config.yml";

  public CanTheSpamConfiguration(final Plugin plugin) throws IOException {
    super(plugin, FILE_NAME);
  }

  public int getHitCount() {
    return this.configuration.getInt("hit-count");
  }

  public Long getThreshold() {
    return Time.parseTime(this.configuration.getString("threshold"));
  }

  public boolean isDebugging() {
    return this.configuration.getBoolean("debugging");
  }

}
