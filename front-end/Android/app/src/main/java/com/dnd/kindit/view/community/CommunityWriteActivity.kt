package com.dnd.kindit.view.community

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dnd.kindit.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_community_write.*
import java.io.File
import java.util.*


class CommunityWriteActivity : AppCompatActivity() {

    companion object {
        private const val PICK_FROM_ALBUM = 1
    }
    private val TAG = this.javaClass.toString()

    private var tempFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write)

        tedPermission()

        getImageToAlbum()
    }

    private fun tedPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                // 권한 요청 성공
                Toast.makeText(applicationContext, "돼", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                // 권한 요청 실패
                Toast.makeText(applicationContext, "안돼", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(resources.getString(R.string.permission_2))
            .setDeniedMessage(resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()
    }

    private fun getImageToAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent,
            PICK_FROM_ALBUM
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FROM_ALBUM) {
            val photoUri = data?.data
            var cursor: Cursor? = null

            try {
                val proj =arrayOf(MediaStore.Images.Media.DATA)
                cursor = contentResolver.query(photoUri!!, proj, null, null, null)

                val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                cursor.moveToFirst()

                tempFile = File(cursor.getString(columnIndex))
            }catch (e: Exception){
                Toast.makeText(this, "이미지 처리중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, e.message.toString())
            }finally {
                cursor?.close()
            }
        }
        setImage()
    }

    private fun setImage() {
        val options = BitmapFactory.Options()
        val originalBm = BitmapFactory.decodeFile(tempFile!!.absolutePath, options)
        val reBitmap = Bitmap.createScaledBitmap(originalBm, cw_img_view.width, cw_img_view.height, false)

        cw_img_view.setImageBitmap(reBitmap)
        cw_ll_img.visibility = View.INVISIBLE

    }
}