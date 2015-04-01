package MazeSolver;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import MazeSolverFileManager.FileManager;

public class MSEventHandler {
	MSGui gui;
	Toolkit tk;
	Icon redIcon;
	Icon whiteIcon;
	Icon blackIcon;
	Icon greenIcon;

	public MSEventHandler(){}
	public MSEventHandler(MSGui ui){
		gui = ui;
		tk = Toolkit.getDefaultToolkit();
		Image red = tk.getImage("red.png");
		Image white = tk.getImage("white.png");
		Image black = tk.getImage("black.png");
		Image green = tk.getImage("green.png");
		redIcon = new ImageIcon(red);
		whiteIcon = new ImageIcon(white);
		blackIcon = new ImageIcon(black);
		greenIcon = new ImageIcon(green);
	}
	
	public void reponseToSelect(String s){
		int num = Integer.parseInt(s);
		int i = num/20;
		int j = num%20;
		gui.data.getMaze()[i][j] = 0;
	}
	public void reponseToUnSelect(String s){
		int num = Integer.parseInt(s);
		int i = num/20;
		int j = num%20;
		gui.data.getMaze()[i][j] = 1;
	}
	public void reponseToLeftRequest(){
		//gui.data.showMatrix();
		String sToDisplay = "==== Solution of Left Wall Follower ====";
		MSDataManager ms = gui.data;
		ArrayList<int[]> mazeInfo = ms.mazeInfo;
		System.out.println("==== Solution of Left Wall Follower ====");
		if(ms.leftWallFollower(ms.startX, ms.startY)){
			System.out.println("This Maze is Solvable");
			sToDisplay+="\n" + "This Maze is Solvable";
		} 
		else{
			System.out.println("No Solution for This Maze");
			sToDisplay+="\n" + "No Solution for This Maze";
		} 
		int totalSteps = 0;
		for(int[] i:mazeInfo){
			String dir = null;
			switch(i[0]){
			case 1: dir = "North";break;
			case 2: dir = "East";break;
			case 3: dir = "South";break;
			case 0: dir = "West";break;
			default:break;
			}
			System.out.println("Direction:"+i[0]+" Steps:"+i[1]);
			sToDisplay += "\n" + "Direction: "+dir+"		Steps:"+i[1];
			totalSteps += i[1];
		}
		sToDisplay += "\n"+"Total Steps:"+totalSteps;
		sToDisplay += "\n"+ "\n" + "**** After Optimization ****";
		System.out.println("**** After Optimization ****");
//		ms.delectDeadEnd();
		ms.fullyOptimize();
		totalSteps = 0;
		for(int[] i:mazeInfo){
			String dir = null;
			switch(i[0]){
			case 1: dir = "North";break;
			case 2: dir = "East";break;
			case 3: dir = "South";break;
			case 0: dir = "West";break;
			default:break;
			}
			System.out.println("Direction:"+i[0]+" Steps:"+i[1]);
			sToDisplay += "\n" + "Direction: "+dir+"	 	Steps:"+i[1];
			totalSteps += i[1];
		}
		sToDisplay += "\n"+"Total Steps:"+totalSteps;
		sToDisplay += "\n"+"\n";
		gui.solutionInfo.setText(sToDisplay);
		System.out.println();
		ms.mazeInfo.clear();
		gui.data.currentDirection = 1;
		gui.data.dis = 0;
	}
	
	public void reponseToClearRequest(){
		for(int i=0;i<400;i++){
			gui.allToggleButtons[i].setIcon(blackIcon);
			gui.allToggleButtons[i].setSelected(false);
		}
		gui.solutionInfo.setText("");
		gui.data.initializeMaze();
		gui.data.mazeInfo.clear();
		gui.data.currentDirection = 1;
		gui.data.dis = 0;
	}
/*	public void respondToKeyPress(int keyCode) {
		if(keyCode==KeyEvent.VK_S){
			System.out.println("aaaaa");
		}
		if(keyCode == KeyEvent.VK_E){
			
		}
	}*/
	public void reponseToEndSelect(int num) {
		int i = num/20;
		int j = num%20;
		gui.data.getMaze()[i][j] = 3;
		gui.data.endX = i;
		gui.data.endY = j;
		gui.allToggleButtons[num].setSelected(false);
		gui.allToggleButtons[num].setIcon(redIcon);
//		System.out.println("end");
	}
	public void reponseToStartingSelect(int num) {
		int i = num/20;
		int j = num%20;
		gui.data.getMaze()[i][j] = 2;
		gui.data.startX = i;
		gui.data.startY = j;
		gui.allToggleButtons[num].setSelected(false);
		gui.allToggleButtons[num].setIcon(whiteIcon);
//		System.out.println("Start");
	}
	public void reponseToRandomRequest() {
		MSDataManager ms = gui.data;
		ArrayList<int[]> mazeInfo = ms.mazeInfo;
		String sToDisplay = "==== Solution Of Random Wall Follower ====";
		System.out.println("==== Solution Of Random Wall Follower ====");
		if(ms.randomWallFollower(ms.startX, ms.startY)){
			System.out.println("This Maze is Solvable");
			sToDisplay+="\n" + "This Maze is Solvable";
		} 
		else{
			System.out.println("No Solution for This Maze");
			sToDisplay+="\n" + "No Solution for This Maze";
		} 
		int totalSteps = 0;
		for(int[] i:mazeInfo){
			String dir = null;
			switch(i[0]){
			case 1: dir = "North";break;
			case 2: dir = "East";break;
			case 3: dir = "South";break;
			case 0: dir = "West";break;
			default:break;
			}
			System.out.println("Direction:"+i[0]+" Steps:"+i[1]);
			sToDisplay += "\n" + "Direction: "+dir+"		Steps:"+i[1];
			totalSteps += i[1];
		}
		sToDisplay += "\n"+"Total Steps:"+totalSteps;
		sToDisplay += "\n"+ "\n" + "**** After Optimization ****";
		System.out.println("**** After Optimization ****");
//		ms.delectDeadEnd();
		ms.fullyOptimize();
		totalSteps = 0;
		for(int[] i:mazeInfo){
			String dir = null;
			switch(i[0]){
			case 1: dir = "North";break;
			case 2: dir = "East";break;
			case 3: dir = "South";break;
			case 0: dir = "West";break;
			default:break;
			}
			System.out.println("Direction:"+i[0]+" Steps:"+i[1]);
			sToDisplay += "\n" + "Direction: "+dir+"		Steps:"+i[1];
			totalSteps += i[1];
		}
		sToDisplay += "\n"+"Total Steps:"+totalSteps;
		sToDisplay += "\n"+"\n";
		gui.solutionInfo.setText(sToDisplay);
		System.out.println();
		ms.mazeInfo.clear();
		gui.data.currentDirection = 1;
		gui.data.dis = 0;
	}
	
	public void responseToLoadPressed() {
		try{
		reponseToClearRequest();
		String tempS = FileManager.loadTextFile(gui.fileToLoadTextArea.getText());
		for(int i=0;i<20;i++){
			for(int j=0;j<40;j+=2){	
				gui.data.maze[i][j/2] = Character.getNumericValue(tempS.charAt(i*41 + j));
				if(gui.data.maze[i][j/2]==0)
					gui.allToggleButtons[i*20+j/2].setIcon(greenIcon);
				if(gui.data.maze[i][j/2]==2){
					gui.allToggleButtons[i*20+j/2].setIcon(whiteIcon);
					gui.data.startX = i;
					gui.data.startY = j/2;
				}
				if(gui.data.maze[i][j/2]==3){
					gui.allToggleButtons[i*20+j/2].setIcon(redIcon);
					gui.data.endX = i;
					gui.data.endY = j/2;
				}
			}
		}
		}catch(Exception e){
			gui.solutionInfo.setText("File Doesn't Exsit !!!");
		}
	}
	public void responseToSavePressed() {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<20;i++){
			for(int j=0;j<20;j++){
				sb.append(gui.data.maze[i][j]);
				sb.append(",");
			}
			sb.append("\n");
		}
		FileManager.writeToFile(sb.toString(), gui.fileToSaveTextArea.getText());
		gui.fileToSaveTextArea.setText("");
	}
}
