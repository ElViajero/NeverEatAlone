package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAlertHelper;

public class ContactTabbedActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	static Map<Integer, String> tagMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contact_tabbed);
		if (tagMap == null)
			tagMap = new HashMap<Integer, String>();

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		if (NotificationAlertHelper.isAlertEnabled("contact")) {
			mViewPager.setCurrentItem(0);
		} else
			mViewPager.setCurrentItem(1);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mSectionsPagerAdapter.getListFragment(arg0).onResume();
				;

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_tabbed, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		FragmentManager fragmentManager;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			fragmentManager = fm;
			// TODO Auto-generated constructor stub
		}

		@Override
		public android.app.Fragment getItem(int position) {

			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).

			if (position == 0) {
				NotificationAlertHelper.disableNotificationAlert("contact");
				return new DisplayContactNotificationActivity();
			}
			if (position == 1)
				return new ContactsActivity();
			if (position == 2)
				return new NearbyContactsActivity();

			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {

			case 0:
				return "All Contacts";
			case 1:
				return "Nearby";

			}
			return null;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Object obj = super.instantiateItem(container, position);

			ListFragment f = (ListFragment) obj;
			tagMap.put(position, f.getTag());
			return obj;

		}

		public ListFragment getListFragment(int position) {
			return (ListFragment) fragmentManager.findFragmentByTag(tagMap
					.get(position));
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_contact_tabbed,
					container, false);
			return rootView;
		}
	}

	public void onAddFriendsButtonClick(View view) {
		Intent intent = new Intent(this, AddFriendsActivity.class);
		this.startActivity(intent);
	}

	/**
	 * Method for contact notification button click
	 * 
	 * 
	 */
	public void onContactNotificationButtonClick(View view) {
		Intent intent = new Intent(this,
				DisplayContactNotificationActivity.class);
		this.startActivity(intent);
	}

	public void onResume() {
		super.onResume();

		if (NotificationAlertHelper.isAlertEnabled("contact")) {

			mViewPager.setCurrentItem(0);
		} else
			mViewPager.setCurrentItem(1);
		MessageToasterHelper.isMessageToastable = true;
	}

	@Override
	public void onPause() {
		MessageToasterHelper.isMessageToastable = false;
		super.onPause();
	}

}
