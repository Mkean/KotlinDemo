package com.mkean.demo.manager;

import android.app.Activity;
import android.text.TextUtils;

import java.util.Stack;

/**
 *
 */
public class SkipActivityLifeStack {

    private static SkipActivityLifeStack instance;
    //activity栈
    private Stack<Activity> activityStack = new Stack<>();

    private boolean isHomeActivityExist = false;

    /**
     * 单例模式
     *
     * @return 单例
     */

    public static SkipActivityLifeStack getInstance() {
        if (instance == null) {
            instance = new SkipActivityLifeStack();
        }
        return instance;
    }

    public int getActivitySize() {
        return activityStack != null ? activityStack.size() : 0;
    }

    /**
     * 把一个activity压入栈中
     *
     * @param activity activity
     */
    public void pushActivity(Activity activity) {
        activityStack.add(activity);
    }


    /**
     * 移除一个activity
     *
     * @param activity activity
     */
    public void popActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
            }
        }
    }

    /**
     * 判断Activity栈中是否包含 指定的 Activity
     *
     * @param activityName activity名称
     * @return
     */
    public boolean isContainActivity(String activityName) {
        if (!activityStack.isEmpty()) {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                Activity activity = activityStack.get(i);
                if (TextUtils.equals(activity.getClass().getSimpleName(), activityName)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 获取Activity栈中指定的 Activity
     *
     * @param activityName activity名称
     * @return
     */
    public Activity getActivity(String activityName) {
        Activity ac = null;
        if (!activityStack.isEmpty()) {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                Activity activity = activityStack.get(i);
                if (TextUtils.equals(activity.getClass().getSimpleName(), activityName)) {
                    ac = activity;
                    break;
                }
            }
        }
        return ac;
    }

    /**
     * 获取栈顶的activity，先进后出原则
     *
     * @return 栈顶的activity
     */
    public Activity getLastActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            return activityStack.lastElement();
        }
        return null;
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls 指定的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();

            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        try {
            for (int i = 0; i < activityStack.size(); i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isHomeActivityExist() {
        return isContainActivity("MainActivity");
    }

    public boolean isCourseDetailActivityExist() {
        return isContainActivity("CourseDetailsActivity");
    }
}
