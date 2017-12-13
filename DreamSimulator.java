/**
 * Created by cb on 11/16/17.
 */
import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;

public class DreamSimulator {

    static ArrayList<String> plist = makeList("positive.txt");
    static ArrayList<String> nlist = makeList("negative.txt");
    static String name;
    static String career;
    static DrawingPanel p;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        start(console);
    }


    public static ArrayList<String> makeList(String name) {
        ArrayList<String> res = new ArrayList<>();
        String line;
        URL path = DreamSimulator.class.getResource(name);
        try {
            FileReader f = new FileReader(path.getFile());
            BufferedReader bufferedReader = new BufferedReader(f);

            while((line = bufferedReader.readLine()) != null) {
                res.add(line.trim());
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + name + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + name + "'");
        }
        return res;
    }

    public static void start(Scanner console) {
        System.out.println("What is your name?");
        name = console.nextLine();
        System.out.println("What is your dream career when you are a child?");
        career = console.nextLine();
        System.out.println("A journey of reliving your childhood dream will start!");
        p = new DrawingPanel(1200, 600);
        Graphics g =p.getGraphics();
        child(console, g);
    }

    public static void child(Scanner console, Graphics g) {
        drawChild(g);
        adolenscent(console, g);
    }

    public static void drawChild(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(0,0,1200, 600);
        g.setColor(Color.yellow);
        g.fillOval(50, 30, 50, 50);
        g.setColor(Color.black);
        g.drawString(name + " is a 5-year-old and want to become a " +
                career + ".", 50, 100);
    }

    public static void adolenscent(Scanner console, Graphics g) {
        System.out.println("What do you want to say to " + name + "?");
        String feedback = console.nextLine();
        int score = judge(feedback);
        if (score == 0) {
            score = prompt(console, feedback);
        }
        int sentiment = 0;
        if (score > 0) {
            sentiment ++;
        }

        String story;
        if (sentiment == 0) {
            story = name + " decides to learn more about " + career +
                    " as a career despite what you said.";
        } else {
            story = "With your encouragement, " + name + " is more determinant to become a " +
            career + ".";
        }
        drawAdolenscent(g, story);

        adult(sentiment, console, g);
    }

    public static void drawAdolenscent(Graphics g, String story) {
        // g.clear();
        g.setColor(Color.blue);
        g.fillOval(50,150, 50, 50);
        g.setColor(Color.black);
        g.drawString(name + " has turned to 15 now.", 50, 220);
        g.drawString(story, 50, 240);
    }

    public static void adult(int sentiment, Scanner console, Graphics g) {

        System.out.println("Do you still think " + career + " is the right choice for " +
                name +"?");
        String feedback = console.nextLine();
        int score = judge(feedback);
        if (score == 0) {
            score = prompt(console, feedback);
        }
        if (score > 0) {
            sentiment ++;
        }

        String story;
        if (sentiment == 0) {
            story = "After thinking about what you have said, " + name +
                    " gave up his childhood dream and pursued something else.";
        } else if (sentiment == 1){
            story = "Although having some doubt about this choice, " + name +
                    " became a " + career + ".";
        } else {
            story = "Thanks to your encouragement, " + name +
                    " is now a confident " + career + ".";
        }
        drawAdult(g, story);

        senior(sentiment, console, g);
    }

    public static void drawAdult(Graphics g, String story) {
        g.setColor(Color.red);
        g.fillOval(50,290, 50, 50);
        g.setColor(Color.black);
        g.drawString(name + " is 30 now.", 50, 360);
        g.drawString(story, 50, 380);
    }

    public static void senior(int sentiment, Scanner console, Graphics g) {
        System.out.println("What's your opinion about " + career + " now?");
        String feedback = console.nextLine();
        int score = judge(feedback);
        if (score == 0) {
            score = prompt(console, feedback);
        }
        if (score > 0) {
            sentiment ++;
        }

        String story;
        if (sentiment == 0) {
            story = name + " never had the chance to try practice " + career +
                    " and is now retired. \nIn his/her free time, he/she still sometimes" +
                    " wonders what if he/she ignored your words and chose to be a " +
                    career + " in the young age.";
        } else if (sentiment == 1){
            story = "Although " + name + " did not become a " + career +
                    ", \nbut he/she started to do some related work just for fun" +
                    " in his/her late years and really enjoyed it.";
        } else if (sentiment == 2){
            story = name + " has been a decent " + career + ". " +
                    "In retrospect, he/she thinks your encouragements helped a lot to " +
                    "confirm his dream!";
        } else {
            story = name + " has achieved great accomplishment as a " + career +
                    " in his/her life. In retrospect, he/she is grateful to the encouragement" +
                    " brought by your kind words!";
        }

        drawSenior(g, story);


    }

    public static void drawSenior(Graphics g, String story) {
        g.setColor(Color.cyan);
        g.fillOval(50,430, 50, 50);
        g.setColor(Color.black);
        g.drawString(name + " is 60 now.", 50, 500);
        g.drawString(story, 50, 520);
        p.sleep(5000);
        g.setColor(Color.white);
        g.fillRect(0,0,1200, 600);
        String sum = "Are you on track of pursuing your dream, " + name + "?";
        g.setFont(Font.getFont("Helvetica"));
        g.setColor(Color.black);
        g.drawString(sum, 480, 300);
    }

    public static int prompt(Scanner console, String feedback) {

        while (judge(feedback) == 0) {
            System.out.println("Your opinion is too neutral, can you provide a more one-sided opinion?");
            feedback = console.nextLine();
        }
        return judge(feedback);
    }



    public static int judge(String input) {
        String[] split = input.split("\\s+");
        int p = 0;
        int n = 0;
        for (int i = 0; i < split.length; i ++) {
            if (plist.contains(split[i])) {
                p ++;
            } else if (nlist.contains(split[i])) {
                n ++;
            }
        }
        return p - n; // > 0 for positive, < 0 for negative, = 0 for neutral
    }
}