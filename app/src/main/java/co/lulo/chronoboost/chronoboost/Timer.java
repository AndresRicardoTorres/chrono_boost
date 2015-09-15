package co.lulo.chronoboost.chronoboost;

import android.content.Context;
import android.widget.ToggleButton;

public class Timer extends ToggleButton {

  private String label = "";
  /**
   * In minutes
   */
  private int totalTime = 0;
  /**
   * In UNIX time
   */
  private long startTime = 0;

  public Timer(Context context, String label) {
    super(context);
    this.label = label;
    setTextOff(label);
    setChecked(false);
  }

  public void start() {
    startTime = System.currentTimeMillis() / 1000L;
    setTextOn(label + "\n Running");
  }

  public void stop() {
    totalTime += ((System.currentTimeMillis() / 1000L) - startTime);
    setChecked(false);
    setTextOff(label + "\n Total time: " + totalTime);
  }
}
