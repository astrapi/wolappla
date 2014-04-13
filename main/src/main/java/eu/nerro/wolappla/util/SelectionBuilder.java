package eu.nerro.wolappla.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static eu.nerro.wolappla.util.LogUtils.LOGV;
import static eu.nerro.wolappla.util.LogUtils.makeLogTag;

/**
 * Helper for building selection clauses for {@link SQLiteDatabase}. Each
 * appended clause is combined using <code>AND</code>.
 * <p/>
 * <strong>Note that this class is <em>not</em> thread safe.</strong>
 */
public class SelectionBuilder {
  private static final String TAG = makeLogTag(SelectionBuilder.class);

  private String mTable = null;
  private StringBuilder mSelection = new StringBuilder();
  private List<String> mSelectionArgs = new ArrayList<String>();

  public SelectionBuilder table(String table) {
    mTable = table;
    return this;
  }

  /**
   * Append the given selection clause to the internal state. Each clause
   * is surrounded with parenthesis and combined using <code>AND</code>.
   */
  public SelectionBuilder where(String selection, String... selectionArgs) {
    if (TextUtils.isEmpty(selection)) {
      if (selectionArgs != null && selectionArgs.length > 0) {
        throw new IllegalArgumentException("Valid selection required when including arguments");
      }
      return this;
    }

    if (mSelection.length() > 0) {
      mSelection.append(" AND ");
    }

    mSelection.append("(").append(selection).append(")");
    if (selectionArgs != null) {
      Collections.addAll(mSelectionArgs, selectionArgs);
    }

    return this;
  }

  /**
   * Execute query using current internal state as <code>WHERE</code> clause.
   */
  public Cursor query(SQLiteDatabase database, String[] columns, String orderBy) {
    return query(database, columns, null, null, orderBy, null);
  }

  /**
   * Execute query using current internal state as <code>WHERE</code> clause.
   */
  public Cursor query(SQLiteDatabase database, String[] columns, String groupBy, String having, String orderBy, String limit) {
    assertTable();
    LOGV(TAG, "query(columns=" + Arrays.toString(columns) + ") " + this);

    return database.query(mTable, columns, getSelection(), getSelectionArgs(), groupBy, having, orderBy, limit);
  }

  /**
   * Execute update using current internal state as <code>WHERE</code> clause.
   */
  public int update(SQLiteDatabase database, ContentValues values) {
    assertTable();
    LOGV(TAG, "update() " + this);

    return database.update(mTable, values, getSelection(), getSelectionArgs());
  }

  /**
   * Execute delete using the current internal state as <code>WHERE</code> clause.
   */
  public int delete(SQLiteDatabase database) {
    assertTable();
    LOGV(TAG, "delete() " + this);

    return database.delete(mTable, getSelection(), getSelectionArgs());
  }

  private void assertTable() {
    if (mTable == null) {
      throw new IllegalStateException("Table not specified");
    }
  }

  /**
   * Return selection string for current internal state.
   */
  public String getSelection() {
    return mSelection.toString();
  }

  /**
   * Return selection arguments for current internal state.
   */
  public String[] getSelectionArgs() {
    return mSelectionArgs.toArray(new String[mSelectionArgs.size()]);
  }

  @Override
  public String toString() {
    return "SelectionBuilder=[table=" + mTable + ", selection=" + getSelection()
        + ", " + Arrays.toString(getSelectionArgs()) + "]";
  }
}
