package se.aqba.app.android.xposed.googleplus;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class Main implements IXposedHookInitPackageResources {
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
                if(params != null) {
                    params.width = 0;
                    params.height = 0;
                    layout.setLayoutParams(params);
                }
            }
        });
    }
}
