package stud.g01.solver;

import algs4.util.StopwatchCPU;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
public class DPDB {

    public static final byte[] costTable_15_puzzle_0 = new byte[4096],
            costTable_15_puzzle_1 = new byte[16777216],
            costTable_15_puzzle_2 = new byte[16777216];

    static {
        loadStreamCostTable("resources/database1.db", costTable_15_puzzle_0);
        loadStreamCostTable("resources/database2.db", costTable_15_puzzle_1);
        loadStreamCostTable("resources/database3.db", costTable_15_puzzle_2);
    }

    private DPDB() { }

    private static void loadStreamCostTable(final String filename,
                                            final byte[] costTable) {
        InputStream is = DPDB.class.getResourceAsStream(filename);
        DataInputStream dis = null;
        try {
            if (is == null) {
                is = new FileInputStream(filename);
            }
            dis = new DataInputStream(new BufferedInputStream(is));
            int i = 0;
            while (true) {
                costTable[i++] = dis.readByte();
            }
        } catch (final EOFException eofe) {

        } catch (final FileNotFoundException fnfe) {
            System.err.println("Error: Cannot find file " + filename + ".");
            System.exit(1);
        } catch (final IOException ioe) {
            System.err.println("Error: Cannot read from file " + filename + ".");
            System.exit(1);
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (final IOException ioe) { }
        }
    }
    //test only
    public static void main(String[] args) {
        // test loading cost tables
        byte[] costTable_15_puzzle_0 = DPDB.costTable_15_puzzle_0;
        byte[] costTable_15_puzzle_1 = DPDB.costTable_15_puzzle_1;
        byte[] costTable_15_puzzle_2 = DPDB.costTable_15_puzzle_2;

        System.out.println("Cost table for 15-puzzle-0 loaded: " + (costTable_15_puzzle_0.length == 4096));
        System.out.println("Cost table for 15-puzzle-1 loaded: " + (costTable_15_puzzle_1.length == 16777216));
        System.out.println("Cost table for 15-puzzle-2 loaded: " + (costTable_15_puzzle_2.length == 16777216));
        System.out.println("Database 1:");
        for (int i = 0; i < costTable_15_puzzle_0.length; i++) {
            System.out.print(costTable_15_puzzle_0[i] + " ");
            if ((i + 1) % 16 == 0) {
                System.out.println();
            }
        }

        System.out.println("\nDatabase 2:");
        for (int i = 0; i < costTable_15_puzzle_1.length; i++) {
            System.out.print(costTable_15_puzzle_1[i] + " ");
            if ((i + 1) % 256 == 0) {
                System.out.println();
            }
        }

        System.out.println("\nDatabase 3:");
        for (int i = 0; i < costTable_15_puzzle_2.length; i++) {
            System.out.print(costTable_15_puzzle_2[i] + " ");
            if ((i + 1) % 256 == 0) {
                System.out.println();
            }
        }
    }
}