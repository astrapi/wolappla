package eu.nerro.wolappla.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static eu.nerro.wolappla.provider.DeviceContract.DevicesColumns;
import static eu.nerro.wolappla.util.LogUtils.LOGD;
import static eu.nerro.wolappla.util.LogUtils.LOGW;
import static eu.nerro.wolappla.util.LogUtils.makeLogTag;

/**
 * Helper for managing {@link SQLiteDatabase} that stores data for {@link DeviceProvider}.
 */
public class DeviceDatabase extends SQLiteOpenHelper {
  private static final String TAG = makeLogTag(DeviceDatabase.class);

  private static final String DATABASE_NAME = "devices.db";
  private static final int DATABASE_VERSION = 1;

  public DeviceDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL("CREATE TABLE " + Tables.DEVICES + " ("
        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + DevicesColumns.DEVICE_NAME + " TEXT NOT NULL,"
        + DevicesColumns.DEVICE_MAC_ADDRESS + " TEXT NOT NULL,"
        + DevicesColumns.DEVICE_IP_ADDRESS + " TEXT NOT NULL,"
        + DevicesColumns.DEVICE_PORT + " INTEGER NOT NULL)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    LOGD(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

    LOGW(TAG, "Destroying old data during upgrade");
    database.execSQL("DROP TABLE IF EXISTS " + Tables.DEVICES);
    onCreate(database);
  }

  interface Tables {
    String DEVICES = "devices";
  }
}
