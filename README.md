#һ���滻����jar˵��

#�޸�һ���滻����.bat��������args�Ĳ��� 
jar·�� jar���� ��־�ļ����� ��־�ļ����� 
����
echo *******����jar����******
start java -jar app-update-tool.jar /home/user/ app-user /home/user/logs/ app-user.log

#2.�޸�config.csv
����linux����������
IP1 �˿ں� �û��� ����
IP2 �˿ں� �û��� ����
����
192.168.128.2,22,root,123456
192.168.128.1,22,root,123456

#��ע
#��������jar��Ϊ��̨�������������ӡ����
#���� 
nohup java -jar  /home/user/app-user.jar >> /home/user/logs/app-user.log 2>&1 &







