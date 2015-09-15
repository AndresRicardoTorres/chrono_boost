package co.lulo.chronoboost.chronoboost;

import android.content.Context;
import android.util.Log;
import android.widget.CompoundButton;

import java.util.List;
import java.util.Vector;

public class Tracker implements CompoundButton.OnCheckedChangeListener{
  public Vector<TimerButton> getTimerButtons() {
    return timerButtons;
  }

  private Vector<TimerButton> timerButtons;
  private int currentTimerPosition = -1;
  private DB database = null;
  private Context context;

  Tracker(Context context) {
    this.context = context;
    database = new DB(context);
    timerButtons = new Vector<>();

    List<Timer> timerList = database.getTimers();
    for (Timer timer: timerList) {
      addInterface(timer);
      Log.d("CB", timer.getLabel());
    }
  }

  private TimerButton addInterface(Timer newTimer) {
    TimerButton tb = new TimerButton(context, newTimer);
    timerButtons.add(tb);
    tb.setOnCheckedChangeListener(this);
    return tb;
  }

  public TimerButton add(String label) {
    Timer timer = new Timer();
    timer.setLabel(label);

    long newId = database.addTimer(timer);
    // TODO: validate -1 == newId
    timer.setId(newId);

    return addInterface(timer);
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    if (currentTimerPosition != -1) {
      timerButtons.elementAt(currentTimerPosition).stop();
      currentTimerPosition = -1;
    }
    if (isChecked) {
      //TODO: optimize this search
      for (int i = 0; i < timerButtons.size(); i++) {
        if (timerButtons.elementAt(i) == buttonView) {
          currentTimerPosition = i;
          TimerButton currentTimerButton = timerButtons.elementAt(i);
          currentTimerButton.start();
        }
      }
    }
  }
}
