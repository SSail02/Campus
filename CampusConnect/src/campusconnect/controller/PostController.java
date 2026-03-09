package campusconnect.controller;

import campusconnect.database.DBConnection;
import campusconnect.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostController {

    public boolean createPost(int userId, String message) {
        String sql = "INSERT INTO posts (user_id, message) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, message);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean likePost(int postId) {
        String sql = "UPDATE posts SET likes = likes + 1 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Post> getAllPosts() {
        return getPostsByKeyword(null);
    }

    public List<Post> searchPosts(String keyword) {
        return getPostsByKeyword(keyword);
    }

    private List<Post> getPostsByKeyword(String keyword) {
        String sql = "SELECT p.id, p.user_id, p.message, p.likes, p.created_at, u.username " +
                "FROM posts p JOIN users u ON p.user_id = u.id " +
                "WHERE (? IS NULL OR p.message LIKE ?) ORDER BY p.created_at DESC";

        List<Post> posts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (keyword == null || keyword.trim().isEmpty()) {
                ps.setString(1, null);
                ps.setString(2, null);
            } else {
                String term = "%" + keyword.trim() + "%";
                ps.setString(1, keyword);
                ps.setString(2, term);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post();
                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setMessage(rs.getString("message"));
                    post.setLikes(rs.getInt("likes"));
                    post.setCreatedAt(rs.getTimestamp("created_at"));
                    post.setUsername(rs.getString("username"));
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
