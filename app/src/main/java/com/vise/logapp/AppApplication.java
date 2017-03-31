package com.vise.logapp;

import android.app.Application;

import com.vise.log.ViseLog;
import com.vise.log.inner.ConsoleTree;
import com.vise.log.inner.FileTree;
import com.vise.log.inner.LogcatTree;

/**
 * @Description:
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/11 20:13.
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            ViseLog.getLogConfig().configAllowLog(true)
                    .configShowBorders(false);
            ViseLog.plant(new FileTree(this, "Log"));
            ViseLog.plant(new ConsoleTree());
            ViseLog.plant(new LogcatTree());
        }
    }
}
