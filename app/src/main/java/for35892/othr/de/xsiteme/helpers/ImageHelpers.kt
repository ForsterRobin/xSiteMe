package for35892.othr.de.xsiteme.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import for35892.othr.de.xsiteme.R
import java.io.IOException
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun showImagePicker(parent: Activity, id: Int) {
  val intent = Intent()
  intent.type = "image/*"
  intent.action = Intent.ACTION_OPEN_DOCUMENT
  intent.addCategory(Intent.CATEGORY_OPENABLE)
  val chooser = Intent.createChooser(intent, R.string.select_site_image.toString())
  parent.startActivityForResult(chooser, id)
}

fun readImage(activity: Activity, resultCode: Int, data: Intent?): Bitmap? {
  var bitmap: Bitmap? = null
  if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
    try {
      bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, data.data)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
  return bitmap
}

fun readImageFromPath(context: Context, path: String): Bitmap? {
  var bitmap: Bitmap? = null
  val uri = Uri.parse(path)
  if (uri != null) {
    try {
      val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
      val fileDescriptor = parcelFileDescriptor.fileDescriptor
      bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
      parcelFileDescriptor.close()
    } catch (e: Exception) {
    }
  }
  return bitmap
}

@Throws(IOException::class)
fun createImageFile(context: Context): File {
  val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
  val imageFileName = "IMG_" + timeStamp + "_"
  val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
  val image = File.createTempFile(
    imageFileName, /* prefix */
    ".jpg", /* suffix */
    storageDir      /* directory */
  )
  println("XXX: " + storageDir)
  val imageFilePath = image.getAbsolutePath()
  return image
}