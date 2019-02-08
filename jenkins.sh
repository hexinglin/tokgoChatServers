#!/bin/bash
mvn clean package
echo "mvn build ok"
#得到进程ID pid，kill该进程
function PidFind()
{
    PIDCOUNT=`ps -ef | grep $1 | grep -v "grep" | grep -v $0 | awk '{print $2}' | wc -l`;
    if [ ${PIDCOUNT} -gt 1 ] ; then
        echo "There are too many process contains name[$1]"
    elif [ ${PIDCOUNT} -le 0 ] ; then
        echo "No such process[$1]!"
    else
        PID=`ps -ef | grep $1 | grep -v "grep" | grep -v ".sh" | awk '{print $2}'` ;
        echo "Find the PID of this progress!--- process:$1 PID=[${PID}] ";

echo "Kill the process $1 ...";
        sudo kill $2  ${PID};
        echo "kill $2 ${PID} $1 done!";
    fi

}
PidFind tokgoChatServers-1.1.2-SNAPSHOT-jar-with-dependencies.jar
ps   -ef |grep  tokgoChatServers-1.1.2-SNAPSHOT-jar-with-dependencies.jar
netstat  -anp  |grep   1315
#执行jar，并将进程挂起，保存进程ID到 pid文件
echo "kill ok"
sleep 1s
nohup java -jar target/tokgoChatServers-1.1.2-SNAPSHOT-jar-with-dependencies.jar >/home/tokgo/qa/chatlog.txt  2>&1 &
echo "Execute shell Finish"
exit

