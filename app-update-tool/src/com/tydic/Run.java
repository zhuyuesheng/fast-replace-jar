package com.tydic;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import com.sun.deploy.util.JarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 *
 * 替换jar里面的文件
 * Created by zhuys
 * 2021-12-20.
 */
public class Run {

    private static String jarPath;

    private static String jarName;

    private static String logsPath;

    private static String logsFile;

    public static void main(String[] args){
        //赋值
        try{
            jarPath = args[0];
            jarName = args[1];
            logsPath= args[2];
            logsFile= args[3];
        }catch (Exception e){
            System.err.println("请输入args的参数 jar路径 jar名字 日志文件名称 日志文件名称" +
                    " \n,例如 java -jar app-update-zzjm.jar /home/user/ app-user /home/user/logs/ app-user.log ");
        }
        //读取配置文件
        System.out.println("读取配置文件");
        List<String> configs = new ArrayList<>();
        try{
            configs = FileUtil.readLines("config.csv","UTF-8");
        }catch (Exception e){
            System.err.println("读取配置文件失败");
            e.printStackTrace();
        }
        System.out.println("读取配置文件成功");
        //批量替换
        for (String configStr:configs) {
            try {
                configStr = configStr.replace("\"","");
                String[] configArray = configStr.split(",");
                String host = configArray[0];
                int port = Integer.valueOf(configArray[1]);
                String user = configArray[2];
                String pass = configArray[3];
                //新建会话,此会话用于ssh连接到跳板机（堡垒机）.
                System.out.println("新建会话:"+host);
                Session session = JschUtil.getSession(host,port,user,pass);
                //查找jar进程
                String res2 = JschUtil.exec(session,"ps -aux|grep "+jarName+"| grep -v grep | awk '{print $2}'", CharsetUtil.CHARSET_UTF_8);
                System.out.println("查找jar进程号为:"+res2);
                //如果有关闭
                if(res2 != null && !res2.trim().equals("")){
                    String res3 = JschUtil.exec(session,"kill -9 "+res2, CharsetUtil.CHARSET_UTF_8);
                    System.out.println("停止当前jar服务成功");
                }
                //创建sftp
                Sftp sftp = JschUtil.createSftp(session);
                String filePath = jarName+".jar";
                File file = FileUtil.newFile(filePath);
                FileUtil.mkParentDirs(file);
                if(file.exists()){
                    file.delete();
                }
                sftp.download(jarPath+jarName+".jar",file);
                sftp.upload(jarPath+jarName+""+ DateUtil.now()+".jar",file);
                System.out.println("备份"+jarName+".jar文件成功");
                System.out.println("下载"+jarName+".jar文件成功");
                RuntimeUtil.exec("WinRAR\\WinRAR.exe  a -o+ -m0 "+jarName+".jar BOOT-INF");
                System.out.println("替换压缩文件成功");
                Thread.sleep(10000);
                sftp.upload(jarPath+jarName+".jar",file);
                System.out.println("上传"+jarName+".jar文件成功");
                //进入自主建模目录
                String res3 = JschUtil.exec(session,"nohup java -jar "+jarPath+jarName+".jar " +
                        ">> "+logsPath+logsFile+" 2>&1 &", CharsetUtil.CHARSET_UTF_8);
                System.out.println(res3);
                System.out.println("重启"+jarName+"服务成功");
                session.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
