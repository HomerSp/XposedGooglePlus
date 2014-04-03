package se.aqba.app.android.xposed.googleplus;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by homer on 24/02/14.
 */
public class Main implements IXposedHookLoadPackage, IXposedHookInitPackageResources {
    private long mTouchStart = 0;
    private boolean mDownWasSent = true;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //XposedBridge.log("[XposedGoogle+] Package: " + lpparam.packageName);

        /*if(lpparam.packageName.equals("com.google.android.apps.plus")) {
            XposedHelpers.findAndHookMethod("com.google.android.apps.plus.phone.HomeActivity", lpparam.classLoader, "onCreateOptionsMenu", Menu.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("[XposedGoogle+] beforeHookedMethod HomeActivity.onCreateOptionsMenu");

                    param.setResult(null);
                }
            });
        }*/
    }

    @Override
    public void handleInitPackageResources(final XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        XposedBridge.log("[XposedGoogle+] Package: " + resparam.packageName);

        if (!resparam.packageName.equals("com.google.android.apps.plus"))
            return;

        resparam.res.hookLayout("com.google.android.apps.plus", "layout", "people_notification_button", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                FrameLayout layout = (FrameLayout)liparam.view;
                ViewGroup.LayoutParams params = layout.getLayoutParams();
                params.width = 0;
                params.height = 0;
                layout.setLayoutParams(params);
            }
        });
    }
}
