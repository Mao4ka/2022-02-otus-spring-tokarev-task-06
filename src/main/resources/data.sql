insert into Genre (id, Genre_name) values (0, 'genre_unknown');
insert into Genre (id, Genre_name) values (1, 'genre_01');
insert into Genre (id, Genre_name) values (2, 'genre_02');
insert into Genre (id, Genre_name) values (3, 'genre_03');

insert into Author (id, Author_name) values (0, 'author_unknown');
insert into Author (id, Author_name) values (1, 'author_01');
insert into Author (id, Author_name) values (2, 'author_02');
insert into Author (id, Author_name) values (3, 'author_03');
insert into Author (id, Author_name) values (4, 'author_04');
insert into Author (id, Author_name) values (5, 'author_05');

insert into Book (Id, Title, Genre_Id, Author_Id)
values (0,'title_1',
        (select id from Genre where Genre_name = 'genre_unknown'),
        (select id from Author where Author_name = 'author_unknown')
);

insert into Book (Id, Title, Genre_Id, Author_Id)
values (1,'title_1',
        (select id from Genre where Genre_name = 'genre_03'),
        (select id from Author where Author_name = 'author_01')
       );

insert into Author(Id, Comment_Text, Book_Id) values (0, 'Аффтар жжот, пешы истчо!', 1);
insert into Author(Id, Comment_Text, Book_Id) values (1, 'Убейся ап стенку', 1);
insert into Author(Id, Comment_Text, Book_Id) values (2, 'Автор, займись, наконец, чем-нибудь полезным. Это явно не твое...', 1);
insert into Author(Id, Comment_Text, Book_Id) values (3, 'Што это? 0_о', 0);