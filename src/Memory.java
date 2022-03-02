import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memory {
    static final int MAX_DEGREE_MP = 8;
    List<Region> allocations = new ArrayList<>();
    List<Region> holes = new ArrayList<>();
    final int SIZE;
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

    private int indexes[];
    public Memory(int size) {
        SIZE = size;
        holes.add(new Region(0,SIZE));
    }

    public void deFrag() {
        Region r = new Region(0,SIZE);

        holes.removeAll(holes);
        holes.add(r);
        Region[] regions = new Region[allocations.size()];
        int i =0;
        for (Region r2 : allocations) {
          regions[i++] = r2;
        }
        for(Region r2: regions) {
         allocations.remove(r2);
            loadProcess(r2.getCurrent_process());
        }
    }
    public int NumberOfHoles() {
        return holes.size();
    }

    public boolean loadProcess(Process process) {
        for (Region r: holes) {
            if (r.getLimit()>=process.getSize())
            {
                holes.remove(r);

                Region r2 = new Region(r.getLower_limit(),r.getLower_limit()+process.getSize());
                r2.setCurrent_process(process);
                allocations.add(r2);
                r.setLimit(r.getLimit()-process.getSize());
                r.setLower_limit(r.getUpper_limit()-r.getLimit());
                r.setBase(r.getLower_limit());

                if (r.getLimit()!=0) {
                    holes.add(r);
                    Collections.sort(holes);
                }

                return true;
            }

        }
        return false;
    }

    public void PrintAllocations() {
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK+"Allocations: ");
        for (Region r : allocations)
            System.out.println(ANSI_GREEN+ANSI_BLACK_BACKGROUND+"Region base: " + r.getBase() + " | Region upper limit: " + r.getUpper_limit() + " | Region Limit: " + r.getLimit() + " | Process ID: " + r.getCurrent_process().getId());
    }

    public void PrintAllHoles() {
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK+"Holes: ");
        for (Region r: holes) {
            System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_BLACK+"Region base: "+ r.getBase()+" | Region upper limit: "+r.getUpper_limit()+" | Region Limit: "+ r.getLimit()+" | Process ID: "+r.getCurrent_process());
        }
    }
    public void TerminateProcess(Process process) {
        for (Region r: allocations){
            if (r.getCurrent_process() == process)
            {
                r.setCurrent_process(null);
                holes.add(r);
                allocations.remove(r);
                Collections.sort(holes);
                Combine(holes);
                break;
            }
        }
    }

    private void Combine(List<Region> holes) {
        for (int i =0;i<holes.size();i++) {
            for (int j = 0;j<holes.size();j++) {
                if (holes.get(i).getUpper_limit() == holes.get(j).getLower_limit()) {
                    Region r1 = holes.get(i);
                    Region r2 = holes.get(j);
                    holes.remove(r1);
                    holes.remove(r2);
                    Region r = new Region(r1.getLower_limit(), r2.getUpper_limit());
                    holes.add(r);
                    Collections.sort(holes);
                    return;
                }
            }
        }
    }
}
