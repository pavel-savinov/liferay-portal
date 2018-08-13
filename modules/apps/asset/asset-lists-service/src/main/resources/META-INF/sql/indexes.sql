create index IX_903EA9BF on AssetList (groupId, type_);
create index IX_7ED244C8 on AssetList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F0355D4A on AssetList (uuid_[$COLUMN_LENGTH:75$], groupId);