package stud.g01.solver;
public class DPDB {

    private int size;
    //不相交模式
    private int[] pattern;
    private int[] factorials;
    //哈希
    private int[] indexMultiplier;

    public DPDB(int[] pattern) {
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
    //计算并返回状态 state 的估价函数值
    public int h(int[] state) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            int val = state[i];
            if (val != 0) {
                int index = findIndex(pattern, val);
                sum += Math.abs(index / size - i / size) + Math.abs(index % size - i % size);
            }
        }
        return sum;
    }

    private int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    //转换为其相对大小编号
    private int[] getIndices(int[] state) {
        int[] indices = new int[size];
        boolean[] used = new boolean[size];
        for (int i = 0; i < size; i++) {
            int val = state[i];
            if (val != 0) {
                int index = findIndex(pattern, val);
                indices[i] = index;
                used[index] = true;
            }
        }
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (!used[i]) {
                indices[count++] = i;
            }
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
    //test only
    public static void main(String[] args) {
        int[] pattern = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        DPDB dpdb = new DPDB(pattern);
        int[] state1 = { 0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println(dpdb.h(state1)); // 1
        System.out.println(dpdb.databaseIndex(state1)); // 7

    }
}