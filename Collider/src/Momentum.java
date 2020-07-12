import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.MenuBarUI;
import javax.swing.plaf.metal.MetalMenuBarUI;
import javax.swing.plaf.multi.MultiMenuItemUI;

class Draw extends JPanel {

	// Object 1
	public int x1 = 35;
	public int y1 = 500;
	public int v1 = 0;

	// Object 2
	public int x2 = 500;
	public int y2 = 500;
	public int v2 = 0;

	// Stops
	public static boolean stop1 = false;
	public static boolean stop2 = false;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);

		if (stop1 == false) {
			g.fill3DRect(x1 += v1, y1, 50, 50, true);
		} else {
			g.fill3DRect(x1, y1, 50, 50, true);
		}

		if (stop2 == false) {
			g.fill3DRect(x2 += v2, y2, 50, 50, true);
		} else {
			g.fill3DRect(x2, y2, 50, 50, true);
		}

		g.setColor(Color.getHSBColor(100, 50, 500));
		g.fillRoundRect(5, 550, 575, 25, 10, 10);
	}
}

public class Momentum implements ActionListener {

	private static JFrame frame;

	// All the sliders...
	private static JSlider massslider1;
	private static JSlider velocityslider1;
	private static JSlider massslider2;
	private static JSlider velocityslider2;

	private static JButton start;
	private static Draw draw = new Draw();

	private Timer once = null;
	private int timercounter = 0;

	public Momentum() {
		// Initializing the frame...

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame = new JFrame();

		// Label to indicate that the selection is for Mass (m1)
		JLabel massm1 = new JLabel("Mass (m1)");
		massm1.setBounds(95, 40, 60, 25);
		frame.add(massm1);

		// First Mass (m1)
		massslider1 = new JSlider(JSlider.HORIZONTAL);
		massslider1.setBounds(20, 70, 200, 50);
		massslider1.setMajorTickSpacing(10);
		massslider1.setMinorTickSpacing(1);
		massslider1.setPaintTicks(true);
		massslider1.setPaintLabels(true);
		massslider1.setToolTipText("Mass (m1)");
		frame.add(massslider1);

		// Label to indicate that the selection is for Velocity (v1)
		JLabel velocityv1 = new JLabel("Velocity (v1)");
		velocityv1.setBounds(370, 40, 65, 25);
		frame.add(velocityv1);

		// First Velocity (v1)
		velocityslider1 = new JSlider(JSlider.HORIZONTAL, -50, 50, 0);
		velocityslider1.setBounds(300, 70, 200, 50);
		velocityslider1.setMajorTickSpacing(10);
		velocityslider1.setMinorTickSpacing(1);
		velocityslider1.setPaintTicks(true);
		velocityslider1.setPaintLabels(true);
		velocityslider1.setToolTipText("Velocity (v1)");
		frame.add(velocityslider1);

		// Label to indicate that the selection is for Second Mass (m2)
		JLabel massm2 = new JLabel("Mass (m2)");
		massm2.setBounds(95, 140, 60, 25);
		frame.add(massm2);

		// Second Mass (m2)
		massslider2 = new JSlider(JSlider.HORIZONTAL);
		massslider2.setBounds(20, 170, 200, 50);
		massslider2.setMajorTickSpacing(10);
		massslider2.setMinorTickSpacing(1);
		massslider2.setPaintTicks(true);
		massslider2.setPaintLabels(true);
		massslider2.setToolTipText("Sets the mass for m2");
		frame.add(massslider2);

		// Label to indicate that the selection is for Second Velocity (v2)
		JLabel velocityv2 = new JLabel("Velocity (v2)");
		velocityv2.setBounds(370, 140, 65, 25);
		frame.add(velocityv2);

		// Second Velocity (v2)
		velocityslider2 = new JSlider(JSlider.HORIZONTAL, -50, 50, 0);
		velocityslider2.setBounds(300, 170, 200, 50);
		velocityslider2.setMajorTickSpacing(10);
		velocityslider2.setMinorTickSpacing(1);
		velocityslider2.setPaintTicks(true);
		velocityslider2.setPaintLabels(true);
		velocityslider2.setToolTipText("Velocity (v2)");
		frame.add(velocityslider2);

		// Start button (and it changes to Stop when you click it!)
		start = new JButton("Start");
		start.setBounds(225, 250, 75, 25);
		start.addActionListener(this);
		frame.add(start);

		// TextArea for recording initial & final mass and velocity
		// INITIAL VALUES -- (next line) -- m1: v1: -- (next line) -- m2: v2: -- then
		// display the final values on the next line
		JTextArea record = new JTextArea();
		record.setEditable(false);
		record.setLineWrap(true);
		record.setWrapStyleWord(true);

		JScrollPane scroller = new JScrollPane(record);
		scroller.setBounds(25, 300, 540, 150);
		frame.add(scroller);

		// MenuBar and Menu Stuff...
		JMenuBar menubar = new JMenuBar();
		menubar.setBounds(0, 0, 600, 25);

		JMenu file = new JMenu("File");
		menubar.add(file);
		frame.add(menubar);

		JMenuItem save = new JMenuItem("Save");
		file.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);

		JMenu game = new JMenu("Game");
		menubar.add(game);

		JMenuItem changetexture = new JMenuItem("Change Texture");
		game.add(changetexture);

		JMenuItem cleartextarea = new JMenuItem("Clear TextArea");
		game.add(cleartextarea);

		JMenuItem masscolor = new JMenuItem("Mass Color");
		game.add(masscolor);

		JMenuItem reset = new JMenuItem("Reset");
		game.add(reset);

		JMenu help = new JMenu("Help");
		menubar.add(help);

		JMenuItem about = new JMenuItem("About");
		help.add(about);

		// Painting all the graphics stuff...

		frame.add(draw);

		// Finalizing stuff...
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Momentum");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new Momentum();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {

			if (velocityslider1.getValue() > 0) {
				draw.v1 = (velocityslider1.getValue() + 10) / 10;
			} else if (velocityslider1.getValue() < 0) {
				draw.v1 = (velocityslider1.getValue() - 10) / 10;
			}
			
			
			if (velocityslider2.getValue() > 0) {
				draw.v2 = (velocityslider2.getValue() + 10) / 10;
			} else if (velocityslider2.getValue() < 0) {
				draw.v2 = (velocityslider2.getValue() - 10) / 10;
			}

			

			ActionListener doit = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (!((draw.x1 > 10 && draw.x1 < 530))) {
						Draw.stop1 = true;
					}

					if (!((draw.x2 > 10 && draw.x2 < 530))) {
						Draw.stop2 = true;
					}

					if (timercounter == 0) {
						if (velocityslider1.getValue() + draw.x1 >= 10 &&  draw.x1 +  velocityslider1.getValue() <= 530) {
							Draw.stop2 = false;
						}
						if (velocityslider2.getValue() + draw.x2 >= 10 && draw.x2 + velocityslider1.getValue() <= 530) {
							Draw.stop2 = false;
						}
						timercounter++;
					}

					draw.repaint(draw.x1 - 5, draw.y1 - 5, 60, 60);
					draw.repaint(draw.x2 - 5, draw.y2 - 5, 60, 60);

					if (Draw.stop1 == true && Draw.stop2 == true) {
						once.stop();
						start.setText("Start");
						Draw.stop1 = false;
						Draw.stop2 = false;
						timercounter = 0;
					}

				}

			};

			if (start.getText().contentEquals("Start")) {
				once = new Timer(10, doit);
				once.setRepeats(true);
				once.start();
				start.setText("Stop");

			} else if (start.getText().contentEquals("Stop")) {
				once.stop();
				start.setText("Start");
				timercounter = 0;
			}

		}
	}

}
