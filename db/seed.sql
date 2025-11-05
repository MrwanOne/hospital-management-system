INSERT INTO users (username, pass_hash, role) VALUES
('admin', '$2a$12$abcdefghijklmnopqrstuv', 'ADMIN'),
('doctor', '$2a$12$abcdefghijklmnopqrstuv', 'DOCTOR');

INSERT INTO patients (mrn, full_name, dob, gender, phone, address) VALUES
('MRN-001', 'John Doe', '1985-05-10', 'Male', '+155555501', '123 Main St'),
('MRN-002', 'Jane Smith', '1990-08-22', 'Female', '+155555502', '456 Oak Ave');

INSERT INTO appointments (patient_id, doctor_user_id, start_at, end_at, status, notes) VALUES
(1, 2, '2024-07-01 09:00:00', '2024-07-01 09:30:00', 'SCHEDULED', 'Initial consult');

INSERT INTO visits (patient_id, doctor_user_id, visit_at, diagnosis, notes) VALUES
(1, 2, '2024-06-01 10:00:00', 'Hypertension', 'Monitor blood pressure');

INSERT INTO prescriptions (visit_id, drug_name, dose, qty, instructions) VALUES
(1, 'Lisinopril', '10mg', 30, 'Take once daily');

INSERT INTO inventory (item_code, name, qty, unit, min_qty, price) VALUES
('MED-001', 'Lisinopril 10mg', 200, 'tablet', 50, 0.45),
('SUP-001', 'Syringe 5ml', 500, 'piece', 100, 0.25);

INSERT INTO lab_orders (visit_id, test_code, status, result_text) VALUES
(1, 'CBC', 'COMPLETED', 'All values normal');

INSERT INTO invoices (patient_id, total, paid, method) VALUES
(1, 150.00, 150.00, 'Cash');

INSERT INTO invoice_items (invoice_id, description, unit_price, qty) VALUES
(1, 'Consultation', 100.00, 1),
(1, 'Laboratory', 50.00, 1);

INSERT INTO audit_log (user_id, action, entity, entity_id) VALUES
(1, 'CREATE', 'PATIENT', 1);
