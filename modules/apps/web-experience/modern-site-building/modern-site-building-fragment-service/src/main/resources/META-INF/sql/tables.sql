create table FragmentCollection (
	uuid_ VARCHAR(75) null,
	fragmentCollectionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

create table FragmentEntry (
	uuid_ VARCHAR(75) null,
	fragmentEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	css VARCHAR(75) null,
	html VARCHAR(75) null,
	js VARCHAR(75) null,
	fragmentCollectionId LONG
);