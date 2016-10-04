package columba.zapiska2;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static columba.zapiska2.R.id.activity_main;
import static columba.zapiska2.R.id.bottom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public long x = 0;
    Button btn1;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.new_page);
        List<File> files = getListFiles(new File(getApplicationInfo().dataDir));
        openFiles(files);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.iiiapka));
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btn1.setBackground(this.getResources().getDrawable(R.drawable.button));
        }
        btn1.setText("");
        btn1.setId((int) x);
        btn1.setOnClickListener(this);
        int width= 1000;
        int heigth= 150;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, heigth);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(50, 30, 50, 0);
        btn1.setLayoutParams(layoutParams);
        transition(btn1);
        LinearLayout layout = (LinearLayout) findViewById(activity_main);
        layout.addView(btn1);
        x++;
    }

    public void openFiles(List<File> files) {
        for (File file : files) {
            btn1 = new Button(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btn1.setBackground(this.getResources().getDrawable(R.drawable.button));
            }
            try {
                btn1.setId(Integer.parseInt(file.getName().substring(0,file.getName().length()-4)));
                btn1.setOnClickListener(this);
                int width= 1000;
                int heigth= 150;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, heigth);
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.setMargins(50, 30, 50, 0);
                btn1.setLayoutParams(layoutParams);
                LinearLayout layout = (LinearLayout) findViewById(activity_main);
                layout.addView(btn1);
                // установка значения x, для создания новых файлов
                if(file == files.get(files.size() - 1)) {
                    x = Integer.parseInt(file.getName().substring(0,file.getName().length()-4))+1;
                }
                btn1.setText(openFile(file.getName()));
            } catch (Exception e) {
                System.out.println("Мамку шатал!");
            }
        }
    }

    private String openFile(String fileName) {
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
                //mEditText.setText(builder.toString());
                //return builder.toString();
                try {
                    return builder.toString().substring(0, 30) +  "...";
                } catch (Exception e) {
                    return builder.toString().substring(0, builder.toString().length()-1);
                }
            }
        } catch (Throwable t) {
        }
        return "";
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".txt")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }


    @Override
    public void onClick(View v) {
        transition(v);
    }

    public void transition(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("fname", Integer.toString(view.getId())+".txt");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        // если пришло ОК
        if (0==resultCode) {
            String DELETED = data.getStringExtra("DELETED");
            ((LinearLayout) findViewById(activity_main)).removeView(findViewById
                    (Integer.parseInt(DELETED.substring(0, DELETED.length()-4))));
            File dir = new File(getApplicationInfo().dataDir+"/files");
            File file = new File(dir, DELETED);
            file.delete();
        } else {
            String nameButt = data.getStringExtra("nameButt");
            String id = data.getStringExtra("id");
            ((Button) findViewById(Integer.parseInt(id.substring(0, id.length()-4)))).setText(nameButt.substring(0, nameButt.length()-1));
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