#一键替换启动jar说明

#修改一键替换启动.bat输入启动args的参数 
jar路径 jar名字 日志文件名称 日志文件名称 
例如
echo *******更新jar程序******
start java -jar app-update-tool.jar /home/user/ app-user /home/user/logs/ app-user.log

#2.修改config.csv
输入linux服务器参数
IP1 端口号 用户名 密码
IP2 端口号 用户名 密码
例如
192.168.128.2,22,root,123456
192.168.128.1,22,root,123456

#备注
#重新启动jar包为后台启动并且输出打印内容
#例如 
nohup java -jar  /home/user/app-user.jar >> /home/user/logs/app-user.log 2>&1 &







