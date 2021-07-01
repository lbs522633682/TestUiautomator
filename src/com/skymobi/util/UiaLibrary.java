package com.skymobi.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import junit.framework.Assert;
import android.text.TextUtils;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class UiaLibrary extends UiAutomatorTestCase {

    public String screenshotDir = "/sdcard/auitest/";

    public String PACKAGE = "com.veryfit2hr.second";
    public String ID_PRE = PACKAGE + ":id/";
    public static final int CLICK_DELAY = 300;
    public static final int LAUNCH_TIMEOUT = 5000;
    public static final int NET_WORK_DELAY = 2000;
    public static final int MAIN_ANIMTOR_DELAY = 3000;
    public static final int DIALOG_dURATION = 600;
    public static final int CLICK_NOW = 100;
    public String dirName;
    public final static String TAG = "UI_TEST_";

    //在屏幕上滑动
    public void swipeLeft() {//左滑
        int y = UiDevice.getInstance().getDisplayHeight();
        int x = UiDevice.getInstance().getDisplayWidth();
        UiDevice.getInstance().swipe(x - 100, y / 2, 100, y / 2, 20);
        sleep(150);
    }

    public void swipeRight() {//右滑
        int y = UiDevice.getInstance().getDisplayHeight();
        int x = UiDevice.getInstance().getDisplayWidth();
        UiDevice.getInstance().swipe(100, y / 2, x - 100, y / 2, 20);
        sleep(150);
    }

    public void swipeDown() {//下滑
        int y = UiDevice.getInstance().getDisplayHeight();
        int x = UiDevice.getInstance().getDisplayWidth();
        UiDevice.getInstance().swipe(x / 2, 200, x / 2, y - 200, 20);
        sleep(150);
    }

    public void swipeUp() {//上滑
        int y = UiDevice.getInstance().getDisplayHeight();
        int x = UiDevice.getInstance().getDisplayWidth();
        UiDevice.getInstance().swipe(x / 2, y - 200, x / 2, 200, 20);
        sleep(150);
    }

    public void swipUpLittle() {//上滑一点点
        int x = UiDevice.getInstance().getDisplayWidth() / 2;
        int y = UiDevice.getInstance().getDisplayHeight() / 2;
        UiDevice.getInstance().swipe(x, y + 150, x, y - 150, 20);
        sleep(150);
    }

    public void swipDownLittle() {//下拉一点点
        int x = UiDevice.getInstance().getDisplayWidth() / 2;
        int y = UiDevice.getInstance().getDisplayHeight() / 2;
        UiDevice.getInstance().swipe(x, y - 150, x, y + 150, 20);
        sleep(150);
    }

    public String getNow() {//获取当前时间
        Date time = new Date();
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = now.format(time);
        return c;
    }

    public String screenShotByDir(String name) {//截图并命名
        return screenShot(name, dirName);
    }

    /**
     * 如果dirName存在，则截图在dirName目标下，否则截图在screenshotDir目标下
     *
     * @param name
     * @return
     */
    public String screenShot(String name) {//截图并命名
        LogUtil.d2(TAG + name);
        if (!TextUtils.isEmpty(dirName)) {
            return screenShotByDir(name);
        }
        return screenShot(name, null);
    }

    public String screenShot(String name, String dir) {//截图并命名
        File file;
        if (TextUtils.isEmpty(dir)) {
            file = new File(screenshotDir);
        } else {
            file = new File(screenshotDir + dir);
        }

        if (!file.exists()) {
            file.mkdirs();
        }
        File files = new File(file, name + ".png");
        if (files.exists()) {
            files.delete();
        }
        UiDevice.getInstance().takeScreenshot(files);
        output(name + ".png 截图成功！");
        String path = files.getAbsolutePath();
        return path;
    }

    //通过文本获取控件
    public UiObject getUiObjectByText(String text) {
        return new UiObject(new UiSelector().text(text));
    }

    //通过文本获取控件
    public UiObject getUiObjectByTextContains(String text) {
        return new UiObject(new UiSelector().textContains(text));
    }

    //通过id获取控件
    public UiObject getOjectByResourceId(String resourceId) {
        UiObject uiObject = new UiObject(new UiSelector().resourceId(ID_PRE + resourceId));
        if (!uiObject.exists()) {
            output("resourceId cannot find [" + ID_PRE + resourceId + "]");
        }

        return uiObject;
    }

    public UiObject getUiObject(String res) {

        return getUiObject(res, true);
    }

    public UiObject getUiObject(String res, boolean isAssertExist) {
        UiObject oneButton = getUiObjectByTextOrDescription(res);
        if (!oneButton.exists()) {
            output("getUiObjectByTextOrDescription cannot find [" + res + "]");
            oneButton = getOjectByResourceId(res);
        }
        if (isAssertExist) {
            Assert.assertEquals(oneButton.exists(), true);
        }
        return oneButton;
    }

    public UiObject getChild(String parent, String child) {
        UiObject gridview = getUiObject(parent);
        UiObject uiObject = null;
        try {
            uiObject = gridview.getChild(new UiSelector().text(child));
            if (!uiObject.exists()) {
                uiObject = gridview.getChild(new UiSelector().description(child));
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return uiObject;
    }

    public UiObject getUiObjectWaitIfExist(String res, int waitTime) {
        UiObject oneButton = getUiObjectByTextOrDescription(res);
        if (!oneButton.exists()) {
            oneButton = getOjectByResourceId(res);
        }
        if (!oneButton.exists()) {
            oneButton.waitForExists(waitTime);
        }
        return oneButton;
    }

    public UiObject getUiObjectByTextWaitIfExist(String res, int waitTime) {
        UiObject oneButton = getUiObjectByText(res);
        if (!oneButton.exists()) {
            oneButton.waitForExists(waitTime);
        }
        return oneButton;
    }

    public void swipeLeft(String resourceId, int step) {
        UiObject uiObject = getUiObject(resourceId);
        try {
            output("[" + resourceId + "]" + "isScrollable:" + uiObject.isScrollable());
            uiObject.swipeLeft(step);
        } catch (UiObjectNotFoundException e) {

            e.printStackTrace();
        }
        sleep(CLICK_DELAY);
    }

    //通过text开始文字查找控件
    public UiObject getUiObjectByStartText(String text) {
        return new UiObject(new UiSelector().textStartsWith(text));
    }

    public UiObject getUiObjectByTextClassName(String text, String classname) {//通过文本和类名获取控件
        return new UiObject(new UiSelector().text(text).className(classname));
    }

    public UiObject getUiObjectByTextResourceId(String text, String id) {//通过文本和id获取对象
        return new UiObject(new UiSelector().text(text).resourceId(id));
    }

    public UiObject getUiObjectByResourceIdClassName(String id, String type) {
        return new UiObject(new UiSelector().resourceId(id).className(type));
    }

    public UiObject getUiObjectByTextOrDescription(String text) {
        UiObject uiObject = new UiObject(new UiSelector().text(text));
        boolean textExist = uiObject.exists();
        if (!textExist) {
            uiObject = new UiObject(new UiSelector().description(text));
        }
        return uiObject;
    }

    public UiObject getUiObjectByResourceId(String id) {//通过资源ID获取控件
        return getOjectByResourceId(id);
    }

    public UiObject getUiObjectByDesc(String desc) {//通过desc获取控件
        return new UiObject(new UiSelector().description(desc));
    }

    public UiObject getUiObjectByStartDescContains(String desc) {
        return new UiObject(new UiSelector().descriptionContains(desc));
    }

    public UiObject getUiObjectByDescContains(String desc) {
        return new UiObject(new UiSelector().descriptionContains(desc));
    }

    public UiObject getUiObjectByClassName(String type) {//通过classname获取控件
        return new UiObject(new UiSelector().className(type));
    }

    public UiObject getUiObjectByResourceIdIntance(String id, int instance) {//通过id和instance获取控件
        return new UiObject(new UiSelector().resourceId(id).instance(instance));
    }

    //长按控件
    public void longclickUiObectByResourceId(String id) throws UiObjectNotFoundException {
        int x = getUiObjectByResourceId(id).getBounds().centerX();
        int y = getUiObjectByResourceId(id).getBounds().centerY();
        UiDevice.getInstance().swipe(x, y, x, y, 300);//最后一个参数单位是5ms
    }

    public void longclickUiObectByDesc(String desc) throws UiObjectNotFoundException {
        int x = getUiObjectByDesc(desc).getBounds().centerX();
        int y = getUiObjectByDesc(desc).getBounds().centerY();
        UiDevice.getInstance().swipe(x, y, x, y, 300);//最后一个参数单位是5ms
    }

    public void longclickUiObectByText(String text) throws UiObjectNotFoundException {
        int x = getUiObjectByText(text).getBounds().centerX();
        int y = getUiObjectByText(text).getBounds().centerY();
        UiDevice.getInstance().swipe(x, y, x, y, 300);//最后一个参数单位是5ms
    }

    //点击中心
    public void clickCenter() {
        int x = UiDevice.getInstance().getDisplayWidth();
        int y = UiDevice.getInstance().getDisplayHeight();
        clickPiont(x / 2, y / 2);
    }

    public void writeText(String text) throws UiObjectNotFoundException {//输入文字
        //      getUiObjectByClassName("android.widget.EditText").setText(Utf7ImeHelper.e(text));
    }

    public UiScrollable getUiScrollabe() {//获取滚动控件
        return new UiScrollable(new UiSelector().scrollable(true));
    }

    public UiScrollable getUiScrollableByResourceId(String id) {//获取滚动对象
        return new UiScrollable(new UiSelector().scrollable(true).resourceId(id));
    }

    public void getChildByTextOfUiScrollableByClassName(String type, String text) throws UiObjectNotFoundException {
        getScrollableByClassName(type).getChildByText(new UiSelector().text(text), text).clickAndWaitForNewWindow();
    }

    public UiObject getUiObjectByResourIdIndex(String id, int index) {//通过ID和index获取控件
        return new UiObject(new UiSelector().resourceId(id).index(index));
    }

    public void randomClickOpiton() throws UiObjectNotFoundException {
        int num = getUiObjectByClassName("android.widget.ListView").getChildCount();
        int i = new Random().nextInt(num);
        getUiObjectByResourceIdIntance("com.gaotu100.superclass:id/simpleitemview_left_text", i).clickAndWaitForNewWindow();
    }

    public void outputBegin(String text) {//输出开始
        LogUtil.d2(TAG + text + "..-. ...- 测试开始！");
    }

    public void outputNow() {//输出当前时间
        System.out.println(getNow());
    }

    public void outputOver(String text) {//输出结束
        LogUtil.d2(TAG + text + "..-. ...- 测试结束！");
    }

    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //明显输出
    public void output(String text) {
        writeLog(text);
        LogUtil.d2(TAG + text);
    }

    //明显输出
    public void outputStart(int index, String text) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TAG);
        for (int i = 1; i < index; i++) {
            stringBuffer.append(" ");
        }
        for (int i = 0; i < 10 - index; i++) {
            stringBuffer.append("*");
        }
        stringBuffer.append(text);
        stringBuffer.append("开始");
        for (int i = 0; i < 10 - index; i++) {
            stringBuffer.append("*");
        }
        writeLog(stringBuffer.toString());
        LogUtil.d2(stringBuffer.toString());
    }

    private static boolean writeStringToFile(String path, String data) {
        boolean isSuccess = true;
        File file = new File(path);
        if (!file.exists()) {
            try {
                isSuccess = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                isSuccess = false;
            }
        }
        if (isSuccess) {
            try {
                FileWriter writer = new FileWriter(path, true);
                writer.write(data);
                writer.write("\n");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                isSuccess = false;
            }
        }

        return isSuccess;
    }

    public void writeLog(String log) {
        log += "[" + myLogSdf.format(new Date()) + "]" + log;
        writeStringToFile(new File(screenshotDir, "ui_result.txt").getAbsolutePath(), log);
    }

    void deleteLogFile() {
        new File(screenshotDir, "ui_result.txt").delete();
    }

    //明显输出
    public void outputEnd(int index, String text) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TAG);
        for (int i = 1; i < index; i++) {
            stringBuffer.append(" ");
        }
        for (int i = 0; i < 5 - index; i++) {
            stringBuffer.append("*");
        }
        stringBuffer.append(text);
        stringBuffer.append("结束");
        writeLog(stringBuffer.toString());
        LogUtil.d2(stringBuffer.toString());
    }


    public void output(int... num) {//方法重载
        for (int i = 0; i < num.length; i++) {
            LogUtil.d2("第" + (i + 1) + "个：" + num[i]);
        }
    }

    public void output(Object... object) {
        for (int i = 0; i < object.length; i++) {
            LogUtil.d2("第" + (i + 1) + "个：" + object[i]);
        }
    }

    public void output(Object object) {
        LogUtil.d2(TAG + object.toString());
    }

    public void pressTimes(int keyCode, int times) {//对于一个按键按多次
        for (int i = 0; i < times; i++) {
            sleep(200);
            UiDevice.getInstance().pressKeyCode(keyCode);
        }
    }

    public void pressTimesDelay(int keyCode, int times, long delayTime) {//对于一个按键按多次
        System.out.println(TAG + ": pressTimesDelay keyCode = " + keyCode + ", times = " + times + ", delayTime = "+ delayTime);
        PLog.i(TAG + ": pressTimesDelay keyCode = " + keyCode + ", times = " + times + ", delayTime = "+ delayTime);
        if (times == 1) {
            sleep(delayTime);
            UiDevice.getInstance().pressKeyCode(keyCode);
            return;
        }

        for (int i = 0; i < times; i++) {
            sleep(delayTime);
            UiDevice.getInstance().pressKeyCode(keyCode);
        }
    }

    public void waitForUiObjectByStartText(String text) {
        getUiObjectByStartText(text).waitForExists(10000);
    }

    //输出时间差
    public void outputTimeDiffer(Date start, Date end) {
        long time = end.getTime() - start.getTime();
        double differ = (double) time / 1000;
        output("总计用时" + differ + "秒！");
    }

    //获取子控件点击
    public void getScrollChildByText(String text) throws UiObjectNotFoundException {
        UiObject child = getUiScrollabe().getChildByText(new UiSelector().text(text), text);
        child.clickAndWaitForNewWindow();
    }

    //通过classname获取滚动控件
    public UiScrollable getScrollableByClassName(String type) {
        return new UiScrollable(new UiSelector().scrollable(true).className(type));
    }

    public void waitForUiObjectByClassName(String type) throws UiObjectNotFoundException {//等待控件出现
        getUiObjectByClassName(type).waitForExists(10000);
    }

    public String getTextByResourceId(String id) throws UiObjectNotFoundException {
        return getUiObjectByResourceId(id).getText();
    }

    public String getDescByResourceI1d(String id) throws UiObjectNotFoundException {
        return getUiObjectByResourceId(id).getContentDescription();
    }

    public String getTextByResourceIdClassName(String id, String type) throws UiObjectNotFoundException {
        return getUiObjectByResourceIdClassName(id, type).getText();
    }

    //获取兄弟控件的文本
    public String getTextByBrother(String myid, String brotherid) throws UiObjectNotFoundException {
        return getUiObjectByResourceId(myid).getFromParent(new UiSelector().resourceId(brotherid)).getText();
    }

    public void clickPiont(int x, int y) {//点击某一个点
        UiDevice.getInstance().click(x, y);
    }

    //等待文本控件并点击
    public void waitForClassNameAndClick(String type) throws UiObjectNotFoundException {
        waitForUiObjectByClassName(type);
        getUiObjectByClassName(type).clickAndWaitForNewWindow();
    }

//        public void waitForTextAndClick(String text) throws UiObjectNotFoundException {
//            waitForUiObjectByText(text);
//    //      getUiObjectByText(text).waitForExists(10000);
//            getUiObjectByText(text).clickAndWaitForNewWindow();
//        }


    //向前滚动
    public boolean scrollForward() throws UiObjectNotFoundException {
        return getUiScrollabe().scrollForward(50);
    }

    //向后滚动
    public boolean scrollBackward() throws UiObjectNotFoundException {
        return getUiScrollabe().scrollBackward(50);
    }

    public void deleteScreenShot() {//删除截图文件夹
        File file = new File("/mnt/sdcard/123/");
        if (file.exists()) {//如果file存在
            File[] files = file.listFiles();//获取文件夹下文件列表
            for (int i = 0; i < files.length; i++) {//遍历删除
                files[i].delete();
            }
            file.delete();//最后删除文件夹，如果不存在直接删除文件夹
        } else {
            output("文件夹不存在！");
        }

    }

    public void pressBack() {
        UiDevice.getInstance().pressBack();
    }

    public String formatTime(long time) {
        return String.format("%02dmin %02ds", time / 1000 / 60, time / 1000 % 60);
    }

}
