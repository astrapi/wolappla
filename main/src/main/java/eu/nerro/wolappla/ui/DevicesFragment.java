package eu.nerro.wolappla.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import eu.nerro.wolappla.R;
import eu.nerro.wolappla.provider.DeviceContract;

import static eu.nerro.wolappla.util.LogUtils.LOGV;
import static eu.nerro.wolappla.util.LogUtils.makeLogTag;

public class DevicesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
  private static final String TAG = makeLogTag(DevicesFragment.class);

  private static final int URL_LOADER = 0;
  private CursorAdapter mAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_list_devices, container, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mAdapter = new DevicesAdapter(getActivity());
    setListAdapter(mAdapter);

    getLoaderManager().restartLoader(URL_LOADER, null, this);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int loaderId, Bundle data) {
    switch (loaderId) {
      case URL_LOADER:
        LOGV(TAG, "onCreateLoader() - cursor loader for devices is created");
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
  public void onListItemClick(ListView l, View v, int position, long id) {
    // TODO: implement logic for calling of detail panel
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

      final TextView nameView = (TextView) view.findViewById(R.id.device_name);
      final TextView macAddressView = (TextView) view.findViewById(R.id.device_mac_address);
      final TextView ipAddressView = (TextView) view.findViewById(R.id.device_ip_address);
      final TextView portView = (TextView) view.findViewById(R.id.device_port);

      final String deviceName = cursor.getString(DevicesQuery.NAME);
      final String deviceMacAddress = cursor.getString(DevicesQuery.MAC_ADDRESS);
      final String deviceIpAddress = cursor.getString(DevicesQuery.IP_ADDRESS);
      final String devicePort = cursor.getString(DevicesQuery.PORT);

      nameView.setText(deviceName);
      macAddressView.setText(deviceMacAddress);
      ipAddressView.setText(deviceIpAddress);
      portView.setText(devicePort);
    }
  }
}
