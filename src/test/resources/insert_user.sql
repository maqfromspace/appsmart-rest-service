DELETE FROM public.users;
INSERT INTO public.users(
    id, created_at, modified_at, password, username)
VALUES (1, current_timestamp , current_timestamp , '$2y$12$Ui0bA9j/5xtfjNYxnD094O4M/MNfyYkjolRGYPvlqKp6fYto6GRkC', 'admin');