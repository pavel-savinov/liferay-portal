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

package com.liferay.layout.page.template.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateVersionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateVersion;
import com.liferay.layout.page.template.service.LayoutPageTemplateVersionLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateVersionPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateVersionUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateVersionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.layout.page.template.service"));

	@Before
	public void setUp() {
		_persistence = LayoutPageTemplateVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateVersion> iterator =
			_layoutPageTemplateVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateVersion layoutPageTemplateVersion =
			_persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplateVersion);

		Assert.assertEquals(layoutPageTemplateVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		_persistence.remove(newLayoutPageTemplateVersion);

		LayoutPageTemplateVersion existingLayoutPageTemplateVersion =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateVersion.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			_persistence.create(pk);

		newLayoutPageTemplateVersion.setUuid(RandomTestUtil.randomString());

		newLayoutPageTemplateVersion.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplateVersion.setCompanyId(RandomTestUtil.nextLong());

		newLayoutPageTemplateVersion.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateVersion.setUserName(RandomTestUtil.randomString());

		newLayoutPageTemplateVersion.setCreateDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateVersion.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateVersion.setLayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateVersion.setPlid(RandomTestUtil.nextLong());

		newLayoutPageTemplateVersion.setVersion(RandomTestUtil.nextDouble());

		_layoutPageTemplateVersions.add(
			_persistence.update(newLayoutPageTemplateVersion));

		LayoutPageTemplateVersion existingLayoutPageTemplateVersion =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateVersion.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getUuid(),
			newLayoutPageTemplateVersion.getUuid());
		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getLayoutPageTemplateVersionId(),
			newLayoutPageTemplateVersion.getLayoutPageTemplateVersionId());
		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getGroupId(),
			newLayoutPageTemplateVersion.getGroupId());
		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getCompanyId(),
			newLayoutPageTemplateVersion.getCompanyId());
		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getUserId(),
			newLayoutPageTemplateVersion.getUserId());
		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getUserName(),
			newLayoutPageTemplateVersion.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateVersion.getCreateDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateVersion.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateVersion.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateVersion.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getLayoutPageTemplateEntryId(),
			newLayoutPageTemplateVersion.getLayoutPageTemplateEntryId());
		Assert.assertEquals(
			existingLayoutPageTemplateVersion.getPlid(),
			newLayoutPageTemplateVersion.getPlid());
		AssertUtils.assertEquals(
			existingLayoutPageTemplateVersion.getVersion(),
			newLayoutPageTemplateVersion.getVersion());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountBylayoutPageTemplateEntryId() throws Exception {
		_persistence.countBylayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		_persistence.countBylayoutPageTemplateEntryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		LayoutPageTemplateVersion existingLayoutPageTemplateVersion =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateVersion.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateVersion, newLayoutPageTemplateVersion);
	}

	@Test(expected = NoSuchPageTemplateVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplateVersion>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutPageTemplateVersion", "uuid", true,
			"layoutPageTemplateVersionId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "layoutPageTemplateEntryId", true, "plid",
			true, "version", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		LayoutPageTemplateVersion existingLayoutPageTemplateVersion =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateVersion.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateVersion, newLayoutPageTemplateVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateVersion missingLayoutPageTemplateVersion =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateVersion newLayoutPageTemplateVersion1 =
			addLayoutPageTemplateVersion();
		LayoutPageTemplateVersion newLayoutPageTemplateVersion2 =
			addLayoutPageTemplateVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateVersion1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateVersion2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateVersion>
			layoutPageTemplateVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateVersions.size());
		Assert.assertEquals(
			newLayoutPageTemplateVersion1,
			layoutPageTemplateVersions.get(
				newLayoutPageTemplateVersion1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutPageTemplateVersion2,
			layoutPageTemplateVersions.get(
				newLayoutPageTemplateVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateVersion>
			layoutPageTemplateVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateVersion>
			layoutPageTemplateVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateVersions.size());
		Assert.assertEquals(
			newLayoutPageTemplateVersion,
			layoutPageTemplateVersions.get(
				newLayoutPageTemplateVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateVersion>
			layoutPageTemplateVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateVersion.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateVersion>
			layoutPageTemplateVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateVersions.size());
		Assert.assertEquals(
			newLayoutPageTemplateVersion,
			layoutPageTemplateVersions.get(
				newLayoutPageTemplateVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutPageTemplateVersionLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutPageTemplateVersion>() {

				@Override
				public void performAction(
					LayoutPageTemplateVersion layoutPageTemplateVersion) {

					Assert.assertNotNull(layoutPageTemplateVersion);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateVersionId",
				newLayoutPageTemplateVersion.getLayoutPageTemplateVersionId()));

		List<LayoutPageTemplateVersion> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateVersion existingLayoutPageTemplateVersion =
			result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateVersion, newLayoutPageTemplateVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateVersionId", RandomTestUtil.nextLong()));

		List<LayoutPageTemplateVersion> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateVersionId"));

		Object newLayoutPageTemplateVersionId =
			newLayoutPageTemplateVersion.getLayoutPageTemplateVersionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateVersionId",
				new Object[] {newLayoutPageTemplateVersionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateVersionId = result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateVersionId,
			newLayoutPageTemplateVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateVersionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateVersionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutPageTemplateVersion newLayoutPageTemplateVersion =
			addLayoutPageTemplateVersion();

		_persistence.clearCache();

		LayoutPageTemplateVersion existingLayoutPageTemplateVersion =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateVersion.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateVersion.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateVersion, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateVersion.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateVersion, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected LayoutPageTemplateVersion addLayoutPageTemplateVersion()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateVersion layoutPageTemplateVersion =
			_persistence.create(pk);

		layoutPageTemplateVersion.setUuid(RandomTestUtil.randomString());

		layoutPageTemplateVersion.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplateVersion.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateVersion.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateVersion.setUserName(RandomTestUtil.randomString());

		layoutPageTemplateVersion.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateVersion.setModifiedDate(RandomTestUtil.nextDate());

		layoutPageTemplateVersion.setLayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		layoutPageTemplateVersion.setPlid(RandomTestUtil.nextLong());

		layoutPageTemplateVersion.setVersion(RandomTestUtil.nextDouble());

		_layoutPageTemplateVersions.add(
			_persistence.update(layoutPageTemplateVersion));

		return layoutPageTemplateVersion;
	}

	private List<LayoutPageTemplateVersion> _layoutPageTemplateVersions =
		new ArrayList<LayoutPageTemplateVersion>();
	private LayoutPageTemplateVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}