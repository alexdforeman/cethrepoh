/**
 * 
 */
package com.alexdforeman.cethrepoh.extractor;

import java.io.File;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.alexdforeman.cethrepoh.extractor.IosStringsExtractor;

/**
 * 
 * Test the IosStringExtractor
 * 
 * @author Alex Foreman at https://github.com/alexdforeman
 */
public class IosStringExtractorTest {

	@Test
	public void testUnSanitizedInput() {

		IosStringsExtractor extractor = new IosStringsExtractor(new File(
				"src/test/resources/sources/ios.strings"));

		
		Collection<String> extractWords = extractor.extractWords();
		Assert.assertNotNull(extractWords);
		Assert.assertTrue(extractWords.size() == 7);
		Assert.assertTrue(extractWords.contains("this"));
		Assert.assertTrue(extractWords.contains("is"));
		Assert.assertTrue(extractWords.contains("a"));
		Assert.assertTrue(extractWords.contains("long"));
		Assert.assertTrue(extractWords.contains("string"));
		Assert.assertTrue(extractWords.contains("seplt"));
		Assert.assertTrue(extractWords.contains("wrongyl"));
	}
}
