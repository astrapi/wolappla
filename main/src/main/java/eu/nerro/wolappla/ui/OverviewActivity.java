package eu.nerro.wolappla.ui;

import android.app.Activity;
import android.os.Bundle;

import eu.nerro.wolappla.R;

/**
 * Activity to show the list of computers which can be turned on.
 */
public class OverviewActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_overview);
  }
}
