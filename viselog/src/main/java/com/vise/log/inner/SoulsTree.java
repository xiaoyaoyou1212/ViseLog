package com.vise.log.inner;

/**
 * @Description: 日志主干树实现
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/10 22:33.
 */
public class SoulsTree extends Tree {

    private volatile Tree[] forestAsArray = new Tree[0];

    @Override
    public void wtf(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].wtf(message, args);
        }
    }

    @Override
    public void wtf(Object object) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].wtf(object);
        }
    }

    @Override
    public void e(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].e(message, args);
        }
    }

    @Override
    public void e(Object object) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].e(object);
        }
    }

    @Override
    public void w(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].w(message, args);
        }
    }

    @Override
    public void w(Object object) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].w(object);
        }
    }

    @Override
    public void d(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].d(message, args);
        }
    }

    @Override
    public void d(Object object) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].d(object);
        }
    }

    @Override
    public void i(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].i(message, args);
        }
    }

    @Override
    public void i(Object object) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].i(object);
        }
    }

    @Override
    public void v(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].v(message, args);
        }
    }

    @Override
    public void v(Object object) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].v(object);
        }
    }

    @Override
    public void json(String json) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].json(json);
        }
    }

    @Override
    public void xml(String xml) {
        Tree[] forest = forestAsArray;
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].xml(xml);
        }
    }

    @Override
    protected void log(int type, String tag, String message) {
        throw new AssertionError("Missing override for log method.");
    }

    public Tree[] getForestAsArray() {
        return forestAsArray;
    }

    public void setForestAsArray(Tree[] forestAsArray) {
        this.forestAsArray = forestAsArray;
    }
}
