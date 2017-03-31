package com.vise.log.common;

import com.vise.log.parser.Parser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 日志转换工具
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/11 10:55.
 */
public class LogConvert {

    /**
     * 获取数组的纬度
     *
     * @param object
     * @return
     */
    private static int getArrayDimension(Object object) {
        int dim = 0;
        for (int i = 0; i < object.toString().length(); ++i) {
            if (object.toString().charAt(i) == '[') {
                ++dim;
            } else {
                break;
            }
        }
        return dim;
    }

    /**
     * 是否为数组
     *
     * @param object
     * @return
     */
    private static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    /**
     * 获取数组类型
     *
     * @param object 如L为int型
     * @return
     */
    private static char getType(Object object) {
        if (isArray(object)) {
            String str = object.toString();
            return str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("[") + 2).charAt(0);
        }
        return 0;
    }

    /**
     * 遍历数组
     *
     * @param result
     * @param array
     */
    private static void traverseArray(StringBuilder result, Object array) {
        if (isArray(array)) {
            if (getArrayDimension(array) == 1) {
                switch (getType(array)) {
                    case 'I':
                        result.append(Arrays.toString((int[]) array));
                        break;
                    case 'D':
                        result.append(Arrays.toString((double[]) array));
                        break;
                    case 'Z':
                        result.append(Arrays.toString((boolean[]) array));
                        break;
                    case 'B':
                        result.append(Arrays.toString((byte[]) array));
                        break;
                    case 'S':
                        result.append(Arrays.toString((short[]) array));
                        break;
                    case 'J':
                        result.append(Arrays.toString((long[]) array));
                        break;
                    case 'F':
                        result.append(Arrays.toString((float[]) array));
                        break;
                    case 'L':
                        Object[] objects = (Object[]) array;
                        result.append("[");
                        for (int i = 0; i < objects.length; ++i) {
                            result.append(objectToString(objects[i]));
                            if (i != objects.length - 1) {
                                result.append(",");
                            }
                        }
                        result.append("]");
                        break;
                    default:
                        result.append(Arrays.toString((Object[]) array));
                        break;
                }
            } else {
                result.append("[");
                for (int i = 0; i < ((Object[]) array).length; i++) {
                    traverseArray(result, ((Object[]) array)[i]);
                    if (i != ((Object[]) array).length - 1) {
                        result.append(",");
                    }
                }
                result.append("]");
            }
        } else {
            result.append("not a array!!");
        }
    }

    /**
     * 将数组内容转化为字符串
     *
     * @param array
     * @return
     */
    private static String parseArray(Object array) {
        StringBuilder result = new StringBuilder();
        traverseArray(result, array);
        return result.toString();
    }

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        return objectToString(object, 0);
    }

    /**
     * 是否为静态内部类
     *
     * @param cla
     * @return
     */
    private static boolean isStaticInnerClass(Class cla) {
        if (cla != null && cla.isMemberClass()) {
            int modifiers = cla.getModifiers();
            if ((modifiers & Modifier.STATIC) == Modifier.STATIC) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将对象转化为String
     *
     * @param object
     * @param childLevel 对象包含子对象层级
     * @return
     */
    private static String objectToString(Object object, int childLevel) {
        if (object == null) {
            return LogConstant.STRING_OBJECT_NULL;
        }
        if (childLevel > LogConstant.MAX_CHILD_LEVEL) {
            return object.toString();
        }
        if (LogConstant.getParsers() != null && LogConstant.getParsers().size() > 0) {
            for (Parser parser : LogConstant.getParsers()) {
                if (parser.parseClassType().isAssignableFrom(object.getClass())) {
                    return parser.parseString(object);
                }
            }
        }
        if (isArray(object)) {
            return parseArray(object);
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder();
            getClassFields(object.getClass(), builder, object, false, childLevel);
            Class superClass = object.getClass().getSuperclass();
            while (!superClass.equals(Object.class)) {
                getClassFields(superClass, builder, object, true, childLevel);
                superClass = superClass.getSuperclass();
            }
            return builder.toString();
        } else {
            // 若对象重写toString()方法默认走toString()
            return object.toString();
        }
    }

    /**
     * 拼接class的字段和值
     *
     * @param cla
     * @param builder
     * @param o           对象
     * @param isSubClass  死否为子class
     * @param childOffset 递归解析属性的层级
     */
    private static void getClassFields(Class cla, StringBuilder builder, Object o, boolean
            isSubClass,
                                       int childOffset) {
        if (cla.equals(Object.class)) {
            return;
        }
        if (isSubClass) {
            builder.append(LogConstant.BR).append(LogConstant.BR).append("=> ");
        }
        String breakLine = "";
        builder.append(cla.getSimpleName()).append(" {");
        Field[] fields = cla.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            field.setAccessible(true);
            if (cla.isMemberClass() && !isStaticInnerClass(cla) && i == 0) {
                continue;
            }
            Object subObject = null;
            try {
                subObject = field.get(o);
            } catch (IllegalAccessException e) {
                subObject = e;
            } finally {
                if (subObject != null) {
                    // 解决Instant Run情况下内部类死循环的问题
                    if (!isStaticInnerClass(cla) && (field.getName().equals("$change") || field
                            .getName().equalsIgnoreCase("this$0"))) {
                        continue;
                    }
                    if (subObject instanceof String) {
                        subObject = "\"" + subObject + "\"";
                    } else if (subObject instanceof Character) {
                        subObject = "\'" + subObject + "\'";
                    }
                    if (childOffset < LogConstant.MAX_CHILD_LEVEL) {
                        subObject = objectToString(subObject, childOffset + 1);
                    }
                }
                String formatString = breakLine + "%s = %s, ";
                builder.append(String.format(formatString, field.getName(),
                        subObject == null ? "null" : subObject.toString()));
            }
        }
        if (builder.toString().endsWith("{")) {
            builder.append("}");
        } else {
            builder.replace(builder.length() - 2, builder.length() - 1, breakLine + "}");
        }
    }

    /**
     * 打印分割线
     *
     * @param dir
     * @return
     */
    public static String printDividingLine(int dir) {
        switch (dir) {
            case LogConstant.DIVIDER_TOP:
                return "╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════";
            case LogConstant.DIVIDER_BOTTOM:
                return "╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════";
            case LogConstant.DIVIDER_NORMAL:
                return "║ ";
            case LogConstant.DIVIDER_CENTER:
                return "╟───────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
            default:
                break;
        }
        return "";
    }


    /**
     * 长字符串转化为List
     *
     * @param msg
     * @return
     */
    public static List<String> largeStringToList(String msg) {
        List<String> stringList = new ArrayList<>();
        int index = 0;
        int maxLength = LogConstant.LINE_MAX;
        int countOfSub = msg.length() / maxLength;
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                stringList.add(sub);
                index += maxLength;
            }
            stringList.add(msg.substring(index, msg.length()));
        } else {
            stringList.add(msg);
        }
        return stringList;
    }

    /**
     * 缩短字符串
     *
     * @param string
     * @param count
     * @param length
     * @return
     */
    public static String shorten(String string, int count, int length) {
        if (string == null) return null;
        String resultString = string;
        if (Math.abs(length) < resultString.length()) {
            if (length > 0)
                resultString = string.substring(0, length);
            if (length < 0)
                resultString = string.substring(string.length() + length, string.length());
        }
        if (Math.abs(count) > resultString.length()) {
            return String.format("%" + count + "s", resultString);
        }
        return resultString;
    }

    /**
     * 缩短类名
     *
     * @param className
     * @param count
     * @param maxLength
     * @return
     * @throws Exception
     */
    public static String shortenClassName(String className, int count, int maxLength) throws
            Exception {
        className = shortenPackagesName(className, count);
        if (className == null) return null;
        if (maxLength == 0) return className;
        if (maxLength > className.length()) return className;
        if (maxLength < 0) {
            maxLength = -maxLength;
            StringBuilder builder = new StringBuilder();
            for (int index = className.length() - 1; index > 0; ) {
                int i = className.lastIndexOf('.', index);
                if (i == -1) {
                    if (builder.length() > 0
                            && builder.length() + index + 1 > maxLength) {
                        builder.insert(0, '*');
                        break;
                    }
                    builder.insert(0, className.substring(0, index + 1));
                } else {
                    if (builder.length() > 0
                            && builder.length() + (index + 1 - i) + 1 > maxLength) {
                        builder.insert(0, '*');
                        break;
                    }
                    builder.insert(0, className.substring(i, index + 1));
                }
                index = i - 1;
            }
            return builder.toString();
        } else {
            StringBuilder builder = new StringBuilder();
            for (int index = 0; index < className.length(); ) {
                int i = className.indexOf('.', index);
                if (i == -1) {
                    if (builder.length() > 0) {
                        builder.insert(builder.length(), '*');
                        break;
                    }
                    builder.insert(builder.length(), className.substring(index, className.length
                            ()));
                    break;
                } else {
                    if (builder.length() > 0
                            && i + 1 > maxLength) {
                        builder.insert(builder.length(), '*');
                        break;
                    }

                    builder.insert(builder.length(), className.substring(index, i + 1));
                }

                index = i + 1;
            }
            return builder.toString();
        }
    }

    /**
     * 缩短包名
     *
     * @param className
     * @param count
     * @return
     */
    private static String shortenPackagesName(String className, int count) {
        if (className == null) return null;
        if (count == 0) return className;
        StringBuilder builder = new StringBuilder();
        if (count > 0) {
            int points = 1;
            for (int index = 0; index < className.length(); ) {
                int i = className.indexOf('.', index);
                if (i == -1) {
                    builder.insert(builder.length(), className.substring(index, className.length
                            ()));
                    break;
                } else {
                    if (points == count) {
                        builder.insert(builder.length(), className.substring(index, i));
                        break;
                    }
                    builder.insert(builder.length(), className.substring(index, i + 1));
                }
                index = i + 1;
                points++;
            }
        } else if (count < 0) {
            String exceptString = shortenPackagesName(className, -count);
            if (className.equals(exceptString)) {
                int from = className.lastIndexOf('.') + 1;
                int to = className.length();
                builder.insert(builder.length(), className.substring(from, to));
            } else
                return className.replaceFirst(exceptString + '.', "");
        }
        return builder.toString();
    }
}
