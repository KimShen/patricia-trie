package org.ardverk.filter;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.ardverk.filter.impl.SimpleTrieFilter;
import org.junit.Test;

/**
 * @author kim 2014年9月2日
 */
public class SimpleTrieFilterText {

	private final Map<String, String> trie = new HashMap<String, String>();

	private final String content;

	public SimpleTrieFilterText() throws Exception {
		super();
		this.content = IOUtils.toString(SimpleTrieFilterText.class.getResourceAsStream("War and Peace.txt"), "UTF-8");
		for (String each : IOUtils.readLines(new FileReader(new File(SimpleTrieFilterText.class.getResource("Words.txt").getFile())))) {
			this.trie.put(each.trim(), "*");
		}
	}

	@Test
	public void testFilter() throws Exception {
		TrieCounter after = new SimpleTrieFilter(this.trie).filter(this.content);
		int total = 0;
		for (int index = 0; index != -1; total++) {
			index = after.source().indexOf("*", index + 1);
		}
		TestCase.assertSame(16, total);
		TestCase.assertSame(16, after.filtered());
		TestCase.assertTrue(after.source().length() < this.content.length());
	}

	@Test
	public void testFilterSingleWord() throws Exception {
		Map<String, String> trie = new HashMap<String, String>();
		trie.put("亲", "*");
		trie.put("你", "*");
		trie.put("你好", "*");
		TrieCounter after = new SimpleTrieFilter(trie).filter("亲爱的你,你好吗");
		TestCase.assertSame(3, after.filtered());
	}
}
