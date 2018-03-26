create unique index IX_19562E6B on FragmentCollection (groupId, fragmentCollectionKey[$COLUMN_LENGTH:75$]);
create index IX_536510F5 on FragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_9A228268 on FragmentCollection (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DFB882EA on FragmentCollection (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_DDB6278B on FragmentEntry (fragmentCollectionId, status);
create unique index IX_62913C70 on FragmentEntry (groupId, fragmentCollectionId, fragmentEntryKey[$COLUMN_LENGTH:75$]);
create index IX_18F9DFE on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$]);
create unique index IX_7F3F0EB3 on FragmentEntry (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$]);
create index IX_C65BF31C on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_553E909E on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_2FB5437D on FragmentEntryLink (groupId, classNameId, classPK);
create index IX_91F46485 on FragmentEntryLink (groupId, fragmentEntryId);
create index IX_9266C536 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AA2B2138 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], groupId);