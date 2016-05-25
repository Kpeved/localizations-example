package kulaga.michail.demolocalizations.grabber;

import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.os.Build;

import com.squareup.spoon.ScreenshotGrabber;

public class InstrumentationBitmapGrabber implements ScreenshotGrabber {

	private Bitmap bitmap;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public InstrumentationBitmapGrabber(Instrumentation instrumentation) {
		bitmap = instrumentation.getUiAutomation().takeScreenshot();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public Bitmap take() {
		return bitmap;
	}
}
