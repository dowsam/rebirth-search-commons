/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Queries.java 2012-3-29 15:15:07 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.lucene.search;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DisjunctionMaxQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;

import cn.com.rebirth.commons.Nullable;


/**
 * The Class Queries.
 *
 * @author l.xue.nong
 */
public class Queries {

	
	
	/** The Constant MATCH_ALL_QUERY. */
	public final static Query MATCH_ALL_QUERY = new DeletionAwareConstantScoreQuery(new MatchAllDocsFilter());

	
	/** The Constant MATCH_ALL_FILTER. */
	public final static Filter MATCH_ALL_FILTER = new MatchAllDocsFilter();

	
	/** The Constant MATCH_NO_FILTER. */
	public final static Filter MATCH_NO_FILTER = new MatchNoDocsFilter();

	
	/** The Constant disjuncts. */
	private final static Field disjuncts;

	static {
		Field disjunctsX;
		try {
			disjunctsX = DisjunctionMaxQuery.class.getDeclaredField("disjuncts");
			disjunctsX.setAccessible(true);
		} catch (Exception e) {
			disjunctsX = null;
		}
		disjuncts = disjunctsX;
	}

	
	/**
	 * Dis max clauses.
	 *
	 * @param query the query
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public static List<Query> disMaxClauses(DisjunctionMaxQuery query) {
		try {
			return (List<Query>) disjuncts.get(query);
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	
	/**
	 * Optimize query.
	 *
	 * @param q the q
	 * @return the query
	 */
	public static Query optimizeQuery(Query q) {
		if (q instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery) q;
			BooleanClause[] clauses = booleanQuery.getClauses();
			if (clauses.length == 1) {
				BooleanClause clause = clauses[0];
				if (clause.getOccur() == BooleanClause.Occur.MUST) {
					Query query = clause.getQuery();
					query.setBoost(booleanQuery.getBoost() * query.getBoost());
					return optimizeQuery(query);
				}
				if (clause.getOccur() == BooleanClause.Occur.SHOULD && booleanQuery.getMinimumNumberShouldMatch() > 0) {
					Query query = clause.getQuery();
					query.setBoost(booleanQuery.getBoost() * query.getBoost());
					return optimizeQuery(query);
				}
			}
		}
		return q;
	}

	
	/**
	 * Checks if is negative query.
	 *
	 * @param q the q
	 * @return true, if is negative query
	 */
	public static boolean isNegativeQuery(Query q) {
		if (!(q instanceof BooleanQuery)) {
			return false;
		}
		List<BooleanClause> clauses = ((BooleanQuery) q).clauses();
		if (clauses.isEmpty()) {
			return false;
		}
		for (BooleanClause clause : clauses) {
			if (!clause.isProhibited())
				return false;
		}
		return true;
	}

	
	/**
	 * Fix negative query if needed.
	 *
	 * @param q the q
	 * @return the query
	 */
	public static Query fixNegativeQueryIfNeeded(Query q) {
		if (isNegativeQuery(q)) {
			BooleanQuery newBq = (BooleanQuery) q.clone();
			newBq.add(MATCH_ALL_QUERY, BooleanClause.Occur.MUST);
			return newBq;
		}
		return q;
	}

	
	/**
	 * Checks if is match all query.
	 *
	 * @param query the query
	 * @return true, if is match all query
	 */
	public static boolean isMatchAllQuery(Query query) {
		if (query instanceof MatchAllDocsQuery) {
			return true;
		}
		if (query instanceof DeletionAwareConstantScoreQuery) {
			DeletionAwareConstantScoreQuery scoreQuery = (DeletionAwareConstantScoreQuery) query;
			if (scoreQuery.getFilter() instanceof MatchAllDocsFilter) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Apply minimum should match.
	 *
	 * @param query the query
	 * @param minimumShouldMatch the minimum should match
	 */
	public static void applyMinimumShouldMatch(BooleanQuery query, @Nullable String minimumShouldMatch) {
		if (minimumShouldMatch == null) {
			return;
		}
		int optionalClauses = 0;
		for (BooleanClause c : query.clauses()) {
			if (c.getOccur() == BooleanClause.Occur.SHOULD) {
				optionalClauses++;
			}
		}

		int msm = calculateMinShouldMatch(optionalClauses, minimumShouldMatch);
		if (0 < msm) {
			query.setMinimumNumberShouldMatch(msm);
		}
	}

	
	/** The space around less than pattern. */
	private static Pattern spaceAroundLessThanPattern = Pattern.compile("(\\s+<\\s*)|(\\s*<\\s+)");

	
	/** The space pattern. */
	private static Pattern spacePattern = Pattern.compile(" ");

	
	/** The less than pattern. */
	private static Pattern lessThanPattern = Pattern.compile("<");

	
	/**
	 * Calculate min should match.
	 *
	 * @param optionalClauseCount the optional clause count
	 * @param spec the spec
	 * @return the int
	 */
	static int calculateMinShouldMatch(int optionalClauseCount, String spec) {
		int result = optionalClauseCount;
		spec = spec.trim();

		if (-1 < spec.indexOf("<")) {
			
			spec = spaceAroundLessThanPattern.matcher(spec).replaceAll("<");
			for (String s : spacePattern.split(spec)) {
				String[] parts = lessThanPattern.split(s, 0);
				int upperBound = Integer.parseInt(parts[0]);
				if (optionalClauseCount <= upperBound) {
					return result;
				} else {
					result = calculateMinShouldMatch(optionalClauseCount, parts[1]);
				}
			}
			return result;
		}

		

		if (-1 < spec.indexOf('%')) {
			
			spec = spec.substring(0, spec.length() - 1);
			int percent = Integer.parseInt(spec);
			float calc = (result * percent) * (1 / 100f);
			result = calc < 0 ? result + (int) calc : (int) calc;
		} else {
			int calc = Integer.parseInt(spec);
			result = calc < 0 ? result + calc : calc;
		}

		return (optionalClauseCount < result ? optionalClauseCount : (result < 0 ? 0 : result));

	}
}
