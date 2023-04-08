package stud.g01.problem.npuzzle;

import java.util.EnumMap;
import java.util.List;

public enum Direction {
    L,
    R,
    U,
    D;
    public static final List<Direction> DIRECTIONS = List.of(Direction.L, Direction.R, Direction.U, Direction.D);
    private static final EnumMap<Direction, int[]> DIRECTION_OFFSET = new EnumMap<>(Direction.class);
    static{
        //列号（或横坐标）增加量；行号（或纵坐标）增加量

        DIRECTION_OFFSET.put(L, new int[]{0, -1});

        DIRECTION_OFFSET.put(R, new int[]{0, 1});

        DIRECTION_OFFSET.put(U, new int[]{-1, 0});

        DIRECTION_OFFSET.put(D, new int[]{1, 0});
    }
    public static int cost(Direction dir){
        return 1;
    }
    // 交换一次花费1


    public static int[] offset(Direction dir){
        return DIRECTION_OFFSET.get(dir);
    }
}
