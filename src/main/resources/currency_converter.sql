
-- Currencies Table
CREATE TABLE currencies (
    currency_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    symbol VARCHAR(5),
    country VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Conversion History Table
CREATE TABLE conversion_history (
    conversion_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    base_currency_code VARCHAR(3) NOT NULL,
    target_currency_code VARCHAR(3) NOT NULL,
    base_amount DECIMAL(19, 2) NOT NULL,
    converted_amount DECIMAL(19, 2) NOT NULL,
    exchange_rate DECIMAL(19, 6) NOT NULL,
    conversion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_identifier VARCHAR(100),
    FOREIGN KEY (base_currency_code) REFERENCES currencies(code),
    FOREIGN KEY (target_currency_code) REFERENCES currencies(code)
);

-- Exchange Rate Table
CREATE TABLE exchange_rate (
    rate_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_currency_code VARCHAR(3) NOT NULL,
    to_currency_code VARCHAR(3) NOT NULL,
    exchange_rate DECIMAL(19, 6) NOT NULL,
    rate_date TIMESTAMP NOT NULL,
    source VARCHAR(100),
    FOREIGN KEY (from_currency_code) REFERENCES currencies(code),
    FOREIGN KEY (to_currency_code) REFERENCES currencies(code)
);

-- Initial Data Insertion
INSERT INTO currencies (code, name, symbol, country) VALUES 
('USD', 'United States Dollar', '$', 'United States'),
('EUR', 'Euro', '€', 'European Union'),
('GBP', 'British Pound Sterling', '£', 'United Kingdom'),
('JPY', 'Japanese Yen', '¥', 'Japan'),
('AUD', 'Australian Dollar', 'A$', 'Australia'),
('CAD', 'Canadian Dollar', 'C$', 'Canada');