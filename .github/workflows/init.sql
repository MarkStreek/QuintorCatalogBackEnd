CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'QUINTOR';
GRANT ALL PRIVILEGES ON spring_db.* TO 'springuser'@'localhost';
FLUSH PRIVILEGES;