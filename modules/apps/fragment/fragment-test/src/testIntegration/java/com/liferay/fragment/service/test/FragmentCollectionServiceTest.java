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

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentCollectionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);
	}

	@After
	public void tearDown() throws Exception {
		Role guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		Role siteMemberRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), FragmentConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_INDIVIDUAL, FragmentConstants.RESOURCE_NAME,
			guestRole.getRoleId(), new String[] {ActionKeys.VIEW});

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), FragmentConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_INDIVIDUAL, FragmentConstants.RESOURCE_NAME,
			siteMemberRole.getRoleId(), new String[] {ActionKeys.VIEW});

		_resourcePermissionLocalService.deleteResourcePermissions(
			TestPropsValues.getCompanyId(), FragmentConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, _group.getGroupId());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentCollectionWithoutPermissions1()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.addFragmentCollection(
			_group.getGroupId(), RandomTestUtil.randomString(),
			StringPool.BLANK, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentCollectionWithoutPermissions2()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.addFragmentCollection(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Test
	public void testAddFragmentCollectionWithPermissions1() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		_addSiteMemberResourcePermission(
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.addFragmentCollection(
			_group.getGroupId(), RandomTestUtil.randomString(),
			StringPool.BLANK, serviceContext);
	}

	@Test
	public void testAddFragmentCollectionWithPermissions2() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		_addSiteMemberResourcePermission(
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.addFragmentCollection(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteFragmentCollectionsWithoutPermissions()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		long[] fragmentCollections = {
			fragmentCollection1.getFragmentCollectionId(),
			fragmentCollection2.getFragmentCollectionId()
		};

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.deleteFragmentCollections(
			fragmentCollections);
	}

	@Test
	public void testDeleteFragmentCollectionsWithPermissions()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		long[] fragmentCollections = {
			fragmentCollection1.getFragmentCollectionId(),
			fragmentCollection2.getFragmentCollectionId()
		};

		_addSiteMemberResourcePermission(
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.deleteFragmentCollections(
			fragmentCollections);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteFragmentCollectionWithoutPermissions()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());
	}

	@Test
	public void testDeleteFragmentCollectionWithPermissions() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_addSiteMemberResourcePermission(
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testFetchFragmentCollectionWithoutPermissions()
		throws Exception {

		Role guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		Role siteMemberRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(), FragmentConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_INDIVIDUAL, FragmentConstants.RESOURCE_NAME,
			guestRole.getRoleId(), ActionKeys.VIEW);

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(), FragmentConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_INDIVIDUAL, FragmentConstants.RESOURCE_NAME,
			siteMemberRole.getRoleId(), ActionKeys.VIEW);

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.fetchFragmentCollection(
			fragmentCollection.getFragmentCollectionId());
	}

	@Test
	public void testFetchFragmentCollectionWithPermissions() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.fetchFragmentCollection(
			fragmentCollection.getFragmentCollectionId());
	}

	@Test
	public void testGetFragmentCollections() throws Exception {
		List<FragmentCollection> originalFragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		List<FragmentCollection> actualFragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId());

		Assert.assertEquals(
			actualFragmentCollections.toString(),
			originalFragmentCollections.size() + 2,
			actualFragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByCreateDateComparatorAsc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection",
				Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "A Fragment Collection",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "B Fragment Collection",
			Timestamp.valueOf(localDateTime));

		FragmentCollectionCreateDateComparator
			fragmentCollectionCreateDateComparator =
				new FragmentCollectionCreateDateComparator(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionCreateDateComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(fragmentCollection, firstFragmentCollection);
	}

	@Test
	public void testGetFragmentCollectionsByOrderByCreateDateComparatorDesc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection",
				Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "A Fragment Collection",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "B Fragment Collection",
			Timestamp.valueOf(localDateTime));

		FragmentCollectionCreateDateComparator
			fragmentCollectionCreateDateComparator =
				new FragmentCollectionCreateDateComparator(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionCreateDateComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(fragmentCollection, lastFragmentCollection);
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAndKeywordsAsc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AC Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), "Collection", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollections.toString(), firstFragmentCollection.getName(),
			fragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAndKeywordsDesc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AC Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), "Collection", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentCollectionNameComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(
			fragmentCollections.toString(), lastFragmentCollection.getName(),
			fragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAsc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollection.toString(), firstFragmentCollection.getName(),
			fragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorDesc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			new FragmentCollectionNameComparator(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionNameComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(
			fragmentCollection.toString(), lastFragmentCollection.getName(),
			fragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByRange() throws Exception {
		int collectionSize = 5;

		for (int i = 0; i < collectionSize; i++) {
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());
		}

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), 1, 4, null);

		Assert.assertEquals(
			fragmentCollections.toString(), collectionSize - 2,
			fragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionsCount() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		int actualFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId());

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentCollectionsCountFilterByName() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId(), "Test");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "Collection Name");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "Collection Test Name");

		int actualFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId(), "Test");

		Assert.assertEquals(
			originalFragmentCollectionsCount + 1,
			actualFragmentCollectionsCount);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetTempFileNamesWithoutPermissions() throws Exception {
		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.getTempFileNames(
			_group.getGroupId(), StringPool.BLANK);
	}

	@Test
	public void testGetTempFileNamesWithPermissions() throws Exception {
		_addSiteMemberResourcePermission(
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.getTempFileNames(
			_group.getGroupId(), StringPool.BLANK);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentCollectionWithoutPermissions()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.updateFragmentCollection(
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Test
	public void testUpdateFragmentCollectionWithPermissions() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_addSiteMemberResourcePermission(
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_groupUser);

		_fragmentCollectionService.updateFragmentCollection(
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	private void _addSiteMemberResourcePermission(String permissionType)
		throws Exception {

		Role siteMemberRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), FragmentConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), permissionType);
	}

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _groupUser;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}