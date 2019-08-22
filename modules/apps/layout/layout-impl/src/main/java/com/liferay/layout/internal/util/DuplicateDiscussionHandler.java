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

package com.liferay.layout.internal.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Alicia García García
 */
public class DuplicateDiscussionHandler {

	public void duplicateDisscussion(
			long oldFragmentEntryLinkId, long newFragmentEntryLinkId)
		throws PortalException {

		if (!_commentManager.hasDiscussion(
				FragmentEntryLink.class.getName(), oldFragmentEntryLinkId)) {

			return;
		}

		Discussion discussion = _commentManager.getDiscussion(
			_targetLayout.getUserId(), _targetLayout.getGroupId(),
			FragmentEntryLink.class.getName(), oldFragmentEntryLinkId,
			className -> _serviceContext);

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		MBMessage rootMBMessage = _mbMessageLocalService.addDiscussionMessage(
			rootDiscussionComment.getUserId(),
			rootDiscussionComment.getUserName(),
			rootDiscussionComment.getGroupId(),
			rootDiscussionComment.getClassName(), newFragmentEntryLinkId,
			WorkflowConstants.ACTION_PUBLISH);

		rootMBMessage.setCreateDate(rootDiscussionComment.getCreateDate());
		rootMBMessage.setModifiedDate(rootDiscussionComment.getModifiedDate());

		_mbMessageLocalService.updateMBMessage(rootMBMessage);

		if (_targetLayout.isSystem()) {
			_commentManager.subscribeDiscussion(
				_targetLayout.getUserId(), _targetLayout.getGroupId(),
				FragmentEntryLink.class.getName(), newFragmentEntryLinkId);

			if (_targetLayout.getUserId() !=
					rootDiscussionComment.getUserId()) {

				_commentManager.subscribeDiscussion(
					rootDiscussionComment.getUserId(),
					_targetLayout.getGroupId(),
					FragmentEntryLink.class.getName(), newFragmentEntryLinkId);
			}
		}

		if (rootDiscussionComment.getDescendantCommentsCount() <= 0) {
			return;
		}

		List<DiscussionComment> parentComments =
			rootDiscussionComment.getDescendantComments();

		for (DiscussionComment parentComment : parentComments) {
			MBMessage parentMBMessage = _mbMessageLocalService.getMBMessage(
				parentComment.getCommentId());

			MBMessage discussionMBMessage =
				_mbMessageLocalService.addDiscussionMessage(
					parentMBMessage.getUserId(), parentMBMessage.getUserName(),
					parentMBMessage.getGroupId(),
					parentMBMessage.getClassName(), newFragmentEntryLinkId,
					rootMBMessage.getThreadId(), rootMBMessage.getMessageId(),
					String.valueOf(Math.random()), parentMBMessage.getBody(),
					_serviceContext);

			if (parentComment.getDescendantCommentsCount() > 0) {
				List<DiscussionComment> childComments =
					parentComment.getDescendantComments();

				for (DiscussionComment childComment : childComments) {
					MBMessage childMBMessage =
						_mbMessageLocalService.addDiscussionMessage(
							childComment.getUserId(),
							childComment.getUserName(),
							childComment.getGroupId(),
							childComment.getClassName(), newFragmentEntryLinkId,
							discussionMBMessage.getThreadId(),
							discussionMBMessage.getMessageId(),
							String.valueOf(Math.random()),
							childComment.getBody(), _serviceContext);

					childMBMessage.setCreateDate(childComment.getCreateDate());
					childMBMessage.setModifiedDate(
						childComment.getModifiedDate());

					_mbMessageLocalService.updateMBMessage(childMBMessage);
				}
			}

			discussionMBMessage.setCreateDate(parentMBMessage.getCreateDate());
			discussionMBMessage.setModifiedDate(
				parentMBMessage.getModifiedDate());
			discussionMBMessage.setStatus(parentMBMessage.getStatus());

			_mbMessageLocalService.updateMBMessage(discussionMBMessage);
		}
	}

	protected DuplicateDiscussionHandler(
		CommentManager commentManager,
		MBMessageLocalService mbMessageLocalService, Layout targetLayout,
		ServiceContext serviceContext) {

		_commentManager = commentManager;
		_mbMessageLocalService = mbMessageLocalService;
		_targetLayout = targetLayout;
		_serviceContext = serviceContext;
	}

	private final CommentManager _commentManager;
	private final MBMessageLocalService _mbMessageLocalService;
	private final ServiceContext _serviceContext;
	private final Layout _targetLayout;

}