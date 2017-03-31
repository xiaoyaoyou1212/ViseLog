package com.vise.log.config;

import android.text.TextUtils;
import android.util.Log;

import com.vise.log.common.LogPattern;
import com.vise.log.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 日志默认配置实现
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/11 11:08.
 */
public class LogDefaultConfig implements LogConfig {
    private boolean enable = true;
    private String tagPrefix;
    private boolean showBorder = true;
    private int logLevel = Log.VERBOSE;
    private List<Parser> parseList;
    private String formatTag;

    private static LogDefaultConfig singleton;

    private LogDefaultConfig() {
        parseList = new ArrayList<>();
    }

    public static LogDefaultConfig getInstance() {
        if (singleton == null) {
            synchronized (LogDefaultConfig.class) {
                if (singleton == null) {
                    singleton = new LogDefaultConfig();
                }
            }
        }
        return singleton;
    }

    @Override
    public LogConfig configAllowLog(boolean allowLog) {
        this.enable = allowLog;
        return this;
    }

    @Override
    public LogConfig configTagPrefix(String prefix) {
        this.tagPrefix = prefix;
        return this;
    }

    @Override
    public LogConfig configFormatTag(String formatTag) {
        this.formatTag = formatTag;
        return this;
    }

    public String getFormatTag(StackTraceElement caller) {
        if (TextUtils.isEmpty(formatTag) || caller == null) {
            return null;
        }
        LogPattern logPattern = LogPattern.compile(formatTag);
        if (logPattern != null) {
            return logPattern.apply(caller);
        } else {
            return null;
        }
    }

    @Override
    public LogConfig configShowBorders(boolean showBorder) {
        this.showBorder = showBorder;
        return this;
    }

    @Override
    public LogConfig configLevel(int logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    @Override
    public LogConfig addParserClass(Class<? extends Parser>... classes) {
        for (Class<? extends Parser> cla : classes) {
            try {
                parseList.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getTagPrefix() {
        if (TextUtils.isEmpty(tagPrefix)) {
            return "ViseLog";
        }
        return tagPrefix;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public List<Parser> getParseList() {
        return parseList;
    }
}
