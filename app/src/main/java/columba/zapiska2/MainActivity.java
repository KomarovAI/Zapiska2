package columba.zapiska2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static columba.zapiska2.R.id.activity_main;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public long x = 0;
    Button btn1;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.new_page);
//        List<File> files = getListFiles(new File("/data/user/0/columba.zapiska2/files/"));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_page:
                save();
                return true;
            default:
                return true;
        }
    }


    public void save() {
        btn1 = new Button(this);
        btn1.setText("");
        btn1.setId((int) x);
        btn1.setOnClickListener(this);
        int width= 500;
        int heigth= 100;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, heigth);
        layoutParams.gravity = Gravity.CENTER;
        btn1.setLayoutParams(layoutParams);
        transition(btn1);
        LinearLayout layout = (LinearLayout) findViewById(activity_main);
        layout.addView(btn1);
        x++;
    }


    @Override
    public void onClick(View v) {
        transition(v);
    }

    public void transition(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("fname", Integer.toString(view.getId()));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        // если пришло ОК
        if (0==resultCode) {
            String DELETED = data.getStringExtra("DELETED");
            ((LinearLayout) findViewById(activity_main)).removeView(findViewById(Integer.parseInt(DELETED)));
        } else {
            String nameButt = data.getStringExtra("nameButt");
            ((Button) findViewById(Integer.parseInt(data.getStringExtra("id")))).setText(nameButt);
        }
            // если вернулось не ОК
    }



//        private List<File> getListFiles(File parentDir) {
//            ArrayList<File> inFiles = new ArrayList<File>();
//            File[] files = parentDir.listFiles();
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    inFiles.addAll(getListFiles(file));
//                } else {
//                        inFiles.add(file);
//                }
//            }
//            return inFiles;
//    }


    public void onStart() {
        super.onStart();
//        try {
//            Intent intent2 = getIntent();
//            DELETED = intent2.getStringExtra("DELETED");
//            ((LinearLayout) findViewById(activity_main)).removeView(findViewById(Integer.parseInt(DELETED)));
//        } catch (Exception e) {
//        }
    }
}