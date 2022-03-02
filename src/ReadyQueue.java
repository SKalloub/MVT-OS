import java.util.ArrayList;
import java.util.List;

public class ReadyQueue {
   public static List<Process> processList = new ArrayList<>();

    public static int Execute() {
        int min = Integer.MAX_VALUE;
        for(Process p: processList)
            if (p.getQ_Time()<min)
                min = p.getQ_Time();

            for (Process p: processList)
                p.setQ_Time(p.getQ_Time()-min);

        return min == Integer.MAX_VALUE ? 0: min;
    }

    public void AddProcess(Process p) {
    processList.add(p);
}
public ArrayList<Process> RemoveProcesses() {
   ArrayList<Process> toBeRemoved = new ArrayList<>();
    for(Process p: processList)

        if (p.getQ_Time()==0)
            toBeRemoved.add(p);

    for(Process p: toBeRemoved)
        processList.remove(p);

    return toBeRemoved;
    }



}
