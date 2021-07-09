
public class ThreadDemo extends Thread {
   private String threadName;
   PrintDemo PD;

   ThreadDemo( String name,  PrintDemo pd) {
      threadName = name;
      PD = pd;
   }
   
   public void run() {
	   // Synchronize the threads!
   synchronized(PD)
	   {
         PD.printCount(threadName);
       }
      System.out.println("Thread " +  threadName + " exiting.");
   }

}