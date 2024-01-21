package com.example.bookingappteam9.utils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.BookingApplication;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.Photo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ImageLoaderTask extends AsyncTask<String, Integer, List<Photo>> {


    @Override
    protected List<Photo> doInBackground(String... strings) {
        List<Photo> photos = new ArrayList<>();
        for (String fileName: strings){
            String path = null;
            try {
                Context context = BookingApplication.getAppContext();
                File file = Glide.with(context).downloadOnly().diskCacheStrategy(DiskCacheStrategy.DATA).load(ClientUtils.getPhotoPath(fileName)).submit().get();
                Bitmap bitmap = Glide.with(context).asBitmap().load(file).submit().get();
                Photo newPhoto = new Photo();
                newPhoto.setFile(file);
                newPhoto.setName(fileName);
                newPhoto.setImage(bitmap);

                newPhoto.setUri( getImageUri(BookingApplication.getAppContext(), bitmap, fileName));
                photos.add(newPhoto);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
        }}

        return photos;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String name) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, name, null);
        return Uri.parse(path);
    }
    private Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
