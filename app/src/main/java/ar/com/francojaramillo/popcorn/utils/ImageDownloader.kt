package ar.com.francojaramillo.popcorn.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import ar.com.francojaramillo.popcorn.data.models.Movie
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * Native Asynctas to download and save an image to disk. Prior to call this class, it MUST be
 * check that there are WRITE_PERMISSIONS to write to disk
 */
class ImageDownloader: AsyncTask<Movie, Unit, Unit>() {

    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    override fun doInBackground(vararg params: Movie?) {

        // Simple download of the image
        val imageUrl = URL(params.get(0)?.poster)
        val bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())

        // Write to disk
        var bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val f = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                params.get(0)?.title + ".jpg")
        f!!.createNewFile()
        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        fo.close()

        return
    }

}