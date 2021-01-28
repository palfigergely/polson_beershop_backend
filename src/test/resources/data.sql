DELETE FROM users;
DELETE FROM beers;

INSERT INTO users (id, username, password, email, brewery, city, country)
VALUES
    (1, 'User', '$10$KOcvXXZMViYICV8jCYOOOejl6lQzx5noiUwyHdN8ZtQNycRA2keAi', 'example@test.com', 'Brewery1', 'City', 'Country');
