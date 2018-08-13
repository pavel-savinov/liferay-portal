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

package com.liferay.asset.lists.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.asset.lists.exception.NoSuchAssetListException;
import com.liferay.asset.lists.model.AssetList;
import com.liferay.asset.lists.service.AssetListLocalServiceUtil;
import com.liferay.asset.lists.service.persistence.AssetListPersistence;
import com.liferay.asset.lists.service.persistence.AssetListUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AssetListPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.asset.lists.service"));

	@Before
	public void setUp() {
		_persistence = AssetListUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetList> iterator = _assetLists.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetList assetList = _persistence.create(pk);

		Assert.assertNotNull(assetList);

		Assert.assertEquals(assetList.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetList newAssetList = addAssetList();

		_persistence.remove(newAssetList);

		AssetList existingAssetList = _persistence.fetchByPrimaryKey(newAssetList.getPrimaryKey());

		Assert.assertNull(existingAssetList);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetList();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetList newAssetList = _persistence.create(pk);

		newAssetList.setUuid(RandomTestUtil.randomString());

		newAssetList.setGroupId(RandomTestUtil.nextLong());

		newAssetList.setCompanyId(RandomTestUtil.nextLong());

		newAssetList.setUserId(RandomTestUtil.nextLong());

		newAssetList.setUserName(RandomTestUtil.randomString());

		newAssetList.setCreateDate(RandomTestUtil.nextDate());

		newAssetList.setModifiedDate(RandomTestUtil.nextDate());

		newAssetList.setName(RandomTestUtil.randomString());

		newAssetList.setDescription(RandomTestUtil.randomString());

		newAssetList.setType(RandomTestUtil.nextInt());

		newAssetList.setLastPublishDate(RandomTestUtil.nextDate());

		_assetLists.add(_persistence.update(newAssetList));

		AssetList existingAssetList = _persistence.findByPrimaryKey(newAssetList.getPrimaryKey());

		Assert.assertEquals(existingAssetList.getUuid(), newAssetList.getUuid());
		Assert.assertEquals(existingAssetList.getAssetListId(),
			newAssetList.getAssetListId());
		Assert.assertEquals(existingAssetList.getGroupId(),
			newAssetList.getGroupId());
		Assert.assertEquals(existingAssetList.getCompanyId(),
			newAssetList.getCompanyId());
		Assert.assertEquals(existingAssetList.getUserId(),
			newAssetList.getUserId());
		Assert.assertEquals(existingAssetList.getUserName(),
			newAssetList.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetList.getCreateDate()),
			Time.getShortTimestamp(newAssetList.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetList.getModifiedDate()),
			Time.getShortTimestamp(newAssetList.getModifiedDate()));
		Assert.assertEquals(existingAssetList.getName(), newAssetList.getName());
		Assert.assertEquals(existingAssetList.getDescription(),
			newAssetList.getDescription());
		Assert.assertEquals(existingAssetList.getType(), newAssetList.getType());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetList.getLastPublishDate()),
			Time.getShortTimestamp(newAssetList.getLastPublishDate()));
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_T(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetList newAssetList = addAssetList();

		AssetList existingAssetList = _persistence.findByPrimaryKey(newAssetList.getPrimaryKey());

		Assert.assertEquals(existingAssetList, newAssetList);
	}

	@Test(expected = NoSuchAssetListException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetList> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetList", "uuid", true,
			"assetListId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"name", true, "description", true, "type", true, "lastPublishDate",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetList newAssetList = addAssetList();

		AssetList existingAssetList = _persistence.fetchByPrimaryKey(newAssetList.getPrimaryKey());

		Assert.assertEquals(existingAssetList, newAssetList);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetList missingAssetList = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetList);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetList newAssetList1 = addAssetList();
		AssetList newAssetList2 = addAssetList();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetList1.getPrimaryKey());
		primaryKeys.add(newAssetList2.getPrimaryKey());

		Map<Serializable, AssetList> assetLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetLists.size());
		Assert.assertEquals(newAssetList1,
			assetLists.get(newAssetList1.getPrimaryKey()));
		Assert.assertEquals(newAssetList2,
			assetLists.get(newAssetList2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetList> assetLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetLists.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetList newAssetList = addAssetList();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetList.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetList> assetLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetLists.size());
		Assert.assertEquals(newAssetList,
			assetLists.get(newAssetList.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetList> assetLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetLists.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetList newAssetList = addAssetList();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetList.getPrimaryKey());

		Map<Serializable, AssetList> assetLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetLists.size());
		Assert.assertEquals(newAssetList,
			assetLists.get(newAssetList.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetListLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AssetList>() {
				@Override
				public void performAction(AssetList assetList) {
					Assert.assertNotNull(assetList);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetList newAssetList = addAssetList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("assetListId",
				newAssetList.getAssetListId()));

		List<AssetList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetList existingAssetList = result.get(0);

		Assert.assertEquals(existingAssetList, newAssetList);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("assetListId",
				RandomTestUtil.nextLong()));

		List<AssetList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetList newAssetList = addAssetList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("assetListId"));

		Object newAssetListId = newAssetList.getAssetListId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("assetListId",
				new Object[] { newAssetListId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetListId = result.get(0);

		Assert.assertEquals(existingAssetListId, newAssetListId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("assetListId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("assetListId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetList newAssetList = addAssetList();

		_persistence.clearCache();

		AssetList existingAssetList = _persistence.findByPrimaryKey(newAssetList.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingAssetList.getUuid(),
				ReflectionTestUtil.invoke(existingAssetList, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingAssetList.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingAssetList,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected AssetList addAssetList() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetList assetList = _persistence.create(pk);

		assetList.setUuid(RandomTestUtil.randomString());

		assetList.setGroupId(RandomTestUtil.nextLong());

		assetList.setCompanyId(RandomTestUtil.nextLong());

		assetList.setUserId(RandomTestUtil.nextLong());

		assetList.setUserName(RandomTestUtil.randomString());

		assetList.setCreateDate(RandomTestUtil.nextDate());

		assetList.setModifiedDate(RandomTestUtil.nextDate());

		assetList.setName(RandomTestUtil.randomString());

		assetList.setDescription(RandomTestUtil.randomString());

		assetList.setType(RandomTestUtil.nextInt());

		assetList.setLastPublishDate(RandomTestUtil.nextDate());

		_assetLists.add(_persistence.update(assetList));

		return assetList;
	}

	private List<AssetList> _assetLists = new ArrayList<AssetList>();
	private AssetListPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}