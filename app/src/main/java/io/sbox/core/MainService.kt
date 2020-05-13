package io.sbox.core

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.Button
import io.comico.library.extensions.toColorFromRes
import io.comico.library.extensions.trace
import io.sbox.R
import io.sbox.databinding.ServiceMainBinding
import io.sbox.event.WindowTouchEvent


@Suppress("DEPRECATION")
class MainService : Service(), OnTouchListener {


    companion object {
        fun newService(context: Context): Intent =
            Intent(context, MainService::class.java)
    }

    private lateinit var sbox: ServiceMainBinding
    private lateinit var screen: View

    private val windowManager: WindowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private lateinit var windowViewLayoutParams: WindowManager.LayoutParams

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()

        var windowViewLayoutParams = WindowManager.LayoutParams()
        windowViewLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        windowViewLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            windowViewLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            windowViewLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        windowViewLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        windowViewLayoutParams.format = PixelFormat.TRANSLUCENT

        windowViewLayoutParams.gravity = Gravity.LEFT or Gravity.TOP

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        sbox = ServiceMainBinding.inflate(inflater, null, false)
        sbox.root.setOnTouchListener(WindowTouchEvent(updateViewLayout = ::updateViewPosition))

        windowManager.addView(sbox.root, windowViewLayoutParams)


        /** screen **/
        var param = WindowManager.LayoutParams()
        param.copyFrom(windowViewLayoutParams)
        param.width = WindowManager.LayoutParams.MATCH_PARENT
        param.height = WindowManager.LayoutParams.MATCH_PARENT

        param.flags = param.flags or
                WindowManager.LayoutParams.FLAG_DIM_BEHIND or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE

        param.dimAmount = 0.8f

        screen = View(baseContext)
/*
        screen.keepScreenOn = true
        screen.setFitsSystemWindows(false)
        screen.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
*/

        windowManager.addView(screen, param)



        //stopSelf()
    }

    private fun updateViewPosition(x: Int, y: Int) {
//        windowViewLayoutParams.x += x
//        windowViewLayoutParams.y += y
//        windowManager.updateViewLayout(sbox.root, windowViewLayoutParams)
    }





    fun onCreate2() {
        super.onCreate()




//        initWindowLayout(getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//        windowBinding.initCreate()


        trace("@@@@@@@@@@@@@@@@@@@  servi ", this)


        val LAYOUT_FLAG: Int
        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }


        /*

        windowManager.addView(windowBinding.root, windowViewLayoutParams)
         */

        val inflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        onTopView = inflater.inflate(R.layout.service_main, null)

//        onTopView!!.setOnTouchListener(this)

        var params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or
                    WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION or
                    WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.LEFT or Gravity.TOP
        manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        manager!!.addView(onTopView, params)


        screen = View(baseContext)
        screen.setBackgroundColor(android.R.color.background_dark.toColorFromRes)
        screen.alpha = 0.4f



        screen.keepScreenOn = true

        screen.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN

        val metrics = DisplayMetrics()
        manager!!.getDefaultDisplay().getMetrics(metrics)

        screen.setFitsSystemWindows(false); // allow us to draw over status bar, navigation bar

        // here I _manually_ set the dimensions, because otherwise it defaults to a square (something like 1024x1024)
        params.width = metrics.widthPixels
        params.height = metrics.heightPixels + 100


        params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE


        manager!!.addView(screen, params)







        onTopView2 = inflater.inflate(R.layout.service_main, null)

        onTopView2!!.setOnTouchListener(this)

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.LEFT or Gravity.TOP
        manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager!!.addView(onTopView2, params)

//        onTopView?.isEnabled = false
//        onTopView?.isClickable = false

//        val closeBtn =
//            onTopView2!!.findViewById<Button>(R.id.close_this_window)

/*

        closeBtn.setOnClickListener {

            manager!!.removeView(onTopView)
            manager!!.removeView(onTopView2)
            onTopView = null
            onTopView2 = null
            stopSelf()

        }
*/

    }


    private var onTopView: View? = null
    private var onTopView2: View? = null
    private var manager: WindowManager? = null


    override fun onDestroy() {
        super.onDestroy()



//        if (::windowBinding.isInitialized) {
//            windowManager.removeView(windowBinding.root)
//        }


        if (onTopView != null) {
            manager!!.removeView(onTopView)
            manager!!.removeView(onTopView2)
            onTopView = null
        }
    }

    var xpos = 0f
    var ypos = 0f
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val wm =
            applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        val action = motionEvent.action
        val pointerCount = motionEvent.pointerCount
        when (action) {
            MotionEvent.ACTION_DOWN -> if (pointerCount == 1) {
                xpos = motionEvent.rawX
                ypos = motionEvent.rawY
            }
            MotionEvent.ACTION_MOVE -> if (pointerCount == 1) {
                val lp =
                    view.layoutParams as WindowManager.LayoutParams
                val dx = xpos - motionEvent.rawX
                val dy = ypos - motionEvent.rawY
                xpos = motionEvent.rawX
                ypos = motionEvent.rawY
                Log.d(
                    "TAG",
                    "lp.x : " + lp.x + ", dx : " + dx + "lp.y : " + lp.y + ", dy : " + dy
                )
                lp.x = (lp.x - dx).toInt()
                lp.y = (lp.y - dy).toInt()
                manager!!.updateViewLayout(view, lp)
                return false
            }
        }
        return false
    }


}