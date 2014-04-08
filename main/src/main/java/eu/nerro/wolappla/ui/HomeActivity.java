package eu.nerro.wolappla.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import eu.nerro.wolappla.R;

import static eu.nerro.wolappla.util.LogUtils.makeLogTag;

/**
 * A launcher activity containing:
 * <ul>
 * <li>view to show a list of devices which can be turned on</li>
 * <li>view to scan network for enabled devices</li>
 * </ul>
 */
public class HomeActivity extends Activity implements ActionBar.TabListener {
  private static final String TAG = makeLogTag(HomeActivity.class);

  private ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    mViewPager = (ViewPager) findViewById(R.id.pager);
    final ActionBar actionBar = getActionBar();
    if (mViewPager != null && actionBar != null) {
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      actionBar.addTab(
          actionBar.newTab()
              .setText(R.string.title_devices)
              .setTabListener(this)
      );
      actionBar.addTab(
          actionBar.newTab()
              .setText(R.string.title_scanning)
              .setTabListener(this)
      );

      actionBar.setHomeButtonEnabled(false);
    }
  }

  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    mViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
  }

  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
  }
}
