package kulaga.michail.demolocalizations.utils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Configuration;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

public class TestUtils {
	public static void waitForActivity(Instrumentation instrumentation,
	                                   Activity activity,  long timeoutMillis) {
		final long startTime = System.currentTimeMillis();
		final long endTime = startTime + timeoutMillis;

		do {
			if (activity.getClass().isInstance(getActivityInstance(instrumentation))) {
				return;
			}

			waitMillis(instrumentation, 500);
		} while (System.currentTimeMillis() < endTime);

		throw new PerformException.Builder()
				.withActionDescription("MainActivity not found")
				.withCause(new TimeoutException())
				.build();
	}

	public static Activity getActivityInstance(Instrumentation instrumentation) {
		final Activity[] currentActivity = new Activity[1];
		instrumentation.runOnMainSync(new Runnable() {
			public void run() {
				Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
				if (resumedActivities.iterator().hasNext()) {
					currentActivity[0] = (Activity) resumedActivities.iterator().next();
				}
			}
		});

		return currentActivity[0];
	}

	public static ViewAction waitId(final int viewId, final long millis) {
		return new ViewAction() {
			@Override
			public Matcher<View> getConstraints() {
				return isRoot();
			}

			@Override
			public String getDescription() {
				return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
			}

			@Override
			public void perform(final UiController uiController, final View view) {
				uiController.loopMainThreadUntilIdle();
				final long startTime = System.currentTimeMillis();
				final long endTime = startTime + millis;
				final Matcher<View> viewMatcher = withId(viewId);

				do {
					for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
						if (viewMatcher.matches(child)) {
							return;
						}
					}

					uiController.loopMainThreadForAtLeast(50);
				}
				while (System.currentTimeMillis() < endTime);

				throw new PerformException.Builder()
						.withActionDescription(this.getDescription())
						.withViewDescription(HumanReadables.describe(view))
						.withCause(new TimeoutException())
						.build();
			}
		};
	}

	@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
	public static void waitMillis(Instrumentation instrumentation, int delay) {
		synchronized (instrumentation) {
			try {
				instrumentation.wait(delay);
			} catch (InterruptedException e) {
				Log.e("OLOLO", e.toString());
			}
		}
	}


	public static void changeLocale(String localeLanguage, Context context) {
		Locale locale;
		if (localeLanguage.contains("_")) {
			locale = new Locale(localeLanguage.substring(0, 2), localeLanguage.substring(localeLanguage.length() - 2, localeLanguage.length()));
		} else {
			locale = new Locale(localeLanguage);
		}
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		if (context.getApplicationContext().getResources() != null && context.getResources().getDisplayMetrics() != null) {
			context.getApplicationContext().getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
		}
	}

}
