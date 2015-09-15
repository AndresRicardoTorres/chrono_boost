package co.lulo.chronoboost.chronoboost;

import android.content.Context;
import android.widget.ToggleButton;

public class TimerButton extends ToggleButton {

  private Timer timer = null;

  public TimerButton(Context context,Timer timer) {
    super(context);
    setTextOff(timer.getLabel());
    setChecked(false);
  }

  public void start() {
    timer.start();
    setTextOn(timer.getLabel() + "\n Running");
  }

  public void stop() {
    timer.stop();
    setChecked(false);
    setTextOff(timer.getLabel() + "\n Total time: " + timer.getTotalTime());
  }

}
