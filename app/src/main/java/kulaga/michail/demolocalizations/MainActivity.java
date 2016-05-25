package kulaga.michail.demolocalizations;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kulaga.michail.demolocalizations.toast.Toasts;

public class MainActivity extends AppCompatActivity {

	private int clicksCount = 1;

	@Bind(R.id.tv_clicks_count)
	TextView clicksTextView;

	@Bind(R.id.fab)
	FloatingActionButton fab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		clicksTextView.setText(getResources().getQuantityString(R.plurals.clicks_count, clicksCount, clicksCount));

		fab.setOnClickListener(view -> {
			Snackbar.make(view, R.string.snackbar_text, Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
			clicksCount++;

			clicksTextView.setText(getResources().getQuantityString(R.plurals.clicks_count, clicksCount, clicksCount));
		});
	}

	public static String getStandaloneMonthName(int monthNumber) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL", Locale.getDefault());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, monthNumber);
		return dateFormat.format(calendar.getTime());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.btn_show_dialog)
	public void onShowDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.dialog_title)
				.setPositiveButton(R.string.ok_button, (dialog, which) -> {
					dialog.dismiss();
				})
				.setNegativeButton(R.string.cancel_button, (dialog, which) -> {
					dialog.dismiss();
				})
				.show();
	}

	@OnClick(R.id.btn_show_scrollview)
	public void onShowScrollview() {
		startActivity(new Intent(this, ScrollViewActivity.class));
	}

	@OnClick(R.id.btn_show_toast)
	public void onShowToast() {
		Toasts.show(this, R.string.toast_text);
	}
}
