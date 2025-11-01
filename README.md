# Android-DebugSnippets

## 项目概述
Android-DebugSnippets 是一个轻量级的辅助 Android 调试的工具库，帮助开发者在开发阶段添加调试开关。

## 功能特性
- 提供全局调试模式开关
- 支持视图事件监控（如点击事件）
- 自动生成视图调试名称
- 内存安全设计，避免内存泄漏
- 模块化设计，易于集成

## 系统要求
- Android SDK 21+ (Android 5.0 Lollipop)
- Java 8

## 核心组件

### DebugApiView 接口
定义可调试视图的基本行为，包括获取调试名称和事件监听。

### DebugViewHelper 类
提供调试名称获取功能，使用视图标签或类名作为调试名称。负责创建装饰性点击监听器，处理事件分发。

### DebugTextView 类
继承自 TextView 并实现 DebugApiView 接口的可调试文本视图组件。自动集成事件监听功能。

### DebugFrameLayout 类
继承自 FrameLayout 并实现 DebugApiView 接口的可调试帧布局容器。自动集成事件监听功能。

### DebugViews 工具类
全局调试控制中心，提供调试模式开关和事件处理机制。采用弱引用管理监听器，避免内存泄漏。

## 使用方法

### 1. 初始化调试环境
在应用启动时配置调试环境：

```java
public class App extends Application {
    private final DebugApiView.DebugListener mDebugListener = (event, debugView, view) -> {
        if ("click".equals(event)) {
            Log.d("DebugViews", "click:" + debugView.getDebugName());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        // 开启调试模式
        DebugViews.setDebug(true);
        // 设置调试监听器
        DebugViews.setDebugListener(mDebugListener);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 清理资源
        DebugViews.destroy();
    }
}
```

### 2. 使用可调试视图
在布局文件中使用 DebugTextView 替代普通 TextView：

```xml
<io.github.xesam.android.debug.views.DebugTextView
    android:id="@+id/text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:tag="customDebugName" />
```

通过设置 android:tag 属性可以自定义调试名称，否则将使用视图的类名作为默认调试名称。

## 自定义其他视图

您可以轻松扩展库以支持其他视图类型。只需遵循以下步骤：

### 1. 创建自定义可调试视图

创建一个继承自目标视图并实现 DebugApiView 接口的新类：

```java
public class CustomDebugView extends YourTargetView implements DebugApiView {
    private DebugViewHelper mDebugViewHelper;

    public CustomDebugView(Context context) {
        super(context);
        init();
    }

    // 实现所有构造方法
    // ...

    private void init() {
        mDebugViewHelper = new DebugViewHelper(this);
        this.setOnClickListener(null);
    }

    @Override
    public String getDebugName() {
        return this.mDebugViewHelper.getDebugName();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(this.mDebugViewHelper.createDecoratedOnClickListener(l));
    }
}
```

### 2. 使用自定义可调试视图

在布局文件中使用您的自定义视图：

```xml
<your.package.CustomDebugView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:tag="customViewDebugName" />
```

通过遵循这个模式，您可以为任何 Android 视图创建可调试版本，使其能够集成到调试框架中，实现统一的事件监控和调试功能。

## 注意事项

1. **内存管理**
   - DebugViews 内部使用 WeakReference 引用监听器，确保不会因调试功能导致内存泄漏
   - 开发者需确保监听器对象有足够长的生命周期，避免被意外回收

2. **调试模式控制**
   - 建议仅在开发和测试阶段开启调试模式
   - 正式发布版本中应关闭调试功能

3. **生命周期管理**
   - 在应用终止时调用 DebugViews.destroy() 清理资源

## 许可证
本项目采用 MIT 许可证。