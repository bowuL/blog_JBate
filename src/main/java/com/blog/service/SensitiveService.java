package com.blog.service;

import javafx.beans.binding.When;
import jdk.nashorn.internal.ir.WhileNode;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.contentstream.operator.text.NextLine;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.text.Position;
import javax.swing.text.TextAction;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Still
 * @create: 2020/07/21 15:08
 */
public class SensitiveService implements InitializingBean {

    private class TrieNode{
        private boolean end=false;
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node){
            subNodes.put(key, node);
        }

        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        public boolean isEnd(){
            return end;
        }

        public void setNodeEnd(boolean end){
            this.end=end;
        }
    }

    private TrieNode root = new TrieNode();

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Sensitivewords.txt");
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String lineTxt;
        while ((lineTxt=bufferedReader.readLine())!=null){
            addWord(lineTxt.trim());
        }
        reader.close();
        is.close();
    }

    public boolean isSymbol(char c){
        int ic = (int)c;
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic<0x2E80 || ic>0x9FFF);
    }

    private  void addWord(String lineTxt){
        TrieNode currentNode = root;
        for (int i=0;i<lineTxt.length();i++){
            Character c = lineTxt.charAt(i);
            if (isSymbol(c))
                continue;
            TrieNode node = currentNode.getSubNode(c);
            if (node==null){
                node=new TrieNode();
                currentNode.addSubNode(c,node);
            }
            currentNode = node;
            if (i==lineTxt.length()-1)
                currentNode.setNodeEnd(true);

        }
    }

    public String filter(String text){
        if (StringUtils.isBlank(text))
            return text;
    StringBuilder result = new StringBuilder();
    String replacement = "***";
    TrieNode tmpNode = root;
    int begin = 0;
    int position = 0;

    while (position<text.length()){
        Character character = text.charAt(position);
        if (isSymbol(character)){
            if (tmpNode==root) {
                result.append(character);
                begin++;
            }
            position++;
            continue;
        }

        tmpNode = tmpNode.getSubNode(character);
        if (tmpNode == null){
            if (position!=begin)
                result.append(text.substring(begin, position));
            else
                result.append(character);
            position = begin+1;
            begin = position;
            tmpNode = root;
        }else if (tmpNode.isEnd()){
            result.append(replacement);
            position++;
            begin = position;
            tmpNode = root;
        }else{
            position++;
        }
    }
    result.append(text.substring(begin));
    return result.toString();
    }
}
