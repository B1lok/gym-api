INSERT INTO user_subscription (id, expiration_date, purchase_date, subscription_type, with_coach,
                               trainings_left, coach_id, subscription_id, user_id)
VALUES (1,'2024-12-31', '2024-12-01', 'GYM', FALSE, 10, NULL, 1, 1),
       (2,'2024-11-30', '2024-11-01', 'GYM', TRUE, 5, 3, 2, 2);
