package com.vise.log.inner;

/**
 * @Description: 日志树接口
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/11 16:05.
 */
public interface ITree {
    void wtf(String message, Object... args);

    void wtf(Object object);

    void e(String message, Object... args);

    void e(Object object);

    void w(String message, Object... args);

    void w(Object object);

    void d(String message, Object... args);

    void d(Object object);

    void i(String message, Object... args);

    void i(Object object);

    void v(String message, Object... args);

    void v(Object object);

    void json(String json);

    void xml(String xml);
}
