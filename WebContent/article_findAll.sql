CREATE DEFINER=`root`@`localhost` PROCEDURE `article_findAll`(
  IN firstRecordIndex INT,
  IN pageSize INT,
  IN ss VARCHAR(10),
  IN st NVARCHAR(100),
  IN od VARCHAR(10)
)
BEGIN

SELECT a.*, b.boardName,u.name
FROM article a 
  LEFT JOIN board b ON b.id = a.boardId  
  LEFT JOIN user u ON u.id=a.userId
WHERE 
  (ss = '0') OR
  (ss = '1' AND u.name LIKE st) OR
  (ss = '2' AND a.title LIKE st)
ORDER BY 
  CASE WHEN od = '0' THEN a.ID 
	 WHEN od='1' THEN u.name
	 WHEN od = '4' THEN a.title  
  END ASC, 
  CASE WHEN od = '3' THEN a.no 
   END DESC,
   CASE WHEN od = '2' THEN u.name 
  END DESC
LIMIT firstRecordIndex, pageSize;
  
END