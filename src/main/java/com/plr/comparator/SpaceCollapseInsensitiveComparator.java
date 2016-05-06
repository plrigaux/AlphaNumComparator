package com.plr.comparator;

import java.util.Comparator;

public class SpaceCollapseInsensitiveComparator implements Comparator<CharSequence> {

	static private final SpaceCollapseInsensitiveComparator instance = new SpaceCollapseInsensitiveComparator();

	@Override
	public int compare(CharSequence s1, CharSequence s2) {

		int len1 = s1.length();
		int len2 = s2.length();

		int k = 0;
		int l = 0;

		char c1;
		char c2;

		char cc1 = 0;
		char cc2 = 0;

		boolean klen = k < len1;
		boolean llen = l < len2;

		outerloop: while (klen && llen) {
			c1 = s1.charAt(k);
			c2 = s2.charAt(l);

			while (Character.isWhitespace(cc1) && Character.isWhitespace(c1)) {
				if (klen = ++k < len1) {
					c1 = s1.charAt(k);
				} else {
					break outerloop;
				}
			}

			while (Character.isWhitespace(cc2) && Character.isWhitespace(c2)) {
				if (llen = ++l < len2) {
					c2 = s2.charAt(l);
				} else {
					break outerloop;
				}
			}

			if (Character.isWhitespace(c1)) {
				c1 = ' ';
			}

			if (Character.isWhitespace(c2)) {
				c2 = ' ';
			}

			if (c1 != c2) {
				return c1 - c2;
			}

			klen = ++k < len1;
			llen = ++l < len2;

			cc1 = c1;
			cc2 = c2;
		}

		if (klen) {
			return Character.isWhitespace(cc1) ? SpaceInsensitiveComparator.evaluateLength(s1, len1, l, 1) : 1;
		} else if (llen) {
			return Character.isWhitespace(cc2) ? SpaceInsensitiveComparator.evaluateLength(s2, len2, l, -1) : -1;
		}

		return 0;
	}

	public static SpaceCollapseInsensitiveComparator getInstance() {
		return instance;
	}
}
