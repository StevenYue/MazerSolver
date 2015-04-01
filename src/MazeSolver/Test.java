package MazeSolver;

import java.util.Arrays;

public class Test {
	public static boolean decisionMakingTrigger(int[][] maze,int i,int j){
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
	static int a = 0;
	public static void main(String[] args) {
//		int[][] maze = new int[][]{{1,1,0},{1,0,0},{1,1,0}};
//		System.out.println(decisionMakingTrigger(maze, 1, 2));
//		Integer a = 0;
		int[] a = new int[]{1,2};
		System.out.println(Arrays.toString(a));
	}

}
