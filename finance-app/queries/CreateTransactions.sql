create table Transactions (
transaction_id int not null auto_increment,
type ENUM('income','expense') not null,
amount decimal(10,2) not null check (amount > 0),
category_id int not null,
date timestamp default current_timestamp,
budget_id int null,
saving_id int null,
note TEXT null,
primary key (transaction_id),
foreign key (category_id) references categories (category_id),
foreign key (budget_id) references budgets (budget_id),
foreign key (saving_id) references savings (saving_id)
);