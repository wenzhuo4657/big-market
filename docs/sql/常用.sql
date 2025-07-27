--   清空业务数据库

--    show tables无法和其他语句配合，看样子只是一个简写的命令
-- USE big_market;
-- SHOW TABLES;

SELECT  table_name	 From information_schema.TABLES  WHERE table_schema = 'your_database_name';