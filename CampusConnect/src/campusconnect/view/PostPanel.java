package campusconnect.view;

import campusconnect.model.Post;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PostPanel extends JPanel {
    private final JButton likeButton = new JButton("Like");
    private final JButton commentButton = new JButton("Comment");

    public PostPanel(Post post, Runnable onLike, Runnable onComment) {
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        JLabel meta = new JLabel(post.getUsername() + " • " + post.getCreatedAt());
        meta.setFont(new Font("SansSerif", Font.BOLD, 13));

        JTextArea message = new JTextArea(post.getMessage());
        message.setWrapStyleWord(true);
        message.setLineWrap(true);
        message.setEditable(false);
        message.setBackground(new Color(245, 247, 250));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.setOpaque(false);
        JLabel likesLabel = new JLabel("Likes: " + post.getLikes());

        likeButton.addActionListener(e -> onLike.run());
        commentButton.addActionListener(e -> onComment.run());

        actions.add(likesLabel);
        actions.add(likeButton);
        actions.add(commentButton);

        add(meta, BorderLayout.NORTH);
        add(new JScrollPane(message), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }
}
