package stud.g19.problem.npuzzle;

public class GUI extends javax.swing.JFrame {
    private javax.swing.JPanel jPanel1;
    public GUI()
    {
        super("n-Puzzle");
        initComponents();
        //center the frame in the screen on open
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setAlwaysOnTop(true);

    }
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);




    }
}
