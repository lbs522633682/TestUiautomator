package com.skymobi.test;

import android.view.KeyEvent;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.skymobi.bean.ClickBean;
import com.skymobi.bean.ClickEvent;
import com.skymobi.bean.TaskStatus;
import com.skymobi.helper.UiAutoHelper;
import com.skymobi.util.*;

import java.util.List;

public class TestClass extends UiAutomatorTestCase {

    public static final String TAG = "TaskProcessor";

    public void testClick() {
        try {

            PLog.i("start script 20210524 1520");

            ThreadUtil.sleep(10000);
            // 输入键盘事件，Keycode
            UiaLibrary uiaLibrary = new UiaLibrary();
            uiaLibrary.pressTimes(KeyEvent.KEYCODE_ESCAPE, 1);

            // 查找页面元素，进行点击
            UiObject uiObj = new UiObject(new UiSelector().resourceId("com.wyapps.jsmobile.lrts:id/btn_mine"));
            if (uiObj.exists()) {
                uiObj.click();
            }


            // 滑动页面
            /*ThreadUtil.sleep(5000);
            int y = UiDevice.getInstance().getDisplayHeight();
            int x = UiDevice.getInstance().getDisplayWidth();
            System.out.println(TAG + ": swipeUp from (" + x + "," + y + ")");
            UiDevice.getInstance().swipe(x / 2, y - 200, x / 2, 200, 20);*/

            System.out.println(TAG + " click task success");
        } catch (Exception e) {
            System.out.println(TAG + " click task fail:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 账号密码登录
     *
     * @param clickBean
     */
    private void executeAccountLogin(ClickBean clickBean) throws UiObjectNotFoundException {
        PLog.i("executeAccountLogin start");
        List<ClickEvent> clickEvents = clickBean.getAccountLoginEvents();
        UiAutoHelper.baseExecuteScript(clickEvents);

        waitAndSetTextById("com.cmgame.gamehalltv:id/home_user_edt", clickBean.getAccount());

        UiaLibrary uiaLibrary = new UiaLibrary();

        uiaLibrary.pressTimes(KeyEvent.KEYCODE_DPAD_DOWN, 1);

        waitAndSetTextById("com.cmgame.gamehalltv:id/home_paswd_edt", clickBean.getPwd());

        uiaLibrary.pressTimes(KeyEvent.KEYCODE_DPAD_DOWN, 1);

        uiaLibrary.pressTimes(KeyEvent.KEYCODE_DPAD_CENTER, 1);

        ThreadUtil.sleep(2000);

        //uiaLibrary.screenShot(System.currentTimeMillis() + "");

        // 判断是否是 vip

        boolean isVip = UiAutoHelper.checkIsVip();

        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setVip(isVip);
        clickBean.setRuntimeStatus(JsonUtil.toJSON(taskStatus));

        PLog.i("executeAccountLogin end isVip = " + isVip);
        System.out.println(TAG + " : executeAccountLogin end isVip = " + isVip);
    }


    /**
     * 通过id查找控件，并设置文本
     *
     * @param resId
     * @param text
     * @throws UiObjectNotFoundException
     */
    private void waitAndSetTextById(String resId, String text) throws UiObjectNotFoundException {
        //long wt = 10 * 1000L;
        System.out.println(TAG + ": wait for the jump button text = " + text);
        System.out.println(TAG + ": wait for the jump button resId:" + resId);
        ThreadUtil.sleep(200);


        UiObject uiObj = new UiObject(new UiSelector().resourceId(resId));

        String getText = uiObj.getText();
        System.out.println(TAG + ": waitAndSetTextById getText = :" + getText);
        uiObj.setText(text);
        //System.out.println(TAG + ": action-1 click skip!");

    }

    /**
     * 执行 注册 账号密码流程
     *
     * @param clickBean
     */
    private void executeRegister(ClickBean clickBean) {
        PLog.i("executeRegister enter");
        List<ClickEvent> clickEvents = clickBean.getRegisterEvents();
        UiAutoHelper.baseExecuteScript(clickEvents);
        PLog.i("executeRegister end");
    }

    /**
     * 执行 验证码登录流程
     *
     * @param clickBean
     */
    private void executeLogin(ClickBean clickBean) {
        PLog.i("executeLogin enter");
        List<ClickEvent> clickEvents = clickBean.getLoginEvents();
        UiAutoHelper.baseExecuteScript(clickEvents);
        PLog.i("executeLogin end");
    }

    /**
     * 查找id 进行点击跳转
     *
     * @param text
     * @param delayTime 单位毫秒
     * @throws UiObjectNotFoundException
     */
    public void waitAndClickJumpByText(String text, long delayTime) throws UiObjectNotFoundException {
        //long wt = 10 * 1000L;
        System.out.println(TAG + ": wait for the jump button text = " + text);
        System.out.println(TAG + ": wait for the jump button for:" + delayTime);
        ThreadUtil.sleep(delayTime);


        UiObject uiObj = new UiObject(new UiSelector().text(text));
        uiObj.click();
        System.out.println(TAG + ": action-1 click skip!");
    }

    /**
     * 查找id 进行点击跳转
     *
     * @param resId
     * @param delayTime 单位毫秒
     * @throws UiObjectNotFoundException
     */
    public void waitAndClickJumpById(String resId, long delayTime) throws UiObjectNotFoundException {
        //long wt = 10 * 1000L;
        System.out.println(TAG + ": wait for the jump button id = " + resId);
        System.out.println(TAG + ": wait for the jump button for:" + delayTime);
        ThreadUtil.sleep(delayTime);


        UiObject uiObj = new UiObject(new UiSelector().resourceId(resId));
        uiObj.click();
        System.out.println(TAG + ": action-1 click skip!");
    }

    public void waitAndClickJump() throws UiObjectNotFoundException {
        long wt = 10 * 1000L;
        System.out.println(TAG + ": wait for the jump button for:" + wt);
        ThreadUtil.sleep(wt);


        UiObject uiObj = new UiObject(new UiSelector().resourceId("com.cmgame.gamehall.mainpage:id/llMine"));
        uiObj.click();
        System.out.println(TAG + ": action-1 click skip!");
    }

    public void swipeUpAndCloseAdv() {//上滑
        long wt = 15 * 1000L;
        System.out.println(TAG + ": wait at the adv page for swipe:" + wt);
        ThreadUtil.sleep(wt);

        int y = UiDevice.getInstance().getDisplayHeight();
        int x = UiDevice.getInstance().getDisplayWidth();

        System.out.println(TAG + ": swipeUp from (" + x + "," + y + ")");

        UiDevice.getInstance().swipe(x / 2, y - 200, x / 2, 200, 20);

        wt = 500L;
        System.out.println(TAG + ": wait for close adv:" + wt);
        ThreadUtil.sleep(wt);

        System.out.println(TAG + ": close adv (" + x + "," + y + ")");
        UiDevice.getInstance().click(x / 2, 1000);

    }


    public void swipeUpAndClickFreeHot() throws UiObjectNotFoundException {
        long wt = 5 * 1000L;
        System.out.println(TAG + ": 1802 wait for wipe to free hot:" + wt);
        ThreadUtil.sleep(wt);

        int h = UiDevice.getInstance().getDisplayHeight();
        int w = UiDevice.getInstance().getDisplayWidth();

        int x = w / 2;
        int y = h - 200;

        int endx = x;
        int endy = h - 480;
        System.out.println(TAG + ": swipe to free hot (" + x + "," + y + ") to (" + endx + "," + endy + ")");
        UiDevice.getInstance().swipe(x, y, endx, endy, 20);

        clickFreeHotByScrren();
    }

    public void clickFreeHotByScrren() throws UiObjectNotFoundException {
//        int viewIndex = 22;
        long wt = 100L;
        System.out.println(TAG + ": wait for click hot book:" + wt);
        ThreadUtil.sleep(wt);

        //
        int h = UiDevice.getInstance().getDisplayHeight();
        int w = UiDevice.getInstance().getDisplayWidth();

        int x = 60;
        int y = 800;
        System.out.println(TAG + ": click at ord (" + x + "," + y + ")");

        UiDevice.getInstance().click(x, y);


    }

    //not id, and the ui may change. So it's abnormal.
    public void clickFreeHotByUiObj() throws UiObjectNotFoundException {
        int viewIndex = 22;
        long wt = 5 * 1000L;
        System.out.println(TAG + ": wait for click hot book:" + wt);
        ThreadUtil.sleep(wt);


        UiObject uiObj = new UiObject(new UiSelector().resourceId("com.ophone.reader.ui:id/base_web_page_layout"));
        System.out.println(TAG + ": base_web_page_layout, obj:" + uiObj);
        System.out.println(TAG + ": base_web_page_layout, class name:" + uiObj.getClassName());
        System.out.println(TAG + ": base_web_page_layout, child count:" + uiObj.getChildCount());
        System.out.println(TAG + ": base_web_page_layout, bound:" + uiObj.getBounds());

        //
        UiObject c1 = uiObj.getChild(new UiSelector().index(0));
        System.out.println(TAG + ": c-1, obj:" + c1);
        System.out.println(TAG + ": c-1, class name:" + c1.getClassName());
        System.out.println(TAG + ": c-1, child count:" + c1.getChildCount());
        System.out.println(TAG + ": c-1, bound:" + c1.getBounds());

        //
        UiObject c10 = c1.getChild(new UiSelector().index(0));
        System.out.println(TAG + ": c10, obj:" + c10);
//		System.out.println(TAG + ": c10, class name:" + c10.getClassName());		
//		System.out.println(TAG + ": c10, child count:" + c10.getChildCount());		
//		System.out.println(TAG + ": c10, bound:" + c10.getBounds()); 


        UiObject c11 = c1.getChild(new UiSelector().index(1));
        System.out.println(TAG + ": c11, obj:" + c11);
        System.out.println(TAG + ": c11, class name:" + c11.getClassName());
        System.out.println(TAG + ": c11, child count:" + c11.getChildCount());
        System.out.println(TAG + ": c11, bound:" + c11.getBounds());

        //
        UiObject c110 = c11.getChild(new UiSelector().index(0));
        System.out.println(TAG + ": c110, obj:" + c110);
//		System.out.println(TAG + ": c110, class name:" + c110.getClassName());		
//		System.out.println(TAG + ": c110, child count:" + c110.getChildCount());		
//		System.out.println(TAG + ": c110, bound:" + c110.getBounds()); 


        UiObject c111 = c11.getChild(new UiSelector().index(1));
        System.out.println(TAG + ": c111, obj:" + c111);
        System.out.println(TAG + ": c111, class name:" + c111.getClassName());
        System.out.println(TAG + ": c111, child count:" + c111.getChildCount());
        System.out.println(TAG + ": c111, bound:" + c111.getBounds());


        UiObject index22Obj = c111.getChild(new UiSelector().index(22));
        System.out.println(TAG + ": index22Obj, obj:" + index22Obj);
        System.out.println(TAG + ": index22Obj, class name:" + index22Obj.getClassName());
        System.out.println(TAG + ": index22Obj, child count:" + index22Obj.getChildCount());
        System.out.println(TAG + ": index22Obj, bound:" + index22Obj.getBounds());

        UiObject targetObj = index22Obj.getChild(new UiSelector().index(0));
        System.out.println(TAG + ": targetObj, obj:" + targetObj);
        System.out.println(TAG + ": targetObj, class name:" + targetObj.getClassName());
        System.out.println(TAG + ": targetObj, child count:" + targetObj.getChildCount());
        System.out.println(TAG + ": targetObj, bound:" + targetObj.getBounds());

        targetObj.click();
    }


    //should wait for more time for web page display.
    public void swipeUpBookDetail() {//上滑
        long wt = 17 * 1000L;
        System.out.println(TAG + ": swipeUpBookDetail wait for swipe:" + wt);
        ThreadUtil.sleep(wt);

        int h = UiDevice.getInstance().getDisplayHeight();
        int w = UiDevice.getInstance().getDisplayWidth();

        int x = w / 2;
        int y = h - 200;

        int endx = x;
        int endy = 200;
        System.out.println(TAG + ": swipe from (" + x + "," + y + ") to (" + endx + "," + endy + ")");

        UiDevice.getInstance().swipe(x, y, endx, endy, 20);

        wt = 3 * 1000L;
        System.out.println(TAG + ": swipeUpBookDetail wait for swipe:" + wt);
        ThreadUtil.sleep(wt);

        System.out.println(TAG + ": swipe from (" + endx + "," + endy + ") to (" + x + "," + y + ")");

        UiDevice.getInstance().swipe(endx, endy, x, y, 20);

    }

    public void clickTryRead() {
        long wt = 2 * 1000L;

        System.out.println(TAG + ": clickTryRead wait for click tryread:" + wt);
        ThreadUtil.sleep(wt);

        //
        int h = UiDevice.getInstance().getDisplayHeight();
        int w = UiDevice.getInstance().getDisplayWidth();

        int x = 720;
        int y = 1100;
        System.out.println(TAG + ": clickTryRead click at ord (" + x + "," + y + ")");

        UiDevice.getInstance().click(x, y);
    }

    public void clickCancelIntroduce() throws UiObjectNotFoundException {
        long wt = 1 * 1000L;

        System.out.println(TAG + ": clickCancelIntroduce:" + wt);
        ThreadUtil.sleep(wt);

        //
        int h = UiDevice.getInstance().getDisplayHeight();
        int w = UiDevice.getInstance().getDisplayWidth();

        int x = w / 2;
        int y = h / 2;
        System.out.println(TAG + ": clickCancelIntroduce click at ord (" + x + "," + y + ")");

        UiDevice.getInstance().click(x, y);


    }

    public void clickNextPage(long wt) throws UiObjectNotFoundException {


        System.out.println(TAG + ": clickNextPage wait for click next page:" + wt);
        ThreadUtil.sleep(wt);


        UiObject uiObj = new UiObject(new UiSelector().resourceId("com.ophone.reader.ui:id/elastic_inner_container"));
        if (!uiObj.exists()) {
            throw new UiObjectNotFoundException("not found book container");
        }

        //
        int h = UiDevice.getInstance().getDisplayHeight();
        int w = UiDevice.getInstance().getDisplayWidth();

        int x = w / 16 * 14;
        int y = h / 2;
        System.out.println(TAG + ": clickNextPage click at ord (" + x + "," + y + ")");

        UiDevice.getInstance().click(x, y);

    }


    public void clickBackKeyQuitFree() throws UiObjectNotFoundException {


        long wt = 16 * 1000L;

        System.out.println(TAG + ": clickBackKeyQuitFree wait for:" + wt);
        ThreadUtil.sleep(wt);

        System.out.println(TAG + ": clickBackKeyQuitFree pressBack");
        UiDevice.getInstance().pressBack();


        wt = 1 * 1000L;

        System.out.println(TAG + ": clickBackKeyQuitFree 2148 wait click OK to quit for:" + wt);
        ThreadUtil.sleep(wt);

        UiObject uiObj = new UiObject(new UiSelector().resourceId("com.ophone.reader.ui:id/button_cancel"));
        if (!uiObj.exists()) {
            throw new UiObjectNotFoundException("not found book container");
        }

        uiObj.click();

//        int x = 200;
//        int y = 700;
//		System.out.println(TAG + ": clickBackKeyQuitFree click at ord (" + x +"," + y +")");	
//		
//        UiDevice.getInstance().click(x, y);

    }


    public void Quit() throws UiObjectNotFoundException {

        long wt = 5 * 1000L;
        System.out.println(TAG + ": Quit wait click back 1 for:" + wt);
        ThreadUtil.sleep(wt);
        UiDevice.getInstance().pressBack();


        wt = 2 * 1000L;
        System.out.println(TAG + ": Quit wait click back 2 for:" + wt);
        ThreadUtil.sleep(wt);
        UiDevice.getInstance().pressBack();


        wt = 2 * 1000L;
        System.out.println(TAG + ": Quit wait click back 3 for:" + wt);
        ThreadUtil.sleep(wt);
        UiDevice.getInstance().pressBack();


        wt = 100L;
        System.out.println(TAG + ": Quit wait click back 4 for:" + wt);
        ThreadUtil.sleep(wt);
        UiDevice.getInstance().pressBack();


        wt = 7 * 1000L;
        System.out.println(TAG + ": wait for flushing log  for:" + wt);
        ThreadUtil.sleep(wt);

    }


}


