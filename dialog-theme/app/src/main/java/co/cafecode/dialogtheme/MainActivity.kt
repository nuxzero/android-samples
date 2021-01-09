package co.cafecode.dialogtheme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		AlertDialog.Builder(this)
				.setTitle("Dialog title")
				.setMessage("Dialog message")
				.setNegativeButton("Cancel", null)
				.setPositiveButton("Confirm", null)
				.setNeutralButton("More Info", null)
				.show()
	}
}
