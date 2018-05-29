package lecture1.article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lecture1.DB;

public class ArticleDAO {


		public static Article createArticle(ResultSet resultSet) throws SQLException {
			Article article =new Article();

			article.setId(resultSet.getInt("id"));
			article.setBoardId(resultSet.getInt("boardId"));
			article.setUserId(resultSet.getInt("userId"));
			article.setNo(resultSet.getInt("no"));
			article.setWriteTime(resultSet.getTimestamp("writeTime"));
			article.setTitle(resultSet.getString("title"));
			article.setBody(resultSet.getString("body"));
			article.setNotice(resultSet.getBoolean("notice"));
			article.setBoardName(resultSet.getString("boardName"));
			article.setName(resultSet.getString("name"));

	      return article;
	  }

		public static List<Article> findAll(int currentPage, int pageSize, String ss, String st, String od)
			    throws Exception {
					String sql="call article_findAll(?, ?, ?, ?, ?)";
			        try (Connection connection = DB.getConnection("bbs2");
			             PreparedStatement statement = connection.prepareStatement(sql)) {
			            statement.setInt(1, (currentPage - 1) * pageSize); // firstRecordIndex
			            statement.setInt(2, pageSize);                     // pageSize
			            statement.setString(3, ss);                        // 조회 방법
			            statement.setString(4, "%" + st + "%");                  // 검색 문자열
			            statement.setString(5, od);                        // 정렬 순서
			            try (ResultSet resultSet = statement.executeQuery()) {
			                ArrayList<Article> list = new ArrayList<Article>();
			                while (resultSet.next()) {

			                	 list.add(createArticle(resultSet));
			                }
			                return list;
			            }
			        }
			    }




    public static int count(String ss,String st) throws Exception {

    	String sql = "call article_count(?,?)";
        try (Connection connection = DB.getConnection("bbs2");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ss);
            statement.setString(2, "%" + st + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return resultSet.getInt(1);
            }
        }
        return 0;
    }

    public static Article findOne(int id) throws Exception {
        String sql = "SELECT * FROM article WHERE id=?";
        try (Connection connection = DB.getConnection("bbs2");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                  Article article =new Article();
	              	article.setId(resultSet.getInt("id"));
	    			article.setTitle(resultSet.getString("title"));
	    			article.setBody(resultSet.getString("body"));
	    			article.setName(resultSet.getString("userId"));
	    			article.setNotice(resultSet.getBoolean("notice"));
        	      return article;
                }
            }
            return null;
        }
    }

    public static void update(Article article) throws Exception {
        String sql = "UPDATE article SET title=?, body=?, userId=?, notice=? " +
                     " WHERE id = ?";
        try (Connection connection = DB.getConnection("bbs2");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getBody());
            statement.setInt(3, article.getUserId());
            statement.setBoolean(4, article.isNotice());
            statement.setInt(5, article.getId());

            statement.executeUpdate();
        }
    }

    public static void insert(Article article) throws Exception {
        String sql = "INSERT article (boardId, no, userId, writeTime, title, body, notice )" +
                     " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, article.getBoardId());
            statement.setInt(2, article.getNo());
            statement.setInt(3, article.getUserId());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(4, timestamp);
            statement.setString(5, article.getTitle());
            statement.setString(6, article.getBody());
            statement.setBoolean(7, article.isNotice());
            statement.executeUpdate();
        }
    }

    public static void delete(int id) throws Exception {
        String sql = "DELETE FROM article WHERE id = ?";
        try (Connection connection = DB.getConnection("bbs2");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }


}







