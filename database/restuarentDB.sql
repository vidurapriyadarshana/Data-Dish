create table customer
(
    CustomerID varchar(10)                not null
        primary key,
    Name       varchar(100)               null,
    Contact    varchar(15)  default 'N/A' null,
    Address    varchar(255) default 'N/A' null,
    Email      varchar(100) default 'N/A' null
);

create table feedback
(
    FeedbackID varchar(10)  not null
        primary key,
    CustomerID varchar(10)  null,
    Time       time         null,
    Rating     int          null,
    Text       varchar(255) null,
    constraint feedback_ibfk_1
        foreign key (CustomerID) references customer (CustomerID)
            on update cascade on delete cascade
);

create index CustomerID
    on feedback (CustomerID);

create table inventory
(
    InventoryID varchar(10) not null
        primary key,
    ItemName    varchar(50) null,
    Qty         int         null,
    StockLevel  int         null
);

create table menuitem
(
    MenuItemID   varchar(10)                         not null
        primary key,
    Name         varchar(50)                         null,
    Price        decimal(10, 2)                      null,
    Category     varchar(50)                         null,
    Availability enum ('Available', 'Not Available') null,
    ImageData    varchar(255)                        null
);

create table menuiteminventory
(
    MenuItemID       varchar(10) null,
    InventoryID      varchar(10) null,
    QuantityRequired int         not null,
    constraint menuiteminventory_ibfk_1
        foreign key (MenuItemID) references menuitem (MenuItemID)
            on update cascade on delete cascade,
    constraint menuiteminventory_ibfk_2
        foreign key (InventoryID) references inventory (InventoryID)
            on update cascade on delete cascade
);

create index InventoryID
    on menuiteminventory (InventoryID);

create index MenuItemID
    on menuiteminventory (MenuItemID);

create table notification
(
    NotificationID varchar(10)  not null
        primary key,
    Description    varchar(255) null,
    Date           date         null,
    CustomerId     varchar(10)  null
);

create table supplier
(
    SupplierID varchar(10)  not null
        primary key,
    Name       varchar(100) null,
    Contact    varchar(100) null,
    Address    varchar(255) null
);

create table supplierorderitem
(
    SupplierID  varchar(10)    null,
    InventoryID varchar(10)    null,
    Qty         int            null,
    Price       decimal(10, 2) null,
    Status      varchar(20)    null,
    OrderDate   date           null,
    constraint supplierorderitem_ibfk_1
        foreign key (SupplierID) references supplier (SupplierID)
            on update cascade on delete cascade,
    constraint supplierorderitem_ibfk_2
        foreign key (InventoryID) references inventory (InventoryID)
            on update cascade on delete cascade
);

create index InventoryID
    on supplierorderitem (InventoryID);

create index SupplierID
    on supplierorderitem (SupplierID);

create table tableinfo
(
    TableID  varchar(10)                    not null
        primary key,
    Status   enum ('Available', 'Reserved') null,
    Capacity int                            null
);

create table reservation
(
    ReservationID varchar(10)                                not null
        primary key,
    CustomerID    varchar(10)                                null,
    TableID       varchar(10)                                null,
    Date          date                                       null,
    Status        enum ('Pending', 'Confirmed', 'Cancelled') null,
    Time          time                                       null,
    constraint reservation_ibfk_1
        foreign key (CustomerID) references customer (CustomerID)
            on update cascade on delete cascade,
    constraint reservation_ibfk_2
        foreign key (TableID) references tableinfo (TableID)
            on update cascade on delete cascade
);

create index CustomerID
    on reservation (CustomerID);

create index TableID
    on reservation (TableID);

create table user
(
    UserName varchar(50)                not null
        primary key,
    Password varchar(8)                 null,
    Email    varchar(100)               null,
    Role     enum ('Admin', 'Employee') null
);

create table employee
(
    EmployeeID varchar(10)                                  not null
        primary key,
    Name       varchar(50)                                  null,
    Contact    varchar(15)                                  null,
    HireDate   date                                         null,
    Status     enum ('Active', 'Inactive') default 'Active' null,
    Address    varchar(100)                                 null,
    Email      varchar(100)                default 'N/A'    null,
    UserName   varchar(100)                default 'N/A'    null,
    constraint UserName
        foreign key (UserName) references user (UserName)
            on update cascade on delete cascade
);

create table orders
(
    OrderID     varchar(10)    not null
        primary key,
    CustomerID  varchar(10)    null,
    TableID     varchar(10)    null,
    Date        date           null,
    TotalAmount decimal(10, 2) null,
    EmployeeID  varchar(10)    null,
    constraint orders_ibfk_1
        foreign key (CustomerID) references customer (CustomerID)
            on update cascade on delete cascade,
    constraint fk_customer_id
        foreign key (CustomerID) references customer (CustomerID)
            on update cascade on delete cascade,
    constraint orders_ibfk_2
        foreign key (TableID) references tableinfo (TableID)
            on update cascade on delete cascade,
    constraint fk_table_id
        foreign key (TableID) references tableinfo (TableID)
            on update cascade on delete cascade,
    constraint orders_ibfk_3
        foreign key (EmployeeID) references employee (EmployeeID)
            on update cascade on delete cascade,
    constraint fk_employee_id
        foreign key (EmployeeID) references employee (EmployeeID)
            on update cascade on delete cascade
);

create table menuorderitem
(
    MenuItemID varchar(10)                                           null,
    OrderID    varchar(10)                                           null,
    Qty        int                                                   null,
    status     enum ('completed', 'incomplete') default 'incomplete' null,
    constraint menuorderitem_ibfk_1
        foreign key (MenuItemID) references menuitem (MenuItemID)
            on update cascade on delete cascade,
    constraint menuorderitem_ibfk_2
        foreign key (OrderID) references orders (OrderID)
            on update cascade on delete cascade
);

create index MenuItemID
    on menuorderitem (MenuItemID);

create index OrderID
    on menuorderitem (OrderID);

create table payment
(
    PaymentID varchar(10)                not null
        primary key,
    OrderID   varchar(10)                null,
    Amount    decimal(10, 2)             null,
    Date      date                       null,
    Type      varchar(50) default 'Cash' null,
    constraint payment_ibfk_1
        foreign key (OrderID) references orders (OrderID)
            on delete cascade
);

create index OrderID
    on payment (OrderID);

create table salary
(
    SalaryID    varchar(10)    not null
        primary key,
    EmployeeID  varchar(10)    null,
    Amount      decimal(10, 2) null,
    PaymentDate date           null,
    constraint salary_ibfk_1
        foreign key (EmployeeID) references employee (EmployeeID)
            on update cascade on delete cascade
);

create index EmployeeID
    on salary (EmployeeID);


