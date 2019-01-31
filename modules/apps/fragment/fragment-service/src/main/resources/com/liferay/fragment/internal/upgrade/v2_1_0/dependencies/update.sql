alter table FragmentEntry add type_ INTEGER;
alter table FragmentEntryLink add type_ INTEGER;

COMMIT_TRANSACTION;

update FragmentEntry set type_ = 0;
update FragmentEntryLink set type_ = 0;