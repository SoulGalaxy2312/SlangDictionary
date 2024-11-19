package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import services.FileService;

public class ShowHistoryView extends PopUpView {
    public ShowHistoryView(JFrame parentFrame, int width, int height, FileService fileService) {
        super(parentFrame, width, height);

        setLayout(new BorderLayout());

        // header
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());

        JLabel label = new JLabel("History of searched slangs");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.RED);
        
        header.add(label);

        //body
        JPanel body = new JPanel();
        body.setLayout(new BorderLayout());
        List<String> history = fileService.getFromHistory();

        JScrollPane scrollPane = new JScrollPane(
            new JList<>(
                history.toArray(new String[history.size()])
            )
        );
        body.add(scrollPane, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        super.turnOn();
    }
}
