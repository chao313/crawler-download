CREATE TABLE `act_ru_job` (
`ID_` VARCHAR ( 64 ) NOT NULL,
`REV_` INT ( 11 ) DEFAULT NULL,
`TYPE_` VARCHAR ( 255 ) NOT NULL,
`LOCK_EXP_TIME_` TIMESTAMP ( 3 ) NULL DEFAULT NULL,
`LOCK_OWNER_` VARCHAR ( 255 ) DEFAULT NULL,
`EXCLUSIVE_` TINYINT ( 1 ) DEFAULT NULL,
`EXECUTION_ID_` VARCHAR ( 64 ) DEFAULT NULL,
`PROCESS_INSTANCE_ID_` VARCHAR ( 64 ) DEFAULT NULL,
`PROC_DEF_ID_` VARCHAR ( 64 ) DEFAULT NULL,
`RETRIES_` INT ( 11 ) DEFAULT NULL,
`EXCEPTION_STACK_ID_` VARCHAR ( 64 ) DEFAULT NULL,
`EXCEPTION_MSG_` VARCHAR ( 4000 ) DEFAULT NULL,
`DUEDATE_` TIMESTAMP ( 3 ) NULL DEFAULT NULL,
`REPEAT_` VARCHAR ( 255 ) DEFAULT NULL,
`HANDLER_TYPE_` VARCHAR ( 255 ) DEFAULT NULL,
`HANDLER_CFG_` VARCHAR ( 4000 ) DEFAULT NULL,
`TENANT_ID_` VARCHAR ( 255 ) DEFAULT '',
PRIMARY KEY ( `ID_` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;