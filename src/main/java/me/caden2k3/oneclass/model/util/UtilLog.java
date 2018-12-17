package me.caden2k3.oneclass.model.util;

import lombok.Getter;
import me.caden2k3.oneclass.model.DataManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-17.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class UtilLog {
  private @Getter static Logger log;

  public static void init() {
    System.setProperty("log4j.configurationFile", "src/main/resources/config/log4j2.xml");
    log = LogManager.getLogger("OneClass");

    //By default enable debug unless there is a valid user that does not have debug on.
    //TODO change this to not be on by default.
    setDebug(DataManager.getInstance().getCurrentUser() == null
        || DataManager.getInstance().getCurrentUser().getPreferences() == null
        || DataManager.getInstance().getCurrentUser().getPreferences().isDebug());

  }

  public static void setDebug(boolean debug) {
    if (debug) {
      LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
      Configuration config = ctx.getConfiguration();
      LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
      loggerConfig.setLevel(Level.DEBUG);
      ctx.updateLoggers();
    } else {
      LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
      Configuration config = ctx.getConfiguration();
      LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
      loggerConfig.setLevel(Level.INFO);
      ctx.updateLoggers();
    }
  }

  public static void debug(String debug) {
    log.debug("debug");
  }

  public static void error(Exception ex) {
    log.warn(ex.getMessage(), ex);
  }

  public static void error(Exception ex, Level level) {
    log.log(level, ex.getMessage(), ex);
  }
}
