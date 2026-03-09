package campusconnect.view;

import campusconnect.controller.CommentController;
import campusconnect.controller.PostController;
import campusconnect.model.Comment;
import campusconnect.model.Post;
import campusconnect.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final User currentUser;
    private final PostController postController = new PostController();
    private final CommentController commentController = new CommentController();

    private final JTextArea createPostArea = new JTextArea(3, 30);
    private final JTextField searchField = new JTextField(20);
    private final JPanel feedPanel = new JPanel();
    private final DefaultListModel<String> commentListModel = new DefaultListModel<>();
    private final JList<String> commentList = new JList<>(commentListModel);
    private final JTextField commentField = new JTextField();
    private final JLabel selectedPostLabel = new JLabel("Selected Post: None");

    private Integer selectedPostId = null;

    public DashboardFrame(User currentUser) {
        this.currentUser = currentUser;
        setTitle("Campus Connect Dashboard - " + currentUser.getUsername());
        setSize(980, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initUI();
        loadFeed(null);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(6, 6));
        topPanel.setBorder(BorderFactory.createTitledBorder("Create Post"));
        createPostArea.setLineWrap(true);
        createPostArea.setWrapStyleWord(true);
        topPanel.add(new JScrollPane(createPostArea), BorderLayout.CENTER);

        JPanel topRight = new JPanel(new GridLayout(3, 1, 4, 4));
        JButton postButton = new JButton("Publish Post");
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh Feed");
        postButton.addActionListener(e -> createPost());
        searchButton.addActionListener(e -> loadFeed(searchField.getText().trim()));
        refreshButton.addActionListener(e -> loadFeed(null));
        topRight.add(postButton);
        topRight.add(searchButton);
        topRight.add(refreshButton);
        topPanel.add(topRight, BorderLayout.EAST);

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchBar.add(new JLabel("Keyword:"));
        searchBar.add(searchField);
        topPanel.add(searchBar, BorderLayout.SOUTH);

        root.add(topPanel, BorderLayout.NORTH);

        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        root.add(new JScrollPane(feedPanel), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(6, 6));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Comment Section"));
        bottomPanel.add(selectedPostLabel, BorderLayout.NORTH);

        commentList.setVisibleRowCount(5);
        bottomPanel.add(new JScrollPane(commentList), BorderLayout.CENTER);

        JPanel addCommentPanel = new JPanel(new BorderLayout(4, 4));
        JButton addCommentButton = new JButton("Add Comment");
        addCommentButton.addActionListener(e -> addComment());
        addCommentPanel.add(commentField, BorderLayout.CENTER);
        addCommentPanel.add(addCommentButton, BorderLayout.EAST);
        bottomPanel.add(addCommentPanel, BorderLayout.SOUTH);

        root.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void createPost() {
        String message = createPostArea.getText().trim();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a post message.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (postController.createPost(currentUser.getId(), message)) {
            createPostArea.setText("");
            loadFeed(null);
        } else {
            JOptionPane.showMessageDialog(this, "Could not create post.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFeed(String keyword) {
        feedPanel.removeAll();
        List<Post> posts = (keyword == null || keyword.isEmpty())
                ? postController.getAllPosts()
                : postController.searchPosts(keyword);

        for (Post post : posts) {
            PostPanel panel = new PostPanel(post,
                    () -> like(post.getId()),
                    () -> selectPost(post));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
            feedPanel.add(panel);
            feedPanel.add(Box.createVerticalStrut(8));
        }

        feedPanel.revalidate();
        feedPanel.repaint();
    }

    private void like(int postId) {
        if (!postController.likePost(postId)) {
            JOptionPane.showMessageDialog(this, "Like failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        loadFeed(searchField.getText().trim());
    }

    private void selectPost(Post post) {
        selectedPostId = post.getId();
        selectedPostLabel.setText("Selected Post: #" + post.getId() + " by " + post.getUsername());
        commentListModel.clear();
        List<Comment> comments = commentController.getCommentsByPost(post.getId());
        for (Comment comment : comments) {
            commentListModel.addElement(comment.getUsername() + ": " + comment.getComment());
        }
    }

    private void addComment() {
        if (selectedPostId == null) {
            JOptionPane.showMessageDialog(this, "Select a post first by clicking Comment on a post card.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String text = commentField.getText().trim();
        if (text.isEmpty()) {
            return;
        }

        if (commentController.addComment(selectedPostId, currentUser.getId(), text)) {
            commentField.setText("");
            List<Comment> comments = commentController.getCommentsByPost(selectedPostId);
            commentListModel.clear();
            for (Comment comment : comments) {
                commentListModel.addElement(comment.getUsername() + ": " + comment.getComment());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add comment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
