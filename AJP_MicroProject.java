package microproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AJP_MicroProject extends JFrame {
    private JTextArea paragraphTextArea;
    private JTextArea inputTextArea;
    private JButton submitButton;
    private JButton re_a;
    private JTextArea speedTA;
    private JTextArea speed_output = new JTextArea();
    private Timer timer;
    private String currentLesson;
    private int lessonIndex;
    private int errorCount;
    private long startTime;
    private long endTime;
    private float totalExecutionTimeInSeconds;
    private JFrame resultFrame = new JFrame();


    private static final String[] WORDS = {
        "The dictionary meaning of advance is a forward"
                + "\nmovement or development or improvement and"
                + "\nthe meaning of improve means a thing that makes"
                + "\nsomething better. All in all, we have to improve"
                + "\nour basic knowledge to master in that particular"
                + "\nfield.",

        "Electricity is both a basic part of nature and"
                + "\none of the most widely used forms of energy. The"
                + "\nelectricity that we use is a secondary energy"
                + "\nsource because it is produced by converting"
                + "\nprimary sources of energy such as coal, natural"
                + "\ngas, nuclear energy, solar energy, and wind"
                + "\nenergy into electrical power."
        // Add more lesson texts here...
    };

    private static final int MAX_LESSON_DURATION_MS = 300000;

    public AJP_MicroProject() {
    	setTitle("Manas_Patil");
        setLayout(new BorderLayout());

        paragraphTextArea = new JTextArea();
        inputTextArea = new JTextArea();
        submitButton = new JButton("Submit");
        speedTA = new JTextArea();
        re_a = new JButton("Re_Attend");

        JPanel inputPanel = new JPanel(new BorderLayout());

        // Add a separator and set its color to white
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(20, 20)); // Adjust the gap size
        separator.setForeground(Color.white); // Set the color to white
        
        inputPanel.add(separator, BorderLayout.NORTH); // Add the separator above the inputTextArea
        inputPanel.add(inputTextArea, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.SOUTH);
        add(paragraphTextArea, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(speedTA, BorderLayout.SOUTH);

        inputTextArea.setEditable(true);
        paragraphTextArea.setEditable(false);
        inputTextArea.setLineWrap(true);
        
        paragraphTextArea.setBackground(Color.cyan);
        inputTextArea.setBackground(Color.yellow);
        
        Font customFont = new Font("Arial", Font.PLAIN, 14);
        paragraphTextArea.setFont(customFont);
        inputTextArea.setFont(customFont);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopLesson();
                calculateTotalExecutionTime();
                setVisible(false);
            }
        });
        re_a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Restart the lesson
                startLesson();
               
                submitButton.setEnabled(true);
                setVisible(true);
                resultFrame.setVisible(false);

            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        
        startLesson();
        
    }

    private void startLesson() {
        if (lessonIndex >= WORDS.length) {
            displayResults();
            return;
        }

        currentLesson = generateRandomParagraph(1);
        paragraphTextArea.setText(currentLesson);
        inputTextArea.setText("");
        inputTextArea.setEditable(true);
        speedTA.setText("	Click 'Submit' To See Result..");
        speedTA.setEditable(false);
        speedTA.setBackground(Color.green);
        errorCount = 0;
        startTime = System.currentTimeMillis();
        endTime = 0;

        timer = new Timer(MAX_LESSON_DURATION_MS, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopLesson();
                calculateTotalExecutionTime();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void stopLesson() {
        timer.stop();
        endTime = System.currentTimeMillis();
        inputTextArea.setEditable(false);
        int mismatchCount = calculateMismatchCount();
        int typingSpeed = calculateTypingSpeed();
        
        float totalExecutionTime = System.currentTimeMillis() - startTime;
        totalExecutionTimeInSeconds = totalExecutionTime / 1000; // Calculate in seconds
        //System.out.println();
        
        speed_output.setText("	Lesson completed...! \n\nTyping Speed: " + typingSpeed + " WPM.\n\nMismatch Count: " + mismatchCount
        		+".\n\nDuration: " + totalExecutionTimeInSeconds + " sec.");
        submitButton.setEnabled(false);
        speed_output.setEnabled(false);
        showResultFrame("Lesson Result", speed_output.getText());
    }

    private int calculateTypingSpeed() {
        long elapsedTimeInSeconds = (endTime - startTime) / 1000;
        int wordCount = currentLesson.split("\\s+").length;
        int typingSpeed = (int) ((wordCount / (double) elapsedTimeInSeconds) * 60);
        return typingSpeed;
    }

    private int calculateMismatchCount() {
        String inputText = inputTextArea.getText();
        int mismatchCount = 0;
        for (int i = 0; i < currentLesson.length() && i < inputText.length(); i++) {
            if (currentLesson.charAt(i) != inputText.charAt(i)) {
                mismatchCount++;
            }
        }
        return mismatchCount;
    }

    private void displayResults() {
        JOptionPane.showMessageDialog(this, "All lessons completed!");
        System.exit(0);
    }

    private String generateRandomParagraph(int numberOfWords) {
        Random random = new Random();
        StringBuilder paragraph = new StringBuilder();

        for (int i = 0; i < numberOfWords; i++) {
            int randomIndex = random.nextInt(WORDS.length);
            paragraph.append(WORDS[randomIndex]).append(" ");
        }

        return paragraph.toString();
    }

    private void calculateTotalExecutionTime() {
        
    }

    private void showResultFrame(String title, String resultText) {
        resultFrame.setLayout(new BorderLayout());
        resultFrame.setTitle("Output");

        JTextArea resultTextArea = new JTextArea(resultText);
        resultTextArea.setEditable(false);
        resultTextArea.setBackground(Color.cyan);
        resultFrame.add(resultTextArea, BorderLayout.CENTER);
        resultFrame.add(re_a, BorderLayout.SOUTH); // Add the re_a button

        resultFrame.setSize(300, 200);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AJP_MicroProject();
            }
        });
    }
}
