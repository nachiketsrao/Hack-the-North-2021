package com.company.stockclock.Activities;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ImageNameHelper {

    //class to save list items and read them in/from device memory as a file

    public static final String FILENAME = "listimages2.dat";

    public static void writeData(ArrayList<String> item, Context context)
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE); //creates a file in the device's memory and opens it
            // Context.MODE_PRIVATE ensures that only this app can access/read the file
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item);
            oas.close(); //closing the file
        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public static ArrayList<String> readData(Context context)
    {
        ArrayList<String> itemList = null;

        try
        {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList = (ArrayList<String>) ois.readObject();
        }

        catch (FileNotFoundException e)
        {
            itemList = new ArrayList<>(); //When the app first runs, there is not file, so the arraylist must be empty

            e.printStackTrace();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return (itemList);

    }

}
