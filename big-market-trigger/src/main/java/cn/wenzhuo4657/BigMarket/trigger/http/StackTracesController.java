package cn.wenzhuo4657.BigMarket.trigger.http;

import java.util.Map;
import java.util.Set;

/**
 * @author: wenzhuo4657
 * @date: 2025/1/1
 * @description:
 */
public class StackTracesController {


    public void queryAllStack(){
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        if (allStackTraces!=null && allStackTraces.size()!=0){
            for (Thread threadSet:allStackTraces.keySet()){
                StackTraceElement[] stackTraceElements = allStackTraces.get(threadSet);
                if (threadSet.equals(Thread.currentThread())){
                    continue;//跳过当前线程
                }
                String name = threadSet.getName();
                String state = threadSet.getState().name();
                long stackTraceLength = stackTraceElements.length;


            }
        }


    }
}
