public class Region implements Comparable<Region>{
    private int lower_limit;
    private int upper_limit;
    private int base;
    private int limit;

    private int size;
    private Process current_process;

    public Region(int size) {
        this.size = size;
    }
    public Region(int lower_limit, int upper_limit) {
        limit = upper_limit - lower_limit;
        base = lower_limit;
        this.upper_limit = upper_limit;
        this.lower_limit = lower_limit;
    }

    public int getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(int lower_limit) {
        this.lower_limit = lower_limit;
    }

    public int getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(int upper_limit) {
        this.upper_limit = upper_limit;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Process getCurrent_process() {
        return current_process;
    }

    public void setCurrent_process(Process current_process) {
        this.current_process = current_process;
    }

    @Override
    public int compareTo(Region o) {
        return this.limit-o.getLimit();
    }
}
