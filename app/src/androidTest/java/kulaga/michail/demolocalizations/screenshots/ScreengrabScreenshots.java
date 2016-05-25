package kulaga.michail.demolocalizations.screenshots;

import android.support.test.rule.ActivityTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import kulaga.michail.demolocalizations.MainActivity;
import kulaga.michail.demolocalizations.R;
import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.locale.LocaleTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(JUnit4.class)
public class ScreengrabScreenshots {

	@ClassRule
	public static final LocaleTestRule localeTestRule = new LocaleTestRule();

	@Rule
	public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void testTakeScreenshot() {
		Screengrab.screenshot("before_button_click");

		onView(withId(R.id.fab)).perform(click());

		Screengrab.screenshot("after_button_click");
	}

	@Test
	public void testOpenActivity() {

		onView(withId(R.id.btn_show_scrollview)).perform(click());

		Screengrab.screenshot("scrollview");
	}

}
