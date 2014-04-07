package eu.nerro.wolappla.ui;

import android.app.Activity;
import android.os.Bundle;

import eu.nerro.wolappla.R;

/**
 * A launcher activity containing:
 * <ul>
 * <li>view to show a list of devices which can be turned on</li>
 * <li>view to scan network for enabled devices</li>
 * </ul>
 */
public class HomeActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
  }
}
