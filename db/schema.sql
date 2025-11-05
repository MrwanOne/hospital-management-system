CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    pass_hash VARCHAR(255) NOT NULL,
    role VARCHAR(32) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mrn VARCHAR(32) NOT NULL UNIQUE,
    full_name VARCHAR(128) NOT NULL,
    dob DATE,
    gender VARCHAR(16),
    phone VARCHAR(32),
    address VARCHAR(256),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_user_id BIGINT NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    status VARCHAR(32) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_appointments_doctor FOREIGN KEY (doctor_user_id) REFERENCES users (id)
);

CREATE TABLE visits (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_user_id BIGINT NOT NULL,
    visit_at TIMESTAMP NOT NULL,
    diagnosis TEXT,
    notes TEXT,
    CONSTRAINT fk_visits_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_visits_doctor FOREIGN KEY (doctor_user_id) REFERENCES users (id)
);

CREATE TABLE prescriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    visit_id BIGINT NOT NULL,
    drug_name VARCHAR(128) NOT NULL,
    dose VARCHAR(64),
    qty INT,
    instructions VARCHAR(255),
    CONSTRAINT fk_prescriptions_visit FOREIGN KEY (visit_id) REFERENCES visits (id)
);

CREATE TABLE inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_code VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(128) NOT NULL,
    qty INT NOT NULL DEFAULT 0,
    unit VARCHAR(32),
    min_qty INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE lab_orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    visit_id BIGINT NOT NULL,
    test_code VARCHAR(64) NOT NULL,
    status VARCHAR(32) NOT NULL,
    result_text TEXT,
    result_file_path VARCHAR(255),
    CONSTRAINT fk_lab_orders_visit FOREIGN KEY (visit_id) REFERENCES visits (id)
);

CREATE TABLE invoices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    paid DECIMAL(10,2) NOT NULL DEFAULT 0,
    method VARCHAR(32),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_invoices_patient FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE invoice_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    qty INT NOT NULL,
    CONSTRAINT fk_invoice_items_invoice FOREIGN KEY (invoice_id) REFERENCES invoices (id)
);

CREATE TABLE audit_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    action VARCHAR(32) NOT NULL,
    entity VARCHAR(64) NOT NULL,
    entity_id BIGINT,
    ts TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_appointments_start ON appointments (doctor_user_id, start_at);
CREATE INDEX idx_visits_visit_at ON visits (visit_at);
CREATE INDEX idx_inventory_code ON inventory (item_code);
CREATE INDEX idx_lab_orders_status ON lab_orders (status);
CREATE INDEX idx_invoices_created ON invoices (created_at);
