create table Transactions2 (
transaction_id int not null auto_increment,
amount decimal(10,2) not null check (amount > 0),
type ENUM('income','expense') not null,
category_id int not null,
date Date null,
note TEXT null,

primary key (transaction_id),
foreign key (category_id) references categories (category_id)
);