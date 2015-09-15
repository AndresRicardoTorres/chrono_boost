package co.lulo.chronoboost.chronoboost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

  private static final int version = 3;
  private static final String database = "chronoboost";
  private static final String table = "timers";

  private static final String column_id = "id";
  private static final String column_label = "label";
  private static final String column_start = "start";
  private static final String column_total = "total";
  private static final String column_state = "state";

  /**
   * Create a helper object to create, open, and/or manage a database.
   * This method always returns very quickly.  The database is not actually
   * created or opened until one of {@link #getWritableDatabase} or
   * {@link #getReadableDatabase} is called.
   *
   * @param context to use to open or create the database
   */
  public DB(Context context) {
    super(context, database, null, version);
  }

  /**
   * Called when the database is created for the first time. This is where the
   * creation of tables and the initial population of the tables should happen.
   *
   * @param db The database.
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    String toCreateTable = "CREATE TABLE " + table + "( " +
            column_id + " INTEGER PRIMARY KEY, " +
            column_label + " TEXT, " +
            column_start + " TEXT, " +
            column_total + " TEXT, " +
            column_state + " TEXT" +
            ")";
    db.execSQL(toCreateTable);
    Log.d("CB", toCreateTable);
  }

  /**
   * Called when the database needs to be upgraded. The implementation
   * should use this method to drop tables, add tables, or do anything else it
   * needs to upgrade to the new schema version.
   * <p/>
   * <p>
   * The SQLite ALTER TABLE documentation can be found
   * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
   * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
   * you can use ALTER TABLE to rename the old table, then create the new table and then
   * populate the new table with the contents of the old table.
   * </p><p>
   * This method executes within a transaction.  If an exception is thrown, all changes
   * will automatically be rolled back.
   * </p>
   *
   * @param db         The database.
   * @param oldVersion The old database version.
   * @param newVersion The new database version.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + table);
    onCreate(db);
  }

  public long addTimer(Timer objTimer) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(column_label, objTimer.getLabel());
    values.put(column_start, "" + objTimer.getStartTime());
    values.put(column_total, "" + objTimer.getTotalTime());
    values.put(column_state, objTimer.getState() ? "ON" : "OFF");

    long result = 0;
    try {
      result = db.insertOrThrow(table, null, values);
      Log.d("CB", "INSERT " + result);
    } catch (SQLException e){
      Log.d("CB", e.getMessage());
    }
    db.close();
    return result;
  }

  /*
  public void getTimer() {}
  */

  public List<Timer> getTimers() {
    ArrayList<Timer> result = new ArrayList<>();
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "SELECT * FROM " + table;

    Cursor cursor = db.rawQuery(query, null);
    Log.d("CB", "COUNT " + cursor.getCount());
    if(cursor.moveToFirst()){
      do{
        Timer objTimer = new Timer();
        objTimer.setId(cursor.getInt(0));
        objTimer.setLabel(cursor.getString(1));
        objTimer.setStartTime(cursor.getLong(2));
        objTimer.setTotalTime(cursor.getInt(3));
        objTimer.setState(cursor.getString(4).equals("ON"));

        result.add(objTimer);
      }while (cursor.moveToNext());
    }

    return result;
  }

  public int updateTimer(Timer objTimer){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put(column_label, objTimer.getLabel());
    values.put(column_start, "" + objTimer.getStartTime());
    values.put(column_total, "" + objTimer.getTotalTime());
    values.put(column_state, objTimer.getState() ? "ON" : "OFF");

    int result = db.update(table, values, column_id + "= ?",
            new String[] {String.valueOf(objTimer.getId())});
    db.close();
    return result;
  }

  public int deleteTimer(Timer objTimer){
    SQLiteDatabase db = this.getWritableDatabase();
    int result = db.delete(table, column_id + "= ?",
            new String[] {String.valueOf(objTimer.getId())});
    db.close();
    return result;
  }
}
