SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int AUTO_INCREMENT,
  `address` varchar(255),
  `cardid` varchar(255),
  `name` varchar(255),
  `phone` varchar(255)
);

-- ----------------------------
-- Table structure for desk
-- ----------------------------
DROP TABLE IF EXISTS `desk`;
CREATE TABLE `desk`  (
  `id` int AUTO_INCREMENT,
  `capacity` int,
  `code` varchar(255),
  `description` varchar(255),
  `open` bit(1)
);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
  `id` int AUTO_INCREMENT,
  `code` varchar(255),
  `description` varchar(255),
  `image_url` varchar(255),
  `name` varchar(255),
  `price` double,
  `dish_category_id` int,
  CONSTRAINT `FKdexet01io54i3kyi50oo9gip2` FOREIGN KEY (`dish_category_id`) REFERENCES `dish_category` (`id`)
);

-- ----------------------------
-- Table structure for dish_category
-- ----------------------------
DROP TABLE IF EXISTS `dish_category`;
CREATE TABLE `dish_category`  (
  `id` int AUTO_INCREMENT,
  `description` varchar(255),
  `tags` varchar(255)
);

-- ----------------------------
-- Table structure for dish_hint
-- ----------------------------
DROP TABLE IF EXISTS `dish_hint`;
CREATE TABLE `dish_hint`  (
  `id` int AUTO_INCREMENT,
  `hint_text` varchar(255),
  `hint_type` varchar(255),
  `priority` varchar(255),
  `dish_id` int,
  CONSTRAINT `FKh80nu50mqsygw0xwkxfyd8gxi` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`)
);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int AUTO_INCREMENT,
  `age` int,
  `name` varchar(255),
  `phone` varchar(255)
);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int AUTO_INCREMENT,
  `create_date` date,
  `price` decimal(10, 2),
  `status` varchar(255),
  `customer_id` int,
  `desk_id` int,
  `employee_id` int,
  CONSTRAINT `FK1oduxyuuo3n2g98l3j7754vym` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FKdjonorx78bdje2vp5oyrt0qrq` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKqs6anf8by84pjk0ojgkr0j697` FOREIGN KEY (`desk_id`) REFERENCES `desk` (`id`)
);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` int AUTO_INCREMENT,
  `quantity` int,
  `dish_id` int,
  `order_id` int,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKs234mi6jususbx4b37k44cipy` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `FKs7aplknkrukmckr29wijlvcy1` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`)
);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(255),
  `password_hash` varchar(255),
  `salt` varchar(255),
  `username` varchar(255),
  PRIMARY KEY (`id`)
);

SET FOREIGN_KEY_CHECKS = 1;
