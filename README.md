# ViseLog

[![Author](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-%E8%83%A1%E4%BC%9F-blue.svg)](http://www.huwei.tech/) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/08746e00437e4a3d8150da3656cdca65)](https://www.codacy.com/app/xiaoyaoyou1212/ViseLog?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=xiaoyaoyou1212/ViseLog&amp;utm_campaign=Badge_Grade) [![License](https://img.shields.io/badge/License-Apache--2.0-green.svg)](https://github.com/xiaoyaoyou1212/ViseLog/blob/master/LICENSE) [![JCenter](https://img.shields.io/badge/JCenter-1.1.2-orange.svg)](https://jcenter.bintray.com/com/vise/xiaoyaoyou/viselog/1.1.2/) [![API](https://img.shields.io/badge/API-8%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=8)

日志系统，使用森林对象维护不同的日志树进行日志输出，可以是Logcat树、文件树等，支持Bundle、Intent、Reference、Throwable、Map、Collection、JSON、Xml等格式化的输出。


项目引用：`compile 'com.vise.xiaoyaoyou:viselog:1.1.2'`

### QQ交流群
![QQ群](https://github.com/xiaoyaoyou1212/XSnow/blob/master/screenshot/qq_chat_first.png)
(此群已满)

![QQ群](https://github.com/xiaoyaoyou1212/XSnow/blob/master/screenshot/qq_chat_second.png)


### 版本说明
- V1.1.2
    - 修复文件打印树中文件命名问题。

- V1.1.0
    - 增加控制台打印树以及文件输出树，并将DefaultTree名称改为LogcatTree。
    - 注：文件输出树是依据不同的打印级别来存放文件的，如Info级别每小时新建一个文件存储，Error级别则是只要产生就创建一个新文件，其他具体存储规则可参考代码。每个文件都会包含手机的基本信息，还有在创建文件树时需要传入Context以及文件存放路径名称。

- V1.0.0
    - 项目初始提交。

版本号说明：版本号第一位为大版本更新时使用，第二位为小功能更新时使用，第三位则是用来bug修复管理。

## 功能介绍
1、在Logcat中完美的格式化输出，能很好的过滤手机和其他App的日志信息；

2、包含线程、类、方法信息，可以清楚地看到日志记录的调用堆栈；

3、支持跳转到源码处；

4、支持格式化输出JSON、XML格式信息；

5、支持List、Set、Map和数组的格式化输出；

6、支持系统对象如Bundle、Intent、Reference和Throwable的格式化输出；

7、支持自定义对象的格式化输出；

8、支持字符串格式化后输出；

9、支持自定义对象解析器；

10、支持自定义日志输出树，如输出到文件的树等。

## 使用介绍
使用前需要进行日志的配置初始化及日志树的添加，默认实现了打印到Logcat的日志树，但需要在应用启动时进行添加，这样才能将日志信息打印到Logcat中。一般需要在自定义Application的OnCreate方法中进行如下配置：
```
ViseLog.getLogConfig()
	.configAllowLog(true)//是否输出日志
    .configShowBorders(true)//是否排版显示
    .configTagPrefix("ViseLog")//设置标签前缀
    .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
    .configLevel(Log.VERBOSE)；//设置日志最小输出级别，默认Log.VERBOSE
ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
```

1、打印基本信息
```
ViseLog.d("test message");
```
![这里写图片描述](http://img.blog.csdn.net/20161212120920265?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

2、打印基本对象
```
ViseLog.d(new Boolean(true));
```
![这里写图片描述](http://img.blog.csdn.net/20161212120936582?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

3、打印Bundle对象
```
ViseLog.d(new Bundle());
```
![这里写图片描述](http://img.blog.csdn.net/20161212120736440?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

4、打印Intent对象
```
ViseLog.d(new Intent());
```
![这里写图片描述](http://img.blog.csdn.net/20161212120818003?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

5、打印Reference对象
```
ViseLog.d(new SoftReference(0));
```
![这里写图片描述](http://img.blog.csdn.net/20161212121002176?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

6、打印Throwable对象
```
ViseLog.e(new NullPointerException("this object is null!"));
```
![这里写图片描述](http://img.blog.csdn.net/20161212121030673?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

7、打印List集合
```
List<String> list = new ArrayList<>();
for (int i = 0; i < 5; i++) {
    list.add("test" + i);
}
ViseLog.d(list);
```
![这里写图片描述](http://img.blog.csdn.net/20161212120800675?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

8、打印Map集合
```
Map<String, String> map = new HashMap<>();
for (int i = 0; i < 5; i++) {
    map.put("xyy" + i, "test" + i);
}
ViseLog.d(map);
```
![这里写图片描述](http://img.blog.csdn.net/20161212120901816?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

9、打印JSON字符串
```
String json = "{'xyy1':[{'test1':'test1'},{'test2':'test2'}],'xyy2':{'test3':'test3','test4':'test4'}}";
ViseLog.json(json);
```
![这里写图片描述](http://img.blog.csdn.net/20161212120844863?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

10、打印XML字符串
```
String xml = "<xyy><test1><test2>key</test2></test1><test3>name</test3><test4>value</test4></xyy>";
ViseLog.xml(xml);
```
![这里写图片描述](http://img.blog.csdn.net/20161212121050532?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGlhb3lhb3lvdTEyMTI=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 核心功能讲解
1、森林结构
其核心思想是将日志系统看成一个森林对象统一进行维护，森林可以添加树和移除树，只有添加了指定的功能树才会有日志输出。日志功能由ITree接口进行定义，统一由主干树SoulsTree来分配，并将日志输出功能提供给上层实现，默认已经实现了日志打印到Logcat的树DefaultTree。

2、自定义解析器
实现Parser接口，并实现parseClassType()和parseString()方法，再通过addParserClass()配置到ViseLog就行，详细可参考文档[自定义对象打印器](https://github.com/pengwei1024/LogUtils/blob/master/doc/custom_parser.md)；

3、个性标签设置详解

|变量|简写|描述|参数|示例|输出结果|
|---|---|---|---|---|--------|
|%%|无|转义%|无|%%d|%d|
|%date|%d|当前时间|格式化时间,如HH:mm:ss|%d{HH:mm:ss:SSS}|22:12:12:112|
|%thread|%t|当前线程名称|无|%t|thread-112|
|%caller|%c|线程信息和类路径|一般用%c{-5}就好了，用法为%c{整数}或者%caller{整数}，整数为包名路径，如路径为com.vise.logapp.MainActivity.onCreate(MainActivity.java:108),%c{1}输出com，以.分割的第一个,如果小于0就是排除前面n个，如%c{-1},vise.logapp.MainActivity.onCreate(MainActivity.java:135)|%c{-3}|MainActivity.onCreate(MainActivity.java:108)|

4、日志配置详解

| 方法 | 描述 | 取值 | 缺省 |
| --- | ---- | --- | --- |
| configAllowLog | 是否允许日志输出 | boolean | true |
| configTagPrefix | 日志log的前缀 | String | "ViseLog" |
| configShowBorders | 是否显示边界 | boolean | false |
| configLevel | 日志显示最小等级 | Log | Log.VERBOSE |
| addParserClass | 自定义对象打印 | Parser | 无 |
| configFormatTag | 个性化设置Tag | String | %c{-5} |

## 感谢
1、[https://github.com/orhanobut/logger](https://github.com/orhanobut/logger)

2、[https://github.com/pengwei1024/LogUtils](https://github.com/pengwei1024/LogUtils)

3、[https://github.com/JakeWharton/timber](https://github.com/JakeWharton/timber)

### 关于作者
#### 作者：胡伟
#### 网站：[http://www.huwei.tech](http://www.huwei.tech)
#### 博客：[http://blog.csdn.net/xiaoyaoyou1212](http://blog.csdn.net/xiaoyaoyou1212)
