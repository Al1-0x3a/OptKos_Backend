/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
