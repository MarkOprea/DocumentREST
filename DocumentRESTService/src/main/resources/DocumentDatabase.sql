CREATE TABLE Author  (
    authorID INTEGER IDENTITY NOT NULL,
    lastName varchar(255) NOT NULL,
    firstName varchar(255),
    PRIMARY KEY (authorID)
);

CREATE TABLE DocumentMeta  (
    documentID INTEGER IDENTITY NOT NULL,
    authorID INTEGER NOT NULL,
	name varchar(255) NOT NULL,
	location varchar(255) NOT NULL,
	description varchar(255),
	uploadTImestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (documentID),
	FOREIGN KEY (AuthorID) REFERENCES Author(AuthorID)
);

INSERT INTO Author
(lastName,
firstName)
VALUES
('Oprea',
'Mark')

INSERT INTO DocumentMeta
(authorID,
name,
location,
description)
VALUES
(0,
'TestUpload.txt',
'\DocumentRESTService\DocStore',
'Test file')

select * from DocumentMeta;