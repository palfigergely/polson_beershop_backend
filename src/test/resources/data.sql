DELETE FROM users;
DELETE FROM beers;

INSERT INTO users (id, username, email, brewery, city, country)
VALUES
    (1, 'User', 'example@test.com', 'Brewery1', 'City', 'Country');
