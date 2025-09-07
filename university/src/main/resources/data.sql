WITH new_user AS (
    INSERT INTO un_user (name, email, password)
    VALUES ('rahul', 'rahul@gmail.com', '{bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMy.MH1JmG0f8hU1xI9Tk3z6v1GpUW1KxXa')
    RETURNING id, email
)
INSERT INTO roles (email, role, user_id)
SELECT email, 'ROLE_USER', id FROM new_user
UNION ALL
SELECT email, 'ROLE_ADMIN', id FROM new_user;