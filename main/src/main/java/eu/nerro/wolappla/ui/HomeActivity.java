package eu.nerro.wolappla.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import eu.nerro.wolappla.R;

/**
 * A launcher activity containing:
 * <ul>
 * <li>view to show a list of devices which can be turned on
 * <li>view to scan network for enabled devices
 * </ul>
 */
public class HomeActivity extends FragmentActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
  private static final int NAVIGATION_TABS_COUNT = 2;
  private static final int NAVIGATION_TAB_DEVICES = 0;
  private static final int NAVIGATION_TAB_SCANNING = 1;

  private ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    mViewPager = (ViewPager) findViewById(R.id.pager);
    final ActionBar actionBar = getActionBar();
    if (mViewPager != null && actionBar != null) {
      mViewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
      mViewPager.setOnPageChangeListener(this);

      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      actionBar.setDisplayHomeAsUpEnabled(false);
      actionBar.setDisplayUseLogoEnabled(true);
      actionBar.addTab(actionBar.newTab()
          .setText(R.string.title_devices)
          .setTabListener(this));
      actionBar.addTab(actionBar.newTab()
          .setText(R.string.title_scanning)
          .setTabListener(this));
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

  @Override
  public void onPageSelected(int position) {
    final ActionBar actionBar = getActionBar();
    if (actionBar != null) {
      actionBar.setSelectedNavigationItem(position);
    }
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override
  public void onPageScrollStateChanged(int state) {
  }

  private class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case NAVIGATION_TAB_DEVICES:
          return new DevicesFragment();

        case NAVIGATION_TAB_SCANNING:
          return new ScanningFragment();
      }

      return null;
    }

    @Override
    public int getCount() {
      return NAVIGATION_TABS_COUNT;
    }
  }
}
