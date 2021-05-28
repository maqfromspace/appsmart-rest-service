DELETE FROM public.user_roles;
DELETE FROM public.roles;
DELETE FROM public.users;

INSERT INTO public.roles(id, name)
VALUES (1, 'ADMIN_ROLE');

INSERT INTO public.users(id, created_at, modified_at, password, username)
VALUES (1, current_timestamp , current_timestamp , '$2y$12$Ui0bA9j/5xtfjNYxnD094O4M/MNfyYkjolRGYPvlqKp6fYto6GRkC', 'admin');

INSERT INTO public.user_roles(user_id, role_id)
VALUES (1, 1);