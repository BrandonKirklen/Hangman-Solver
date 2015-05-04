import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MainWindow extends JFrame {
	
	private int remainingGuesses;
	private int uniqueLettersRemaining;
	private String wrongGuesses;
	private String word;
	private String visible;

	public MainWindow() {
		final int startingGuesses = 10;
		remainingGuesses = startingGuesses;
		wrongGuesses = "";
		word = getWord();
		uniqueLettersRemaining = uniqueChars(word);

		visible = "";

		for(int i = 0; i < word.length(); ++i) {
			visible += "_ ";
		}

		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());
		
		final JLabel status = new JLabel("You have "+remainingGuesses+" guesses remaining", SwingConstants.CENTER);
		final JLabel wrong = new JLabel("Wrong guesses so far: "+wrongGuesses);
		final JLabel visibleLabel = new JLabel(visible, SwingConstants.CENTER);
		final JTextField input = new JTextField();
		final JMenuBar menubar = new JMenuBar();

		final HangmanFigure hf = new HangmanFigure();

		////////////////////////////////MENU BAR STUFF/////////////////////////
		JMenu file = new JMenu("File");
		JMenu game = new JMenu("Game");

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		JMenuItem nMenuItem = new JMenuItem("New Game");
		nMenuItem.setMnemonic(KeyEvent.VK_N);
		nMenuItem.setToolTipText("Start A New Game");
		nMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				word = getWord();
				remainingGuesses = startingGuesses;
				wrongGuesses = "";
				uniqueLettersRemaining = uniqueChars(word);
				status.setText("You have " + remainingGuesses + " guesses remaining");
				wrong.setText("Wrong guesses so far: " + wrongGuesses);
				visible = "";
				for(int i = 0; i < word.length(); ++i) {
					visible += "_ ";
				}
				visibleLabel.setText(visible);
				hf.set(0);
			}
		});

		JMenuItem hMenuItem = new JMenuItem("Hint");
		hMenuItem.setMnemonic(KeyEvent.VK_H);
		hMenuItem.setToolTipText("Get a Hint");
		hMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) { //needs to give them a letter
				remainingGuesses -= 2;
				status.setText("You have " + remainingGuesses + " guesses remaining");
				hf.set(startingGuesses - remainingGuesses);
			}
		});

		file.add(nMenuItem);
		file.add(eMenuItem);
		game.add(hMenuItem);

		menubar.add(file);
		menubar.add(game);
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
						if (--remainingGuesses >= 0) { //reduce 1 from guesses, if they didn't lose do this
							status.setText("You have " + remainingGuesses + " guesses remaining");
							wrongGuesses += text + " ";
							wrong.setText("Wrong guesses so far: " + wrongGuesses);
							hf.set(startingGuesses - remainingGuesses); //add a limb to the hangman
						} else { //if they lost do this
							status.setText("You lost: the word was " + word);
							input.setEnabled(false);
						}
					} else { //they guessed right

						//String actualVisible = "";
						//for(int i = 0; i < visible.length(); i+=2) {
						//	actualVisible += visible.charAt(i);
						//}

						//if(actualVisible.equals(word)) {
						//	status.setText("Congratulations, you have won!");
						//	input.setEnabled(false);
						//}

						if (--uniqueLettersRemaining == 0) {
							status.setText("Congratulations, you have won!");
							input.setEnabled(false);
						}
					}

				} else {
					System.out.println("Invalid input!");
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

	private String getWord() { //make this work
		return "cat";
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
}