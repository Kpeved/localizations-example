package kulaga.michail.demolocalizations.screenshots;

import android.app.Activity;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.squareup.spoon.Spoon;
import com.squareup.spoon.retrievers.ScrollViewScreenshotGrabber;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kulaga.michail.demolocalizations.MainActivity;
import kulaga.michail.demolocalizations.R;
import kulaga.michail.demolocalizations.grabber.InstrumentationBitmapGrabber;
import kulaga.michail.demolocalizations.toast.Toasts;
import kulaga.michail.demolocalizations.utils.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static kulaga.michail.demolocalizations.utils.TestUtils.getActivityInstance;
import static kulaga.michail.demolocalizations.utils.TestUtils.waitMillis;

@RunWith(AndroidJUnit4.class)
public class ScreenshotTest {
	@Rule
	public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
		@Override
		protected void beforeActivityLaunched() {
			super.beforeActivityLaunched();

			String locale = InstrumentationRegistry.getArguments().getString(SpoonTestSuite.LOCALE);

			if (!TextUtils.isEmpty(locale)) {
				TestUtils.changeLocale(locale, InstrumentationRegistry.getTargetContext());
			}

		}

		@Override
		protected void afterActivityLaunched() {
			super.afterActivityLaunched();
		}
	};

	@Test
	public void testMain() {
		waitMillis(InstrumentationRegistry.getInstrumentation(), 500);
		screenshot("main");
	}

	@Test
	public void testDialog() {
		waitMillis(InstrumentationRegistry.getInstrumentation(), 500);
		onView(withId(R.id.btn_show_dialog)).perform(click());

		screenshot("dialog");
	}

	private void screenshot(String screenshotName) {
		Activity activity = getActivityInstance(InstrumentationRegistry.getInstrumentation());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Spoon.screenshot(activity, screenshotName,
					new InstrumentationBitmapGrabber(InstrumentationRegistry.getInstrumentation()));
		} else {
			Spoon.screenshot(activity, screenshotName);
		}
	}

	@Test
	public void testToast() {
		waitMillis(InstrumentationRegistry.getInstrumentation(), 500);
		onView(withId(R.id.btn_show_toast)).perform(click());
		screenshot("toast");
		Toasts.cancelLastToast();
	}

	@Test
	public void testScrollView() {
		waitMillis(InstrumentationRegistry.getInstrumentation(), 500);
		onView(withId(R.id.btn_show_scrollview)).perform(click());

		screenshot("scrollview_screen");

		Activity activity = getActivityInstance(InstrumentationRegistry.getInstrumentation());

		ViewGroup scrollview = (ViewGroup) activity.findViewById(R.id.scrollview);

		Spoon.screenshot(activity, "scrollView", new ScrollViewScreenshotGrabber(scrollview));
	}

}
