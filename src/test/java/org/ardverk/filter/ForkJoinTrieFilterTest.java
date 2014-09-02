package org.ardverk.filter;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.ardverk.filter.impl.ForkJoinTrieFilter;
import org.junit.Test;

/**
 * @author kim 2014年9月2日
 */
public class ForkJoinTrieFilterTest {

	private final Map<String, String> trie = new HashMap<String, String>();

	private final String content;

	public ForkJoinTrieFilterTest() throws Exception {
		super();
		this.content = IOUtils.toString(SimpleTrieFilterText.class.getResourceAsStream("War and Peace.txt"), "UTF-8");
		for (String each : IOUtils.readLines(new FileReader(new File(SimpleTrieFilterText.class.getResource("words.txt").getFile())))) {
			this.trie.put(each.trim(), "*");
		}
	}

	@Test
	public void testFilter() throws Exception {
		TrieCounter after = new ForkJoinTrieFilter(1024, this.trie).filter(this.content);
		int total = 0;
		for (int index = 0; index != -1; total++) {
			index = after.source().indexOf("*", index + 1);
		}
		TestCase.assertSame(16, total);
		TestCase.assertSame(16, after.filtered());
		TestCase.assertTrue(after.source().length() < this.content.length());
	}
}
