*******一键替换启动 jar说明*******
``` bash
本工具旨在快速的替换jar包里面的文件
1.替换代码里面的class文件，静态资源文件，lib依赖包文件
2.本工具调用winRar命令进行替换,替换无损，正常运行
3.本工具替换规则为jar包中相同目录下的相同文件进行替换，jar包中没有目录，就进行添加
4.本工具能为分布式部署的相同jar包进行批量替换
5.本工具使用场景是为了无法到达部署现场又急于更新代码所使用的，放好资源位置，打包给现场的不会编码的人员一键替换启动
```

*******一键替换启动 jar使用*******

1.放入替换的文件
``` bash
BOOT-INF/classes目录下面和jar包里面相同的结构 
#例如
BOOT-INF/classes/com/tydic/common/test.class
```

2.修改一键替换启动.bat输入启动args的参数 
``` bash
jar路径 jar名字 日志文件名称 日志文件名称 
#例如
echo *******更新jar程序******
start java -jar app-update-tool.jar /home/user/ app-user /home/user/logs/ app-user.log
```

3.修改config.csv
``` bash
#输入linux服务器参数
IP1 端口号 用户名 密码
IP2 端口号 用户名 密码
#例如
192.168.128.2,22,root,123456
192.168.128.1,22,root,123456
```

4.备注说明
``` bash
#重新启动jar包为后台启动并且输出打印内容
#例如 
nohup java -jar  /home/user/app-user.jar >> /home/user/logs/app-user.log 2>&1 &
```


*******一键替换启动 jar展示*******
![image1](https://github.com/zhuyuesheng/vue-blog/blob/master/img-store/1.png)





