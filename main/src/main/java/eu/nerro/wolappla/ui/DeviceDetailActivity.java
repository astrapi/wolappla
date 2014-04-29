package eu.nerro.wolappla.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eu.nerro.wolappla.R;
import eu.nerro.wolappla.provider.DeviceContract;

public class DeviceDetailActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final ActionBar actionBar = getActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    setContentView(R.layout.activity_device_detail);

    Button saveButton = (Button) findViewById(R.id.device_detail_save_button);
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = getIntent();

        EditText deviceNameView = (EditText) findViewById(R.id.device_detail_name);
        intent.putExtra(DeviceContract.Devices.DEVICE_NAME, deviceNameView.getText().toString());
        EditText deviceMacAddressView = (EditText) findViewById(R.id.device_detail_mac_address);
        intent.putExtra(DeviceContract.Devices.DEVICE_MAC_ADDRESS, deviceMacAddressView.getText().toString());
        EditText deviceIpAddressView = (EditText) findViewById(R.id.device_detail_ip_address);
        intent.putExtra(DeviceContract.Devices.DEVICE_IP_ADDRESS, deviceIpAddressView.getText().toString());
        EditText devicePortView = (EditText) findViewById(R.id.device_detail_port);
        intent.putExtra(DeviceContract.Devices.DEVICE_PORT, Integer.valueOf(devicePortView.getText().toString()));

        setResult(RESULT_OK, intent);
        finish();
      }
    });

    Button cancelButton = (Button) findViewById(R.id.device_detail_cancel_button);
    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setResult(RESULT_CANCELED, getIntent());
        finish();
      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
