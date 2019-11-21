package com.tariqkirsten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Grammar {
	ArrayList<Character> VCharacters = new ArrayList<Character>(Arrays.asList('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'));
	ArrayList<Character> TCharacters = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','-','+','*','/','}','{','=', '!', '#', '%', '^', '&', '(', ')', '?')) ;
	ArrayList<Character> V;
	ArrayList<Character> T;
	ArrayList<String> PLeft;
	ArrayList<String> PRight;
	char S;
	String [][] derivationTable;
	
	public Grammar(ArrayList<Character> V, ArrayList<Character> T, char S, ArrayList<String> PLeft, ArrayList<String> PRight) {
		this.V = V;
		this.T = T;
		this.S = S;
		this.PLeft = PLeft;
		this.PRight = PRight;
	}
	
	public Grammar() {
	}
	
	public boolean isValidGrammar() {
		boolean isValid = true;
		
		//checking formatting of input, V(uppercase) and T(lowercase) and checking the starting symbol is part of the variables
		
		for (int i = 0; i < V.size(); i++) {
			if(!VCharacters.contains(V.get(i))) {
				return false;
			}
			
		}
		
		for (int i = 0; i < T.size(); i++) {
			if(!TCharacters.contains(T.get(i))) {
				return false;
			}
		}
		
		if(!V.contains(S)) {
			return false;
		}
		
		for (int i = 0; i < PLeft.size(); i++) {
			if(PLeft.get(i).length()!=1) {
				return false;
			}
			else if(!V.contains(PLeft.get(i).charAt(0))){
				return false;
			}
			
		}
		
		int terminalCharacters = 0;
		for (int i = 0; i < PRight.size(); i++) {
			String s = PRight.get(i);
			if(s.length()>0) {
				for (int j = 0; j < s.length(); j++) {
					if(!V.contains(s.charAt(j)) && !T.contains(s.charAt(j))) {
						return false;
					}
					
					if(j==0 && !T.contains(s.charAt(j))) {//true: right hand side of production begins with a non-terminal
						return false;
					}
					
					if(T.contains(s.charAt(j))) {//increment on number of terminal chars
						terminalCharacters++;;
					}
					
					if(terminalCharacters>1) {
						return false;
					}
				}
				terminalCharacters = 0;
			}
			else {
				return false;
			}
			
		}
		
		for (int i = 0; i < V.size(); i++) {
			if(!PLeft.contains(V.get(i).toString())) {
				JOptionPane.showMessageDialog(null, "Error! Please add production rule for Variable "+V.get(i).toString(), "Error", JOptionPane.ERROR_MESSAGE); //executes if a variable is missing a production rule
				return false;
			}
		}
		
		for (int i = 0; i < PLeft.size(); i++) {
			for (int j = 0; j < PLeft.size(); j++) {
				if(i!=j && PLeft.get(i).equals(PLeft.get(j)) && PRight.get(i).charAt(0)==PRight.get(j).charAt(0)) {
					return false;
				}
			}
		}
		
		derivationTable = new String[V.size()][T.size()]; // generating the derivation table
		for (int i = 0; i < V.size(); i++) {
			for (int j = 0; j < PLeft.size(); j++) {
				if(V.get(i).toString().equals(PLeft.get(j))) {
					int column = T.indexOf(PRight.get(j).charAt(0));//looking for row to insert the new rule
					derivationTable[i][column] = PRight.get(j);
				}
			}
		}
		return isValid;
	}
	
	public JTextArea leftDerivation(String input, JTextArea textAreaOutput) {
		Stack<Character> stack = new Stack<Character>();
		String original = input;//to compare if derivation is complete
		input = input + "$";//$ indicates end of string
		char lookAheadToken = input.charAt(0);
		stack.push(S);
		textAreaOutput.setText(String.format("%-25s%-15s\n", "Production", "Derivation"));
		textAreaOutput.append(String.format("%-20s%-18s\n", "", S+""));
		boolean member = true;
		String derivation = S+"";
		while(member) {
			if(!lookaheadExists(lookAheadToken)) {
				textAreaOutput.append("\nNo rule for "+stack.pop()+" with "+lookAheadToken+" as lookahead. \nString rejected.");
				break;
			}
			else {
				char variable = stack.pop();
				
				String rule = derivationTable[V.indexOf(variable)][T.indexOf(lookAheadToken)];
				if(rule!=null) {
					for (int i = rule.length()-1; i >=0; i--) {
						stack.push(rule.charAt(i));
					}
					
				}
				//no production rule found
				else {
					textAreaOutput.append("\nNo rule for "+variable+" with "+lookAheadToken+" as lookahead token. \nString rejected.");
					break;
				}
				derivation = derivation.replaceFirst(variable+"", rule);
				textAreaOutput.append(String.format("%-20s%-18s\n", variable+" -> "+rule, derivation));
				char terminal = stack.pop();//pop terminal character from stack
				if(terminal==lookAheadToken) {
					input = input.substring(1);
					lookAheadToken = input.charAt(0);
					if(lookAheadToken == '$' || stack.isEmpty()) {
						if(derivation.equals(original)) {
							textAreaOutput.append("\nString parsed successfully");
						}
						else {
							textAreaOutput.append("\nParsing failed, String rejected!.");
						}
						break;//exit loop
					}
				}else {//terminal character does not match lookahead thus string rejected
					textAreaOutput.append("\nRule not found for "+terminal+" with "+lookAheadToken+" as a lookahead token. \nString rejected.");
					break;
				}
			}
		}
		return textAreaOutput;
		
	}
	
	//checks if terminal characters list contains lookahead
	private boolean lookaheadExists(char c) {
		return T.contains(c);
	}
}
