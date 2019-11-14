package beatRepeatGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import round.Grid;
import round.Round;

public class BeatRepeatGUI extends JFrame
{	
	private static JToggleButton[][] uniqueFreeTileButton;
	
	
	private static boolean correct; 
	
	
	private static boolean welcomeDone;
	private static boolean multipleChoicesDone;
	private static boolean freeTileDone;
	private static boolean endScreenDone;
	
	private static boolean backToMain = true;
	private static boolean tryAgain;
	
	private static final int SCORE_EASY = 50;
	private static final int SCORE_MEDIUM = 100;
	private static final int SCORE_HARD = 150;
	
	private static final int SCORE_SIZE1 = 50;
	private static final int SCORE_SIZE2 = 100;
	private static final int SCORE_SIZE3 = 150;
	
	
	private static final JPanel difficultyButtons = new JPanel(new GridLayout(4, 1));
	private static final JPanel sizeButtons = new JPanel(new GridLayout(4, 1));
	private static final String EASY = "Easy";
	private static final String MEDIUM = "Medium";
	private static final String HARD = "Hard";
	
	private static int[] freeTileSelection;
	private static int multipleChoiceSelection; //
	
	private static int row;
	private static int column;
	private static String difficultyMode;
	
	
	private static Round currentRound;
	
	private static Container welcomeFrame;
	private static Container endFrame;
	private static Container buttonGridFrame;
	private static Container multipleChoicesFrame;
	private static Container pictureGridFrame;
	private static Container listenFrame;
	
	private static int numberOfScore = 0;
	
	public BeatRepeatGUI()
	{
		tryAgain = true;
		
		welcomeDone = false;
		
		correct = true;
		
		welcome();
		uniqueFreeTileButton = new JToggleButton[row][column];
	}
	
	public void welcome()
	{
		dispose();
		
		welcomeFrame = getContentPane();
		welcomeFrame.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500,500));
		
		JLabel titleLabel = new JLabel("Welcome to BeatRepeat", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
		
		JLabel difficultyLabel = new JLabel("Difficulty", SwingConstants.CENTER);
		difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		
		difficultyButtons.add(difficultyLabel);
		JToggleButton easy = new JToggleButton(EASY);
		JToggleButton medium = new JToggleButton(MEDIUM);
		JToggleButton hard = new JToggleButton(HARD);
		
		easy.setBackground(Color.WHITE);
		medium.setBackground(Color.WHITE);
		hard.setBackground(Color.WHITE);
		
		ButtonGroup difficultyButtonGroup = new ButtonGroup();
		difficultyButtonGroup.add(easy);
		difficultyButtonGroup.add(medium);
		difficultyButtonGroup.add(hard);
		
		easy.addActionListener(createDifficultyButtonListener());
		medium.addActionListener(createDifficultyButtonListener());
		hard.addActionListener(createDifficultyButtonListener());
		
		difficultyButtons.add(easy);
		difficultyButtons.add(medium);
		difficultyButtons.add(hard);
		
		JLabel sizeLabel = new JLabel("Size", SwingConstants.CENTER);
		sizeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		sizeButtons.add(sizeLabel);
		
		JToggleButton size1 = new JToggleButton("3 x 4");
		JToggleButton size2 = new JToggleButton("4 x 5");
		JToggleButton size3 = new JToggleButton("6 x 6");

		size1.setBackground(Color.WHITE);
		size2.setBackground(Color.WHITE);
		size3.setBackground(Color.WHITE);
		
		ButtonGroup sizeButtonGroup = new ButtonGroup();
		sizeButtonGroup.add(size1);
		sizeButtonGroup.add(size2);
		sizeButtonGroup.add(size3);
		
		size1.addActionListener(createSizeButtonListener());
		size2.addActionListener(createSizeButtonListener());
		size3.addActionListener(createSizeButtonListener());
		
		sizeButtons.add(size1);
		sizeButtons.add(size2);
		sizeButtons.add(size3);
		
		JPanel buttons = new JPanel(new GridLayout(1, 2));
		buttons.add(difficultyButtons);
		buttons.add(sizeButtons);
		
		JButton goButton = new JButton("Go");
		goButton.setBackground(Color.GREEN);
		
		welcomeFrame.add(titleLabel, BorderLayout.NORTH);
		welcomeFrame.add(buttons, BorderLayout.CENTER);
		welcomeFrame.add(goButton, BorderLayout.SOUTH);
		
		goButton.addActionListener(createGoButtonListener());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BeatRepeat");
		pack();
		setVisible(true);
		
		while(!welcomeDone)
		{
			System.out.print("");
			//empty
		}
	}
	
	public static ActionListener createSizeButtonListener()
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						 AbstractButton abstractButton = (AbstractButton)event.getSource();
						 if(abstractButton.getModel().isSelected())
						 {
							 row = Character.getNumericValue(event.getActionCommand().charAt(0));
							 column = Character.getNumericValue(event.getActionCommand().charAt(4));
						 }
					}
				};
	}
	
	public static ActionListener createDifficultyButtonListener()
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						 AbstractButton abstractButton = (AbstractButton)event.getSource();
						 if(abstractButton.getModel().isSelected())
						 {
							 difficultyMode = event.getActionCommand();
						 }
					}
				};
	}
	
	public static ActionListener createGoButtonListener()
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						welcomeDone = true;
						welcomeFrame.removeAll();
					}
				};
	}
	
	public void endScreen()
	{
		endScreenDone = true;
		
		endFrame = getContentPane();
		setPreferredSize(new Dimension(500,500));
		endFrame.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		JPanel titlePanel = new JPanel(new BorderLayout());
		
		JLabel titleLabel = new JLabel("BeatRepeat", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
		JLabel empty = new JLabel(" ");
		empty.setFont(new Font("Arial", Font.PLAIN, 40));
		JLabel scoreTitle = new JLabel("Your score is: ", SwingConstants.CENTER);
		scoreTitle.setFont(new Font("Arial", Font.PLAIN, 25));
		
		
		titlePanel.add(titleLabel, BorderLayout.NORTH);
		titlePanel.add(empty, BorderLayout.CENTER);
		titlePanel.add(scoreTitle, BorderLayout.SOUTH);
		
		JLabel scoreLabel = new JLabel("" + numberOfScore, SwingConstants.CENTER);
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		
		JButton tryAgain = new JButton("Try Again");
		
		JButton mainMenu = new JButton("Main Menu");
		
		endFrame.add(titlePanel, BorderLayout.NORTH);
		endFrame.add(scoreLabel, BorderLayout.CENTER);
		buttonPanel.add(tryAgain);
		buttonPanel.add(mainMenu);
		endFrame.add(buttonPanel, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BeatRepeat");
		pack();
		setVisible(true);
		
		mainMenu.addActionListener(createBackToMenuButtonListener());
		tryAgain.addActionListener(createTryAgainButtonListener());
		
		while(endScreenDone)
		{
			System.out.print("");
		}
		dispose();
	}
	
	public static ActionListener createBackToMenuButtonListener()
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						backToMain = true;
						tryAgain = false;						
						endFrame.removeAll();
						
						endScreenDone = false;
					}
				};
	}
	
	public static ActionListener createTryAgainButtonListener()
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						backToMain = false;
						tryAgain = true;
						correct = true;
						endFrame.removeAll();

						endScreenDone = false;
					}
				};
	}
	
	public void buttonGrid()
	{
		freeTileDone = false;
		freeTileSelection = new int[column];
		
		buttonGridFrame = getContentPane();
		JPanel buttonGrid = new JPanel(new GridLayout(row, column));
		
		
		ButtonGroup[] columnGroup = new ButtonGroup[column];
		for(int index = 0; index < columnGroup.length; index++)
		{
			columnGroup[index] = new ButtonGroup();
		}
		for(int rowIndex = 0; rowIndex < row; rowIndex++)
		{
			
			for(int columnIndex = 0; columnIndex < column; columnIndex++)
			{	
				uniqueFreeTileButton[rowIndex][columnIndex] = new JToggleButton();
				uniqueFreeTileButton[rowIndex][columnIndex].setBackground(Grid.DEFAULT_COLOR);
				uniqueFreeTileButton[rowIndex][columnIndex].setBorder(BorderFactory.createLineBorder(Color.black));
				buttonGrid.add(uniqueFreeTileButton[rowIndex][columnIndex]);
				uniqueFreeTileButton[rowIndex][columnIndex].addActionListener(createGridButtonListener(rowIndex,
																				columnIndex));
				columnGroup[columnIndex].add(uniqueFreeTileButton[rowIndex][columnIndex]);
				
			}
		}
		buttonGridFrame.add(buttonGrid);
		
		JButton submitButton = new JButton("Submit");
		submitButton.setBackground(Color.GREEN);
		buttonGrid.add(submitButton);
		buttonGridFrame.add(submitButton, BorderLayout.SOUTH);
		submitButton.addActionListener(createFreeTileSubmitButtonListener());
		
		setPreferredSize(new Dimension(500,500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BeatRepeat");
		pack();
		setVisible(true);
		
		while(!freeTileDone)
		{
			System.out.print("");
		}
	}
	
	public static ActionListener createGridButtonListener(final int row, final int column)
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						 AbstractButton abstractButton = (AbstractButton)event.getSource();
						 if(abstractButton.getModel().isSelected())
						 {
							 freeTileSelection[column] = row;
							 playSound(currentRound.getNote(row), 100);
						 }
					}
				};
	}

	public static ActionListener createFreeTileSubmitButtonListener()
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						String stringSelection = "";
						for(int index = 0; index < freeTileSelection.length; index++)
						{
							stringSelection += freeTileSelection[index];
						}
						correct = stringSelection.equals(currentRound.getQuestionPictureToString());
						
						if(correct)
						{
							numberOfScore += SCORE_HARD;
							
							if(row == 3)
							{
								numberOfScore += SCORE_SIZE1;
							}
							else if(row == 4)
							{
								numberOfScore += SCORE_SIZE2;
							}
							else if(row == 6)
							{
								numberOfScore += SCORE_SIZE3;
							}
						}
						
						buttonGridFrame.removeAll();
						
						freeTileDone = true;
					}
				};
	}
	
	public void multipleChoicesGrid()
	{
		multipleChoicesFrame = getContentPane();
		multipleChoicesFrame.setLayout(new BorderLayout());
		
		multipleChoicesDone = false;
		
		JPanel[] choicePicture = new JPanel[4];
		for(int choiceIndex = 0; choiceIndex < choicePicture.length; choiceIndex++)
		{
			choicePicture[choiceIndex] = new JPanel(new GridLayout(currentRound.getRow(), 
																	currentRound.getColumn()));
			for(int rowIndex = 0; rowIndex < currentRound.getRow(); rowIndex++)
			{
				for(int columnIndex = 0; columnIndex < currentRound.getColumn(); columnIndex++)
				{
					JLabel coloredTile = new JLabel();
					coloredTile.setOpaque(true);
					coloredTile.setBackground(currentRound.getMultipleChoicesTileColor(rowIndex, 
																						columnIndex, 
																						choiceIndex));
					coloredTile.setBorder(BorderFactory.createLineBorder(Color.black));
					choicePicture[choiceIndex].add(coloredTile);	
				}	
			}
		}
		
		JPanel multipleChoice = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();	
		
		ButtonGroup group = new ButtonGroup();
		JToggleButton[] selection = new JToggleButton[4];
		
		c.insets = new Insets (5, 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 75;
		c.ipady = 200;
		c.gridx = 0;
		c.gridy = 0;
		multipleChoice.add(choicePicture[0], c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 0;
		multipleChoice.add(choicePicture[1], c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 5;
		selection[0] = new JToggleButton("Option " + 1);
		multipleChoice.add(selection[0], c);
		
		
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 5;
		selection[1] = new JToggleButton("Option " + 2);
		multipleChoice.add(selection[1], c);
		
		c.gridx--;
		c.gridy++;
		c.ipadx = 75;
		c.ipady = 200;
		multipleChoice.add(choicePicture[2], c);
		
		c.gridx++;
		multipleChoice.add(choicePicture[3], c);
		
		c.gridx--;
		c.gridy++;
		c.ipady = 10;
		selection[2] = new JToggleButton("Option " + 3);
		multipleChoice.add(selection[2], c);
		
		
		c.gridx++;
		selection[3] = new JToggleButton("Option " + 4);
		multipleChoice.add(selection[3], c);
		
		JButton submit = new JButton("Submit");
		submit.setBackground(Color.GREEN);
		
		multipleChoicesFrame.add(multipleChoice, BorderLayout.CENTER);
		multipleChoicesFrame.add(submit, BorderLayout.SOUTH);
		
		group.add(selection[0]);
		group.add(selection[1]);
		group.add(selection[2]);
		group.add(selection[3]);
		
		selection[0].addActionListener(createOptionButtonListener(0));
		selection[1].addActionListener(createOptionButtonListener(1));
		selection[2].addActionListener(createOptionButtonListener(2));
		selection[3].addActionListener(createOptionButtonListener(3));
		
		submit.addActionListener(createMultipleChoicesSubmitButtonListener());
		
		setPreferredSize(new Dimension(500,500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("BeatRepeat");
		pack();
		setVisible(true);
		
		while(!multipleChoicesDone)
		{
			System.out.print("");
		}
	}
	
	public static ActionListener createOptionButtonListener(int option)
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						multipleChoiceSelection = option;
					}
				};
	}
	
	public static ActionListener createMultipleChoicesSubmitButtonListener()
	{
		return new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						correct 
						= currentRound.
							getMultipleChoicesPictureToString(multipleChoiceSelection).
									equals(currentRound.getQuestionPictureToString());
						if(correct)
						{
							if(difficultyMode == EASY)
							{
								numberOfScore += SCORE_EASY;
							}
							else
							{
								numberOfScore += SCORE_MEDIUM;
							}
							
							if(row == 3)
							{
								numberOfScore += SCORE_SIZE1;
							}
							else if(row == 4)
							{
								numberOfScore += SCORE_SIZE2;
							}
							else if(row == 6)
							{
								numberOfScore += SCORE_SIZE3;
							}
						}
						multipleChoicesFrame.removeAll();
						multipleChoicesDone = true;
					}
				};
	}
	
	public void pictureGrid()
	{
		pictureGridFrame = getContentPane();
		pictureGridFrame.setLayout(new BorderLayout());
		
		JPanel gridFrame = new JPanel(new GridLayout(row, column));
		
		
		for(int index1 = 0; index1 < row; index1++)
		{
			for(int index2 = 0; index2 < column; index2++)
			{	
				JLabel coloredTiles = new JLabel();
				coloredTiles.setOpaque(true);
				coloredTiles.setBackground(currentRound.getQuestionTileColor(index1, index2));
				coloredTiles.setBorder(BorderFactory.createLineBorder(Color.black));
				gridFrame.add(coloredTiles); 
			}
		}
		
		gridFrame.setPreferredSize(new Dimension(400,400));
		
		JLabel round = new JLabel("Round: " + Round.numberOfRounds, SwingConstants.LEFT);
		round.setFont(new Font("Arial", Font.PLAIN, 20));
		JLabel score = new JLabel("Score: " + numberOfScore, SwingConstants.LEFT);
		score.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel empty = new JLabel("    ");
		JLabel empty1 = new JLabel("    ");
		
		pictureGridFrame.add(round, BorderLayout.NORTH);
		pictureGridFrame.add(empty1, BorderLayout.WEST);
		pictureGridFrame.add(gridFrame, BorderLayout.CENTER);
		pictureGridFrame.add(empty, BorderLayout.EAST);
		pictureGridFrame.add(score, BorderLayout.SOUTH);
		
		if(difficultyMode == "Easy")
		{
			setPreferredSize(new Dimension(500, 450));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("BeatRepeat");
			pack();
			setVisible(true);
		}
		else
		{
			listenCarefully();
		}
		playMelody(currentRound.getQuestionPictureToString());
		
		try 
		{
			Thread.sleep(3000);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		setVisible(false);
		pictureGridFrame.removeAll();
	}

	public void playMelody(String melody)
	{
		for(int noteIndex = 0; noteIndex < melody.length(); noteIndex++ )
		{
			playSound(currentRound.getNote(Character.getNumericValue(melody.charAt(noteIndex))), 
						1000);
		}
		
	}
	
	private static void playSound(File Sound, long timeSleep)
	{
		try 
		{ 
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
			Thread.sleep(timeSleep);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void listenCarefully()
	{
		listenFrame = getContentPane();
		listenFrame.setLayout(new BorderLayout());
		
		JLabel roundLabel = new JLabel("Round: " + Round.numberOfRounds, SwingConstants.CENTER);
		JLabel listenLabel = new JLabel("Listen Carefully...", SwingConstants.CENTER);
		JLabel scoreLabel = new JLabel("Score: " + numberOfScore, SwingConstants.CENTER);
		
		roundLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		listenLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		
		listenFrame.add(roundLabel, BorderLayout.NORTH);
		listenFrame.add(listenLabel, BorderLayout.CENTER);
		listenFrame.add(scoreLabel, BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(500,500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BeatRepeat");
		pack();
		setVisible(true);

	}
	
	public void nextRound()
	{
		currentRound = new Round(row, column);
	}
	
	
	public static void main(String[] args)
	{
		boolean coninue = true;
		BeatRepeatGUI newGame;
		while(coninue)
		{
			newGame = new BeatRepeatGUI();
			
			while(BeatRepeatGUI.tryAgain)
			{
				if(BeatRepeatGUI.difficultyMode != BeatRepeatGUI.HARD)
				{
					
					while(BeatRepeatGUI.correct)
					{
						newGame.nextRound();
						newGame.pictureGrid();
						newGame.multipleChoicesGrid();
					}
				}
				else
				{
					while(BeatRepeatGUI.correct)
					{
						newGame.nextRound();
						newGame.pictureGrid();
						newGame.buttonGrid();
					}
				}
				
				newGame.endScreen();
				
				Round.numberOfRounds = 0;
				BeatRepeatGUI.numberOfScore = 0;
				
				coninue = BeatRepeatGUI.backToMain;
			}
			System.out.println("Back to main");
		}
	}
}

