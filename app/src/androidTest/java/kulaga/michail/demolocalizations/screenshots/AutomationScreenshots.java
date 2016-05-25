package kulaga.michail.demolocalizations.screenshots;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;
import com.facebook.testing.screenshot.internal.Registry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kulaga.michail.demolocalizations.MainActivity;
import kulaga.michail.demolocalizations.R;

@RunWith(AndroidJUnit4.class)
public class AutomationScreenshots {

	private MainActivity activity;
	@Rule
	public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
		@Override
		protected void beforeActivityLaunched() {
			super.beforeActivityLaunched();
		}

		@Override
		protected void afterActivityLaunched() {
			super.afterActivityLaunched();
			activity = getActivity();
		}
	};

	@Before
	public void setUp() throws Exception {
		Registry registry = Registry.getRegistry();
		registry.instrumentation = InstrumentationRegistry.getInstrumentation();
	}

	@Test
	public void testMainFragment() throws Exception {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.content_main, null, false);

		ViewHelpers.setupView(view)
				.setExactWidthDp(300)
				.layout();

		Screenshot.snap(view)
				.record();

	}
}
