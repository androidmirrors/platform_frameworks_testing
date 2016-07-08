package android.support.test.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

/**
 * An activity that allows the user to pick a number and return the number as the activity result
 */
public class PickActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_PICKED_NUMBER = "picked_number";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_activity);

        Button button = (Button) findViewById(R.id.button_pick_number);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_pick_number) {
            Intent resultData = new Intent();
            resultData.putExtra(EXTRA_PICKED_NUMBER, 7);
            setResult(RESULT_OK, resultData);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }
}
