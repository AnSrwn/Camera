package com.example.user.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File



class MainActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1

    //Generate Content URI
    private val fileName = "temp_photo"
    private var imageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.visibility = View.GONE
    }

    fun onCameraButtonClick(view: View) {
        val myIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val imgPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        imageFile = File.createTempFile(fileName, ".jpg", imgPath)
        val imageURI: Uri = FileProvider.getUriForFile(this,"com.example.user.camera", imageFile!!)

        if(myIntent.resolveActivity(packageManager) != null) {
            myIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
            startActivityForResult(myIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, recIntent: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val imageURI: Uri = FileProvider.getUriForFile(this,"com.example.user.camera", imageFile!!)
            val myIntent = Intent(android.content.Intent.ACTION_VIEW)

            myIntent.setDataAndType(imageURI, "image/*")
            myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(myIntent)
        }
    }
}
