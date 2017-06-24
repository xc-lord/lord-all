CREATE TABLE groupon (
	id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	deleted bit(1) COMMENT '是否删除',
	name VARCHAR(200) COMMENT '名称',
	price DOUBLE COMMENT '价格',
	img_url VARCHAR(500) COMMENT '图片',
	store_num INT COMMENT '库存数',
	like_num INT COMMENT '喜欢数',
	descs text COMMENT '描述',
	start_time TIMESTAMP COMMENT '开始时间',
	end_time TIMESTAMP COMMENT '结束时间',
	self bit(1) COMMENT '是否自营'
) COMMENT='团购'