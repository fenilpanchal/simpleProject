
INSERT INTO roles(name) VALUES ('USER'), ('ADMIN'), ('SUPER_ADMIN');


INSERT INTO permissions(name) VALUES
('VIEW_USER'), ('UPDATE_USER'), ('DELETE_USER'),
('VIEW_ADMIN'), ('UPDATE_ADMIN'), ('DELETE_ADMIN');


INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE r.name = 'USER' AND p.name IN ('VIEW_USER', 'UPDATE_USER', 'DELETE_USER');


INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE r.name = 'ADMIN' AND p.name IN (
    'VIEW_USER', 'UPDATE_USER', 'DELETE_USER',
    'VIEW_ADMIN', 'UPDATE_ADMIN', 'DELETE_ADMIN'
);


INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id FROM roles r CROSS JOIN permissions p
WHERE r.name = 'SUPER_ADMIN';
