package com.example.homework1.content

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homework1.R
import com.example.homework1.broadcast.BroadcastReceiverExampleActivity
import com.example.homework1.content.DatabaseHelper.Companion.COLUMN_NAME
import com.example.homework1.service.ServiceExampleActivity
import com.google.android.material.button.MaterialButton

/**
 * Экран позволяет с помощью MyContentProvider:
 * - сохранять данные в таблицу бд SQLite
 * - выводить сохраненные данные
 *
 * Примеры использования ContentProvider в приложениях - Контакты, Сообщения.
 * */
class ContentProviderExampleActivity : AppCompatActivity() {

    private lateinit var btnSave: MaterialButton
    private lateinit var btnLoad: MaterialButton
    private lateinit var tvResult: TextView
    private lateinit var etValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_example)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
        setClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initView() {
        btnSave = findViewById(R.id.btn_save)
        btnLoad = findViewById(R.id.btn_load)
        etValue = findViewById(R.id.et_value)
        tvResult = findViewById(R.id.tv_result)
    }

    private fun setClickListener() {
        btnSave.setOnClickListener {
            saveData()
        }

        btnLoad.setOnClickListener {
            loadDataFromContentProvider()
        }
    }

    private fun saveData() {
        val values = ContentValues()
        values.put(MyContentProvider.name, etValue.text.toString())
        etValue.text.clear()
        val uri = contentResolver.insert(MyContentProvider.CONTENT_URI, values)
        if (uri != null) {
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadDataFromContentProvider() {
        val cursor = contentResolver.query(Uri.parse(MyContentProvider.URL) , null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val str = StringBuilder()
                while (!cursor.isAfterLast) {
                    val name = cursor.getColumnIndex(COLUMN_NAME)
                    str.append(cursor.getString(name) + "\n")
                    cursor.moveToNext()
                }
                tvResult.text = str
            }
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, ContentProviderExampleActivity::class.java)
        }
    }
}