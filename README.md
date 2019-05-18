# DDNS-ByAliyunSDK
使用阿里云DNS SDK实现域名的动态更新。

技术说明：
1、使用 Maven 构建的 Springboot 应用；
2、使用阿里DNS SDK完成域名相关操作；

使用说明：
一、配置文件：
/resource/domain.properties

ipQueryServer=http://www.taobao.com/help/getip.php    #用户查询本机外网IP。
regionId=cn-shanghai    #区域ID,cn-shanghai,cn-hangzhou。
accessKeyId=Your Ali AccessKeyId    #阿里云个人accessKeyId。
accessKeySecret=Your Ali Access Key Secret    #阿里云个人accessKeySecret。
rootDomain=One of your root domain which wants to update record   #想要动态修改记录的根域名，暂时只能配置一个根域名。

二、启动方式
1、使用IDEA导入项目后，找到DDNSApplication,点击main方法启动；
2、使用mvn clean install 命令打包成jar后，使用以jar方式启动Springboot应用启动。
