package com.example.yanjun.xmlloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listView);
        final Button button = (Button) findViewById(R.id.button);

        File[] files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).listFiles();
        final List<File> xmlFiles = new ArrayList<File>();

        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".xml")) {
                xmlFiles.add(files[i]);
            }
        }

        String[] fileNames = new String[xmlFiles.size()];

        for (int i = 0; i < xmlFiles.size(); i++) {
            fileNames[i] = xmlFiles.get(i).getName();
        }

        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < xmlFiles.size(); i++) {
                    try {
                        InputStream fis = new FileInputStream(xmlFiles.get(i));
                        OutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/FIRST/" + xmlFiles.get(i).getName()));

                        byte[] buf = new byte[(int) xmlFiles.get(i).length()];
                        fis.read(buf);
                        fos.write(buf);

                        fis.close();
                        fos.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
