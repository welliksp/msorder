CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code BIGINT NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    current_stock INT NOT NULL,
    stock_minimum INT NOT NULL
);
