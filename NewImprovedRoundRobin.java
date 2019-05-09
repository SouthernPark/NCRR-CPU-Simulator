
package newimprovedroundrobin;
import java.util.ArrayList;
import java.util.Arrays;
public class NewImprovedRoundRobin {
    public static ArrayList<Process> readyQueue = new ArrayList<Process>();
    public static ArrayList<Gchart> chart = new ArrayList<Gchart>();
    public static int TQ = 12;
    public static void main(String[] args) { 
        Process p1 = new Process(1,0,16,0);
        Process p2 = new Process(2,0,15,2);
        Process p3 = new Process(3,0,20,1);
        Process p4 = new Process(4,0,1,1);
        Process p5 = new Process(5,0,12,1);
        Process[] process = {p1, p2, p3, p4,p5};
        Arrays.sort(process);
        
        if(sameAT(process)){
            readyQueue.addAll(Arrays.asList(process));
            sameArrivalTime();
        }
        infoPrint(chart,process.length);
    }
    private static void sameArrivalTime(){
        setTQ();         
        ArrayList<Process> smallBT = findSmallBT();
        if(smallBT.size()>0){
            int time = 0;
            for(int i=0; i<= smallBT.size()-1;i++){
                chart.add( new Gchart(smallBT.get(i),smallBT.get(i).getBurstTime()) );
                readyQueue.remove(smallBT.get(i));
            }
            OnlyOneProcess();
        }        
        while(readyQueue.size() != 0){            
            Process pro = readyQueue.get(0);
            int priority = pro.getPriority();
            if(priority == 2){
                int pTQ = (int) (1.2*TQ);
                if(pro.getBurstTime() <= pTQ){
                    chart.add( new Gchart(pro,pro.getBurstTime()));
                    readyQueue.remove(0);
                    OnlyOneProcess();
                }
                else{
                    chart.add( new Gchart(pro,pTQ));
                    pro.setBurstTime(pro.getBurstTime() - pTQ);
                    readyQueue.add(pro);
                    readyQueue.remove(0); 
                }               
            }
            if(priority == 1){
                int pTQ = TQ;
                if(pro.getBurstTime() <= pTQ){
                    chart.add( new Gchart(pro,pro.getBurstTime()));
                    readyQueue.remove(0);
                    OnlyOneProcess();
                }
                else{
                    chart.add( new Gchart(pro,pTQ));
                    pro.setBurstTime(pro.getBurstTime() - pTQ);
                    readyQueue.add(pro);
                    readyQueue.remove(0);}                
            }
            if(priority == 0){
                int pTQ = (int) (0.8*TQ);
                if(pro.getBurstTime() <= pTQ){
                    chart.add( new Gchart(pro,pro.getBurstTime()));
                    readyQueue.remove(0);
                    OnlyOneProcess();
                }
                else{
                    chart.add( new Gchart(pro,pTQ));
                    pro.setBurstTime(pro.getBurstTime() - pTQ);
                    readyQueue.add(pro);
                    readyQueue.remove(0);}                
            }
        }
    }
    private static boolean sameAT(Process[] pro){
        int arrivalTime = pro[0].getArrivalTime();
        for(int i = 1; i<=pro.length-1;i++){
            if(arrivalTime != pro[i].getArrivalTime()){
                return false;
            }
        }
        return true;
    }
    private static void setTQ(){
        ArrayList<Process> copy = new ArrayList<Process> ();
        copy.addAll(readyQueue);
        copy.sort(new Process());
        int length = copy.size();
        int firstLargest = copy.get(length-1).getBurstTime();
        int secondLargest = copy.get(length-2).getBurstTime();
        TQ = (int) (0.8*(firstLargest + secondLargest)/2);
    }
    private static void OnlyOneProcess(){
        if(readyQueue.size() == 1){
            chart.add(new Gchart( readyQueue.get(0), readyQueue.get(0).getBurstTime() ));
            readyQueue.remove(0);
        }
    }
    
    private static ArrayList<Process> findSmallBT(){
        ArrayList<Process> smallBT = new ArrayList<Process>();
        for(Process p:readyQueue){
            if(p.getBurstTime() <= TQ/10){
                smallBT.add(p);
            }
        }
        return smallBT;
    }
    private static void infoPrint(ArrayList<Gchart> chart, int numberOfProcesses){
        for(Gchart c:chart){
            System.out.print(c);
        }
        System.out.println();
         System.out.println("The average waiting time(AWT) of those processes is:" + AWT(chart, numberOfProcesses));
         System.out.println("The average turn around time(AWT) of those processes is:" + ATA(chart, numberOfProcesses));
    }
    
    private static float ATA(ArrayList<Gchart> chart, int numberOfProcesses){
        ArrayList<Integer> list = new ArrayList<>();
        
        for(int i=1; i<= numberOfProcesses; i++){
            int first = firstLocation(chart,i);
            int last = lastLocation(chart,i);
            int turnAround = 0;
            for(int j = first;j<=last;j++){
                turnAround += chart.get(j).runTime;
            }
            list.add(turnAround);
        }
        float fl = 0;
        for(Integer i:list){
            fl += i;
        }
        fl /= numberOfProcesses;
        return fl;
    }
    private static float AWT(ArrayList<Gchart> chart, int numberOfProcesses){
        ArrayList<Integer> awt = new ArrayList<>();
        for(int i=1; i<=numberOfProcesses; i++){
            int waitingTime = 0;
            int index = lastLocation(chart,i);
            for(int j=0; j<=index; j++){
                if(chart.get(j).processNo != i){
                    waitingTime = waitingTime + chart.get(j).runTime;
                }
            }
            awt.add(waitingTime);
        }
        float averageWaitingTime = 0;
        for(Integer i:awt){
            averageWaitingTime +=i;
        }
        averageWaitingTime /= numberOfProcesses;
        return averageWaitingTime;
    }
    private static int firstLocation(ArrayList<Gchart> chart, int number){
        for(int i=0; i<chart.size();i++){
            if(chart.get(i).processNo==number){
                return i;
            }
        }
        return -1;
    }
    private static int lastLocation(ArrayList<Gchart> chart, int number){
        for(int i = chart.size()-1; i>=0 ;i--){
            if(chart.get(i).processNo == number){
                return i;
            }
        }
        return -1;
    }  
}

class Gchart{
    int processNo;
    int runTime;
    public Gchart(Process p,int runTime){
        this.processNo = p.getProcessNo();
        this.runTime = runTime;
    }
    @Override
    public String toString(){
        return "|" + runTime + "(" + processNo + ")" + "|";
    }   
}


