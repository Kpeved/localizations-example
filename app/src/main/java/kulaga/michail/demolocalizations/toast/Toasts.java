package kulaga.michail.demolocalizations.toast;

import android.content.Context;
import android.widget.Toast;

public class Toasts {
	private static Toast toast;

	public static void show(Context context, int stringId) {
		toast = Toast.makeText(context, stringId, Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void cancelLastToast() {
		if (toast != null) {
			toast.cancel();
		}
	}
}
