-- Insertion de données dans la table address
INSERT INTO address (address_id, street_name, number_address, complementary_number, complementary_address)
VALUES
    (1, 'Rue de la Paix', 123, 'A', 'Appartement 456'),
    (2, 'Avenue des Roses', 789, 'B', 'Bâtiment C');

-- Insertion de données dans la table customer avec des address_id valides
INSERT INTO customer (customer_id, first_name, last_name, email, phone_number, address_id)
VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', '0000000', 1),
    (2, 'Jane', 'Doe', 'jane.doe@example.com', '0000000', 2);

-- Reste de vos insertions ou opérations de test