package data_models;

public class WorktimeStatisticModel {
    private int[] worktime;
    private int[] actualWorktime;

    public WorktimeStatisticModel(long[][] result){
        worktime = new int[result.length];
        actualWorktime= new int[result.length];
        for (int i = 0; i<result.length; i++){
            worktime[i] = (int)result[i][0] ;
            actualWorktime[i] = (int)result[i][1] ;
        }
    }

    public WorktimeStatisticModel() {
    }

    public int[] getWorktime() {
        return worktime;
    }

    public void setWorktime(int[] worktime) {
        this.worktime = worktime;
    }

    public int[] getActualWorktime() {
        return actualWorktime;
    }

    public void setActualWorktime(int[] actualWorktime) {
        this.actualWorktime = actualWorktime;
    }
}
