package com.example.brecht.sensortest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

/**
 * Created by Brecht on 23/02/2015.
 */
public class FileWriter{

    private String fileName;
    private  File file;

    public FileWriter(String fileWriter, Context context) {
        fileName = fileWriter;
        file = new File(context.getExternalFilesDir(null), fileName);

        if(file.exists())
            file.delete();
    }

    public void Write(Context c, String string)
    {
        FileOutputStream outputStream;
        OutputStreamWriter osw;

        if(isExternalStorageWritable() ==  false) {
            Toast.makeText(c, "No access to file", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            outputStream = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(outputStream);
            osw.append(string);
            osw.flush();
            osw.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Not necessarily at the moment!
    Checks if external storage is available to at least read
    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    */
}
