## CmGame_点击脚本开发日志

------------------------------------------20210506-----------------------------------------------------------------------------------

### 使用

	1. 配置好 ant 环境

	2. 在工程 根目录下 使用 ant clean

	3. 使用 ant build，会在 根目录下生成 bin目录，里面有 UiTestRead.jar

	4. UiTestRead.jar 就是目标文件

	5. 将 UiTestRead.jar 放入手机的  /data/local/tmp 目录  并赋予权限 777

	6. 执行 uiautomator runtest UiTestRead.jar -c com.skymobi.test.TestClass

### 问题

	1. Failure [INSTALL_FAILED_SHARED_USER_INCOMPATIBLE]

	这是由于使用了sharedUserId后，使用不同的签名造成的。