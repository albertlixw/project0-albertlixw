DROP TABLE IF EXISTS homes CASCADE;
DROP TABLE IF EXISTS map_users_accounts CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;

CREATE TABLE homes (
	home_name VARCHAR(50) PRIMARY KEY,
	home_number VARCHAR(10),
	home_street VARCHAR(50),
	home_city VARCHAR(50),
	home_region VARCHAR(50),
	home_zip VARCHAR(15),
	home_country VARCHAR (20)
);

CREATE TABLE users (
	userid SERIAL PRIMARY KEY,
	--accountid integer, 
	user_level integer, --LEVEL 0 AS customer, 1 AS clerk, 2 AS admin
	username varchar(50) UNIQUE,
	pwd varchar(50),
	keyword varchar(50),
	home varchar(100) REFERENCES homes(home_name)
);

CREATE TABLE accounts(
	accountid serial PRIMARY KEY, 
	--userid integer,-- REFERENCES map_users_accounts(userid), 
	balance numeric(50, 2),
	created_on timestamp
);

CREATE TABLE map_users_accounts(
	userid integer  REFERENCES users(userid), 
	accountid integer REFERENCES accounts(accountid),
	joint_id serial PRIMARY KEY
);

CREATE OR REPLACE FUNCTION created_timestamp() RETURNS TRIGGER AS 
$BODY$
BEGIN 

	UPDATE accounts SET created_on = (CURRENT_TIMESTAMP)
	WHERE accounts.accountid  = NEW.accountid;
	RETURN NEW;
	
END
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER created_timestamp AFTER INSERT ON accounts
	FOR EACH ROW EXECUTE PROCEDURE created_timestamp();


--	Alter map ON DELETE CASCADE 

INSERT INTO homes (home_name, home_number, home_street, home_city, home_region, home_zip, home_country)
	VALUES ('stark tower', '123', 'stark lane', 'new york', 'ny', '10587', 'usa');

INSERT INTO homes (home_name, home_number, home_street, home_city, home_region, home_zip, home_country)
	VALUES ('helicarrier', '111', 'patomic river', 'washington', 'dc', '20105', 'usa');


INSERT INTO users(user_level, username, pwd, keyword, home)
	values(2, 'admin', '012*', 'keyword', 'stark tower');
	
INSERT INTO users(user_level, username, pwd, keyword, home)
	values(1, 'user1', '123*', 'keyword', 'stark tower');

INSERT INTO users(user_level, username, pwd, keyword, home)
	values(0, 'user0', '012*', 'keyword', 'stark tower');

INSERT INTO users(user_level, username, pwd, keyword)
	values(0, 'hulk', 'smash*', 'Natasha');

INSERT INTO users(user_level, username, pwd, keyword)
	values(0, 'Natasha', 'Natasha*', 'Bruce');
	

INSERT INTO accounts(balance) 
 	VALUES (300.99);
 
--INSERT INTO accounts(userid, balance) 
-- 	VALUES (500.99);

 INSERT INTO accounts(balance) 
 	VALUES (200.00); 
INSERT INTO accounts(balance) 
 	VALUES (100.00);
INSERT INTO accounts(balance) 
 	VALUES (58.88);
 


INSERT INTO map_users_accounts (userid, accountid) 
	VALUES (1, 1);
 INSERT INTO map_users_accounts (userid, accountid) 
	VALUES (1, 3);
 INSERT INTO map_users_accounts (userid, accountid)
 	VALUES (1, 2);
 INSERT INTO map_users_accounts (userid, accountid) 
	VALUES (2, 2); 
INSERT INTO map_users_accounts (userid, accountid) 
	VALUES (3, 2);
INSERT INTO map_users_accounts (userid, accountid) 
	VALUES (3, 3);


 



--Proper findAllAccountsByUser
--SELECT * FROM accounts a 
--JOIN map_users_accounts m ON a.accountid = m.accountid
--JOIN users u 
--ON u.userid = m.userid
--WHERE u.userid = 1

