package skizo.sbox

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.facebook.stetho.Stetho
import skizo.library.Skizo
import skizo.sbox.core.Config

class SBOXApplication: MultiDexApplication(), Application.ActivityLifecycleCallbacks {


    companion object {
        var instance: SBOXApplication? = null
            private set
    }





    override fun onCreate() {
        super.onCreate()

        instance = this

        Config.isDebugMode = BuildConfig.DEBUG_MODE

        Skizo.startInit(this)
            .isDebugMode(Config.isDebugMode)

        registerActivityLifecycleCallbacks(SJActivityLifecycleCallbacks())

        Stetho.initializeWithDefaults(this)

        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)


//        OneSignal.startInit(this)
//            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//            .unsubscribeWhenNotificationsAreDisabled(true)
//            .init()

//        if(Config.isDebugMode) {
//            OneSignal.getTags { tags -> OneSignal.sendTags(tags) }
//            OneSignal.sendTag("PUSH_TEST", "OK")
//            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//        }

    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        Glide.get(this).trimMemory(level)
    }



    override fun onActivityPaused(p0: Activity?) {
    }

    override fun onActivityResumed(p0: Activity?) {
    }

    override fun onActivityStarted(p0: Activity?) {
    }

    override fun onActivityDestroyed(p0: Activity?) {
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity?) {
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
    }


    enum class AppStatus {
        BACKGROUND,                // app is background
        RETURNED_TO_FOREGROUND,    // app returned to foreground(or first launch)
        FOREGROUND;                // app is foreground
    }

    var mAppStatus: AppStatus = AppStatus.FOREGROUND
    var isReturnedToForeground: Boolean = false
        get() = mAppStatus == AppStatus.RETURNED_TO_FOREGROUND
    var isForeground: Boolean = false
        get() = mAppStatus.ordinal > AppStatus.BACKGROUND.ordinal

    inner class SJActivityLifecycleCallbacks: ActivityLifecycleCallbacks {
        var running: Int = 0

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity?) {
            if (++running == 1) {
                mAppStatus = AppStatus.RETURNED_TO_FOREGROUND;
            } else if (running > 1) {
                mAppStatus = AppStatus.FOREGROUND;
            }
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityStopped(activity: Activity?) {
            if (--running == 0) {
                mAppStatus = AppStatus.BACKGROUND;
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }
    }

}