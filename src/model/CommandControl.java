package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.Stack;

/**
 * @author Romain
 */
public abstract class CommandControl {

    public static void parser(String command){
        if(validParenthesage(command)) {
            String[] parts = command.split(" ");
            if(parts[0] == "MATCH"){

            }
            else if(parts[0] == "CREATE"){
                if(validParenthesage(parts[1])){
                    String[] subparts = parts[1].split("()");
                    for(String s : subparts)
                        System.out.println(s);
                }
            }
            else if(parts[0] == "DELETE"){

            }
        }
    }

    public static boolean validParenthesage(String s) {
        HashMap<Character, Character> map = new HashMap();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);

            if (map.keySet().contains(curr)) {
                stack.push(curr);
            } else if (map.values().contains(curr)) {
                if (!stack.empty() && map.get(stack.peek()) == curr) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }
}
