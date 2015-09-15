package co.lulo.chronoboost.chronoboost;

public class Timer {

  private String label = "";
  /**
   * In minutes
   */
  private int totalTime = 0;
  /**
   * In UNIX time
   */
  private long startTime = 0;

  private long databaseId = 0;

  /*
   * is running ?
   */
  private boolean state = false;

  public void start() {
    state = true;
    startTime = System.currentTimeMillis() / 1000L;
  }

  public void stop() {
    state = false;
    totalTime += ((System.currentTimeMillis() / 1000L) - startTime);
  }

  public long getId() {
    return databaseId;
  }

  public void setId(long databaseId) {
    this.databaseId = databaseId;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(int totalTime) {
    this.totalTime = totalTime;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public boolean getState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }
}
