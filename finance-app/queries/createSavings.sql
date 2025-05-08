create table savings2(
saving_id int not null auto_increment,
name varchar(50) not null,
amount decimal(10,2) not null check (amount > 0),
category_id int,
saved decimal(10,2) default 0,
progress decimal(5,2) generated always as (saved / amount * 100),
note TEXT null,
primary key (saving_id),
foreign key (category_id) references categories (category_id)
);