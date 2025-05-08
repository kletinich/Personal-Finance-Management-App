create table Transactions_Savings(
transaction_id int not null,
saving_id int not null,
primary key (transaction_id, saving_id),
foreign key (transaction_id) references transactions2 (transaction_id),
foreign key (saving_id) references savings2 (saving_id)
);