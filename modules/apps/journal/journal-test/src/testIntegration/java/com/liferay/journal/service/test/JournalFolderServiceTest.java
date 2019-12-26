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

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.exception.InvalidDDMStructureException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.trash.kernel.exception.RestoreEntryException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@RunWith(Arquillian.class)
public class JournalFolderServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAddArticle() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder");

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), folder.getFolderId(), "Test Article",
			"This is a test article.");

		Assert.assertEquals(article.getFolderId(), folder.getFolderId());
	}

	@Test
	public void testAddArticleToRestrictedFolder() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = {ddmStructure1.getStructureId()};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalFolderLocalService.updateFolder(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			folder.getFolderId(), folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		List<DDMStructure> ddmStructures =
			_journalFolderLocalService.getDDMStructures(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					_group.getGroupId()),
				folder.getFolderId(),
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

		Assert.assertFalse(ddmStructures.toString(), ddmStructures.isEmpty());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate2 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure2.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			LocaleUtil.getDefault());

		try {
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(), folder.getFolderId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
				ddmStructure2.getStructureKey(), ddmTemplate2.getTemplateKey());

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Test 1.1");

		try {
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(), subfolder.getFolderId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
				ddmStructure2.getStructureKey(), ddmTemplate2.getTemplateKey());

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		_journalFolderLocalService.deleteFolder(folder.getFolderId());

		ddmStructures = _journalFolderLocalService.getDDMStructures(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(_group.getGroupId()),
			folder.getFolderId(),
			JournalFolderConstants.
				RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

		Assert.assertTrue(ddmStructures.toString(), ddmStructures.isEmpty());
	}

	@Test
	public void testGetFoldersAndArticlesCountWithArticleAndFolder()
		throws Exception {

		int initialCount = _journalFolderService.getFoldersAndArticlesCount(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new int[] {WorkflowConstants.STATUS_ANY});

		JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Article",
			"This is a test article.");

		int actualCount = _journalFolderService.getFoldersAndArticlesCount(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new int[] {WorkflowConstants.STATUS_ANY});

		Assert.assertEquals(initialCount + 2, actualCount);
	}

	@Test
	public void testGetFoldersAndArticlesCountWithDifferentStatuses()
		throws Exception {

		int initialCount = _journalFolderService.getFoldersAndArticlesCount(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new int[] {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_DRAFT
			});

		User user = UserTestUtil.addUser();

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, user.getUserId());

			serviceContext.setCommand(Constants.ADD);
			serviceContext.setLayoutFullURL("http://localhost");

			JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, true, serviceContext);

			JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, false, serviceContext);

			int actualCount = _journalFolderService.getFoldersAndArticlesCount(
				_group.getGroupId(), TestPropsValues.getUserId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				new int[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				});

			Assert.assertEquals(initialCount + 2, actualCount);
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetFoldersAndArticlesCountWithStatus() throws Exception {
		int initialCount = _journalFolderService.getFoldersAndArticlesCount(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			new int[] {WorkflowConstants.STATUS_APPROVED});

		User user = UserTestUtil.addUser();

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, user.getUserId());

			serviceContext.setCommand(Constants.ADD);
			serviceContext.setLayoutFullURL("http://localhost");

			JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, true, serviceContext);

			JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, false, serviceContext);

			int actualCount = _journalFolderService.getFoldersAndArticlesCount(
				_group.getGroupId(), TestPropsValues.getUserId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				new int[] {WorkflowConstants.STATUS_APPROVED});

			Assert.assertEquals(initialCount + 1, actualCount);
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetFoldersAndArticlesWithArticleAndFolder()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder");

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Article",
			"This is a test article.");

		List<Object> foldersAndArticles =
			_journalFolderService.getFoldersAndArticles(
				_group.getGroupId(), TestPropsValues.getUserId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				new int[] {WorkflowConstants.STATUS_ANY}, LocaleUtil.US, 0, 100,
				null);

		Assert.assertEquals(
			foldersAndArticles.toString(), 2, foldersAndArticles.size());

		Assert.assertTrue(foldersAndArticles.contains(journalFolder));
		Assert.assertTrue(foldersAndArticles.contains(journalArticle));
	}

	@Test
	public void testGetFoldersAndArticlesWithDifferentStatuses()
		throws Exception {

		User user = UserTestUtil.addUser();

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, user.getUserId());

			serviceContext.setCommand(Constants.ADD);
			serviceContext.setLayoutFullURL("http://localhost");

			JournalArticle journalArticle1 = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, true, serviceContext);

			JournalArticle journalArticle2 = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, false, serviceContext);

			List<Object> foldersAndArticles =
				_journalFolderService.getFoldersAndArticles(
					_group.getGroupId(), TestPropsValues.getUserId(),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					new int[] {
						WorkflowConstants.STATUS_APPROVED,
						WorkflowConstants.STATUS_DRAFT
					},
					LocaleUtil.US, 0, 100, null);

			Assert.assertTrue(foldersAndArticles.contains(journalArticle1));
			Assert.assertTrue(foldersAndArticles.contains(journalArticle2));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetFoldersAndArticlesWithStatus() throws Exception {
		User user = UserTestUtil.addUser();

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, user.getUserId());

			serviceContext.setCommand(Constants.ADD);
			serviceContext.setLayoutFullURL("http://localhost");

			JournalArticle journalArticle = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, true, serviceContext);

			JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(),
				true, false, serviceContext);

			List<Object> foldersAndArticles =
				_journalFolderService.getFoldersAndArticles(
					_group.getGroupId(), TestPropsValues.getUserId(),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					new int[] {WorkflowConstants.STATUS_APPROVED},
					LocaleUtil.US, 0, 100, null);

			Assert.assertEquals(
				foldersAndArticles.toString(), 1, foldersAndArticles.size());

			Assert.assertTrue(foldersAndArticles.contains(journalArticle));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetInheritedWorkflowFolderId() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalFolderService.updateFolder(
			serviceContext.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, null,
			new long[0], JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW,
			false, serviceContext);

		JournalFolder countriesFolder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Countries");

		Assert.assertEquals(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_journalFolderLocalService.getInheritedWorkflowFolderId(
				countriesFolder.getFolderId()));

		JournalFolder germanyFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), countriesFolder.getFolderId(), "Germany");

		Assert.assertEquals(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_journalFolderLocalService.getInheritedWorkflowFolderId(
				germanyFolder.getFolderId()));

		JournalFolder spainFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), countriesFolder.getFolderId(), "Spain");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = {ddmStructure1.getStructureId()};

		_journalFolderService.updateFolder(
			serviceContext.getScopeGroupId(), spainFolder.getFolderId(),
			spainFolder.getParentFolderId(), spainFolder.getName(),
			spainFolder.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		Assert.assertEquals(
			spainFolder.getFolderId(),
			_journalFolderLocalService.getInheritedWorkflowFolderId(
				spainFolder.getFolderId()));

		JournalFolder madridFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), spainFolder.getFolderId(), "Madrid");

		Assert.assertEquals(
			spainFolder.getFolderId(),
			_journalFolderLocalService.getInheritedWorkflowFolderId(
				madridFolder.getFolderId()));
	}

	@Test
	public void testMoveArticleFromTrashToFolder() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			LocaleUtil.getDefault());

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder1.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		_journalFolderLocalService.moveFolderToTrash(
			TestPropsValues.getUserId(), folder1.getFolderId());

		JournalFolder folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 2");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = {ddmStructure2.getStructureId()};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalFolderLocalService.updateFolder(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			folder2.getFolderId(), folder2.getParentFolderId(),
			folder2.getName(), folder2.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalArticle.class.getName());

		try {
			trashHandler.checkRestorableEntry(
				article.getResourcePrimKey(), folder2.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder2.getFolderId(), "Test 2.1");

		try {
			trashHandler.checkRestorableEntry(
				article.getResourcePrimKey(), subfolder.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}
	}

	@Test
	public void testMoveArticleToRestrictedFolder() throws Exception {
		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			LocaleUtil.getDefault());

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = {ddmStructure2.getStructureId()};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalFolderLocalService.updateFolder(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			folder.getFolderId(), folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		try {
			JournalArticleLocalServiceUtil.moveArticle(
				_group.getGroupId(), article.getArticleId(),
				folder.getFolderId(), serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Test 1.1");

		try {
			JournalArticleLocalServiceUtil.moveArticle(
				_group.getGroupId(), article.getArticleId(),
				subfolder.getFolderId(), serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}
	}

	@Test
	public void testMoveFolderWithAnArticleInTrashToFolder() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		JournalFolder folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(), folder1.getFolderId(), "Test 2");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			LocaleUtil.getDefault());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder2.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		_journalFolderLocalService.moveFolderToTrash(
			TestPropsValues.getUserId(), folder1.getFolderId());

		JournalFolder folder3 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 3");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = {ddmStructure2.getStructureId()};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalFolderLocalService.updateFolder(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			folder3.getFolderId(), folder3.getParentFolderId(),
			folder3.getName(), folder3.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			JournalFolder.class.getName());

		try {
			trashHandler.checkRestorableEntry(
				folder2.getFolderId(), folder3.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder3.getFolderId(), "Test 3.1");

		try {
			trashHandler.checkRestorableEntry(
				folder2.getFolderId(), subfolder.getFolderId(), null);

			Assert.fail();
		}
		catch (RestoreEntryException ree) {
		}
	}

	@Test
	public void testMoveFolderWithAnArticleToFolder() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			LocaleUtil.getDefault());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder1.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		JournalFolder folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 2");

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = {ddmStructure2.getStructureId()};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalFolderLocalService.updateFolder(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			folder2.getFolderId(), folder2.getParentFolderId(),
			folder2.getName(), folder2.getDescription(), ddmStructureIds,
			JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
			false, serviceContext);

		try {
			_journalFolderLocalService.moveFolder(
				folder1.getFolderId(), folder2.getFolderId(), serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder2.getFolderId(), "Test 2.1");

		try {
			_journalFolderLocalService.moveFolder(
				folder1.getFolderId(), subfolder.getFolderId(), serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}
	}

	@Test
	public void testSubfolders() throws Exception {
		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		JournalFolder folder11 = JournalTestUtil.addFolder(
			_group.getGroupId(), folder1.getFolderId(), "Test 1.1");

		JournalFolder folder111 = JournalTestUtil.addFolder(
			_group.getGroupId(), folder11.getFolderId(), "Test 1.1.1");

		Assert.assertTrue(folder1.isRoot());
		Assert.assertFalse(folder11.isRoot());
		Assert.assertFalse(folder111.isRoot());
		Assert.assertEquals(
			folder1.getFolderId(), folder11.getParentFolderId());
		Assert.assertEquals(
			folder11.getFolderId(), folder111.getParentFolderId());
	}

	@Test
	public void testUpdateFolderRestrictions() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"Test Article");

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure1.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			LocaleUtil.getDefault());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long[] ddmStructureIds = {ddmStructure2.getStructureId()};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		try {
			_journalFolderLocalService.updateFolder(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				folder.getFolderId(), folder.getParentFolderId(),
				folder.getName(), folder.getDescription(), ddmStructureIds,
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
				false, serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Test 1.1");

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), subfolder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey());

		try {
			_journalFolderLocalService.updateFolder(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				folder.getFolderId(), folder.getParentFolderId(),
				folder.getName(), folder.getDescription(), ddmStructureIds,
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW,
				false, serviceContext);

			Assert.fail();
		}
		catch (InvalidDDMStructureException iddmse) {
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private JournalFolderService _journalFolderService;

}