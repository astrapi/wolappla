package eu.nerro.wolappla.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import eu.nerro.wolappla.R;
import eu.nerro.wolappla.provider.DeviceContract;
import eu.nerro.wolappla.provider.DeviceProvider;

public class DevicesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
  public static final int CREATE_DEVICE_ENTRY_REQUEST_CODE = 100;
  public static final int UPDATE_DEVICE_ENTRY_REQUEST_CODE = 101;

  private static final int URL_LOADER = 0;
  private static final int DEFAULT_DEVICE_PORT = 9;
  private final ContentObserver mObserver = new DeviceContentObserver(new Handler());
  private CursorAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_list_devices, container, false);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.devices, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_add_device:
        Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
        startActivityForResult(intent, CREATE_DEVICE_ENTRY_REQUEST_CODE);
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mAdapter = new DevicesAdapter(getActivity());
    setListAdapter(mAdapter);

    ListView listView = getListView();
    listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
    listView.setMultiChoiceModeListener(new DeviceMultiChoiceListener(listView));

    getLoaderManager().restartLoader(URL_LOADER, null, this);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    activity.getContentResolver().registerContentObserver(DeviceContract.Devices.CONTENT_URI, true, mObserver);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    getActivity().getContentResolver().unregisterContentObserver(mObserver);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int loaderId, Bundle data) {
    switch (loaderId) {
      case URL_LOADER:
        return new CursorLoader(
            getActivity(),
            DeviceContract.Devices.CONTENT_URI,
            DevicesQuery.PROJECTION,
            null,
            null,
            DeviceContract.Devices.DEFAULT_SORT);

      default:
        return null;
    }
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    mAdapter.swapCursor(null);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      final String deviceName = data.getStringExtra(DeviceContract.Devices.DEVICE_NAME);
      final String deviceMacAddress = data.getStringExtra(DeviceContract.Devices.DEVICE_MAC_ADDRESS);
      final String deviceIpAddress = data.getStringExtra(DeviceContract.Devices.DEVICE_IP_ADDRESS);
      final int devicePort = data.getIntExtra(DeviceContract.Devices.DEVICE_PORT, DEFAULT_DEVICE_PORT);

      ContentValues value = new ContentValues();
      value.put(DeviceContract.Devices.DEVICE_NAME, deviceName);
      value.put(DeviceContract.Devices.DEVICE_MAC_ADDRESS, deviceMacAddress);
      value.put(DeviceContract.Devices.DEVICE_IP_ADDRESS, deviceIpAddress);
      value.put(DeviceContract.Devices.DEVICE_PORT, devicePort);

      switch (requestCode) {
        case CREATE_DEVICE_ENTRY_REQUEST_CODE:
          getActivity().getContentResolver().insert(DeviceContract.Devices.CONTENT_URI, value);
          break;

        case UPDATE_DEVICE_ENTRY_REQUEST_CODE:
          break;
      }
    }
  }

  /**
   * {@link DeviceContract.Devices} query parameters.
   */
  private interface DevicesQuery {
    String[] PROJECTION = {
        DeviceContract.Devices._ID,
        DeviceContract.Devices.DEVICE_NAME,
        DeviceContract.Devices.DEVICE_MAC_ADDRESS,
        DeviceContract.Devices.DEVICE_IP_ADDRESS,
        DeviceContract.Devices.DEVICE_PORT,
    };

    int ID = 0;
    int NAME = 1;
    int MAC_ADDRESS = 2;
    int IP_ADDRESS = 3;
    int PORT = 4;
  }

  /**
   * {@link CursorAdapter} that renders a {@link DevicesQuery}.
   */
  private class DevicesAdapter extends CursorAdapter {
    public DevicesAdapter(Context context) {
      super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
      return getActivity().getLayoutInflater().inflate(R.layout.list_item_device, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
      final String deviceId = cursor.getString(DevicesQuery.ID);
      if (deviceId == null) {
        return;
      }

      final TextView nameView = (TextView) view.findViewById(R.id.list_item_device_name);
      final TextView macAddressView = (TextView) view.findViewById(R.id.list_item_device_mac_address);
      final TextView ipAddressView = (TextView) view.findViewById(R.id.list_item_device_ip_address);
      final ImageButton startButton = (ImageButton) view.findViewById(R.id.list_item_device_start_button);

      final String deviceName = cursor.getString(DevicesQuery.NAME);
      final String deviceMacAddress = cursor.getString(DevicesQuery.MAC_ADDRESS);
      final String deviceIpAddress = cursor.getString(DevicesQuery.IP_ADDRESS);

      nameView.setText(deviceName);
      macAddressView.setText(deviceMacAddress);
      ipAddressView.setText(deviceIpAddress);
      startButton.setFocusable(false);
    }
  }

  /**
   * Receives call backs for changes to {@link DeviceProvider}.
   */
  private class DeviceContentObserver extends ContentObserver {
    public DeviceContentObserver(Handler handler) {
      super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
      super.onChange(selfChange);

      if (!isAdded()) {
        return;
      }

      getLoaderManager().restartLoader(URL_LOADER, null, DevicesFragment.this);
    }
  }

  private class DeviceMultiChoiceListener implements AbsListView.MultiChoiceModeListener {
    private AbsListView listView;

    public DeviceMultiChoiceListener(AbsListView listView) {
      this.listView = listView;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
      MenuInflater inflater = mode.getMenuInflater();
      if (inflater != null) {
        inflater.inflate(R.menu.devices_context, menu);
        return true;
      }
      return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
      return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
      return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }
  }
}
