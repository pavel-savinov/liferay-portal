/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ExceptionMessageCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_THROW};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChild = detailAST.getFirstChild();

		firstChild = firstChild.getFirstChild();

		if (firstChild.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST elistAST = firstChild.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
			elistAST, false, TokenTypes.EXPR);

		for (DetailAST exprAST : exprASTList) {
			firstChild = exprAST.getFirstChild();

			if (firstChild.getType() != TokenTypes.STRING_LITERAL) {
				return;
			}

			String text = firstChild.getText();

			if (text.matches("\"[A-Z][^.]+\\.\"") ||
				text.matches("\"[A-Z][^.]+\\. [A-Z][^.]+\"")) {

				log(firstChild.getLineNo(), _MSG_INCORRECT_MESSAGE);
			}
		}
	}

	private static final String _MSG_INCORRECT_MESSAGE = "message.incorrect";

}