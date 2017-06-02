DROP TABLE IF EXISTS t_ip_table;
CREATE TABLE t_ip_table(
  id int(20) primary key AUTO_INCREMENT,
	ip varchar(16),
	name varchar(64),
	operator int(20),
	updated_at datetime,
	if_use int(1),
	created_at datetime
);

DROP TABLE IF EXISTS t_job_manager;
CREATE TABLE t_job_manager(
  id int(20) primary key AUTO_INCREMENT,
  created_at datetime,
  creator int(20),
  if_use int(1),
  job_name varchar(255),
  updated_at datetime,
  c_desc varchar(255),
  role_type varchar(32)
);

DROP TABLE IF EXISTS t_permission;
CREATE TABLE t_permission(
  id int(20) primary key AUTO_INCREMENT,
  code varchar(64),
  type varchar(32)
);

DROP TABLE IF EXISTS t_region;
CREATE TABLE t_region (
  id int(20) primary key AUTO_INCREMENT,
  code varchar(64),
  name varchar(64),
  zip_code varchar(64),
  r_type int(1),
  parent varchar(64),
  area_code varchar(64)
);

DROP TABLE IF EXISTS t_resource;
CREATE TABLE t_resource (
  id int(20) primary key AUTO_INCREMENT,
  code varchar(64),
  name varchar(64),
  url varchar(255),
  type varchar(32),
  r_type varchar(32),
  attr varchar(32)
);

DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role(
  id int(20) primary key AUTO_INCREMENT,
  name varchar(64),
  if_system int(1),
  if_use int(1),
  created_at datetime,
  updated_at datetime,
  operator_id int(20),
  type varchar(32)
);

DROP TABLE IF EXISTS t_role_permission;
CREATE TABLE t_role_permission (
  roles_id int(20) ,
  permissions_id int(20) 
);

DROP TABLE IF EXISTS t_task;
CREATE TABLE t_task(
  id int(20) primary key AUTO_INCREMENT,
  count int(4),
  creator_id varchar(20),
  status varchar(16),
  url varchar(255),
  file_path varchar(255),
  file_name varchar(255),
  note varchar(255),
  if_use int(1),
  params varchar(255),
  created_at datetime,
  updated_at datetime
);

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
  id int(20) primary key AUTO_INCREMENT,
  user_name varchar(64),
  name varchar(64),
  password varchar(64),
  phone varchar(32),
  created_at datetime,
  updated_at datetime,
  login_at  datetime,
  last_login_at datetime,
  last_login_ip varchar(32),
  login_ip varchar(32),
  if_system int(1),
  if_use int(1),
  secret_key varchar(64),
  creator_id int(20),
  is_admin int(1) DEFAULT 0
);

DROP TABLE IF EXISTS t_user_role;
CREATE TABLE t_user_role (
  roles_id int(20),
  users_id int(20) 
);