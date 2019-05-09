/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newimprovedroundrobin;

import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public class Process implements Comparable<Process>,Comparator<Process> {
    private int arrivalTime;
    private int burstTime;
    private int processNo;
    private int waitingTime;
    private int turnaroundTime;
    private int priority;
    private boolean finish=false; 
    
    public Process(){}
    
    public Process(int processNo, int arrivalTime, int burstTime, int priority){
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.processNo = processNo;
        this.priority = priority;
    }

    int getArrivalTime() {
        return arrivalTime;
    }

    int getProcessNo() {
        return processNo;
    }

    int getBurstTime() {
        return burstTime;
    }

    int getWaitingTime() {
        return waitingTime;
    }

    int getTurnaroundTime() {
        return turnaroundTime;
    }
    
    int getPriority(){
        return priority;
    }

    void setArrivalTime(int at) {
        arrivalTime = at;
    }

    void setBurstTime(int bt) {
        burstTime = bt;
    }

    void setWaitingTime(int wt) {
        waitingTime = wt;
    }

    void setTurnaroundTime(int tat) {
        turnaroundTime = tat;
    }

    void setFinish(boolean finish) {
        this.finish = finish;
    }
    
    @Override
    public int compare(Process p1, Process p2) {
       return p1.burstTime < p2.burstTime ? -1 : p1.burstTime == p2.burstTime ? 0 : 1;
    }
    @Override
    public int compareTo(Process p) {
        return(this.arrivalTime - p.arrivalTime);
    }
    
    @Override
    public String toString(){
        return "processNo is: " + this.processNo + "\t burstTime is: " + this.burstTime + "\t arrivalTime is: " + this.arrivalTime;
    }
    
    
}
