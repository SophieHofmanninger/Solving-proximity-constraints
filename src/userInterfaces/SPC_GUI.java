/**
 * 
 */
package userInterfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;

import elements.Function;
import tool.SimpleInputChecker;
import tool.Tuple;
import unificationProblem.InputParser;
import unificationProblem.UnificationProblem;

import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 * Use the Algorithm via graphical user interface.
 * Singleton!
 * @author Jan-Michael Holzinger
 * @version 1.0
 */
public class SPC_GUI {

	private static SPC_GUI Instance = new SPC_GUI();
	private JFrame frmSolvingProximityConstraints;
	private JTextField txtlhs;
	private JTextField txtrhs;
	private Component verticalStrut;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JButton btnRead;
	private JPanel paneltop;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private JPanel panelConstraints;

	private String s="";
	private String t="";
	private float lambda=0;
	private ArrayList<JLabel> lol= new ArrayList<JLabel>();
	private ArrayList<JTextField> lot= new ArrayList<JTextField>();
	private JPanel panelCenter;
	private JPanel panelStart;
	private JLabel lblConstraints;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private JPanel panelEnd;
	private JLabel lblLambda;
	private JTextField txtL;
	private Component horizontalStrut_4;
	private JButton btnUnify;
	private Component horizontalStrut_5;
	private Component horizontalStrut_6;
	private Component horizontalStrut_7;
	private Component horizontalStrut_8;
	private Component horizontalStrut_9;
	private Component horizontalGlue_2;
	private Component horizontalStrut_10;
	private Component horizontalStrut_11;
	private Component horizontalStrut_12;

	UnificationProblem sTP;

	UnificationProblem intermediateResult;
	private JPanel panelSouth;
	private Component horizontalStrut_13;
	private Component horizontalStrut_14;
	private JPanel panelResult;
	private Component verticalStrut_4;
	private Component verticalStrut_5;
	private JLabel lblResults;
	private JLabel lblresultString;
	private JLabel lblSteps;
	private JLabel lblSteps1;
	private JLabel lblSteps2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SPC_GUI window = SPC_GUI.getInstance();
					window.initialize();
					window.frmSolvingProximityConstraints.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
	/**
	 * Returns the GUI.
	 * @return the GUI.
	 */
	private static SPC_GUI getInstance() {
		return Instance;
	}

	/**
	 * Forbid usage of Constructor.
	 */
	private SPC_GUI() {	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSolvingProximityConstraints = new JFrame();
		frmSolvingProximityConstraints.setResizable(false);
		frmSolvingProximityConstraints.setTitle("Solving Proximity Constraints");
		frmSolvingProximityConstraints.setBounds(100, 100, 650, 600);
		frmSolvingProximityConstraints.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSolvingProximityConstraints.getContentPane().setLayout(new BorderLayout(0, 0));

		paneltop = new JPanel();
		frmSolvingProximityConstraints.getContentPane().add(paneltop, BorderLayout.NORTH);
		paneltop.setLayout(new BorderLayout(0, 0));

		JPanel panelEq = new JPanel();
		paneltop.add(panelEq);
		panelEq.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelEq = new GridBagLayout();
		panelEq.setLayout(gbl_panelEq);

		horizontalStrut_4 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_4 = new GridBagConstraints();
		gbc_horizontalStrut_4.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_4.gridx = 6;
		gbc_horizontalStrut_4.gridy = 1;
		panelEq.add(horizontalStrut_4, gbc_horizontalStrut_4);

		verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_3.gridx = 2;
		gbc_verticalStrut_3.gridy =2;
		panelEq.add(verticalStrut_3, gbc_verticalStrut_3);

		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 0;
		panelEq.add(verticalStrut, gbc_verticalStrut);

		JLabel lblEquation = new JLabel("Equation:");
		GridBagConstraints gbc_lblEquation = new GridBagConstraints();
		gbc_lblEquation.insets = new Insets(0, 0, 5, 5);
		gbc_lblEquation.gridx = 0;
		gbc_lblEquation.gridy = 1;
		panelEq.add(lblEquation, gbc_lblEquation);

		txtlhs = new JTextField();
		GridBagConstraints gbc_txtlhs = new GridBagConstraints();
		gbc_txtlhs.insets = new Insets(0, 0, 5, 5);
		gbc_txtlhs.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtlhs.gridx = 1;
		gbc_txtlhs.gridy = 1;
		panelEq.add(txtlhs, gbc_txtlhs);
		txtlhs.setColumns(15);

		horizontalGlue = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalGlue.gridx = 2;
		gbc_horizontalGlue.gridy = 1;
		panelEq.add(horizontalGlue, gbc_horizontalGlue);

		JLabel lblunif = new JLabel("=?");
		GridBagConstraints gbc_lblunif = new GridBagConstraints();
		gbc_lblunif.insets = new Insets(0, 0, 5, 5);
		gbc_lblunif.gridx = 3;
		gbc_lblunif.gridy = 1;
		panelEq.add(lblunif, gbc_lblunif);

		horizontalGlue_1 = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue_1 = new GridBagConstraints();
		gbc_horizontalGlue_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalGlue_1.gridx = 4;
		gbc_horizontalGlue_1.gridy = 1;
		panelEq.add(horizontalGlue_1, gbc_horizontalGlue_1);

		txtrhs = new JTextField();
		GridBagConstraints gbc_txtrhs = new GridBagConstraints();
		gbc_txtrhs.insets = new Insets(0, 0, 5, 5);
		gbc_txtrhs.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtrhs.gridx = 5;
		gbc_txtrhs.gridy = 1;
		panelEq.add(txtrhs, gbc_txtrhs);
		txtrhs.setColumns(15);



		horizontalStrut = Box.createHorizontalStrut(20);
		paneltop.add(horizontalStrut, BorderLayout.WEST);

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		paneltop.add(horizontalStrut_1, BorderLayout.EAST);

		verticalStrut_1 = Box.createVerticalStrut(20);
		paneltop.add(verticalStrut_1, BorderLayout.NORTH);

		verticalStrut_2 = Box.createVerticalStrut(20);
		paneltop.add(verticalStrut_2, BorderLayout.SOUTH);

		panelCenter = new JPanel();
		frmSolvingProximityConstraints.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		panelStart = new JPanel();
		panelCenter.add(panelStart, BorderLayout.NORTH);
		GridBagLayout gbl_panelStart = new GridBagLayout();
		gbl_panelStart.columnWidths = new int[]{293, 60, 0};
		gbl_panelStart.rowHeights = new int[]{14, 0};
		gbl_panelStart.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelStart.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelStart.setLayout(gbl_panelStart);

		lblConstraints = new JLabel("Constraints:");
		GridBagConstraints gbc_lblConstraints = new GridBagConstraints();
		gbc_lblConstraints.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblConstraints.gridx = 0;
		gbc_lblConstraints.gridy = 0;
		panelStart.add(lblConstraints, gbc_lblConstraints);

		panelConstraints = new JPanel();
		panelConstraints.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelCenter.add(panelConstraints);

		panelEnd = new JPanel();
		panelCenter.add(panelEnd, BorderLayout.SOUTH);
		GridBagLayout gbl_panelEnd = new GridBagLayout();
		gbl_panelEnd.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelEnd.rowHeights = new int[]{0, 0};
		gbl_panelEnd.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panelEnd.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelEnd.setLayout(gbl_panelEnd);

		lblLambda = new JLabel("Lambda:");
		GridBagConstraints gbc_lblLambda = new GridBagConstraints();
		gbc_lblLambda.insets = new Insets(0, 0, 0, 5);
		gbc_lblLambda.gridx = 0;
		gbc_lblLambda.gridy = 0;
		panelEnd.add(lblLambda, gbc_lblLambda);

		horizontalGlue_2 = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue_2 = new GridBagConstraints();
		gbc_horizontalGlue_2.anchor = GridBagConstraints.WEST;
		gbc_horizontalGlue_2.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalGlue_2.gridx = 1;
		gbc_horizontalGlue_2.gridy = 0;
		panelEnd.add(horizontalGlue_2, gbc_horizontalGlue_2);

		txtL = new JTextField();
		txtL.setColumns(10);
		GridBagConstraints gbc_txtL = new GridBagConstraints();
		gbc_txtL.anchor = GridBagConstraints.WEST;
		gbc_txtL.insets = new Insets(0, 0, 0, 5);
		gbc_txtL.gridx = 2;
		gbc_txtL.gridy = 0;
		panelEnd.add(txtL, gbc_txtL);

		horizontalStrut_11 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_11 = new GridBagConstraints();
		gbc_horizontalStrut_11.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_11.gridx = 4;
		gbc_horizontalStrut_11.gridy = 0;
		panelEnd.add(horizontalStrut_11, gbc_horizontalStrut_11);

		horizontalStrut_12 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_12 = new GridBagConstraints();
		gbc_horizontalStrut_12.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_12.gridx = 6;
		gbc_horizontalStrut_12.gridy = 0;
		panelEnd.add(horizontalStrut_12, gbc_horizontalStrut_12);

		horizontalStrut_10 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_10 = new GridBagConstraints();
		gbc_horizontalStrut_10.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_10.gridx = 11;
		gbc_horizontalStrut_10.gridy = 0;
		panelEnd.add(horizontalStrut_10, gbc_horizontalStrut_10);

		horizontalStrut_8 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_8 = new GridBagConstraints();
		gbc_horizontalStrut_8.gridx = 13;
		gbc_horizontalStrut_8.gridy = 0;
		panelEnd.add(horizontalStrut_8, gbc_horizontalStrut_8);

		horizontalStrut_7 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_7 = new GridBagConstraints();
		gbc_horizontalStrut_7.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_7.gridx = 5;
		gbc_horizontalStrut_7.gridy = 0;
		panelEnd.add(horizontalStrut_7, gbc_horizontalStrut_7);

		horizontalStrut_6 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_6 = new GridBagConstraints();
		gbc_horizontalStrut_6.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_6.gridx = 7;
		gbc_horizontalStrut_6.gridy = 0;
		panelEnd.add(horizontalStrut_6, gbc_horizontalStrut_6);

		horizontalStrut_9 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_9 = new GridBagConstraints();
		gbc_horizontalStrut_9.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_9.gridx = 8;
		gbc_horizontalStrut_9.gridy = 0;
		panelEnd.add(horizontalStrut_9, gbc_horizontalStrut_9);

		horizontalStrut_5 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_5 = new GridBagConstraints();
		gbc_horizontalStrut_5.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_5.gridx = 9;
		gbc_horizontalStrut_5.gridy = 0;
		panelEnd.add(horizontalStrut_5, gbc_horizontalStrut_5);

		btnUnify = new JButton("Unify");
		btnUnify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				boolean allOk=true;
				try {
					lambda=Float.parseFloat(txtL.getText());
					if(lambda<0||lambda>1) throw new NumberFormatException();
					sTP.setLambda(lambda);
				}catch(NumberFormatException e) {
					allOk=false;
					inError
					("The value you have entered does not seem to be a float in [0,1].");
				}

				for(int i=0;i<lol.size();i++) {
					String both = lol.get(i).getText();
					int posOfColon = both.indexOf(',');
					String f=both.substring(1, posOfColon-1);
					String s=both.substring(posOfColon+2,both.length()-1);
					Tuple<Function> t = new Tuple<Function>(
							new Function(f),new Function(s));
					float value=0;
					try {
						value=Float.parseFloat(lot.get(i).getText());
						if(value<0||value>1) throw new NumberFormatException();
						sTP.closeCase(t, value);
					}catch(NumberFormatException e) {
						allOk=false;
						inError
						("The value you have entered for "+both +" does not seem to be a float in [0,1].");
						break;
					}
				}



				if(allOk && sTP.checkOpenCases()) {
					panelResult.revalidate();
					
					
					StringBuffer sb1 = new StringBuffer();
					StringBuffer sb2 = new StringBuffer();
					boolean result = sTP.solveNext(sb1);
					if(!result) {
						JOptionPane.showMessageDialog(frmSolvingProximityConstraints, "Pre-unification failed, there is no solution.", "Information", JOptionPane.INFORMATION_MESSAGE);
						lblLambda.setVisible(false);
						txtL.setVisible(false);
						btnUnify.setVisible(false);
						btnUnify.setEnabled(false);
					}else {
						intermediateResult=sTP.clone();
						result = sTP.solveNext(sb2);
						if(!result) {
							JOptionPane.showMessageDialog(frmSolvingProximityConstraints, "Constraint simplification failed, there is no solution.",
									"Information", JOptionPane.INFORMATION_MESSAGE);
						}else {
						lblresultString.setText(sTP.resultString());
						lblSteps1.setText(sb1.toString());
						lblSteps2.setText(sb2.toString());
						sTP=intermediateResult.clone();	
						panelResult.revalidate();
						}
						
					}
				}



			}
		});
		GridBagConstraints gbc_btnUnify = new GridBagConstraints();
		gbc_btnUnify.insets = new Insets(0, 0, 0, 5);
		gbc_btnUnify.gridx = 10;
		gbc_btnUnify.gridy = 0;
		panelEnd.add(btnUnify, gbc_btnUnify);
		btnRead = new JButton("Read");


		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelConstraints.removeAll();
				panelConstraints.revalidate();
				lblLambda.setVisible(true);
				txtL.setVisible(true);
				btnUnify.setVisible(true);
				btnUnify.setEnabled(true);
				panelConstraints.revalidate();

				lot.clear();
				lol.clear();
				s=txtlhs.getText();
				t=txtrhs.getText();
				String equation = s+" =? " +t;
				if(new SimpleInputChecker().check(equation)) {
					boolean allOk=true;

					sTP = InputParser.parse(equation).get(0);

					if(allOk && !(sTP.checkOpenCases())) {
						int cases= sTP.getNumberOfOpenCases();
						GridBagLayout gbl_panelCo = new GridBagLayout();
						panelConstraints.setLayout(gbl_panelCo);

						for(int i=0;i<cases;i++) {
							Tuple<Function> oC = sTP.getOpenCase(i);
							JLabel currentlbl = new JLabel("("+oC.getFirst().toString()+" , "+
									oC.getSecond().toString()+")");
							JTextField currenttxt = new JTextField();
							currenttxt.setColumns(4);

							lol.add(currentlbl);
							lot.add(currenttxt);
							GridBagConstraints gbc_lblcurr = new GridBagConstraints();
							gbc_lblcurr.insets = new Insets(0, 0, 5, 5);
							gbc_lblcurr.gridx = (i/9) * 3;
							gbc_lblcurr.gridy = i%9+1;
							panelConstraints.add(currentlbl, gbc_lblcurr);

							GridBagConstraints gbc_txtcurr = new GridBagConstraints();
							gbc_txtcurr.insets = new Insets(0, 0, 5, 0);
							gbc_txtcurr.gridx = (i/9)*3+1;
							gbc_txtcurr.gridy = i%9+1;
							panelConstraints.add(currenttxt, gbc_txtcurr);

							Component currstr = Box.createHorizontalStrut(5);
							GridBagConstraints gbc_strcurr = new GridBagConstraints();
							gbc_strcurr.insets = new Insets(0, 0, 5, 0);
							gbc_strcurr.gridx = (i/9)*3+2;
							gbc_strcurr.gridy = i%9+1;
							panelConstraints.add(currstr, gbc_strcurr);


						}

						panelConstraints.revalidate();
					}

				}else {
					inError("The equation contains errors.");
				}

			}


		});
		GridBagConstraints gbc_btnRead = new GridBagConstraints();
		gbc_btnRead.insets = new Insets(0, 0, 5, 0);
		gbc_btnRead.gridx = 7;
		gbc_btnRead.gridy = 1;
		panelEq.add(btnRead, gbc_btnRead);

		horizontalStrut_2 = Box.createHorizontalStrut(20);
		frmSolvingProximityConstraints.getContentPane().add(horizontalStrut_2, BorderLayout.WEST);

		horizontalStrut_3 = Box.createHorizontalStrut(20);
		frmSolvingProximityConstraints.getContentPane().add(horizontalStrut_3, BorderLayout.EAST);

		lblLambda.setVisible(false);
		txtL.setVisible(false);
		btnUnify.setVisible(false);
		btnUnify.setEnabled(false);
		
		panelSouth = new JPanel();
		frmSolvingProximityConstraints.getContentPane().add(panelSouth, BorderLayout.SOUTH);
				panelSouth.setLayout(new BorderLayout(0, 0));
				
				horizontalStrut_13 = Box.createHorizontalStrut(20);
				panelSouth.add(horizontalStrut_13, BorderLayout.EAST);
				
				horizontalStrut_14 = Box.createHorizontalStrut(20);
				panelSouth.add(horizontalStrut_14, BorderLayout.WEST);
				
				panelResult = new JPanel();
				panelResult.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				
				panelSouth.add(panelResult, BorderLayout.CENTER);
				GridBagLayout gbl_panelResult = new GridBagLayout();
				gbl_panelResult.columnWidths = new int[]{254, 41, 1, 1, 31, 1, 1, 0};
				gbl_panelResult.rowHeights = new int[]{14, 0};
				gbl_panelResult.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				gbl_panelResult.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				panelResult.setLayout(gbl_panelResult);
				
				lblResults = new JLabel("Results:");
				GridBagConstraints gbc_lblResults = new GridBagConstraints();
				gbc_lblResults.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblResults.insets = new Insets(0, 0, 0, 5);
				gbc_lblResults.gridx = 0;
				gbc_lblResults.gridy = 0;
				panelResult.add(lblResults, gbc_lblResults);
				
				lblresultString = new JLabel("");
				GridBagConstraints gbc_lblsigma = new GridBagConstraints();
				gbc_lblsigma.anchor = GridBagConstraints.WEST;
				gbc_lblsigma.insets = new Insets(0, 0, 0, 5);
				gbc_lblsigma.gridx = 0;
				gbc_lblsigma.gridy = 1;
				panelResult.add(lblresultString, gbc_lblsigma);
				
				lblSteps = new JLabel("Steps:");
				GridBagConstraints gbc_lblSteps = new GridBagConstraints();
				gbc_lblSteps.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblSteps.insets = new Insets(0, 0, 0, 5);
				gbc_lblSteps.gridx = 0;
				gbc_lblSteps.gridy = 3;
				panelResult.add(lblSteps, gbc_lblSteps);
				
				lblSteps1 = new JLabel("");
				GridBagConstraints gbc_lblSteps1 = new GridBagConstraints();
				gbc_lblSteps1.anchor = GridBagConstraints.WEST;
				gbc_lblSteps1.insets = new Insets(0, 0, 0, 5);
				gbc_lblSteps1.gridx = 0;
				gbc_lblSteps1.gridy = 4;
				panelResult.add(lblSteps1, gbc_lblSteps1);
				
				lblSteps2 = new JLabel("");
				GridBagConstraints gbc_lblSteps2 = new GridBagConstraints();
				gbc_lblSteps2.anchor = GridBagConstraints.WEST;
				gbc_lblSteps2.gridx = 0;
				gbc_lblSteps2.gridy = 5;
				panelResult.add(lblSteps2, gbc_lblSteps2);
				
				verticalStrut_4 = Box.createVerticalStrut(20);
				panelSouth.add(verticalStrut_4, BorderLayout.SOUTH);
				
				verticalStrut_5 = Box.createVerticalStrut(20);
				panelSouth.add(verticalStrut_5, BorderLayout.NORTH);

	}
	private void inError(String string) {
		JOptionPane.showMessageDialog(frmSolvingProximityConstraints, string, "Input Error", JOptionPane.ERROR_MESSAGE);


	}
}
