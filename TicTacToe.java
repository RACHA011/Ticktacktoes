import java.util.*;

public class TicTacToe {
	static String log = "         ";
	static char[] arrs = log.toCharArray();
    final static int t = 3, z = 0, f = 9,s = 7;
    
    
	public static void main (String[] args) {
		
		printArray(arrs);
		
		String result = results(log);
		
		System.out.println(result);
	}
	
	
	static char arrss (int n,char[] log) {
		char element = ' ';
		
		for (int i = 0;i < log.length; i++) {
			if (i == n) {
				element = log[i];
			}
		}
		
		return element;
	}
	
	static void printArray (char[] log) {
		int n = 0;
		System.out.println("---------");
		
	    for (int i = 0;i < t;i++) {
	        
		     for (int j = 0;j < f;j++) {
		         if(j == 0 || j == f - 1) {
		             System.out.print("|");
		         } else if (!(j % 2 == z)) {
		        	 System.out.print(" ");
		         }
		         else {
		             System.out.print(arrss(n ,log));
		             n++;
		         }
		     }
		   System.out.println();
		    }
		    System.out.println("---------");	
		    
		    
		    
	}
	
	static String results (String log) {
		
		String result = " ";
		int player = 0;
		
		while (result.equals(" ")) {
			
			nextMove(log , player);
			
			String a = Character.toString(arrs[0]) + Character.toString(arrs[1]) + Character.toString(arrs[2]);
			String b = Character.toString(arrs[3]) + Character.toString(arrs[4]) + Character.toString(arrs[5]);
			String c = Character.toString(arrs[6]) + Character.toString(arrs[7]) + Character.toString(arrs[8]);
			
			String d = Character.toString(arrs[0]) + Character.toString(arrs[3]) + arrs[6];
			String e = Character.toString(arrs[1]) + Character.toString(arrs[4]) + arrs[7];
			String f = Character.toString(arrs[2]) + Character.toString(arrs[5]) + arrs[8];
			
			String g = Character.toString(arrs[0]) + Character.toString(arrs[4]) + arrs[8];
			String h = Character.toString(arrs[2]) + Character.toString(arrs[4]) + arrs[6];
			
			String winX = "XXX";
			String winO = "OOO";
			
			String[] arrS = {a ,b ,c ,d ,e ,f ,g ,h};
			
			int x = 0;
			int o = 0;
			
			for (int i = 0; i < 9; i++) {
				
				if (arrs[i] == 'X') {
					x++;
				} else if (arrs[i] == 'O') {
					o++;
				}
				
			}
			int res = x + o;
			for (int i = 0; i < 8;i++) {
				if (arrS[i].equals(winX)) {
					return "X wins";
				} else if (arrS[i].equals(winO)) {
					return "O wins";
				}
			}
			if (res == 9) {
				return "Draw";
			}
			
			player++;
		}
		return result;
	}
	
	static void nextMove (String log ,int player) {
		Scanner scanner = new Scanner(System.in);
		try {
			
			System.out.print("Enter the coordinates: ");
			
			int row = scanner.nextInt();
			int col = scanner.nextInt();
			
			int pos = pos(row , col);
			
			if (arrs[pos] == 'X' || arrs[pos] == 'O') {
				System.out.println("This cell is occupied! Choose another one!");
				nextMove(log ,player);
			} else {
				char[] position = log(row ,col ,player);
				printArray(position);
			}
			
		} catch (InputMismatchException e) {
			System.out.println("You should enter numbers!"); 
			nextMove(log, player);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Coordinates should be from 1 to 3!");
			nextMove(log, player);
		}
	}
	 
	static char[] log (int row,int col , int player) {
		
		char tPlayer = ' ';
		player = player % 2;
		
		switch (player) {
		case 0:
			tPlayer = 'X';
			break;
		case 1:
			tPlayer = 'O';
			break;
		
		} 
		
		if (row == 1 && col == 1) {
			
			arrs[0] = tPlayer;
		} else if (row == 1 && col == 2) {
			
			arrs[1] = tPlayer;
		} else if (row == 1 && col == 3) {
			
			arrs[2] = tPlayer;
		} else if (row == 2 && col == 1) {
			
			arrs[3] = tPlayer;
		} else if (row == 2 && col == 2) {
			
			arrs[4] = tPlayer;
		} else if (row == 2 && col == 3) {
			
			arrs[5] = tPlayer;
		} else if (row == 3 && col == 1) {
			
			arrs[6] = tPlayer;
		} else if (row == 3 && col == 2) {
			
			arrs[7] = tPlayer;
		} else if (row == 3 && col == 3) {
			
			arrs[8] = tPlayer;
		} 
		
		
		//log = log.substring(0, pos) + tPlayer + log.substring(pos + 1);

		return arrs;
	}
	
	static int pos (int row ,int col) {
		int pos = 1000;
		
		if (row == 1 && col == 1) {
			pos = 0;
		} else if (row == 1 && col == 2) {
			pos = 1;
		} else if (row == 1 && col == 3) {
			pos = 2;
		} else if (row == 2 && col == 1) {
			pos = 3;
		} else if (row == 2 && col == 2) {
			pos = 4;
		} else if (row == 2 && col == 3) {
			pos = 5;
		} else if (row == 3 && col == 1) {
			pos = 6;
		} else if (row == 3 && col == 2) {
			pos = 7;
		} else if (row == 3 && col == 3) {
			pos = 8;
		}
		
		return pos;
		
	}
	
}
