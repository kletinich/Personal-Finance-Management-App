create table Budgets(
budget_id int not null auto_increment,
name varchar(50) not null,
amount decimal(10,2) not null check (amount > 0),
category_id int,
spent decimal(10,2) default 0,
progress decimal(5,2) generated always as (spent / amount * 100),
note TEXT null,
primary key (budget_id),
foreign key (category_id) references categories (category_id)
);