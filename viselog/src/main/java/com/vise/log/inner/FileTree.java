package com.vise.log.inner;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 文件树-输出日志信息到文件
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-03-31 14:26
 */
public class FileTree extends Tree {

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    private static final String FILE_NAME_VERBOSE = "verbose_";
    private static final String FILE_NAME_INFO = "info_";
    private static final String FILE_NAME_DEBUG = "debug_";
    private static final String FILE_NAME_WARN = "warn_";
    private static final String FILE_NAME_ERROR = "error_";
    private static final String FILE_NAME_ASSERT = "assert_";
    private static final String FILE_NAME_SUFFIX = ".log";
    private Context mContext;
    private String mDirectory;
    private File mLogFile;
    private boolean mIsPrintPhoneInfo = false;

    public FileTree(Context mContext, String mDirectory) {
        this.mContext = mContext;
        this.mDirectory = mDirectory;
    }

    @Override
    protected void log(int type, String tag, String message) {
        saveMessageToSDCard(type, tag, message);
    }

    private void saveMessageToSDCard(int type, String tag, String message) {
        //如果SD卡不存在或无法使用，则无法把日志信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            System.out.print("sdcard unmounted, skip dump exception");
            return;
        }
        File dir = new File(PATH + mDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        String timeHour = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date(System.currentTimeMillis()));
        String timeMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis()));
        String timeSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        String fileName = FILE_NAME_VERBOSE;
        switch (type) {
            case Log.VERBOSE:
                fileName = FILE_NAME_VERBOSE + timeMinute;
                break;
            case Log.INFO:
                fileName = FILE_NAME_INFO + timeHour;
                break;
            case Log.DEBUG:
                fileName = FILE_NAME_DEBUG + timeMinute;
                break;
            case Log.WARN:
                fileName = FILE_NAME_WARN + timeDay;
                break;
            case Log.ERROR:
                fileName = FILE_NAME_ERROR + timeSecond;
                break;
            case Log.ASSERT:
                fileName = FILE_NAME_ASSERT + timeDay;
                break;
            default:
                break;
        }
        mLogFile = new File(PATH + mDirectory + File.separator + fileName + FILE_NAME_SUFFIX);
        try {
            if (!mLogFile.exists()) {
                mLogFile.createNewFile();
                mIsPrintPhoneInfo = true;
            } else {
                mIsPrintPhoneInfo = false;
            }
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(mLogFile, true)));
            if (!mIsPrintPhoneInfo) {
                //换行
                printWriter.println();
                printWriter.println();
            }
            //打印发生异常的时间
            printWriter.println(timeSecond);
            if (mIsPrintPhoneInfo) {
                //打印手机信息
                printPhoneInfo(printWriter);
                //换行
                printWriter.println();
            }
            //打印日志信息
            printWriter.print(tag + "\t" + message);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印手机信息
     *
     * @param printWriter
     */
    private void printPhoneInfo(PrintWriter printWriter) {
        try {
            //应用的版本名称和版本号
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            printWriter.print("App Version:");
            printWriter.print(packageInfo.versionName);
            printWriter.print('_');
            printWriter.println(packageInfo.versionCode);

            //android版本号
            printWriter.print("OS Version:");
            printWriter.print(Build.VERSION.RELEASE);
            printWriter.print('_');
            printWriter.println(Build.VERSION.SDK_INT);

            //手机制造商
            printWriter.print("Vendor:");
            printWriter.println(Build.MANUFACTURER);

            //手机型号
            printWriter.print("Model:");
            printWriter.println(Build.MODEL);

            //cpu架构
            printWriter.print("CPU ABI:");
            printWriter.println(Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
