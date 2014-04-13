package eu.nerro.wolappla.provider;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Arrays;

import eu.nerro.wolappla.util.SelectionBuilder;

import static eu.nerro.wolappla.provider.DeviceContract.Devices;
import static eu.nerro.wolappla.provider.DeviceDatabase.Tables;
import static eu.nerro.wolappla.util.LogUtils.LOGV;
import static eu.nerro.wolappla.util.LogUtils.makeLogTag;

/**
 * Provider that stores {@link DeviceContract} data. Data is inserted
 * and queried by various {@link Activity} instances.
 */
public class DeviceProvider extends ContentProvider {
  private static final String TAG = makeLogTag(DeviceProvider.class);

  private static final UriMatcher sUriMatcher = buildUriMatcher();

  private static final int DEVICES = 100;
  private static final int DEVICES_ID = 101;

  private DeviceDatabase mOpenHelper;

  /**
   * Build and return a {@link UriMatcher} that catches all {@link Uri}
   * variations supported by this {@link ContentProvider}.
   */
  private static UriMatcher buildUriMatcher() {
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = DeviceContract.CONTENT_AUTHORITY;

    matcher.addURI(authority, "devices", DEVICES);
    matcher.addURI(authority, "devices/*", DEVICES_ID);

    return matcher;
  }

  @Override
  public boolean onCreate() {
    mOpenHelper = new DeviceDatabase(getContext());
    return true;
  }

  @Override
  public String getType(Uri uri) {
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case DEVICES:
        return Devices.CONTENT_TYPE;

      case DEVICES_ID:
        return Devices.CONTENT_ITEM_TYPE;

      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    LOGV(TAG, "query(uri=" + uri + ", projection=" + Arrays.toString(projection) + ")");

    final SQLiteDatabase database = mOpenHelper.getReadableDatabase();
    final SelectionBuilder builder = buildSimpleSelection(uri);

    return builder.where(selection, selectionArgs).query(database, projection, sortOrder);
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    LOGV(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");

    final SQLiteDatabase database = mOpenHelper.getWritableDatabase();
    if (database == null) {
      throw new UnsupportedOperationException("No database found");
    }

    final int match = sUriMatcher.match(uri);
    switch (match) {
      case DEVICES:
        database.insertOrThrow(Tables.DEVICES, null, values);
        notifyChange(uri);
        return Devices.buildDeviceUri(values.getAsString(Devices.DEVICE_ID));

      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    LOGV(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");

    final SQLiteDatabase database = mOpenHelper.getWritableDatabase();
    final SelectionBuilder builder = buildSimpleSelection(uri);
    final int affectedRows = builder.where(selection, selectionArgs).update(database, values);
    notifyChange(uri);

    return affectedRows;
  }

  @Override
  public int delete(Uri uri, String s, String[] strings) {
    return 0;
  }

  private SelectionBuilder buildSimpleSelection(Uri uri) {
    final SelectionBuilder builder = new SelectionBuilder();

    final int match = sUriMatcher.match(uri);
    switch (match) {
      case DEVICES:
        return builder.table(Tables.DEVICES);

      case DEVICES_ID:
        final String deviceId = Devices.getDeviceId(uri);
        return builder.table(Tables.DEVICES).where(Devices.DEVICE_ID + "=?", deviceId);

      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  private void notifyChange(Uri uri) {
    Context context = getContext();
    if (context == null) {
      return;
    }

    context.getContentResolver().notifyChange(uri, null, false);
    // Send broadcast here for refreshing widgets (currently no widgets exist)
  }
}
