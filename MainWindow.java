import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class MainWindow extends JFrame {
	
	private int remainingGuesses;
	private int uniqueLettersRemaining;
	private int diff;
	private String wrongGuesses;
	private String word;
	private String visible;

	HangmanWordChoice currentDictionary = new HangmanWordChoice("wordlist.txt");

	public MainWindow() {
		super("Hangman!"); //give frame a title
		diff = 1;
		final int startingGuesses = 10;
		remainingGuesses = startingGuesses;
		wrongGuesses = "";
		word = currentDictionary.newWord(diff);
		uniqueLettersRemaining = uniqueChars(word);

		visible = "";

		for(int i = 0; i < word.length(); ++i) {
			visible += "_ ";
		}

		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());
		//set background color
		corePanel.setOpaque(true);
		corePanel.setBackground(new Color(126,200, 47));
		this.setBackground(new Color(126,200, 47));
		
		final JLabel status = new JLabel("You have "+remainingGuesses+" guesses remaining", SwingConstants.CENTER);
		final JLabel wrong = new JLabel("Wrong guesses so far: "+wrongGuesses);
		final JLabel visibleLabel = new JLabel(visible, SwingConstants.CENTER);
		final JTextField input = new JTextField();
		final JMenuBar menubar = new JMenuBar();

		final HangmanFigure hf = new HangmanFigure();

		////////////////////////////////MENU BAR STUFF/////////////////////////
		JMenu file = new JMenu("File");
		JMenu game = new JMenu("Game");
		JMenu difficulty = new JMenu("Difficulty");

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		JMenuItem hMenuItem = new JMenuItem("Hint");

		JMenuItem nMenuItem = new JMenuItem("New Game");
		nMenuItem.setMnemonic(KeyEvent.VK_N);
		nMenuItem.setToolTipText("Start A New Game");
		nMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				word = currentDictionary.newWord(diff);
				remainingGuesses = startingGuesses;
				wrongGuesses = "";
				uniqueLettersRemaining = uniqueChars(word);
				status.setText("You have " + remainingGuesses + " guesses remaining");
				wrong.setText("Wrong guesses so far: " + wrongGuesses);
				visible = "";
				for (int i = 0; i < word.length(); ++i) {
					visible += "_ ";
				}
				hMenuItem.setEnabled(true);
				visibleLabel.setText(visible);
				hf.set(0);
				input.setText("");
				input.setEnabled(true);
			}
		});

		hMenuItem.setMnemonic(KeyEvent.VK_H);
		hMenuItem.setToolTipText("Get a Hint");
		hMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				remainingGuesses -= 3;
				if (remainingGuesses <= 3) {
					hMenuItem.setEnabled(false);
				}
				if (remainingGuesses > 1) {
					status.setText("You have " + remainingGuesses + " guesses remaining");
				} else {
					status.setText("You have " + remainingGuesses + " guess remaining");
				}
				hf.set(startingGuesses - remainingGuesses);

				String actualVisible = "";
				for (int i = 0; i < visible.length(); i += 2) {
					actualVisible += visible.charAt(i);
				}
				ArrayList<Integer> indexes = new ArrayList<>();
				for (int i = 0; i < actualVisible.length(); i++) {
					if (("_").equals(actualVisible.charAt(i) + "")) {
						indexes.add(i);
					}
				}
				Random rnd = new Random();
				int choice = rnd.nextInt(indexes.size());
				String letter = "" + word.charAt(indexes.get(choice));

				for (int i = 0; i < word.length(); ++i) { //look through word for guessed character
					if (letter.charAt(0) == word.charAt(i)) {

						String newVisible = "";
						for (int j = 0; j < visible.length(); ++j) { //update word
							if (j == (i * 2)) {
								newVisible += word.charAt(i);
							} else {
								newVisible += visible.charAt(j);
							}
						}
						visible = newVisible;
						visibleLabel.setText(visible);
					}
				}

				uniqueLettersRemaining--;
				if (uniqueLettersRemaining <= 2) {
					hMenuItem.setEnabled(false);
				}
			}
		});

		JMenuItem easyMenuItem = new JMenuItem("Easy");
		JMenuItem mediumMenuItem = new JMenuItem("Medium");
		mediumMenuItem.setEnabled(false);
		JMenuItem hardMenuItem = new JMenuItem("Hard");

		easyMenuItem.setMnemonic(KeyEvent.VK_E);
		easyMenuItem.setToolTipText("Easy Difficulty");
		easyMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				diff = 0;
				easyMenuItem.setEnabled(false);
				mediumMenuItem.setEnabled(true);
				hardMenuItem.setEnabled(true);
				nMenuItem.doClick();
			}
		});

		mediumMenuItem.setMnemonic(KeyEvent.VK_M);
		mediumMenuItem.setToolTipText("Medium Difficulty");
		mediumMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				diff = 1;
				easyMenuItem.setEnabled(true);
				mediumMenuItem.setEnabled(false);
				hardMenuItem.setEnabled(true);
				nMenuItem.doClick();
			}
		});

		hardMenuItem.setMnemonic(KeyEvent.VK_A);
		hardMenuItem.setToolTipText("Hard Difficulty");
		hardMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				diff = 2;
				easyMenuItem.setEnabled(true);
				mediumMenuItem.setEnabled(true);
				hardMenuItem.setEnabled(false);
				nMenuItem.doClick();
			}
		});

		difficulty.add(easyMenuItem);
		difficulty.add(mediumMenuItem);
		difficulty.add(hardMenuItem);
		file.add(nMenuItem);
		file.add(eMenuItem);
		game.add(hMenuItem);

		menubar.add(file);
		menubar.add(game);
		menubar.add(difficulty);
		///////////////////////////////////////////////////////////
		
		JPanel southPanel = new JPanel(new GridLayout(4, 1));
		southPanel.add(status);
		southPanel.add(visibleLabel);
		southPanel.add(input);
		southPanel.add(wrong);
		
		corePanel.add(southPanel, BorderLayout.SOUTH);

		setJMenuBar(menubar);

		corePanel.add(hf, BorderLayout.CENTER);

		this.add(corePanel, BorderLayout.CENTER);
		
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String text = input.getText().toLowerCase(); //all of our words are lowercase

				if (text.length() == 1 && text.matches("[a-z]") && !(wrongGuesses.contains(text))) {

					boolean guessFound = false;

					for (int i = 0; i < word.length(); ++i) { //look through word for guessed character
						if (text.charAt(0) == word.charAt(i)) {
							guessFound = true; //found the character

							String newVisible = "";
							for (int j = 0; j < visible.length(); ++j) { //update word
								if (j == (i * 2)) {
									newVisible += word.charAt(i);
								} else {
									newVisible += visible.charAt(j);
								}
							}
							visible = newVisible;
							visibleLabel.setText(visible);
						}
					}

					if (!guessFound) { //they guessed wrong
						if (--remainingGuesses <= 3) {
							hMenuItem.setEnabled(false);
						}

						hf.set(startingGuesses - remainingGuesses); //update hangman

						if (remainingGuesses > 0) { //reduce 1 from guesses, if they didn't lose do this

							if (remainingGuesses > 1) {
								status.setText("You have " + remainingGuesses + " guesses remaining");
							} else {
								status.setText("You have " + remainingGuesses + " guess remaining");
							}

							wrongGuesses += text + " ";
							wrong.setText("Wrong guesses so far: " + wrongGuesses);
						} else { //if they lost do this
							status.setText("You lost: the word was " + word);
							input.setEnabled(false);
						}
					} else { //they guessed right

						String actualVisible = "";
						for (int i = 0; i < visible.length(); i += 2) {
							actualVisible += visible.charAt(i);
						}

						if (actualVisible.equals(word)) {
							status.setText("Congratulations, you have won!");
							input.setEnabled(false);
						}

						if (--uniqueLettersRemaining <= 2) {
							hMenuItem.setEnabled(false);
						}
					}

				} else {
					if (remainingGuesses > 1) {
						status.setText("Invalid input! " + remainingGuesses + " guesses remaining");
					} else {
						status.setText("Invalid input! " + remainingGuesses + " guess remaining");
					}
				}

				input.setText("");
			}

		});

		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static int uniqueChars(String s) { //returns min # of guesses
		char chars[] = s.toCharArray();
		int num = s.length();

		for (int i = 0; i < chars.length; i++) {
			if (i != s.indexOf(chars[i])) {
				num--;
			}
		}
		return num;
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
}
