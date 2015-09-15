package co.lulo.chronoboost.chronoboost;

import android.content.Context;
import android.util.Log;
import android.widget.CompoundButton;
import java.util.Vector;

public class Tracker implements CompoundButton.OnCheckedChangeListener{
    private Vector<Timer> timers;
    private int currentTimerPosition = -1;

    Tracker() {
        timers = new Vector<>();
    }

    public Timer add(Context context, String label) {
      Timer newTimer = new Timer(context, label);
      timers.add(newTimer);
      newTimer.setOnCheckedChangeListener(this);
      return newTimer;
    }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    Log.d("Chrono", "isChecked: " + isChecked);

    if (currentTimerPosition != -1) {
      Log.d("Chrono", "isChecked current: " + timers.elementAt(currentTimerPosition).isChecked());
      timers.elementAt(currentTimerPosition).stop();
      currentTimerPosition = -1;
    }
    if (isChecked) {
      //TODO: optimize this search
      for (int i = 0; i < timers.size(); i++) {
        if (timers.elementAt(i) == buttonView) {
          currentTimerPosition = i;
          Timer currentTimer = timers.elementAt(i);
          currentTimer.start();
        }
      }
    }
  }
}
