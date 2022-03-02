public class Process {
    private int id;
    private int Q_Time;
    private int size;

    public Process(int id, int Q_Time, int size) {
        this.id = id;
        this.Q_Time = Q_Time;
        this.size = size;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQ_Time() {
        return Q_Time;
    }

    public void setQ_Time(int q_Time) {
        Q_Time = q_Time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
