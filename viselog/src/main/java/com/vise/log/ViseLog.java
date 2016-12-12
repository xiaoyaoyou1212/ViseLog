package com.vise.log;

import com.vise.log.config.LogConfig;
import com.vise.log.config.LogDefaultConfig;
import com.vise.log.inner.SoulsTree;
import com.vise.log.inner.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * @Description: 日志操作
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/10 21:49.
 */
public class ViseLog {
    //森林
    private static final List<Tree> FOREST = new ArrayList<>();
    //主干树
    private static final Tree TREE_OF_SOULS = new SoulsTree();
    //默认配置
    private static final LogDefaultConfig LOG_DEFAULT_CONFIG = LogDefaultConfig.getInstance();

    private ViseLog() {
        throw new AssertionError("No instances.");
    }

    public static void wtf(String message, Object... args) {
        TREE_OF_SOULS.wtf(message, args);
    }

    public static void wtf(Object object) {
        TREE_OF_SOULS.wtf(object);
    }

    public static void e(String message, Object... args) {
        TREE_OF_SOULS.e(message, args);
    }

    public static void e(Object object) {
        TREE_OF_SOULS.e(object);
    }

    public static void w(String message, Object... args) {
        TREE_OF_SOULS.w(message, args);
    }

    public static void w(Object object) {
        TREE_OF_SOULS.w(object);
    }

    public static void d(String message, Object... args) {
        TREE_OF_SOULS.d(message, args);
    }

    public static void d(Object object) {
        TREE_OF_SOULS.d(object);
    }

    public static void i(String message, Object... args) {
        TREE_OF_SOULS.i(message, args);
    }

    public static void i(Object object) {
        TREE_OF_SOULS.i(object);
    }

    public static void v(String message, Object... args) {
        TREE_OF_SOULS.v(message, args);
    }

    public static void v(Object object) {
        TREE_OF_SOULS.v(object);
    }

    public static void json(String json) {
        TREE_OF_SOULS.json(json);
    }

    public static void xml(String xml) {
        TREE_OF_SOULS.xml(xml);
    }

    public static Tree asTree() {
        return TREE_OF_SOULS;
    }

    /**
     * 获取配置信息，可重新进行设置
     * @return
     */
    public static LogConfig getLogConfig() {
        return LOG_DEFAULT_CONFIG;
    }

    /**
     * 设置标签
     * @param tag
     * @return
     */
    public static Tree setTag(String tag) {
        Tree[] forest = ((SoulsTree) TREE_OF_SOULS).getForestAsArray();
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].setTag(tag);
        }
        return TREE_OF_SOULS;
    }

    /**
     * 植树，添加一颗树
     * @param tree
     */
    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == TREE_OF_SOULS) {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            ((SoulsTree) TREE_OF_SOULS).setForestAsArray(FOREST.toArray(new Tree[FOREST.size()]));
        }
    }

    /**
     * 植树，添加几颗树
     * @param trees
     */
    public static void plant(Tree... trees) {
        if (trees == null) {
            throw new NullPointerException("trees == null");
        }
        for (Tree tree : trees) {
            if (tree == null) {
                throw new NullPointerException("trees contains null");
            }
            if (tree == TREE_OF_SOULS) {
                throw new IllegalArgumentException("Cannot plant Timber into itself.");
            }
        }
        synchronized (FOREST) {
            Collections.addAll(FOREST, trees);
            ((SoulsTree) TREE_OF_SOULS).setForestAsArray(FOREST.toArray(new Tree[FOREST.size()]));
        }
    }

    /**
     * 移除一颗树
     * @param tree
     */
    public static void uproot(Tree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                throw new IllegalArgumentException("Cannot uproot tree which is not planted: " +
                        tree);
            }
            ((SoulsTree) TREE_OF_SOULS).setForestAsArray(FOREST.toArray(new Tree[FOREST.size()]));
        }
    }

    /**
     * 移除所有树
     */
    public static void uprootAll() {
        synchronized (FOREST) {
            FOREST.clear();
            ((SoulsTree) TREE_OF_SOULS).setForestAsArray(new Tree[0]);
        }
    }

    /**
     * 获取森林
     * @return
     */
    public static List<Tree> forest() {
        synchronized (FOREST) {
            return unmodifiableList(new ArrayList<>(FOREST));
        }
    }

    /**
     * 获取当前森林有几颗树
     * @return
     */
    public static int treeCount() {
        synchronized (FOREST) {
            return FOREST.size();
        }
    }
}
