import java.util.ArrayList;

public class MVT {
    Memory memory;
    ReadyQueue readyQueue;
    JobQueue jobQueue;
    int counter_ids = 1;
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public MVT() {
      memory = new Memory(1024-256);
      readyQueue = new ReadyQueue();
      jobQueue = new JobQueue();

        Intialiaze();
    }

    private void Intialiaze() {
        Process p = getRandomProcess();
        while (memory.loadProcess(p)) {
            readyQueue.AddProcess(p);
        p = getRandomProcess();
        }


        for (int i = 0; i < 12; i++)
            jobQueue.AddProcess(getRandomProcess());
    }

    private Process getRandomProcess() {
        int time  = (int) (Math.random() * (100 - 1)) + 1;
        int size = (int) (Math.random()*(500-50)) + 50;

        return new Process(counter_ids++,time,size);
    }

    public void Run() {
        while (true) {
            if (memory.NumberOfHoles()>3) {
                System.out.println(ANSI_RED_BACKGROUND + ANSI_BLUE + "DeFragging..");
                try {
                    memory.deFrag();
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                }
            memory.PrintAllocations();
            memory.PrintAllHoles();
            int t = ReadyQueue.Execute();
            try {
                Thread.sleep(t * 400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Process> toBeRemoved = readyQueue.RemoveProcesses();
            for (Process p : toBeRemoved) {
                System.out.println(ANSI_RESET+ANSI_RED +"Process #"+p.getId()+" terminated.");
                memory.TerminateProcess(p);
            }
            ArrayList<Process> toReady = new ArrayList<>();

            for (Process p : jobQueue.processList)
                if (memory.loadProcess(p)) {
                    System.out.println(ANSI_RESET+ANSI_GREEN+"Process #"+p.getId()+" loaded to Ready Queue.");
                    toReady.add(p);
                }

            for (Process p: toReady)
            {
                readyQueue.AddProcess(p);
                jobQueue.processList.remove(p);
                jobQueue.processList.add(getRandomProcess());
            }

        }
    }
}
