package stud.g01.problem.npuzzle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.*;

public class GUI extends javax.swing.JFrame {

    public static void createAndShowGUI() {

        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(false);

        // 创建及设置窗口
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加 "Hello World" 标签
//        JLabel label = new JLabel("Hello World");
//        frame.getContentPane().add(label);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);
    }

}
