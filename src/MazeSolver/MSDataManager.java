package MazeSolver;

import java.util.ArrayList;
import java.util.Random;

public class MSDataManager {
	protected int[][] maze; //0:open 1:block 2:start 3:stop
	protected int startX,startY;
	protected int endX,endY;
	private MSGui gui;
	private Random random;
	
	public MSDataManager(){}
	public MSDataManager(MSGui ui){
		gui = ui;
		maze = new int[20][20];
		initializeMaze();
		mazeInfo = new ArrayList<int[]>();
		random = new Random();
	}
	public int[][] getMaze() {
		return maze;
	}
	public void setMaze(int[][] maze) {
		this.maze = maze;
	}
	public void initializeMaze(){
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++)
				maze[i][j]=1;
	}
	public void showMatrix(){
		for(int i=0;i<20;i++){
			for(int j=0;j<20;j++){
				System.out.print(" "+maze[i][j]);
			}
			System.out.println();
		}
	}
	/**
	 * Robotics Simulation
	 */
	protected int currentDirection = 1;
	protected ArrayList<int[]> mazeInfo;
	protected int dis = 0;
	public boolean decisionMakingTrigger(int i,int j){
		int count = 0;
		int[] dirX = new int[]{1,-1,0,0};
		int[] dirY = new int[]{0,0,1,-1};
		for(int k=0;k<4;k++){
			try{
				if(maze[i+dirX[k]][j+dirY[k]]==0) count++;
			}catch(Exception e){}
		}
		if(count>=3) return true;
		else return false;
	}
	public void rightTurn(){
		currentDirection++;
		currentDirection = currentDirection%4;
	}
	public void leftTurn(){
		currentDirection+=3;
		currentDirection = currentDirection%4;
	}
	public void turnAround(){
		currentDirection+=2;
		currentDirection = currentDirection%4;
	}
	//front sensor
	public int sensorF(int i,int j){// 0:open  1:block  2:start  3:stop
		try{
			switch(currentDirection){
				case 1: if(maze[i-1][j]==3||maze[i-1][j]==2) return 0;
						else return maze[i-1][j];
				case 2: if(maze[i][j+1]==3||maze[i][j+1]==2) return 0;
						else return maze[i][j+1];
				case 3: if(maze[i+1][j]==3||maze[i+1][j]==2) return 0;
						else return maze[i+1][j];
				case 0: if(maze[i][j-1]==3||maze[i][j-1]==2) return 0;
						else return maze[i][j-1];	
				default: return -1;
			}
		}catch(Exception e){
			return 1;
		}
	}
	//right sensor
	public int sensorR(int i,int j){
		try{
		switch(currentDirection){
		case 1: if(maze[i][j+1]==3||maze[i][j+1]==2) return 0;
				else return maze[i][j+1];
		case 2: if(maze[i+1][j]==3||maze[i+1][j]==2) return 0;
				else return maze[i+1][j];
		case 3: if(maze[i][j-1]==3||maze[i][j-1]==2) return 0;
				else return maze[i][j-1];
		case 0: if(maze[i-1][j]==3||maze[i-1][j]==2) return 0;
				else return maze[i-1][j];	
		default: return -1;
		}}catch(Exception e){
			return 1;
		}
	}
	//left sensor
	public int sensorL(int i,int j){
		try{
		switch(currentDirection){
		case 1: if(maze[i][j-1]==3||maze[i][j-1]==2) return 0;
				else return maze[i][j-1];
		case 2: if(maze[i-1][j]==3||maze[i-1][j]==2) return 0;
				else return maze[i-1][j];
		case 3: if(maze[i][j+1]==3||maze[i][j+1]==2) return 0;
				else return maze[i][j+1];
		case 0: if(maze[i+1][j]==3||maze[i+1][j]==2) return 0;
				else return maze[i+1][j];		
		default: return -1;
		}}catch(Exception e){
			return 1;
		}
	}
	
	
	/**
	 * This is the Implementation of a left
	 * wall follower
	 * @param curX
	 * @param curY
	 * @return
	 */
	public boolean leftWallFollower(int curX,int curY){
		try{
		if((curX==endX)&&(curY==endY)) {
			mazeInfo.add(new int[]{currentDirection,dis});
			return true;
		}else{
		int sL = sensorL(curX,curY);
		int sR = sensorR(curX,curY);
		int sF = sensorF(curX,curY);
		int numOfPossiblePathes = 0;
		if(sL==0) numOfPossiblePathes++;
		if(sR==0) numOfPossiblePathes++;
		if(sF==0) numOfPossiblePathes++;
		
		//reaching dead end, turn around
		if(numOfPossiblePathes==0){
			mazeInfo.add(new int[]{currentDirection,dis});
			turnAround();
			dis=0;
			if(leftWallFollower(curX, curY)) return true;
			else return false;
		}
		//no decision making needed,only one possible path
		else if(numOfPossiblePathes==1){
			if(sL==0){
				mazeInfo.add(new int[]{currentDirection,dis});
				leftTurn();
				dis=0;
				int[] temp = moveOneStep(curX, curY);
				curX = temp[0];
				curY = temp[1];
				if(leftWallFollower(curX, curY)) return true;
				else return false;
			}
			else if(sR==0){
				mazeInfo.add(new int[]{currentDirection,dis});
				rightTurn();
				dis=0;
				int[] temp = moveOneStep(curX, curY);
				curX = temp[0];
				curY = temp[1];
				if(leftWallFollower(curX, curY)) return true;
				else return false;
			} 
			else{
				int[] temp = moveOneStep(curX, curY);
				curX = temp[0];
				curY = temp[1];
				if(leftWallFollower(curX, curY)) return true;
				else return false;
			} 
		}
		
		//decision making needed, more than one possible path
		else{
			mazeInfo.add(new int[]{currentDirection,dis});
			dis=0;
			if(sL==0){
				leftTurn();
				int[] temp = moveOneStep(curX, curY);
				curX = temp[0];
				curY = temp[1];
				if(leftWallFollower(curX, curY)) return true;
				else return false;
			}
			else{
				int[] temp = moveOneStep(curX, curY);
				curX = temp[0];
				curY = temp[1];
				if(leftWallFollower(curX, curY)) return true;
				else return false;
			}
		}
		}
		}catch(StackOverflowError  e){
			mazeInfo.clear();
			return false;
		}
	}
	
	/**
	 * This is a random Wall Follower, which means, if it reaches an intersection
	 * with more than one possible paths, robot will randomly pick one and move
	 * along that direction 
	 * @param curX
	 * @param curY
	 * @return
	 */
	public boolean randomWallFollower(Integer curX,Integer curY){
		try{
			if ((curX == endX) && (curY == endY)) {
				mazeInfo.add(new int[] { currentDirection, dis });
				return true;
			} else {
				int sL = sensorL(curX, curY);
				int sR = sensorR(curX, curY);
				int sF = sensorF(curX, curY);
				int numOfPossiblePathes = 0;
				if (sL == 0)	numOfPossiblePathes++;
				if (sR == 0)	numOfPossiblePathes++;
				if (sF == 0)	numOfPossiblePathes++;
				// reaching dead end, turn around
				if (numOfPossiblePathes == 0) {
					mazeInfo.add(new int[] { currentDirection, dis });
					turnAround();
					dis = 0;
					if (randomWallFollower(curX, curY))	return true;
					else	return false;
				}
				// no decision making needed
				else if (numOfPossiblePathes == 1) {
					if (sL == 0) {
						mazeInfo.add(new int[] { currentDirection, dis });
						leftTurn();
						dis = 0;
						int[] temp = moveOneStep(curX, curY);
						curX = temp[0];
						curY = temp[1];
						if (randomWallFollower(curX, curY))
							return true;
						else
							return false;
					} else if (sR == 0) {
						mazeInfo.add(new int[] { currentDirection, dis });
						rightTurn();
						dis = 0;
						int[] temp = moveOneStep(curX, curY);
						curX = temp[0];
						curY = temp[1];
						if (randomWallFollower(curX, curY))return true;
						else	return false;
					} else {
						int[] temp = moveOneStep(curX, curY);
						curX = temp[0];
						curY = temp[1];
						if (randomWallFollower(curX, curY))	return true;
						else	return false;
					}
				}

				/**
				 * Decision Making Needed, more than 1 possible paths
				 */
				else {
					mazeInfo.add(new int[] { currentDirection, dis });
					dis = 0;
					
					//0: turn left, 1:go forward, 2:turn right
					int pathToPick = random.nextInt(numOfPossiblePathes);
					if(numOfPossiblePathes == 2) // deal with special Case
						if(sL==1||(sF==1&&pathToPick!=0)) pathToPick++; 
					
					if (sL == 0 && pathToPick == 0) {
						leftTurn();
						int[] temp = moveOneStep(curX, curY);
						curX = temp[0];
						curY = temp[1];
						if (randomWallFollower(curX, curY))	return true;
						else	return false;
					} else if((sF == 0 && pathToPick == 1)){
						int[] temp = moveOneStep(curX, curY);
						curX = temp[0];
						curY = temp[1];
						if (randomWallFollower(curX, curY))	return true;
						else	return false;
					}else {
						rightTurn();
						int[] temp = moveOneStep(curX, curY);
						curX = temp[0];
						curY = temp[1];
						if (randomWallFollower(curX, curY))	return true;
						else	return false;
					}
				}
		}
		}catch(StackOverflowError  e){
			mazeInfo.clear();
			return false;
		}
	}
	
	
	public void delectDeadEnd(){
		while(true){
			boolean loopJudger = false;
			for(int i=0;i<mazeInfo.size()-1;i++){
				if(Math.abs(mazeInfo.get(i)[0] - mazeInfo.get(i+1)[0])== 2){
					mazeInfo.remove(i);
					mazeInfo.remove(i);
					i--;
					loopJudger = true;
				}
			}
			if(!loopJudger) break;
		}
	}
	
	
	public void fullyOptimize(){
		int xDistance = 0;
		int yDistance = 0;
		int counter=0;
		for(int i=0;i<mazeInfo.size()-1;i++){
			xDistance = 0;
			yDistance = 0;
			counter=0;
			int m = i;
			while(!(xDistance==0&&yDistance==0)||counter==0){
				try{
					switch(mazeInfo.get(m)[0]){
						case 1: xDistance+=mazeInfo.get(m)[1];counter++;break;
						case 2: yDistance+=mazeInfo.get(m)[1];counter++;break;
						case 3: xDistance-=mazeInfo.get(m)[1];counter++;break;
						case 0: yDistance-=mazeInfo.get(m)[1];counter++;break;
						default:break;
					}
					m++;
				}catch(Exception e){ break;}
			}
			if(xDistance==0&&yDistance==0){
				for(int j=0;j<counter;j++)
					mazeInfo.remove(i);
				i--;
			}
		}
	}
	
	public int[] moveOneStep(Integer curX, Integer curY){
		switch (currentDirection) {
		case 1:
			curX = curX - 1;
			break;
		case 2:
			curY = curY + 1;
			break;
		case 3:
			curX = curX + 1;
			break;
		case 0:
			curY = curY - 1;
			break;
		default:
			break;
		}
		dis++;
		return new int[]{curX,curY};
	}
}
