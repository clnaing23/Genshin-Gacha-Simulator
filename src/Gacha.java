// Made by Chris Naing. Based on the limited character banner from the game "Genshin Impact" made by miHoYo Co., Ltd
//Version 1.0
import java.lang.Math;
import java.util.Random;
import java.util.Scanner;
import java.util.*;

public class Gacha{
    //current pools for character banners. Will increase as the game updates
    private String[] fivePool = {"Diluc", "Mona", "Keqing", "Jean", "Qiqi" };
    private String[] fourPool = {"Rosaria", "Bennett", "Razor", "Yanfei", "Xinyan", "Diona", "Chongyun", "Xingqiu", "Xiangling", "Ningguang", "Beidou", "Sucrose", "Fischl", "Noelle", "Barbara", "Favonius Sword", "The Flute", "Sacrificial Sword", "Lion's Roar", "Favonius Greatsword", "The Bell", "Sacrificial Greatsword", "Rainslasher", "Dragon's Bane", "Favonius Lance", "Favonius Codex", "The Widsith", "Sacrificial Fragments", "Eye of Perception", "Favonius Warbow", "The Stringless", "Sacrificial Bow", "Rust"};;
    private String[] threePool = {"Cool Steel", "Harbinger of Dawn", "Skyrider Sword", "Ferrous Shadow", "Bloodtainted Greatsword", "Debate Club", "Black Tassel", "Magic Guide", "Thrilling Tales of Dragon Slayers", "Emerald Orb", "Raven Bow", "Sharpshooter's Oath", "Slingshot"};
    //initial pity numbers for gacha. Once it reaches a certain number, it will automatically give you a certain rarity
    private int pity5 = 0;
    private int pity4 = 0;
    //keeps track of number of rolls and primogems spent so far
    private int numRolls = 0;
    private int primos = 0;
    //stores pulled weapons/units in a binary tree
    private Node pulls;
    //Stack that holds all pulls for point system.
    private Stack<Node> pointSystem = new Stack<>();
    private int points =0;
    //base constructor
    public void Gacha(){}
    //constructor that allows for specific pity/progress
    public void Gacha(int fivePity, int fourPity, int number, int spentGems){
        pity5 = fivePity;
        pity4 = fourPity;
        numRolls = number;
        primos = spentGems;
    }
    //method that runs the gacha. Uses the limited five star and four star focus characters as arguments
    //summons() and all of the methods it calls runs in O(1) time.
    //rates and pity amounts are based on the game
    public String summons(String five, String four1, String four2, String four3){
        //updates primos and numRolls
        updateProgress();
        //used for gacha rates
        double roll = Math.random();
        //used to determine whether the focus/banner characters are pulled
        double flip = Math.random();
        //hard pity for banner character. Guaranteed banner unit or "five"
        if (getPity5() == 179){
            //resets pity
            pity5 = 0;
            pity4 = 0;
            //adds pull to binary tree. Also happens for other pulls.
            addPull(five, 5);
            //adds pull to stack. Also happens for other pulls.
            pointSystem.push(new Node(five, 5, 100));
            //prints pull. Also happens for other pulls.
            System.out.println(five);
            return five;
        }
        //four star pity
        else if (getPity4() == 9){
            //if five star is pulled
            if (roll < 0.006){
                //whether banner character is pulled
                if (flip >= 0.5){
                    pity5 = 0;
                    pity4 = 0;
                    addPull(five,5);
                    pointSystem.push(new Node(five, 5, 100));
                    System.out.println(five);
                    return five;
                } else{
                    pity5 = 90;
                    pity4 = 0;
                    //rolls for the five star pool
                    String hold = rollFivePool();
                    addPull(hold,5);
                    pointSystem.push(new Node(hold, 5, 50));
                    System.out.println(hold);
                    return hold;
                }
            } else{
                //reset four star pity
                pity4 = 0;
                pity5++;
                //fourRoll() rolls for four star,
                String hold= fourRoll(four1, four2, four3);
                addPull(hold, 4);
                pointSystem.push(new Node(hold, 4, 10));
                System.out.println(hold);
                return hold;
            }
        //non-pity rates, repeats earlier
        } else if (roll <0.006){
            if (flip >= 0.5){
                pity5 = 0;
                pity4 = 0;
                addPull(five,5);
                pointSystem.push(new Node(five, 5, 100));
                System.out.println(five);
                return five;
            } else{
                pity5 = 90;
                pity4 = 0;
                String hold = rollFivePool();
                addPull(hold,5);
                pointSystem.push(new Node(hold, 5, 50));
                System.out.println(hold);
                return hold;
            }
        } else if (roll < 0.057){
            pity4 = 0;
            pity5++;
            String hold = fourRoll(four1, four2, four3);
            addPull(hold,4);
            pointSystem.push(new Node(hold, 4, 10));
            System.out.println(hold);
            return hold;
        } else{
            //three star pull, adds to both pities
            pity4++;
            pity5++;
            //rolls within the three star pool
            String hold =  rollThreePool();
            addPull(hold,3);
            pointSystem.push(new Node(hold, 3, 0));
            System.out.println(hold);
            return hold;
        }
    }
    //getters for instance variables
    public int getPity5(){
        return pity5;
    }
    public int getPity4(){
        return pity4;
    }
    public String[] getFivePool(){
        return fivePool;
    }
    public String[] getFourPool(){
        return fivePool;
    }
    public String[] getThreePool(){
        return threePool;
    }
    public int getNumRolls(){
        return numRolls;
    }
    public int getPrimos(){
        return primos;
    }
    public Stack<Node> getPointSystems(){
        return pointSystem;
    }
    public int getPoints(){
        return points;
    }
    public Node getPulls(){
        return pulls;
    }
    //rolls for each pool
    public String rollFivePool(){
        int rand = new Random().nextInt(fivePool.length);
        return fivePool[rand];
        }
    public String rollFourPool(){
        int rand = new Random().nextInt(fourPool.length);
        return fourPool[rand];
    }
    public String rollThreePool(){
        int rand = new Random().nextInt(threePool.length);
        return threePool[rand];
    }
    public String fourRoll(String four1, String four2, String four3){
        double flip = Math.random();
        //whether or not it rolls the focus four star characters
        if (flip > 0.5){
            double fourRoll = Math.random();
            if (fourRoll <0.33){
               return four1;
            } else if (fourRoll <0.67){
                return four2;
            } else{
                return four3;
            }
        } else{
            return rollFourPool();
        }
    }
    //removes the focus four star characters from general four star pool in order to get accurate rates
    public void setFourPool(String four1, String four2, String four3){
        //new array for fourPool
        String[] temp = new String[fourPool.length-3];
        //keeps track of temp index, skips focus characters
        int placement = 0;
        for (int i = 0; i < fourPool.length;i++){
            if (fourPool[i].compareTo(four1) !=0 & fourPool[i].compareTo(four2) !=0 & fourPool[i].compareTo(four3) != 0){
                temp[placement++] = fourPool[i];
            }
        }
        fourPool = temp;
    }
    //setters for instance variables
    public void setFivePoll(String[] five){
        fivePool = five;
    }
    public void setFourPoll(String[] four){
        fourPool = four;
    }
    public void setPity5(int amount){
        pity5 = amount;
    }
    public void setPity4(int amount){
        pity4 = amount;
    }
    public void setFivePool(String[] pool){
        fivePool = pool;
    }
    public void setFourPool(String[] pool){
        fourPool = pool;
    }
    public void setThreePool(String[] pool){
        threePool = pool;
    }
    public void setProgress(int startRolls){
        numRolls = startRolls;
        primos = startRolls * 160;
    }
    //increments numRolls. Adds 160 to primos (each roll costs 160 summons)
    public void updateProgress(){
        numRolls++;
        primos += 160;
    }
    //rolls an certain amount of times
    //loops unroll for increased efficiency
    public void rollXTimes(int rolls, String five, String four1, String four2, String four3){
        if (rolls >= 5){
            for (int i = 0; i<rolls;i += 5){
                summons(five,four1,four2,four3);
                summons(five,four1,four2,four3);
                summons(five,four1,four2,four3);
                summons(five,four1,four2,four3);
                summons(five,four1,four2,four3);
            }
        }
        for (int i = 0; i < (rolls % 5); i++){
            summons(five,four1,four2,four3);
        }
    }
    //adds pull to binary tree iteratively
    public void addPull(String nm, int rare){
        //creates new node from name and rarity
        Node unit = new Node(nm,rare);
        if (pulls == null){
            pulls = unit;
        } else{
            //keeps track of current and parent nodes
            Node head = pulls;
            Node tail;
            while (true){
                tail = head;
                //adds to amount if unit is already present
                if (unit.getName().compareTo(head.getName())==0){
                    tail.addAmount();
                    return;
                    //down left branch
                } else if (unit.getName().compareTo(head.getName())<0){
                    head = head.getLeft();
                    if (head == null){
                        tail.setLeft(unit);
                        return;
                    }
                } else{
                    //down right branch
                    head = head.getRight();
                    if (head == null){
                        tail.setRight(unit);
                        return;
                    }
                }
            }
        }
    }
    //find for specific node
    public Node findNode(String nm){
        Node head = pulls;
        while (head.getName().compareTo(nm) ==0){
            if (head == null){
                return null;
            }
            if (head.getName().compareTo(nm) <0){
                head = head.getLeft();
            } else{
                head = head.getRight();
            }
        }
        return head;
    }
    //uses recursion to print binary tree. Traverses tree in an InOrder pattern
    public void printPulls(Node root){
        if (root != null){
            Node holder = root.getLeft();
            printPulls(holder);
            root.printData(root);
            Node holder2 = root.getRight();
            printPulls(holder2);
        }
    }
    //pops every node in stack and adds points from each node
    public void redeemAll(){
        while (!pointSystem.empty()){
            Node popped = pointSystem.pop();
            points += popped.getPoints();
        }
    }
    //pops a specific node in stack and adds points from each node
    public void redeemAmount(int popAmount){
        int stop = 0;
        while (stop < popAmount){
            if (pointSystem.empty()){
                break;
            }
            Node popped = pointSystem.pop();
            points += popped.getPoints();
            stop++;
        }
    }
    public static void main(String[] args){
        Gacha session = new Gacha();
        Scanner sc = new Scanner(System.in);
        //add arguments with scanner
        System.out.println("Enter five star focus unit: ");
        String bannerUnit = sc.nextLine();
        System.out.println("Enter first four start focus unit: ");
        String banner1 = sc.nextLine();
        System.out.println("Enter second four start focus unit: ");
        String banner2 = sc.nextLine();
        System.out.println("Enter third four start focus unit: ");
        String banner3 = sc.nextLine();
        //sets fourPool from entered arguments
        session.setFourPool(banner1, banner2, banner3);
        System.out.println("Enter the amount of rolls you want to perform or press 0 to exit");
        while (sc.nextInt()!= 0) {
            try{
                if (sc.hasNextInt()){
                    //holds sc.nextInt()
                    int temp = sc.nextInt();
                    //calls rollXtimes()
                    if (temp > 0){
                        session.rollXTimes(temp,bannerUnit, banner1, banner2,banner3);
                        //print pulls
                    } else if (temp == -1){
                        session.printPulls(session.getPulls());
                        //search unit
                    } else if (temp == -2){
                        Scanner sc2 = new Scanner(System.in);
                        System.out.println("Enter Weapon/Unit you want to search: ");
                        String searched = sc2.next();
                        System.out.println(session.findNode(searched));
                        //breaks loop
                    } else if (temp == 0){
                        break;
                    } else if(temp == -3){
                        //to redeem points/pop stats
                        Scanner scPS = new Scanner(System.in);
                        System.out.println("Enter how many of your last summons you want to pop or -1 to redeem all");
                        int redeem = scPS.nextInt();
                        if (redeem > -1){
                            session.redeemAmount(redeem);
                        } else if (redeem == -1){
                            session.redeemAll();
                        } else{
                            System.out.println("Invalid Input");
                        }
                    } else{
                        System.out.println("Invalid Input");
                    }
                }
            } catch (Exception e){
                System.out.println("Error");
            } finally {
                //Shows current stats
                System.out.println("\nProgress: \n");
                System.out.println("Number of Rolls: "+ session.getNumRolls() + "\n");
                System.out.println("Number of Primogems spent so far: " + session.getPrimos()+ "\n");
                //if stack is empty
                if (session.getPointSystems().empty()){
                    System.out.println("No Unredeemed Last Summon\n");
                    //shows last unredeemed unit
                } else{
                    System.out.println("Last Unredeemed Summon: "+ session.getPointSystems().peek().getName());
                    System.out.println(session.getPointSystems().peek().getRarity() + " Star\n");
                }
                System.out.println("Current Points : " + session.getPoints()+"\n");
                System.out.println("Enter number again or 0 to exit, -1 for print or -2 for search, -3 to redeem points: ");
            }
        }
        System.out.println("Thank you for playing!");
    }
 }