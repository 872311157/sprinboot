CREATE TABLE bootuser(
	id serial PRIMARY KEY not null,
	username VARCHAR,
	passwords VARCHAR,
	sex VARCHAR,
	age INTEGER,
	workno VARCHAR,
	email VARCHAR
);

select * from bootuser;


CREATE TABLE userinfo(
	id serial PRIMARY KEY not null,
	username VARCHAR,
	sex VARCHAR,
	age INTEGER,
	workno VARCHAR
);

select * from userinfo;
insert into userinfo(username, sex, age, workno) values('张三','男',23,'421125001')

CREATE TABLE calendar(
	id serial PRIMARY KEY not null,
	workyear INTEGER,
	workmonth INTEGER,
	workday INTEGER,
	workweek INTEGER,--星期几
	workdate TIMESTAMP,
	holiday VARCHAR,--节假日
	workstatus INTEGER--0工作日，1节假日，2周末
);
select * from calendar where workyear=2020 and workmonth=10 order by workdate asc
delete from calendar;

insert into calendar(workyear, workmonth, workday, workweek, workdate, holiday, workstatus) values()

--数据类型代码表
CREATE TABLE boot_typetable(
	id serial PRIMARY KEY not null,
	code INTEGER, -- 类型
    codevalue VARCHAR -- 数据
);

--默认数据代码表
CREATE TABLE boot_valuetable(
	id serial PRIMARY KEY not null,
	type VARCHAR UNIQUE, -- 默认数据名称
    typevalue VARCHAR -- 默认数据值
);

--角色模型关联表
CREATE TABLE boot_rolemodule(
	id serial PRIMARY KEY not null,
	roleid INTEGER, -- 角色id
    moduleid INTEGER -- 模型id
);

--用户角色关联表
CREATE TABLE bootrole(
	id serial PRIMARY KEY not null,
	roleid INTEGER, -- 角色id
    userid INTEGER -- 用户id
);

--角色表
CREATE TABLE bootrole(
	id serial PRIMARY KEY not null,
	rolename VARCHAR, -- 角色名称
    roletype INTEGER -- 角色类型
);

--模型表
CREATE TABLE bootmodule(
	id serial PRIMARY KEY not null,
	modulename VARCHAR, --模型名称
	moduleaddress VARCHAR, --模型地址
	parentid INTEGER --父模型id
);
ALTER TABLE bootmodule ADD mtype INTEGER; --模型类型0分类，1引用


INSERT INTO bootuser (username,passwords,sex,age,workno,email) VALUES('han','123','男',22,'120','123@qq.com');
INSERT INTO bootrole (rolename,roletype) VALUES('管理员',0);
INSERT INTO boot_roleuser (roleid,userid) VALUES(0,1);
INSERT INTO bootmodule (modulename,moduleaddress,parentid) VALUES('系统管理','',0);
INSERT INTO bootmodule (modulename,moduleaddress,parentid) VALUES('模块管理','./system/module/module_list.html',1);

--创建用户模型关联视图
drop view view_usermodules;

create or replace VIEW view_usermodules AS
select ru.userid, ru.roleid, m.* from bootuser u
left join boot_roleuser ru on u.id=ru.userid
left join boot_rolemodule rm on ru.roleid = rm.roleid
left join bootmodule m on rm.moduleid = m.id where m.mtype=0 and parentid=0

--初始化数据
INSERT INTO bootmodule(id, modulename, moduleaddress, parentid, mtype) VALUES(1, '系统管理', '', 0, 0);
INSERT INTO bootmodule(id, modulename, moduleaddress, parentid, mtype) VALUES(2, '模块管理', './system/module/module_list.html', 1, 1);
INSERT INTO bootmodule(id, modulename, moduleaddress, parentid, mtype) VALUES(3, '角色管理', './system/module/role_list.html', 1, 1);
INSERT INTO bootmodule(id, modulename, moduleaddress, parentid, mtype) VALUES(4, '系统管理', './system/module/system_list.html', 1, 1);
INSERT INTO bootmodule(id, modulename, moduleaddress, parentid, mtype) VALUES(5, '用户管理', './system/module/user_list.html', 1, 1);

--postgresql递归查询
WITH RECURSIVE r AS (
   SELECT * FROM bootmodule WHERE id = 1
   union ALL
   SELECT bootmodule.* FROM bootmodule, r WHERE bootmodule.parentid = r.id
)
SELECT * FROM r ORDER BY id asc;