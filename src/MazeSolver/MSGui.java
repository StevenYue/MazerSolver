package MazeSolver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

public class MSGui extends JFrame{
	private static final long serialVersionUID = -8500176414851165949L;
	JToggleButton[] allToggleButtons;
	JButton leftWFButton;
	JButton randomWFButton;
	JButton clearButton;
	JButton loadButton;
	JButton saveButton;
	JTextField fileToLoadTextArea;
	JTextField fileToSaveTextArea;
	JPanel midPanel;
	JPanel lowPanel;
	JPanel highPanel;
	JScrollPane rightPanel;
	JEditorPane solutionInfo;
	Toolkit tk;
	MSEventHandler handler;
	MSDataManager data;
	MouseEvent me;
	int mouseX;
	int mouseY;
	
	public MSGui(){
		super("Maze Solver Algorithm Test");
		setLocation(200, 200);
//		setPreferredSize(new Dimension(420, 240));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tk = Toolkit.getDefaultToolkit();
//		Image frameIcon = tk.getImage("E:\\Java_project\\CSE219\\juve.jpg");
//		setIconImage(frameIcon);
		handler = new MSEventHandler(this);
		data = new MSDataManager(this);
		initComponents();
		myLayout();		
	}
	public void initComponents(){
		fileToLoadTextArea = new JTextField("");
		fileToLoadTextArea.setPreferredSize(new Dimension(50, 25));
		fileToSaveTextArea = new JTextField("");
		fileToSaveTextArea.setPreferredSize(new Dimension(50, 25));
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.responseToLoadPressed();
			}});
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.responseToSavePressed();
			}});
		
		
		leftWFButton = new JButton("Left WF");
		leftWFButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.reponseToLeftRequest();
			}});
		randomWFButton = new JButton("Random WF");
		randomWFButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.reponseToRandomRequest();
			}});
		
		clearButton = new JButton("CLEAR");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				handler.reponseToClearRequest();
			}
		});
		Image green = tk.getImage("E:\\Java_project\\MazeSolver\\green.png");
		Image black = tk.getImage("E:\\Java_project\\MazeSolver\\black.png");
		Icon greenIcon = new ImageIcon(green);
		Icon blackIcon = new ImageIcon(black);
		allToggleButtons = new JToggleButton[400];
		for(int i=0;i<400;i++){
			final int num = i;
			allToggleButtons[i] = new JToggleButton(blackIcon);
			allToggleButtons[i].setSelectedIcon(greenIcon);
			allToggleButtons[i].setActionCommand(""+i);
			allToggleButtons[i].addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(allToggleButtons[num].isSelected())
						handler.reponseToSelect(e.getActionCommand());
					else 
						handler.reponseToUnSelect(e.getActionCommand());
//					System.out.println(num);
				}
			});
			allToggleButtons[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e){}
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					if(SwingUtilities.isRightMouseButton(e)){
						handler.reponseToEndSelect(num);
					}
					if(SwingUtilities.isMiddleMouseButton(e)){
						handler.reponseToStartingSelect(num);
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {}
				public void mouseClicked(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
			});
		}
		/*this.setFocusable(true);
		this.requestFocusInWindow(); 
        this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {   
                handler.respondToKeyPress(ke.getKeyCode());    
            }
        });*/
	}
	public void myLayout(){
		midPanel = new JPanel(new GridLayout(20,20));
		midPanel.setPreferredSize(new Dimension(400, 400));
		for(int i=0;i<400;i++){
			midPanel.add(allToggleButtons[i]);
		}
		lowPanel = new JPanel();
		lowPanel.add(leftWFButton);
		lowPanel.add(randomWFButton);
		lowPanel.add(clearButton);
		highPanel = new JPanel();
		highPanel.add(fileToLoadTextArea);
		highPanel.add(loadButton);
		highPanel.add(fileToSaveTextArea);
		highPanel.add(saveButton);
		solutionInfo = new JEditorPane();
		solutionInfo.setEditable(false);
		rightPanel = new JScrollPane(solutionInfo);
		rightPanel.setPreferredSize(new Dimension(300,400));
		this.add(highPanel,BorderLayout.NORTH);
		this.add(midPanel,BorderLayout.CENTER);
		this.add(lowPanel,BorderLayout.SOUTH);
		this.add(rightPanel,BorderLayout.EAST);
		this.pack();
	}
}
