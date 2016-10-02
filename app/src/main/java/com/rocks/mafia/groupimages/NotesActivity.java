package com.rocks.mafia.groupimages;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener {
    String isSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(this);

        Intent intent = getIntent();

        isSecret = intent.getStringExtra("isSecret");
        Log.v("CHECK", isSecret);
        if (isSecret.equals("true")) {
            getprivateNotes();
        } else if (isSecret.equals("partial")) {
            getPublicNotes();
        } else {
            getDoublePublicNotes();
        }
    }

    /*
        Retrieves Notes data from the Internal storage
     */

    public void getprivateNotes() {
        EditText notesEdit = (EditText) findViewById(R.id.notes_textBox);
        File file = new File(getFilesDir(), "internal.txt");
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contents = new String(bytes);
        notesEdit.setText(contents);
    }


    /*
        Saves Notes data from the Internal storage
    */
    public void saveData() {
        EditText notesEdit = (EditText) findViewById(R.id.notes_textBox);
        String string = notesEdit.getText().toString();

        // Get the directory for the app's private pictures directory.
        File file = new File(getFilesDir(), "internal.txt");

        try {
            FileOutputStream stream = null;
            stream = new FileOutputStream(file);
            stream.write(string.getBytes());
            Toast.makeText(this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v("URLPrivateExternal", file.getAbsolutePath());
        if (!file.exists()) {
            Log.e("Private External", "File don't created");
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
        Saves Notes data to the private External storage
    */
    public void savePublicData() {
        EditText notesEdit = (EditText) findViewById(R.id.notes_textBox);
        String string = notesEdit.getText().toString();

        // Get the directory for the app's private pictures directory.
        File file = new File(getExternalFilesDir(
                Environment.DIRECTORY_DCIM), "privateExternal.txt");

        try {
            FileOutputStream stream = null;
            stream = new FileOutputStream(file);
            stream.write(string.getBytes());
            Toast.makeText(this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v("URLPrivateExternal", file.getAbsolutePath());
        if (!file.exists()) {
            Log.e("Private External", "File don't created");
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
        Retrieves Notes data from the private external storage
    */
    public void getPublicNotes() {
        EditText notesEdit = (EditText) findViewById(R.id.notes_textBox);
        File file = new File(getExternalFilesDir(
                Environment.DIRECTORY_DCIM), "privateExternal.txt");
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contents = new String(bytes);
        notesEdit.setText(contents);
    }

    /*
        Saves Notes datato thepublic external storage
    */
    public void saveDoublePublicData() {
        EditText notesEdit = (EditText) findViewById(R.id.notes_textBox);
        String string = notesEdit.getText().toString();

        // Get the directory for the app's private pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "publicExternal.txt");

        try {
            FileOutputStream stream = null;
            stream = new FileOutputStream(file);
            stream.write(string.getBytes());
            Toast.makeText(this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!file.exists()) {
            Log.e("Private External", "File don't created");
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
        Retrieves Notes data from the public storage
    */
    public void getDoublePublicNotes() {
        EditText notesEdit = (EditText) findViewById(R.id.notes_textBox);
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "publicExternal.txt");
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contents = new String(bytes);
        notesEdit.setText(contents);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit) {
            if (isSecret.equals("true")) {
                saveData();
            } else if (isSecret.equals("partial")) {
                savePublicData();
            } else {
                saveDoublePublicData();
            }
        }
    }
}
