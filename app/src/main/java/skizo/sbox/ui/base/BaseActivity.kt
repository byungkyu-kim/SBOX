package skizo.sbox.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import skizo.library.Builder
import skizo.library.extensions.trace
import skizo.sbox.ui.activity.SplashActivity

abstract class BaseActivity: AppCompatActivity() {


    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        if(Builder.isReturnedToForeground) {
            if(this is SplashActivity) {
                trace("##BaseActivity## App Status firstStart")
            } else {
                trace("##BaseActivity## App Status returnToForeground")
            }

        }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()

        if(Builder.isBackground) {
            trace("##BaseActivity## App Status isBackground")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }



}



