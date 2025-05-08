create table Transactions_Budgets(
transaction_id int not null,
budget_id int not null,
primary key (transaction_id, budget_id),
foreign key (transaction_id) references transactions2 (transaction_id),
foreign key (budget_id) references budgets (budget_id)
);