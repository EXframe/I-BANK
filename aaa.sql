select * from account;
select * from deposit;

insert into account values (GETDATE(),'A����','440105199301014567','13533322211','65231579','666666','����','����ְԱ');
insert into deposit values (GETDATE(),'65231579','666732.00','0','ȡ��')
update account set status = '����'

alter table deposit alter column ddate datetime
alter table deposit add constraint ID_DES primary key (ddate,card)
alter table account drop constraint ck_account_privilege
sp_helpconstraint account
alter table account add constraint ck_account_privilege check (privelege='���˿ͻ�' OR privelege='����ְԱ' OR privelege='���о���') 

Create table account(
	adate datetime,
	name varchar(20),
	ID char(18),
	tel char(11) ,
	card char(8) primary key not null, 
	password char(6)not null,
	status char(4)not null,
	privelege char(8) not null,
	check (status in('����','����')),
	check (privelege in('���˿ͻ�','����ְԱ','�ܾ���')),
);
create table deposit(
	ddate datetime,
	card char(8) foreign key references account(card) not null,
	amount decimal(10,2) not null,
	balance decimal(10,2) ,
	type char(4)not null,
	check (type in('���','ȡ��')),
    Constraint ID_DES primary key (ddate,card),
);

--**************************
drop table account;
drop table deposit;