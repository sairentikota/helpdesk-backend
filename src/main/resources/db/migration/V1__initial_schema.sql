CREATE TABLE app_users(id BIGINT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(120) NOT NULL,email VARCHAR(190) NOT NULL UNIQUE,password_hash VARCHAR(100) NOT NULL,role VARCHAR(20) NOT NULL CHECK(role IN('USER','AGENT','ADMIN')),enabled BOOLEAN NOT NULL DEFAULT TRUE,created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE categories(id BIGINT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(80) NOT NULL UNIQUE);
CREATE TABLE tickets(id BIGINT AUTO_INCREMENT PRIMARY KEY,title VARCHAR(180) NOT NULL,description TEXT NOT NULL,category_id BIGINT NOT NULL REFERENCES categories(id),priority VARCHAR(20) NOT NULL CHECK(priority IN('LOW','MEDIUM','HIGH','URGENT')),status VARCHAR(20) NOT NULL CHECK(status IN('OPEN','ASSIGNED','IN_PROGRESS','RESOLVED','CLOSED')),requester_id BIGINT NOT NULL REFERENCES app_users(id),assigned_agent_id BIGINT REFERENCES app_users(id),resolution_notes TEXT,created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,resolved_at TIMESTAMP);
CREATE TABLE ticket_comments(id BIGINT AUTO_INCREMENT PRIMARY KEY,ticket_id BIGINT NOT NULL REFERENCES tickets(id) ON DELETE CASCADE,author_id BIGINT NOT NULL REFERENCES app_users(id),body TEXT NOT NULL,created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
CREATE INDEX idx_tickets_requester ON tickets(requester_id);
CREATE INDEX idx_tickets_agent ON tickets(assigned_agent_id);
CREATE INDEX idx_tickets_status ON tickets(status);
CREATE INDEX idx_tickets_priority ON tickets(priority);
INSERT INTO categories(name) VALUES('Hardware'),('Software'),('Network'),('Account Access'),('Other');

-- Demo credentials use Password123!; replace or remove this seed for public deployment.
INSERT INTO app_users(name,email,password_hash,role) VALUES
('Demo Admin','admin@helpdesk.local','$2a$10$7EqJtq98hPqEX7fNZaFWoO5qO5x5Q8QZ6XQ6M0GzqK5K7j3g7Kz5W','ADMIN'),
('Support Agent One','agent1@helpdesk.local','$2a$10$7EqJtq98hPqEX7fNZaFWoO5qO5x5Q8QZ6XQ6M0GzqK5K7j3g7Kz5W','AGENT'),
('Support Agent Two','agent2@helpdesk.local','$2a$10$7EqJtq98hPqEX7fNZaFWoO5qO5x5Q8QZ6XQ6M0GzqK5K7j3g7Kz5W','AGENT'),
('Demo User','user@helpdesk.local','$2a$10$7EqJtq98hPqEX7fNZaFWoO5qO5x5Q8QZ6XQ6M0GzqK5K7j3g7Kz5W','USER');
INSERT INTO tickets(title,description,category_id,priority,status,requester_id,assigned_agent_id) VALUES
('Campus Wi-Fi not connecting','Laptop connects but internet is unavailable.',3,'HIGH','ASSIGNED',4,2),
('IDE installation request','Please help install the Java development environment.',2,'MEDIUM','IN_PROGRESS',4,3),
('Keyboard key not responding','The enter key occasionally fails.',1,'LOW','OPEN',4,NULL);
