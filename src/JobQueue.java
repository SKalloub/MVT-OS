import java.util.ArrayList;
import java.util.List;

public class JobQueue {
    List<Process> processList = new ArrayList<>();
    public void AddProcess(Process p) {
        processList.add(p);
    }

}
