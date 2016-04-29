package com.plr.comparator.alphanum;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * The Alphanum Algorithm is an improved sorting algorithm for strings
 * containing numbers.  Instead of sorting numbers in ASCII order like
 * a standard sort, this algorithm sorts numbers in numeric order.
 *
 * The Alphanum Algorithm is discussed at http://www.DaveKoelle.com
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaturalComparator implements Comparator<String> {

	private static final Logger logger = LoggerFactory.getLogger(NaturalComparator.class);

	final private Comparator<String> alphaComparator;

	// TODO make the regex not capture the point
	// final static String NUM_PAT =
	// "(?:\\s)*((?:^|\\s)[-])?0*([1-9]\\d*|0)((\\.\\d++)(?!\\.\\d))?";

	// Compare without space
	// final static String NUM_PAT =
	// "\\s+|(?:(?:(?<=^|\\s)[-])?0*([1-9]\\d*|0))((\\.\\d++)(?!\\.\\d))?";

	final static String NUM_PAT = "(?:\\s)*(?:((?<=^|\\s)[-])?0*([1-9]\\d*|0))((\\.\\d++)(?!\\.\\d))?";

	final static public Comparator<String> ASCII = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	};

	public enum Flags {
		SPACE
	};

	public NaturalComparator(Flags... flags) {
		this(ASCII, flags);
	}

	private boolean pureNumbers;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NaturalComparator(Collator collator, Flags... flags) {
		this((Comparator) collator, flags);

	}

	final private Pattern pattern;

	public NaturalComparator(Comparator<String> alphaComparator, Flags... flags) {
		this.alphaComparator = alphaComparator;

		EnumSet<NaturalComparator.Flags> flagSet = EnumSet.noneOf(Flags.class);
		flagSet.addAll(Arrays.asList(flags));

		pureNumbers = flagSet.contains(Flags.SPACE);

		pattern = Pattern.compile(NUM_PAT);
	}

	List<TokenComparable> split(String toSplit) {

		List<TokenComparable> list = new ArrayList<>();

		Matcher matcher = pattern.matcher(toSplit);

		int start = 0;

		int j = 0;
		while (matcher.find()) {

			if (logger.isDebugEnabled()) {
				logger.debug("toSplit:'{}'", toSplit);
				for (int i = 0; i < matcher.groupCount(); i++) {
					logger.debug("find {} Gr{}: '{}'", j, i, matcher.group(i));
				}
				j++;
				logger.debug("");
			}

			if (start != matcher.start()) {
				String prev = toSplit.substring(start, matcher.start());
				list.add(new AlphaTokenComparable(prev, alphaComparator));
			}

			String wholeStr = matcher.group(0);

			String number = matcher.group(2);

			
			boolean isNegative = matcher.group(1) != null;

			String decimal = matcher.group(3);
			// TODO try to find why there is a dot at front
			decimal = decimal == null ? null : decimal.substring(1);

			list.add(new NumberTokenComparable(isNegative, number, decimal, wholeStr, alphaComparator));

			start = matcher.end();
		}

		if (start != toSplit.length()) {
			String last = toSplit.substring(start, toSplit.length());
			list.add(new AlphaTokenComparable(last, alphaComparator));
		}

		return list;
	}

	public int compare(String s1, String s2) {

		List<TokenComparable> list1 = split(s1);
		List<TokenComparable> list2 = split(s2);

		if (logger.isDebugEnabled()) {
			logger.debug("list1: {}", list1);
			logger.debug("list2: {}", list2);
		}

		int len1 = list1.size();
		int len2 = list2.size();
		int lim = Math.min(len1, len2);

		int k = 0;
		int result;
		while (k < lim) {
			TokenComparable ss1 = list1.get(k);
			TokenComparable ss2 = list2.get(k);

			result = ss1.compareTo(ss2);

			if (result != 0) {
				return result;
			}
			k++;
		}

		result = len1 - len2;

		if (pureNumbers && result != 0) {

			int limMax;
			List<TokenComparable> listMax;
			if (result > 0) {
				listMax = list1;
				limMax = len1;
			} else {
				listMax = list2;
				limMax = len2;
			}

			TokenComparable ss = listMax.get(k);

			boolean isAllWhiteSpace = ss.isAllWhiteSpace();

			//means that is tailing white space
			if (isAllWhiteSpace && limMax == (k + 1)) {
				result = 0;
			}
		}

		if (result != 0) {
			return result;
		}
		// For now equals, now compare leading zeros
		if (!pureNumbers) {
			k = 0;
			while (k < lim) {
				TokenComparable ss1 = list1.get(k);
				TokenComparable ss2 = list2.get(k);

				result = ss1.compareLeadingZerosTo(ss2);

				if (result != 0) {
					return result;
				}
				k++;
			}

			if (result == 0) {
				result = s1.compareTo(s2);
			}
		}

		return result;

	}

	public boolean equals(String s1, String s2) {
		return compare(s1, s2) == 0;
	}

}