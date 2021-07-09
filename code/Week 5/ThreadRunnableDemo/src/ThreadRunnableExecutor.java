
public class ThreadRunnableExecutor
{

	public static void main(String[] args)
	{	
	   RunnableDemo R1 = new RunnableDemo( "Thread-1");
	   Thread t1 = new Thread (R1);
	   t1.start();
	      
	    RunnableDemo R2 = new RunnableDemo( "Thread-2");
	    Thread t2 = new Thread (R2);
	    t2.start();	    
	  // wait for threads to end
      try
      {
      	Thread.sleep(250);
      	t1.interrupt(); // comment and uncomment and see how interuption works
		t2.interrupt();
		t1.join();
        t2.join();
        System.out.println("Main Thread exiting.");
      }
      
      
      catch( Exception e)
      {
         System.out.println("Interrupted");
      }
	}
	 
}
