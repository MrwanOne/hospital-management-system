INSERT INTO users (username, pass_hash, role, active) VALUES
 ('admin', '$2a$10$z0SPxGX3YeliYg5OtTSkeuB2q8np5xpoE2mR7BfrpsbR9f7D78Dfe', 'ADMIN', 1),
 ('dr_smith', '$2a$10$z0SPxGX3YeliYg5OtTSkeuB2q8np5xpoE2mR7BfrpsbR9f7D78Dfe', 'DOCTOR', 1),
 ('nurse_jane', '$2a$10$z0SPxGX3YeliYg5OtTSkeuB2q8np5xpoE2mR7BfrpsbR9f7D78Dfe', 'NURSE', 1),
 ('reception', '$2a$10$z0SPxGX3YeliYg5OtTSkeuB2q8np5xpoE2mR7BfrpsbR9f7D78Dfe', 'RECEPTION', 1);

INSERT INTO patients (mrn, full_name, dob, gender, phone, address) VALUES
 ('MRN-1001', 'John Doe', '1985-03-15', 'Male', '+11234567890', '123 Main St'),
 ('MRN-1002', 'Mary Smith', '1990-07-22', 'Female', '+11234567891', '456 High St');

INSERT INTO appointments (patient_id, doctor_user_id, start_at, end_at, status, notes) VALUES
 (1, 2, '2024-05-01 09:00:00', '2024-05-01 09:30:00', 'SCHEDULED', 'Annual checkup'),
 (2, 2, '2024-05-01 10:00:00', '2024-05-01 10:30:00', 'SCHEDULED', 'Follow-up visit');

INSERT INTO visits (patient_id, doctor_user_id, visit_at, diagnosis, notes) VALUES
 (1, 2, '2024-04-15 09:00:00', 'Hypertension', 'Monitor blood pressure'),
 (2, 2, '2024-04-18 10:00:00', 'Diabetes Type II', 'Adjust medication');

INSERT INTO prescriptions (visit_id, drug_name, dose, qty, instructions) VALUES
 (1, 'Lisinopril', '10mg', 30, 'Take once daily'),
 (2, 'Metformin', '500mg', 60, 'Take twice daily');

INSERT INTO inventory (item_code, name, qty, unit, min_qty, price) VALUES
 ('DRG-001', 'Lisinopril 10mg', 200, 'tablets', 50, 0.25),
 ('DRG-002', 'Metformin 500mg', 300, 'tablets', 50, 0.18);

INSERT INTO lab_orders (visit_id, test_code, status, result_text, result_file_path) VALUES
 (1, 'CBC', 'COMPLETED', 'All values within range', NULL),
 (2, 'HBA1C', 'PENDING', NULL, NULL);

INSERT INTO invoices (patient_id, total, paid, method) VALUES
 (1, 120.00, 120.00, 'CASH'),
 (2, 200.00, 150.00, 'CARD');

INSERT INTO invoice_items (invoice_id, description, unit_price, qty) VALUES
 (1, 'Consultation', 80.00, 1),
 (1, 'Lab Tests', 40.00, 1),
 (2, 'Consultation', 100.00, 1),
 (2, 'Medication', 50.00, 2);

INSERT INTO audit_log (user_id, action, entity, entity_id) VALUES
 (1, 'CREATE', 'PATIENT', 1),
 (1, 'CREATE', 'PATIENT', 2),
 (2, 'CREATE', 'APPOINTMENT', 1);
