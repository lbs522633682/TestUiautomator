package com.skymobi.helper;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.skymobi.bean.ClickEvent;
import com.skymobi.test.TestClass;
import com.skymobi.util.FileUtils;
import com.skymobi.util.PLog;
import com.skymobi.util.ThreadUtil;
import com.skymobi.util.UiaLibrary;

import java.io.File;
import java.util.List;

/**
 * @author Boshuai.li
 * @version 20210507
 * @desc 模拟点击工具类
 */
public class UiAutoHelper {

    public static final String CONFIG_PATH = "/mnt/sdcard/MiguGameTaskInfo.txt";

    /**
     * 修改过的 json 重新写入文件
     *
     * @param json
     */
    public static void write2Config(String json) {
        PLog.i("write2Config json = " + json);
        FileUtils.writeFileFromString(CONFIG_PATH, json, false);
    }

    public static String readConfig() {
        System.out.println(TestClass.TAG + ": readConfig enter");
        String content = null;
        try {
            File file = new File(CONFIG_PATH);
            System.out.println(TestClass.TAG + "： file.exits = " + file.exists());
            if (file.exists() && file.isFile()) {
                System.out.println(TestClass.TAG + "： file.exits333 = " + file.exists());
                byte[] bytes = FileUtils.readFile(file);
                System.out.println(TestClass.TAG + "： bytes.length  = " + bytes.length);
                content = new String(bytes);
                PLog.i("readConfig content = " + content);
                System.out.println(TestClass.TAG + ": content = " + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 查找id 进行点击跳转
     *
     * @param text
     * @param delayTime 单位毫秒
     * @throws UiObjectNotFoundException
     */
    public static void waitAndClickJumpByText(String text, long delayTime) throws UiObjectNotFoundException {
        PLog.i("waitAndClickJumpByText text = " + text + ", delayTime = " + delayTime);
        ThreadUtil.sleep(delayTime);
        UiObject uiObj = new UiObject(new UiSelector().text(text));
        uiObj.click();
        PLog.i("waitAndClickJumpByText end");
    }

    /**
     * 查找id 进行点击跳转
     *
     * @param resId
     * @param delayTime 单位毫秒
     * @throws UiObjectNotFoundException
     */
    public static void waitAndClickJumpById(String resId, long delayTime) throws UiObjectNotFoundException {
        //long wt = 10 * 1000L;
        PLog.i("waitAndClickJumpById resId = " + resId + ", delayTime = " + delayTime);

        ThreadUtil.sleep(delayTime);


        UiObject uiObj = new UiObject(new UiSelector().resourceId(resId));
        uiObj.click();
        PLog.i("waitAndClickJumpById end");
    }

    public static void baseExecuteScript(List<ClickEvent> clickEvents) {
        UiaLibrary uiaLibrary = new UiaLibrary();
        if (clickEvents != null && clickEvents.size() > 0) {
            for (int i = 0; i < clickEvents.size(); i++) {
                ClickEvent clickEvent = clickEvents.get(i);
                if (clickEvent.getDelayTime() != 0) {
                    uiaLibrary.pressTimesDelay(clickEvent.getKeyCode(), clickEvent.getTimes(), clickEvent.getDelayTime());
                } else {
                    uiaLibrary.pressTimes(clickEvent.getKeyCode(), clickEvent.getTimes());
                }
            }
        }
    }

    /**
     * 检查是否是vip
     *
     * @return
     */
    public static boolean checkIsVip() {

        ThreadUtil.sleep(2000);


        UiObject uiObj = new UiObject(new UiSelector().resourceId("com.cmgame.gamehall.mainpage:id/tv_member"));

        String getText = null;
        try {
            getText = uiObj.getText();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        PLog.i("checkIsVip getText = " + getText);
        return "尊贵的咪咕快游会员".equals(getText);
    }
}
