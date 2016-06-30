package com.framework.akka.messages;


public class WordCount {
	public String word;
	public Integer count;
	public WordCount(String word, Integer cnt) {
	    this.word = word;
	    this.count = cnt;
    }

}
