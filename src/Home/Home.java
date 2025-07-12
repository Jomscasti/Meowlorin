package Home;

import javax.swing.JFrame;

public class Home extends HomePanel {

    public Home(MainPanel mp, JFrame mainFrame) {
        super(mp, mainFrame);
    }

    public static void main(String[] args) {
        JFrame frm = new JFrame("Meowlorin");
        
        frm.setVisible(true);
        frm.setSize(800, 600);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MainPanel mp = new MainPanel(frm);
        frm.add(mp);
        frm.revalidate();
    }
}
