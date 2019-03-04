



DROP DATABASE IF EXISTS oauth;
CREATE DATABASE oauth DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP DATABASE IF EXISTS service1;
CREATE DATABASE service1 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP DATABASE IF EXISTS service2;
CREATE DATABASE service2 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP USER IF EXISTS dbuser;
CREATE USER dbuser@'%' IDENTIFIED WITH mysql_native_password BY 'DBPassw0rd!';
GRANT ALL PRIVILEGES ON oauth.* TO 'dbuser'@'%';
GRANT ALL PRIVILEGES ON service1.* TO 'dbuser'@'%';
GRANT ALL PRIVILEGES ON service2.* TO 'dbuser'@'%';
FLUSH PRIVILEGES;




http://localhost:5000/api/pub/auth/login

{
	"usernameOrEmail": "admin",
	"password": "USPassw@rd!"
}


SessionManagementFilter