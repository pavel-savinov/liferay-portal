create table AssetListEntry (
	uuid_ VARCHAR(75) null,
	assetListEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	type_ INTEGER,
	lastPublishDate DATE null
);