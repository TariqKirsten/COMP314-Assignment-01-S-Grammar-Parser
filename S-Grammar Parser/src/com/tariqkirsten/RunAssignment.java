package com.tariqkirsten;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.SwingConstants;

public class RunAssignment extends JFrame {

	private JPanel mainPane;
	private JTextField textfieldV;
	private JTextField textfieldT;
	private JTextField textfieldS;
	private JTextField textfieldPLeft;
	private JTextField textfieldPRight;
	private JTextField textfieldInput;
    private JTextArea textAreaOutput;
	private Grammar gram= new Grammar();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RunAssignment frame = new RunAssignment();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RunAssignment() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 782);
		mainPane = new JPanel();
		mainPane.setBackground(new Color(51, 153, 255));
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Grammar Input");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(65, 85, 192, 28);
		lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
		mainPane.add(lblNewLabel);
		
		JLabel labelV = new JLabel("V =");
		labelV.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelV.setBounds(32, 124, 39, 22);
		mainPane.add(labelV);
		
		textfieldV = new JTextField();
		textfieldV.setBounds(96, 127, 161, 20);
		mainPane.add(textfieldV);
		textfieldV.setColumns(10);
		
		JLabel label = new JLabel("{");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(83, 117, 16, 37);
		mainPane.add(label);
		
		JLabel label_1 = new JLabel("}");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_1.setBounds(262, 117, 16, 37);
		mainPane.add(label_1);
		
		JLabel labelT = new JLabel("T =");
		labelT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelT.setBounds(32, 164, 39, 22);
		mainPane.add(labelT);
		
		JLabel label_3 = new JLabel("{");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(83, 157, 16, 37);
		mainPane.add(label_3);
		
		textfieldT = new JTextField();
		textfieldT.setColumns(10);
		textfieldT.setBounds(96, 167, 161, 20);
		mainPane.add(textfieldT);
		
		JLabel label_4 = new JLabel("}");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_4.setBounds(262, 157, 16, 37);
		mainPane.add(label_4);
		
		JLabel labelS = new JLabel("S =");
		labelS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelS.setBounds(32, 204, 39, 22);
		mainPane.add(labelS);
		
		JLabel label_5 = new JLabel("{");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_5.setBounds(83, 197, 16, 37);
		mainPane.add(label_5);
		
		textfieldS = new JTextField();
		textfieldS.setColumns(10);
		textfieldS.setBounds(96, 207, 161, 20);
		mainPane.add(textfieldS);
		
		JLabel label_6 = new JLabel("}");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_6.setBounds(262, 197, 16, 37);
		mainPane.add(label_6);
		
		JLabel labelP = new JLabel("P:");
		labelP.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelP.setBounds(32, 244, 39, 22);
		mainPane.add(labelP);
		
		textfieldPLeft = new JTextField();
		textfieldPLeft.setBounds(96, 244, 37, 20);
		mainPane.add(textfieldPLeft);
		textfieldPLeft.setColumns(10);
		
		JLabel label_2 = new JLabel("->");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_2.setBounds(143, 242, 39, 22);
		mainPane.add(label_2);
		
		textfieldPRight = new JTextField();
		
		textfieldPRight.setColumns(10);
		textfieldPRight.setBounds(171, 244, 86, 20);
		mainPane.add(textfieldPRight);
		
		JButton addProductionRuleButton = new JButton("Add Rule");
		
		addProductionRuleButton.setBounds(362, 338, 115, 23);
		mainPane.add(addProductionRuleButton);
		
		JButton checkButton = new JButton("Check");
		
		checkButton.setBounds(480, 380, 161, 37);
		mainPane.add(checkButton);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList ruleList = new JList(listModel);
		ruleList.setFont(new Font("Monospaced", Font.PLAIN, 13));
		
		ArrayList<String> PLeft = new ArrayList<String>();//list for left hand side of production rule
		ArrayList<String> PRight = new ArrayList<String>();//list for right hand side of production rule
		
		JButton removeRuleButton = new JButton("Remove Rule");
		removeRuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PLeft.remove(ruleList.getSelectedIndex());//remove from left hand side of production rule
					PRight.remove(ruleList.getSelectedIndex());//remove from right hand side of production rule
				} catch (Exception e2) {
				}
				listModel.removeElement(ruleList.getSelectedValue());//delete from list
			}
		});
		removeRuleButton.setBounds(578, 338, 115, 23);
		mainPane.add(removeRuleButton);
		
		textfieldInput = new JTextField();
		textfieldInput.setEnabled(false);
		textfieldInput.setColumns(10);
		textfieldInput.setBounds(408, 461, 331, 20);
		mainPane.add(textfieldInput);
		
		JButton parseInputButton = new JButton("Parse Input");
		parseInputButton.setEnabled(false);
		
		parseInputButton.setBounds(526, 492, 115, 23);
		mainPane.add(parseInputButton);
		
		JLabel inputStringLabel = new JLabel("Input String");
		inputStringLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inputStringLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 17));
		inputStringLabel.setBounds(490, 428, 158, 22);
		mainPane.add(inputStringLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(40, 458, 331, 240);
		mainPane.add(scrollPane);
		
		textAreaOutput = new JTextArea();
		scrollPane.setViewportView(textAreaOutput);
		textAreaOutput.setEditable(false);
		textAreaOutput.setFont(new Font("Monospaced", Font.PLAIN, 13));
		
		JScrollPane listScrollPane = new JScrollPane(ruleList);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setBounds(362, 85, 331, 240);
		mainPane.add(listScrollPane);
		
		JButton loadInputFileButton = new JButton("Load Textfile");
		loadInputFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textAreaOutput.setText("");
					listModel.clear();
					PLeft.clear();
					PRight.clear();
					Scanner sc = new Scanner(new FileReader("input.txt"));//load textfile
					String line = sc.nextLine();//read line
					while(sc.hasNext()) {//loop while file has more lines
						
						if(line.equals("***")) {
							
							
							textfieldInput.setText(sc.nextLine());
							textfieldV.setText(sc.nextLine());
							textfieldT.setText(sc.nextLine());
							textfieldS.setText(stripWhitespaceComma(sc.nextLine()));
							
							line = sc.nextLine();
							while(!line.equals("***") && sc.hasNext() && !line.equals("end")){
								String [] information = line.split("->");//split the line into details array
							
								textfieldPLeft.setText(information[0]);//left handside of production rules
								textfieldPRight.setText(information[1]);//right hand side of production rules
								line = sc.nextLine();
								addProductionRuleButton.doClick();//auto add prod rule instead of waiting for user to manually press it
							}
							
							checkButton.doClick();// all rules loaded begin checking
							parseInputButton.doClick();
							if(line.equals("***")) {
								int option = JOptionPane.showOptionDialog(null, "Load next S-Grammar?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,null);
								
								if(option==0) {
									//clearing collections
									listModel.clear();
									PLeft.clear();
									PRight.clear();
									continue;
								}
								else {
									break;
								}
							}else if(line.equals("end")) {//EOF
								break;
							}
							
						}
						line = sc.nextLine(); 
					}
				} catch (FileNotFoundException exception) {
					JOptionPane.showMessageDialog(null, "Error! Input file not found.","", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		loadInputFileButton.setBounds(117, 709, 161, 28);
		mainPane.add(loadInputFileButton);
		
		JLabel lblSgrammarParser = new JLabel("S-Grammar Parser");
		lblSgrammarParser.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
		lblSgrammarParser.setBounds(269, 11, 228, 52);
		mainPane.add(lblSgrammarParser);
		
		
		addProductionRuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String leftSide = stripWhitespaceComma(textfieldPLeft.getText());
				String rightSide = stripWhitespaceComma(textfieldPRight.getText());
				
				if(!rightSide.contains("|")) {//no 'or' statements
					PLeft.add(leftSide);
					PRight.add(rightSide);
					listModel.addElement(leftSide + " -> "+rightSide);
				}
				else {//or statements found
					String [] details = rightSide.split("\\|");//split or statements
					
					for (int i = 0; i < details.length; i++) {//separate or statements into their own production rules
						PLeft.add(leftSide);
						PRight.add(details[i]);
						listModel.addElement(leftSide + " -> "+details[i]);
					}
				}
				textfieldPLeft.setText("");
				textfieldPRight.setText("");
				
			}
		});
		
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textfieldS.getText().length() !=1) {
					JOptionPane.showMessageDialog(null, "Please enter 1 character only for S", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					ArrayList<Character> V = stringToArrayList(stripWhitespaceComma(textfieldV.getText()));
					ArrayList<Character> T = stringToArrayList(stripWhitespaceComma(textfieldT.getText()));
					
					gram = new Grammar(V, T, textfieldS.getText().charAt(0), PLeft, PRight);//create new object
					if(gram.isValidGrammar()) {
						JOptionPane.showMessageDialog(null, "S-Grammar is valid.", "Valid S-Grammar", JOptionPane.INFORMATION_MESSAGE);
						parseInputButton.setEnabled(true);
						textfieldInput.setEnabled(true);
					}
					else {//grammar not valid
						JOptionPane.showMessageDialog(null, "S-Grammar is not valid.", "Not Valid S-Grammar", JOptionPane.ERROR_MESSAGE);
						textfieldInput.setEnabled(false);
						parseInputButton.setEnabled(false);
					}
					
					
				}
				
			}
		});
		parseInputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaOutput = gram.leftDerivation(textfieldInput.getText(), textAreaOutput);//call method in grammar class - returns output
				
			}
		});
		textfieldPRight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {//check if enter key is pressed on right hand side of production rule textfield
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addProductionRuleButton.doClick();
					textfieldPLeft.requestFocus();//makes adding rules more user friendly
				}
			}
		});
	}
	
	public String stripWhitespaceComma(String s) {
		String str = s;
		str = str.trim().replaceAll("\\s", "").replaceAll(",", "");
		return str;
	}
	
	public ArrayList<Character> stringToArrayList(String s){
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i = 0; i < s.length(); i++) {
			list.add(s.charAt(i));
		}
		
		return list;
	}
}
