package com.example.eerestaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

//This is to change the image given by the user to a ByteArray and saving it within the database, another method then is called to get the ByteArray and decode it back to an image.

public class Utils {
    public static byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage (byte[] data)
    {
        return BitmapFactory.decodeByteArray(data, 0 , data.length);
    }
}
