package stud.g01.solver;

import java.util.Arrays;

public class DPDB {

    private int size;
    //不相交模式
    private int[][] pattern;
    private int[] factorials;
    //哈希
    private int[] indexMultiplier;
    //test
    public static void main(String[] args) {
        int[][] pattern = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};
        DPDB db = new DPDB(pattern);
        int[] state = {1, 5, 3, 4, 2, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 0};

        // 测试 h() 方法
        int h = db.h(state);
        System.out.println("h = " + h);

        // 测试 getIndices() 方法
        int[] indices = db.getIndices(state);
        System.out.println("indices = " + Arrays.toString(indices));

        // 测试 databaseIndex() 方法
        int index = db.databaseIndex(state);
        System.out.println("index = " + index);
    }

    //System.out.println("State: " + Arrays.toString(state));
    //System.out.println("Index: " + index);
    //System.out.println("Heuristic: " + h);
    /*
    int[][] pattern1 = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};不相交模式
    DPDB db1 = new DPDB(pattern1); 构建数据库
    int h1 = db1.h(state);
     */
    public DPDB(int[][] pattern) {
        this.size = pattern.length;
        this.pattern = pattern;
        this.factorials = new int[size + 1];
        this.indexMultiplier = new int[size];
        this.factorials[0] = 1;
        for (int i = 1; i <= size; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        for (int i = size - 1; i >= 0; i--) {
            indexMultiplier[size - 1 - i] = factorials[i];
        }
    }
//state = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12};初始状态格式
    //计算并返回状态 state 的估价函数值
    public int h(int[] state) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            System.out.println(state.length);
            sum += pattern[i][state[i]];
        }
        return sum;
    }
    //转换为其相对大小编号
    private int[] getIndices(int[] state) {
        int[] indices = new int[size];
        boolean[] used = new boolean[size];
        for (int i = 0; i < size; i++) {
            int val = state[i];
            int count = 0;
            for (int j = 0; j < val; j++) {
                if (!used[j]) {
                    count++;
                }
            }
            indices[i] = count;
            used[val] = true;
        }
        return indices;
    }
    //计算并返回状态 state 在数据库中索引值
    public int databaseIndex(int[] state) {
        int[] indices = getIndices(state);
        int index = 0;
        for (int i = 0; i < size; i++) {
            index += indices[i] * indexMultiplier[i];
        }
        return index;
    }

}
