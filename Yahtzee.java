// Corinne Bintz and Courtenay Roche
// CS201 Section B 
// Final Project
// Yahtzee! 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;

@SuppressWarnings("serial")

//  https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
// http://www.rgagnon.com/javadetails/java-0468.html


public class Yahtzee extends Applet implements ActionListener, Runnable {
	// color constants
	static final Color black = Color.black;
	static final Color white = Color.white;
	static final Color red = Color.red;
	static final Color yellow = Color.yellow;
	static final Color orange = Color.orange;
	static final Color blue = Color.blue;
	static final Color lblue = new Color(200, 220, 255);
	YahtzeeCanvas c;
	Button roll, threeX, fourX, fullHouse, smallStraight, largeStraight, buttonYahtzee,
	newTurn, chance, reset;
	public Label score1, score2, score3, round, rollCount, turnLabel, scoreOne, scoreTwo,
	scoreThree, total, total1, winner;
	int score=0, playerTotalScore, compTotalScore, scorePressed, turnCount;
	Image dOne, dTwo, dThree, dFour, dFive, dSix, blank;


	boolean animate = false;   // whether we're animating
	long starttime = 0;        // animation start time
	Thread mythread;  
	String x;				// string for winner 

	// creating user interface
	// includes scores for each round, total score for player1 and computer
	// buttons for each scoring category, canvas for dice,
	// roll, round, winner, and turn labels
	public void init() {
		dOne = getImage(getDocumentBase(), "1dice.png");
		dTwo = getImage(getDocumentBase(), "2dice.png");
		dThree = getImage(getDocumentBase(), "3dice.png");
		dFour = getImage(getDocumentBase(), "4dice.png");
		dFive = getImage(getDocumentBase(), "5dice.png");
		dSix = getImage(getDocumentBase(), "6dice.png");
		blank = getImage(getDocumentBase(), "White_square.png");
		c = new YahtzeeCanvas(dOne, dTwo, dThree, dFour, dFive,dSix);
		c.setBackground(white);
		c.addMouseListener(c);
		setFont(new Font("AmericanTypewriter", Font.BOLD, 28));
		setLayout(new BorderLayout(2,2));
		this.setBackground(black);
		Panel p1 = new Panel();
		p1.setLayout(new BorderLayout());
		p1.setBackground(white);
		Panel p9 = new Panel();
		p9.setLayout(new GridLayout(5,1));
		Label yahtzee = new Label("YAHTZEE!");
		yahtzee.setAlignment(Label.CENTER);
		yahtzee.setBackground(lblue);
		p9.add(yahtzee);
		round = new Label(" Round:" + c.round);
		round.setBackground(white);
		round.setFont(new Font("AmericanTypewriter", Font.BOLD, 20));
		p9.add(round);
		rollCount = new Label(" Rolls: " + c.roll);
		rollCount.setBackground(white);
		rollCount.setFont(new Font("AmericanTypewriter", Font.BOLD, 20));
		p9.add(rollCount);
		turnLabel = new Label(" Turn: Player 1");
		turnLabel.setBackground(white);
		turnLabel.setFont(new Font("AmericanTypewriter", Font.BOLD, 20));
		p9.add(turnLabel);
		winner = new Label(" Winner: ");
		winner.setBackground(white);
		winner.setFont(new Font("AmericanTypewriter", Font.BOLD, 20));
		winner.setForeground(blue);
		p9.add(winner);
		p1.add("North", p9);
		p1.add("Center", c);
		roll = new Button("ROLL!");
		roll.setBackground(red);
		roll.addActionListener(this);
		p1.add("South", roll);



		this.add("Center", p1);

		Panel p2 = new Panel();
		p2.setLayout(new GridLayout(3,1,2,2));
		p2.setBackground(black);
		Label round1 = new Label("1");
		round1.setBackground(lblue);
		p2.add(round1);
		Label round2 = new Label("2");
		round2.setBackground(lblue);
		p2.add(round2);
		Label round3 = new Label("3");
		round3.setBackground(lblue);
		p2.add(round3);


		Panel p3 = new Panel();
		p3.setLayout(new GridLayout(3,1,2,2));
		p3.setBackground(black);
		p3.setFont(new Font("AmericanTypewriter", Font.PLAIN, 46));
		score1 = new Label(Integer.toString(score));
		score1.setBackground(white);
		score1.setAlignment(Label.CENTER);
		p3.add(score1);
		score2 = new Label(Integer.toString(score));
		score2.setBackground(white);
		score2.setAlignment(Label.CENTER);
		p3.add(score2);
		score3 = new Label(Integer.toString(score));
		score3.setBackground(white);
		score3.setAlignment(Label.CENTER);
		p3.add(score3);

		Panel p4 = new Panel();
		p4.setBackground(black);
		p4.setLayout(new BorderLayout(2,2));
		total = new Label("Total: " + Integer.toString(playerTotalScore));
		total.setBackground(white);
		total.setFont(new Font("AmericanTypeWriter", Font.BOLD, 24));
		Label player = new Label("Player 1 ");
		player.setBackground(yellow);
		p4.add("South", total);
		p4.add("North", player);
		p4.add("Center", p3);
		p4.add("West", p2);
		this.add("West", p4);

		Panel p5 = new Panel();
		p5.setLayout(new GridLayout(3,1,2,2));
		p5.setBackground(black);
		Label roundOne = new Label("1");
		roundOne.setBackground(lblue);
		p5.add(roundOne);
		Label roundTwo = new Label("2");
		roundTwo.setBackground(lblue);
		p5.add(roundTwo);
		Label roundThree = new Label("3");
		roundThree.setBackground(lblue);
		p5.add(roundThree);

		Panel p6 = new Panel();
		p6.setFont(new Font("AmericanTypewriter", Font.PLAIN, 46));
		p6.setLayout(new GridLayout(3,1,2,2));
		p6.setBackground(black);
		scoreOne = new Label(Integer.toString(score));
		scoreOne.setBackground(white);
		scoreOne.setAlignment(Label.CENTER);
		p6.add(scoreOne);
		scoreTwo = new Label(Integer.toString(score));
		scoreTwo.setBackground(white);
		scoreTwo.setAlignment(Label.CENTER);
		p6.add(scoreTwo);
		scoreThree = new Label(Integer.toString(score));
		scoreThree.setBackground(white);
		scoreThree.setAlignment(Label.CENTER);
		p6.add(scoreThree);

		Panel p7 = new Panel();
		p7.setBackground(black);
		p7.setLayout(new BorderLayout(2,2));
		Label computer = new Label("Computer");
		total1 = new Label("Total: " + Integer.toString(compTotalScore));
		total1.setBackground(white);
		total1.setFont(new Font("AmericanTypeWriter", Font.BOLD, 24));
		computer.setBackground(yellow);
		p7.add("South", total1);
		p7.add("North", computer);
		p7.add("Center", p6);
		p7.add("East", p5);
		this.add("East", p7);

		Panel p8 = new Panel();
		p8.setLayout(new GridLayout(2,4));
		threeX = new Button("3-of-a-kind");
		threeX.addActionListener(this);
		p8.add(threeX);
		fourX = new Button("4-of-a-kind");
		p8.add(fourX);
		fourX.addActionListener(this);
		fullHouse = new Button("Full House");
		fullHouse.addActionListener(this);
		p8.add(fullHouse);
		buttonYahtzee = new Button("YAHTZEE!");
		p8.add(buttonYahtzee);
		smallStraight = new Button("Small straight");
		smallStraight.addActionListener(this);
		p8.add(smallStraight);
		largeStraight = new Button("Large straight");
		largeStraight.addActionListener(this);
		p8.add(largeStraight);
		chance = new Button("Chance");
		chance.addActionListener(this);
		p8.add(chance);
		newTurn = new Button("Turn");
		newTurn.setBackground(orange);
		newTurn.addActionListener(this);
		p8.add(newTurn);


		Panel p10 = new Panel();
		p10.setLayout(new GridLayout(1,1));
		reset=new Button("Reset!");
		reset.setBackground(red);
		reset.addActionListener(this);
		p10.add(reset);

		Panel p11 = new Panel();
		p11.setLayout(new BorderLayout());
		p11.add("Center", p8);
		p11.add("East", p10);
		this.add("South", p11);


	}



	// starts animation 
	public void go() {
		animate = true;
		starttime = System.currentTimeMillis();
		repaint();
	}
	public void start() {
        mythread = new Thread(this);
        mythread.start();
    }

    public void stop() {
        mythread = null;
    }
    
	// runs and animates the computer's rolls 
	public void run() {
		while (true) { // main animation loop -- runs constantly
			if (animate) { // we're animating
				double t = (System.currentTimeMillis() - starttime) / 1000.0;   // elapsed time of animation

				if (t < 0.5) { 
					computerPlays();
				} 
				else if (t<1.5) {
					computerPlays();
				} 
				else if (t<2.0) {
					computerPlays();
				}
				else {
					animate=false;
					computerScores();
				}

				repaint(); // note repaint is only called if animate > 0

			}
			try {
				int fps = 2; // frame rate (frames per second)
				Thread.sleep(1000 / fps); // wait time in milliseconds
			} catch (InterruptedException e) { }

		}


	}

	// determine winner of game by comparing total scores
	public String determineWinner() {
		if (playerTotalScore>compTotalScore)
			return x = "Player1";
		else 
			return x = "Computer";
	}


	// call method for each button
	public void actionPerformed(ActionEvent e) {


		//roll the dice 
		if (e.getSource() == roll) {
			if (c.roll<3) {
				c.roll+=1;
				rollCount.setText(" Rolls: " +c.roll);
				c.roll(null);

			}

		}

		// for each button, check if category applies to dice
		// can only score once per turn
		// if a category applies, its valued is scored and you cannot score again by 
		// pressing another score button

		// check if dice have three-of-a-kind
		// if so, score the sum of the dice
		else if (e.getSource() == threeX) {
			if (scorePressed==0 && c.roll>0 && (turnCount==0 || turnCount==2 || turnCount==4))
				if (c.threeOfKind()) {
					score+=c.diceTotal();
					playerTotalScore+=score;
					scorePressed+=1;
					writePlayerScore();
				}
		}

		// check if dice have four-of-a-kind
		// if so, score the sum of the dice
		else if (e.getSource() == fourX) {
			if (scorePressed==0 && c.roll>0 && (turnCount==0 || turnCount==2 || turnCount==4))
				if (c.fourOfKind()) {
					score+=c.diceTotal();
					playerTotalScore+=score;
					scorePressed+=1;
					writePlayerScore();
				}
		}

		// check if dice constitute a full house
		// if so, score 25 points
		if (e.getSource() == fullHouse) {
			if (scorePressed==0 && c.roll>0 && (turnCount==0 || turnCount==2 || turnCount==4))
				if (c.fullHouse()) {
					score+=25;
					playerTotalScore+=score;
					scorePressed+=1;
					writePlayerScore();
				}

		}

		// check if dice have a small straight
		// if so, score 30 points
		if (e.getSource() == smallStraight) {
			if (scorePressed==0 && c.roll>0 && (turnCount==0 || turnCount==2 || turnCount==4))
				if (c.smallStraight()) {
					score+=30;
					playerTotalScore+=score;
					scorePressed+=1;
					writePlayerScore();
				}
		}

		// check if dice constitute a small straight
		// if so, score 40 points
		if (e.getSource() == largeStraight) {
			if (scorePressed==0 && c.roll>0 && (turnCount==0 || turnCount==2 || turnCount==4))
				if (c.largeStraight()) {
					score+=40;
					playerTotalScore+=score;
					scorePressed+=1;
					writePlayerScore();
				}
		}


		// check if dice constitute a yahtzee
		// if so, score 50 points
		if (e.getSource() == buttonYahtzee) {
			if (scorePressed==0 && c.roll>0 && (turnCount==0 || turnCount==2 || turnCount==4))
				if (c.yahtzeeRoll()) {
					score+=50;
					playerTotalScore+=score;
					scorePressed+=1;
					writePlayerScore();
				}

		}

		// apply the chance category
		// score (sum of dice)/2
		if (e.getSource() == chance) {
			if (scorePressed==0 && c.roll>0 && (turnCount==0 || turnCount==2 || turnCount==4)) {
				score+=c.diceTotal()/2;
				playerTotalScore+=score;
				scorePressed+=1;
				writePlayerScore();
			}
		}

		// change turns (from player1 to computer or computer to player1)
		// if changing turns from computer to player, start new round
		if (e.getSource() == newTurn) {
			if (turnCount==0 || turnCount==2 || turnCount==4) {
				score=0;
				c.changeTurn();
				turnLabel.setText(" Turn: Computer");
				go();

			}

			if (turnCount==1 || turnCount==3)  {
				clearDice();
				score=0;
				c.newRound();
				round.setText(" Round:" + c.round);
				turnLabel.setText(" Turn: Player1");
			}

			rollCount.setText(" Rolls:" + c.roll);
			turnCount += 1;
			scorePressed=0;

		}

		// initiate a new game 
		if (e.getSource() == reset) {
			clearDice();
			c.roll=0;
			c.round=1;
			score=0;
			playerTotalScore=0;
			compTotalScore=0;
			scorePressed=0;
			turnCount=0;
			winner.setText(" Winner: ");
			rollCount.setText(" Rolls: " + c.roll);
			round.setText(" Round: " + c.round);
			turnLabel.setText(" Turn: Player1");
			score1.setText(Integer.toString(score));
			score2.setText(Integer.toString(score));
			score3.setText(Integer.toString(score));
			scoreOne.setText(Integer.toString(score));
			scoreTwo.setText(Integer.toString(score));
			scoreThree.setText(Integer.toString(score));
			total.setText("Total: " +score);
			total1.setText("Total: " +score);

		}

	}

	// simulate computer's turn
	// randomly select two dice to hold each roll
	// roll three times
	public void computerPlays() {
		Random rand = new Random();
		int random1 = rand.nextInt(5);
		int random2= rand.nextInt(5);
		ActionEvent ae = new ActionEvent((Object)roll, ActionEvent.ACTION_PERFORMED, "");
		roll.dispatchEvent(ae);	
		for(int i = 0; i< 3; i++) {

			try {
				//sending the actual Thread of execution to sleep X milliseconds
				Thread.sleep(500);
			} catch(InterruptedException ie) {}
			c.dice[random1].held=true;
			c.dice[random2].held=true;
			roll.dispatchEvent(ae);	

		}
	}



	// score computer's rolls
	// checks each category in order below and scores if the category applies
	// like player, can only score once
	public int computerScores() {
		if (c.fullHouse()) {
			score+=25;
			compTotalScore+=score;
			writeComputerScore();
			return compTotalScore;
		}
		else if (c.fourOfKind()) {
			score+=c.diceTotal();
			compTotalScore+=score;
			writeComputerScore();
			return compTotalScore;
		}
		else if (c.threeOfKind()) {
			score+=c.diceTotal();
			compTotalScore+=score;
			writeComputerScore();
			return compTotalScore;
		}
		else if (c.largeStraight()) {
			score+=40;
			compTotalScore+=score;
			writeComputerScore();
			return compTotalScore;
		}
		else if (c.smallStraight()) {
			score+=30;
			compTotalScore+=score;
			writeComputerScore();
			return compTotalScore;
		}
		else if (c.yahtzeeRoll()) {
			score+=50;
			compTotalScore+=score;
			writeComputerScore();
			return compTotalScore;
		}
		else {
			score+=(c.diceTotal()/2);
			compTotalScore+=score;
			writeComputerScore();
			return compTotalScore;
		}

	}

	// update computer score labels 
	public void writeComputerScore() {
		if (c.round==1) {
			scoreOne.setText(Integer.toString(score));
		}
		else if (c.round==2) {
			scoreTwo.setText(Integer.toString(score));
		}
		else if (c.round==3) {
			scoreThree.setText(Integer.toString(score));
			winner.setText(" Winner: " + determineWinner());
		}
		total1.setText("Total: " + compTotalScore);	
	}

	// update player score labels
	public void writePlayerScore() {
		if (c.round==1) 
			score1.setText(Integer.toString(score));
		else if (c.round==2)
			score2.setText(Integer.toString(score));
		else if (c.round==3) {
			score3.setText(Integer.toString(score));
		}
		total.setText("Total: " + playerTotalScore);
	}

	// clear the dice from the screen
	public void clearDice() {
		for (int i=0; i<5; i++) {
			if (c.dice[i].held)
				c.dice[i].held=!c.dice[i].held;
			c.dice[i].myImage=blank;
		}
		c.repaint();
	}

}

@SuppressWarnings("serial")
class YahtzeeCanvas extends Canvas implements MouseListener {
	// instance variables representing the game
	int n = 5;	// number of dice 
	int border = 75;
	int size = 125;
	Image dOne, dTwo, dThree, dFour, dFive, dSix;	// images used for dice 
	Dice[] dice = new Dice[n]; 	// array of dice from Dice.java class 
	int roll;				// keeps track of number of rolls 
	int threeValue;			// value of dice in three-of-kind, used to check if fullHouse
	int round=1;				// round value 
	long starttime = 0;       // animation start time
	Thread mythread;           // thread for animation  
	boolean animate = false;   // whether we're animating

	// initialize the canvas
	public YahtzeeCanvas(Image d1, Image d2, Image d3, Image d4, Image d5, Image d6) {
		dOne= d1;
		dTwo = d2;
		dThree = d3;
		dFour = d4;
		dFive = d5;
		dSix = d6;
		for (int i = 0; i < n; i++) {
			dice[i]= new Dice();
		}
	}

	// draw the dice
	public void paint(Graphics g)
	{
		for (int i = 0; i < n; i++) {
			int x = i * size + border;
			int y = border;

			if (!dice[i].held) {
				g.drawImage(dice[i].myImage, x , y, this);
			}
			else {
				g.setColor(Color.red);
				g.drawRect(x-20, y-20, size, size);
				g.drawImage(dice[i].myImage, x , y, this);
			}

		}
	}


	// check if clicked in die area
	// if clicked in die area, hold die while rolling
	public void mousePressed(MouseEvent event) {
		Point p = event.getPoint();

		int x = p.x - border;
		int y = p.y - border;
		if (x >= 0 && x < n*size &&
				y >= 0 && y < size) {
			int k = x / size;
			dice[k].held = !dice[k].held;
		}
		repaint();

	}

	// roll the dice 
	public void roll(Graphics g) {
		if (roll<4) {
			assignNewValue();
			assignImage();
			repaint();
		}

	}

	// set up a new round, starting with player1
	public void newRound() {
		if (round<3) {
			for (int i=0; i<n; i++)
				if (dice[i].held) {
					dice[i].held=!dice[i].held;
					repaint();
				}
			roll=0;
			round+=1;
		}
	}



	// randomly assign a value of 1-6 for each die
	public void assignNewValue () {
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			if (!dice[i].held) {
				int random1 = rand.nextInt(6)+1;
				dice[i].value=random1;
			}
		}

	}

	// assign the image that corresponds to each die's randomly assigned value 
	public void assignImage() {
		for (int i = 0; i < n; i++) {
			if (dice[i].value == 1)
				dice[i].myImage = dOne;
			else if (dice[i].value == 2)
				dice[i].myImage = dTwo;
			else if (dice[i].value == 3)
				dice[i].myImage = dThree;
			else if (dice[i].value == 4)
				dice[i].myImage = dFour;
			else if (dice[i].value == 5)
				dice[i].myImage = dFive;
			else
				dice[i].myImage = dSix;
		}
	}


	// check if dice have three-of-a-kind (exclusive to four-of-a-kind)
	public boolean threeOfKind () {
		Vector<Integer> threes = new Vector<Integer>();
		for (int i = 0; i<n; i++) {
			for (int x = i+1; x<n; x++) {
				if (dice[i].value == dice[x].value) {
					threes.add(dice[x].value);
				}
			}
			if (threes.size() == 2) {
				threeValue=threes.get(0);
				return true;
			}
			else if (threes.size()>2)
				return false;
			else
				threes.clear();
		}

		return false;
	}

	// check if dice have four-of-a-kind
	public boolean fourOfKind () {
		Vector<Integer> fours = new Vector<Integer>();
		for (int i = 0; i<n; i++) {
			for (int x = i+1; x<n; x++) {
				if (dice[i].value == dice[x].value) {
					fours.add(dice[x].value);
				}
			}
			if (fours.size() == 3) 
				return true;
			else if (fours.size()>3)
				return false;
			else
				fours.clear();

		}	
		return false;
	}

	// check if dice constitute a yahtzee (five-of-a-kind)
	public boolean yahtzeeRoll() {
		Vector<Integer> fives = new Vector<Integer>();
		int i=0;
		for (int x = i+1; x<n; x++) {
			if (dice[i].value== dice[x].value) {
				fives.add(dice[x].value);
			}

		}
		if (fives.size() == 4) 
			return true;
		else
			return false;

	}

	// check if dice constitute a large straight (5 dice in order)
	public boolean largeStraight () {
		Vector<Integer> checkLargeStraight = new Vector<Integer>();
		for (int i = 0; i<n; i++) {
			checkLargeStraight.add(dice[i].value);
		}
		for (int i = 1; i<6; i++) {
			if (checkLargeStraight.contains(i))
				continue;
			else
				for (int k = 2; k<7; k++) {
					if (checkLargeStraight.contains(k))
						continue;
					else
						return false;
				}
		}

		return true;
	}

	// check if dice constitute a small straight (4 dice in order)
	public boolean smallStraight() {
		Vector<Integer> smallStraight = new Vector<Integer>();
		for (int i = 0; i<n; i++) {
			smallStraight.add(dice[i].value);
		}
		for (int i = 1; i<5; i++) {
			if (smallStraight.contains(i))
				continue;
			else
				for (int k = 2; k<6; k++) {
					if (smallStraight.contains(k))
						continue;
					else
						for (int j = 3; j<7; j++) {
							if (smallStraight.contains(j))
								continue;
							else
								return false;
						}
				}

		}	
		if (largeStraight())
			return false;
		else
			return true;
	}

	// check if dice constitute a full house (three-of-a-kind and two-of-a-kind)
	public boolean fullHouse() {
		Vector<Integer> checkFullHouse = new Vector<Integer>();
		for (int i = 0; i<n; i++) {
			checkFullHouse.add(dice[i].value);
		}
		if (threeOfKind()) {
			int i = 0;
			while (checkFullHouse.size()>2) {
				if (checkFullHouse.get(i) == threeValue) {
					checkFullHouse.remove(i);
				}
				else
					i++;
			}
			if (checkFullHouse.get(0) == checkFullHouse.get(1)) 
				return true;
			else 
				return false;
		}	
		else
			return false;

	}

	// sum the value of dice
	public int diceTotal() {
		int sum=0;
		for (int i=0; i<n; i++) {
			sum+=dice[i].value;
		}
		return sum;
	}


	// switch from player to computer 
	public void changeTurn () {
		for (int i=0; i<n; i++)
			if (dice[i].held) {
				dice[i].held=!dice[i].held;
				repaint();
			}
		roll=0;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
