package stud.g19.solver;


public class test {
    //"D:/15-puzzle-663-0.db";
    // ±¾µØÍ¼Æ¬Â·¾¶
    public static void main(String[] args){
        int index0 = 0, index1 = 0, index2 = 0;
        for (int pos = 15; pos >= 0; --pos) {
            int[] tmp={1, 1, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
            int[] tilePositions = {-1, 0, 0, 1, 2, 1, 2, 0, 1, 3, 4, 2, 3, 5, 4, 5};
            int[] tileSubsets = {-1, 1, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
            final int tile = tmp[pos];
            if (tile != 0) {
                final int subsetNumber = tileSubsets[tile];
                switch (subsetNumber) {
                    case 2:
                        index2 |= pos << (tilePositions[tile] << 2);
                        System.out.println("index2"+index2);
                        break;
                    case 1:
                        index1 |= pos << (tilePositions[tile] << 2);
                        System.out.println("index1"+index1);
                        break;
                    default:
                        index0 |= pos << (tilePositions[tile] << 2);
                        System.out.println("index0"+index0);
                        break;
                }
            }
        }
    }




}
