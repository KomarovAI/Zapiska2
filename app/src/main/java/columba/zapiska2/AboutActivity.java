package columba.zapiska2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static columba.zapiska2.R.id.activity_main;
import static columba.zapiska2.R.id.editText;

/**
 * Created by Artur on 23.09.2016.
 */

public class AboutActivity extends AppCompatActivity {
    private EditText mEditText;
    Button del;
    String fname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mEditText = (EditText) findViewById(R.id.editText);
        del = (Button) findViewById(R.id.deleted);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.iiiapka2));

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleted:
                deletedFile(fname);
                return true;
            case android.R.id.home:
                nameButt();
            default:
                return true;
        }
    }

    private void openFile(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
                mEditText.setText(builder.toString());
            }
        } catch (Throwable t) {
        }
    }
    private  void saveFile(String fileName) {
        try {
            OutputStream outputStream = openFileOutput(fileName, 0);
            OutputStreamWriter osw =  new OutputStreamWriter(outputStream);
            osw.write(mEditText.getText().toString());
            osw.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void deletedFile(String fileName) {
        try {
            OutputStream outputStream = openFileOutput(fileName, 0);
            OutputStreamWriter osw =  new OutputStreamWriter(outputStream);
            osw.write("");
            mEditText.setText("");
            osw.close();
            Intent intent2 = new Intent(this, MainActivity.class);
            intent2.putExtra("DELETED", fileName);
            setResult(0, intent2);
            finish();
//            LinearLayout layout = (LinearLayout) findViewById(activity_main);
//            layout.findViewById(Integer.parseInt(fileName)).setVisibility(View.GONE);
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void nameButt() {
        Intent intent2 = new Intent(this, MainActivity.class);
        try {
            intent2.putExtra("nameButt", mEditText.getText().toString().substring(0, 30) +  "...");
        } catch (Exception e) {
            intent2.putExtra("nameButt", mEditText.getText().toString());
        }

        if(!mEditText.getText().toString().equals("")) {
            intent2.putExtra("id", fname);
            setResult(228, intent2);
        }
        else {
            intent2.putExtra("DELETED", fname);
            setResult(0, intent2);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        nameButt();
        super.onBackPressed();
    }

    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        fname = intent.getStringExtra("fname");
        openFile(fname);
    }
    public void onPause() {
        super.onPause();
        saveFile(fname);
    }
}
