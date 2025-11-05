CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    pass_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mrn VARCHAR(64) NOT NULL UNIQUE,
    full_name VARCHAR(200) NOT NULL,
    dob DATE,
    gender VARCHAR(10),
    phone VARCHAR(30),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_user_id BIGINT NOT NULL,
    start_at DATETIME NOT NULL,
    end_at DATETIME NOT NULL,
    status VARCHAR(30) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_appointments_doctor FOREIGN KEY (doctor_user_id) REFERENCES users(id),
    CONSTRAINT chk_appointments_time CHECK (end_at > start_at)
);

CREATE TABLE visits (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_user_id BIGINT NOT NULL,
    visit_at DATETIME NOT NULL,
    diagnosis TEXT,
    notes TEXT,
    CONSTRAINT fk_visits_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_visits_doctor FOREIGN KEY (doctor_user_id) REFERENCES users(id)
);

CREATE TABLE prescriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    visit_id BIGINT NOT NULL,
    drug_name VARCHAR(200) NOT NULL,
    dose VARCHAR(100),
    qty INT NOT NULL,
    instructions TEXT,
    CONSTRAINT fk_prescriptions_visit FOREIGN KEY (visit_id) REFERENCES visits(id)
);

CREATE TABLE inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_code VARCHAR(64) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    qty INT NOT NULL,
    unit VARCHAR(20),
    min_qty INT NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE lab_orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    visit_id BIGINT NOT NULL,
    test_code VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    result_text TEXT,
    result_file_path VARCHAR(255),
    CONSTRAINT fk_lab_orders_visit FOREIGN KEY (visit_id) REFERENCES visits(id)
);

CREATE TABLE invoices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    paid DECIMAL(10,2) NOT NULL,
    method VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_invoices_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE invoice_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    qty INT NOT NULL,
    CONSTRAINT fk_invoice_items_invoice FOREIGN KEY (invoice_id) REFERENCES invoices(id)
);

CREATE TABLE audit_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    action VARCHAR(100) NOT NULL,
    entity VARCHAR(100) NOT NULL,
    entity_id BIGINT,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_appointments_patient ON appointments(patient_id);
CREATE INDEX idx_visits_patient ON visits(patient_id);
CREATE INDEX idx_lab_orders_visit ON lab_orders(visit_id);
CREATE INDEX idx_invoice_items_invoice ON invoice_items(invoice_id);
