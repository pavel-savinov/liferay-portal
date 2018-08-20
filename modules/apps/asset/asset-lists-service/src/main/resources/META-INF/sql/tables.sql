create table AssetList (
	uuid_ VARCHAR(75) null,
	assetListId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	type_ INTEGER,
	lastPublishDate DATE null
);