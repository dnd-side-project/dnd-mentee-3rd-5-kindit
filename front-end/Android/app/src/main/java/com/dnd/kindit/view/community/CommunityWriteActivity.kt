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
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.util.PreferenceManager
import com.google.gson.GsonBuilder
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_community_write.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
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

        initListener()
    }

    private fun initListener() {
        cw_btn_load.setOnClickListener {
            getImageToAlbum()
        }
        cw_tv_upload.setOnClickListener {
            val token = PreferenceManager.getString(this, "kindit_token").toString()

            val uploadService = RetrofitClient.kindItCommunityService()


            val content = RequestBody.create(
                MediaType.parse("application/json"),
                cw_edt_text.text.toString())

            // multipart/form-data
            val reqFile = RequestBody.create(
                MediaType.parse("upload_image"),
                tempFile!!
            )
            val imageFile = MultipartBody.Part.createFormData(
                "upload_image",
                tempFile!!.name,
                reqFile
            )


            var uploadCall : Call<CommonResponse>
            uploadCall =  if (tempFile != null) {
                uploadService.uploadPost1(token, content, imageFile)
            } else {
                uploadService.uploadPost2(token, content)
            }

            uploadCall.enqueue(object  : retrofit2.Callback<CommonResponse>{
                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.result == "success") {
                            Toast.makeText(applicationContext, responseBody.message, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        val responseErrorBody = GsonBuilder().create()
                            .fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                        Log.d(TAG, responseErrorBody.toString())
                        // 실패 없음
                    }
                }

            })
        }
    }

    private fun tedPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                // 권한 요청 성공
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                // 권한 요청 실패
                Toast.makeText(applicationContext, "권한이 필요한 서비스 입니다.", Toast.LENGTH_SHORT).show()
                finish()
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
        startActivityForResult(
            intent,
            PICK_FROM_ALBUM
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FROM_ALBUM) {
            val photoUri = data?.data
            var cursor: Cursor? = null

            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = contentResolver.query(photoUri!!, proj, null, null, null)

                val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                cursor.moveToFirst()

                tempFile = File(cursor.getString(columnIndex))

                setImage()
            } catch (e: Exception) {
                Toast.makeText(this, "이미지 처리중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, e.message.toString())
            } finally {
                cursor?.close()
            }
        }
    }

    private fun setImage() {
        val options = BitmapFactory.Options()
        val originalBm = BitmapFactory.decodeFile(tempFile!!.absolutePath, options)
        val reBitmap =
            Bitmap.createScaledBitmap(originalBm, cw_img_view.width, cw_img_view.height, false)

        cw_img_view.setImageBitmap(reBitmap)
        cw_ll_img.visibility = View.INVISIBLE

    }
}