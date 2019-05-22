import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;




public class TicTacToe extends JFrame implements ActionListener {
	

    public class Move{ 
      int row, col; 
 	  Move(int r,int c){
          row=r;
          col=c;
      }
    }

	public static int BOARD_SIZE = 3;

	public static enum GameStatus {
		Incomplete, XWins, ZeroWins, Tie
	}

	private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
	public TicTacToe() {
		super.setTitle("TicTacToe");
		super.setSize(600, 600);
		GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(grid);
		Font font = new Font(Font.SERIF, Font.ITALIC, 75);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton("");
				button.setBackground(Color.WHITE);
                button.setContentAreaFilled(false);
                button.setOpaque(true);
				buttons[row][col] = button;
				button.setFont(font);
				button.addActionListener(this);
				super.add(button);
			}
		}
		super.setResizable(false);
		super.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {

		JButton clickedButton = (JButton) e.getSource();
		userMove(clickedButton);
		GameStatus gs = this.getGameStatus();
		if (gs == GameStatus.Incomplete) {
			computerMove();
			GameStatus gs1 = this.getGameStatus();
			if (gs1== GameStatus.Incomplete) {
				return;
		    }
			else {
			  declareWinner(gs1);
			
		    }
		}	
		else {
		   declareWinner(gs);
		}

		int choice = JOptionPane.showConfirmDialog(this, "Do you want to restart the Game");
		if (choice == JOptionPane.YES_OPTION) {
			for (int row = 0; row < BOARD_SIZE; row++) {
				for (int col = 0; col < BOARD_SIZE; col++) {
					buttons[row][col].setText("");
					buttons[row][col].setBackground(Color.WHITE);
					buttons[row][col].setContentAreaFilled(false);
					buttons[row][col].setOpaque(true);
					
				}
			}
			
		} else {
			super.dispose();
		}
	}
	private void computerMove() {
		
		char [][]charArray=new char[3][3];
		for (int i = 0; i<BOARD_SIZE; i++){ 
	        for (int j = 0; j<BOARD_SIZE; j++){
	        	if(buttons[i][j].getText().length()==0) {
	        		charArray[i][j]=' ';
	        	}
	        	else {
	        	    charArray[i][j]=buttons[i][j].getText().charAt(0);
	        	}
	        }
	        
	    }
		
		Move bestMove= findBestMove(charArray);
		int x=bestMove.row;
		int y=bestMove.col;
		buttons[x][y].setText("0");
		buttons[x][y].setBackground(Color.LIGHT_GRAY);
		buttons[x][y].setContentAreaFilled(false);
		buttons[x][y].setOpaque(true);
		
		
	}
	
	//Implementation of min-max AI algorithm  
	
	private boolean isMovesLeft(char b[][]){ 
	    for (int i = 0; i<BOARD_SIZE; i++) 
	        for (int j = 0; j<BOARD_SIZE; j++) 
	            if (b[i][j]==' ') 
	                return true; 
	    return false; 
	} 
	  

	private int evaluate(char b[][]){
		
		// Checking for Rows for X or O victory.
	    for (int row = 0; row<BOARD_SIZE; row++){ 
	        if (b[row][0]==b[row][1] && b[row][1]==b[row][2]){ 
	            if (b[row][0]=='0'){ 
	                return +100;
	            }    
	            else if(b[row][0]=='X'){ 
	                return -100; 
	            }    
	        } 
	    } 
	  
	    // Checking for Columns for X or O victory.
	    for (int col = 0; col<BOARD_SIZE; col++){ 
	        if (b[0][col]==b[1][col] && b[1][col]==b[2][col]){ 
	            if (b[0][col]=='0'){ 
	                return +100; 
	            }
	            else if (b[0][col]=='X'){ 
	                return -100; 
	            }    
	        } 
	    } 
	  
	    // Checking for Diagonals for X or O victory.
	    
	    if (b[0][0]==b[1][1] && b[1][1]==b[2][2]){ 
	        if (b[0][0]=='0'){
	        	return +100; 
	        }          
	        else if (b[0][0]=='X'){ 
	            return -100; 
	        }    
	    } 
	  
	    if (b[0][2]==b[1][1] && b[1][1]==b[2][0]){ 
	        if (b[0][2]=='0'){ 
	            return +100;
	        }    
	        else if (b[0][2]=='X'){
	        	return -100; 
	        }
	            
	    } 
	    return 0; 
	} 
	  

	private int minimax(char board[][], int depth, boolean isMax){ 
		
	    int score = evaluate(board); 
	  
	    if (score == 100){ 
	        return score;
	    }
	    
	    if (score == -100){ 
	        return score; 
	    }    
	    
	    if (isMovesLeft(board)==false){ 
	        return 0; 
	    }    
	        
	   
	    if (isMax){ 
	       int best = -1000; 
	       for (int i = 0; i<BOARD_SIZE; i++){ 
	         for (int j = 0; j<BOARD_SIZE; j++){ 
	            if (board[i][j]==' '){ 
	                 board[i][j] = '0'; 
	                 best = Math.max( best, minimax(board, depth+1, !isMax) ); 
	                 //back track
	                 board[i][j] = ' '; 
	             } 
	         } 
	       } 
	      return best; 
	    } 
	    else {
	        int best = 1000; 
	        for (int i = 0; i<BOARD_SIZE; i++){ 
	            for (int j = 0; j<BOARD_SIZE; j++){ 
	                if (board[i][j]==' '){ 
	                     board[i][j] = 'X';                  
	                     best =Math. min(best, minimax(board, depth+1, !isMax)); 
	                    // back track
	                     board[i][j] = ' '; 
	                } 
	            } 
	        } 
	        return best; 
	    } 
	} 
	  

	private Move findBestMove(char board[][]){ 
	    int bestVal = -1000; 
	    Move bestMove = new Move(-1,-1); 
	    
	    for (int i = 0; i<3; i++){ 
	        for (int j = 0; j<3; j++){ 
	            if (board[i][j]==' '){ 
	                board[i][j] = '0'; 
	                int moveVal = minimax(board, 0, false); 
	                //backtrack
	                board[i][j] = ' '; 
	                if (moveVal > bestVal) 
	                { 
	                    bestMove.row = i; 
	                    bestMove.col = j; 
	                    bestVal = moveVal; 
	                } 
	            } 
	        } 
	    } 
	    return bestMove; 
	} 
	
	
	
    private void userMove(JButton clickedButton) {
		String btntext = clickedButton.getText();
		if (btntext.length() > 0) {
			JOptionPane.showMessageDialog(this, "Invalid Move");
		} 
		else{
			clickedButton.setText("X");
			clickedButton.setBackground(Color.GREEN);
			clickedButton.setContentAreaFilled(false);
			clickedButton.setOpaque(true);
		}			
	}
	

	private GameStatus getGameStatus() {
		String text1 = "", text2 = "";
		int row = 0, col = 0;

		// rows
		row = 0;
		while (row < BOARD_SIZE) {
			col = 0;
			while (col < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row][col + 1].getText();
				if (!text1.equals(text2) || text1.length() == 0) {
					break;
				}
				col++;
			}
			if (col == BOARD_SIZE - 1) {
				for(int colIndex=0;colIndex<BOARD_SIZE; colIndex++) {
					buttons[row][colIndex].setBackground(Color.cyan);
					buttons[row][colIndex].setContentAreaFilled(false);
					buttons[row][colIndex].setOpaque(true);
				}
				if (text1.equals("X")) {
					return GameStatus.XWins;
				} else {
					return GameStatus.ZeroWins;
				}
			}
			row++;
		}

		//  cols
		col = 0;
		while (col < BOARD_SIZE) {
			row = 0;
			while (row < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row + 1][col].getText();
				if (!text1.equals(text2) || text1.length() == 0) {
					break;
				}
				row++;
			}
			if (row == BOARD_SIZE - 1) {
				for(int rowIndex=0;rowIndex<BOARD_SIZE; rowIndex++) {
					buttons[rowIndex][col].setBackground(Color.cyan);
					buttons[rowIndex][col].setContentAreaFilled(false);
					buttons[rowIndex][col].setOpaque(true);
				}
				if (text1.equals("X")) {
					return GameStatus.XWins;
				} else {
					return GameStatus.ZeroWins;
				}
			}
			col++;
		}

		// diagnol1

		row = 0;
		col = 0;
		while (row < BOARD_SIZE - 1) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row + 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0) {
				break;
			}
			row++;
			col++;
		}
		if (row == BOARD_SIZE - 1) {
			
			int rowIndex = 0;
			int colIndex = 0;
			while (rowIndex < BOARD_SIZE ) {
				buttons[rowIndex][colIndex].setBackground(Color.cyan);
				buttons[rowIndex][colIndex].setContentAreaFilled(false);
				buttons[rowIndex][colIndex].setOpaque(true);
				rowIndex++;
				colIndex++;
			}
			if (text1.equals("X")) {
				return GameStatus.XWins;
			} else {
				return GameStatus.ZeroWins;
			}
		}

		//  diagnol2

		row = BOARD_SIZE - 1;
		col = 0;
		while (row > 0) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row - 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0) {
				break;
			}
			row--;
			col++;
		}
		if (row == 0) {
			int rowIndex = BOARD_SIZE -1;
			int colIndex = 0;
			while (rowIndex >= 0) {
				buttons[rowIndex][colIndex].setBackground(Color.cyan);
				buttons[rowIndex][colIndex].setContentAreaFilled(false);
				buttons[rowIndex][colIndex].setOpaque(true);
				rowIndex--;
				colIndex++;
			
			}
			
			if (text1.equals("X")) {
				return GameStatus.XWins;
			} else {
				return GameStatus.ZeroWins;
			}
		}

		String txt = "";
		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE; col++) {
				txt = buttons[row][col].getText();
				if (txt.length() == 0) {
					return GameStatus.Incomplete;
				}
			}
		}

		return GameStatus.Tie;
	}

	private void declareWinner(GameStatus gs) {
		if (gs == GameStatus.XWins) {
			JOptionPane.showMessageDialog(this, "X Wins");
		} else if (gs == GameStatus.ZeroWins) {
			JOptionPane.showMessageDialog(this, "Zero Wins");
		} else {
			JOptionPane.showMessageDialog(this, "It is a tie");
		}
	}
	
	public static void main(String[] args) {
		TicTacToe t = new TicTacToe();
	}

}