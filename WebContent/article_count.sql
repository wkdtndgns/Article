CREATE DEFINER=`root`@`localhost` PROCEDURE `article_count`(
  IN ss VARCHAR(10),
  IN st NVARCHAR(100)
)
BEGIN

SELECT COUNT(a.ID)
FROM article a 
    LEFT JOIN board b ON b.id = a.boardId  
	LEFT JOIN user u ON u.id=a.userId
WHERE 
  (ss = '0') OR
  (ss = '1' AND u.name LIKE st) OR
  (ss = '2' AND a.title LIKE st);

END