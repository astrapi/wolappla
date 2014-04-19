package eu.nerro.wolappla.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import eu.nerro.wolappla.R;

public class DevicesFragment extends ListFragment {
  private String[] mDevices = { "One", "Two", "Three", "Four" };

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mDevices);
    setListAdapter(mAdapter);
    return inflater.inflate(R.layout.fragment_list_devices, container, false);
  }
}
